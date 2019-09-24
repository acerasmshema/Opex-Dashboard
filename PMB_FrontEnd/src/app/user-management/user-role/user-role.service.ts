import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRole } from './user-role.model';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { MessageService } from 'primeng/primeng';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { FormGroup } from '@angular/forms';

@Injectable()
export class UserRoleService {

    addUserRoleURL = API_URL.user_api_URLs.ADD_USER_ROLE;
    updateRoleURL = API_URL.user_api_URLs.UPDATE_USER_ROLE;

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private commonService: CommonService,
        private apiCallService: ApiCallService) { }

    getUserRoles(userRoles: UserRole[]) {
        this.commonService.getAllUserRole(false)
            .subscribe(
                (roleList: UserRole[]) => {
                    userRoles.push(...roleList);
                    this.statusService.common.userRoles = roleList;
                },
                (error: any) => {
                    console.log("error in user role");
                }
            );
    }

    addUserRole(userRoleForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);
        let userRole = new UserRole();
        userRole.roleName = userRoleForm.controls.roleName.value;
        userRole.active = userRoleForm.controls.active.value;
        userRole.description = userRoleForm.controls.description.value;
        userRole.createdBy = this.statusService.common.userDetail.username;
        userRole.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callAPIwithData(this.addUserRoleURL, userRole).
            subscribe(
                response => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshUserList.next(true);
                    userRoleForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                },
                error => {
                    this.statusService.spinnerSubject.next(false);
                    if (error.error.status === '1012') {
                        userRoleForm.controls.active.setValue(true);
                        userRoleForm.controls.userExistError.setValue(userRole.roleName + ' ' + CommonMessage.ERROR_CODES[1012]);
                    } else {
                        this.messageService.add({ severity: "error", summary: '', detail: CommonMessage.ERROR.SERVER_ERROR });
                    }
                }
            );
    }

    updateUserRole(userRoleForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let userRole = new UserRole();
        userRole.userRoleId = userRoleForm.controls.userRoleId.value;
        userRole.roleName = userRoleForm.controls.roleName.value;
        userRole.active = userRoleForm.controls.active.value;
        userRole.description = userRoleForm.controls.description.value;
        userRole.createdBy = userRoleForm.controls.createdBy.value;
        userRole.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callPutAPIwithData(this.updateRoleURL, userRole).
            subscribe(
                response => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    this.statusService.refreshUserList.next(true);
                    userRoleForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                },
                error => {
                    this.statusService.spinnerSubject.next(false);
                    if (error.error.status === '1012') {
                        userRoleForm.controls.active.setValue(true);
                        userRoleForm.controls.userExistError.setValue(userRole.roleName + ' ' + CommonMessage.ERROR_CODES[1012]);
                    } else {
                        this.messageService.add({ severity: "error", summary: '', detail: CommonMessage.ERROR.SERVER_ERROR });
                    }
                }
            );
    }
} 
