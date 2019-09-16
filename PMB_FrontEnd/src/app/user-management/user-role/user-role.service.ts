import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRole } from './user-role.model';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { MessageService } from 'primeng/primeng';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Injectable()
export class UserRoleService {

    addUserRole = API_URL.user_api_URLs.ADD_USER_ROLE;
    updateRole = API_URL.user_api_URLs.UPDATE_USER_ROLE;

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private commonService: CommonService,
        private apiCallService: ApiCallService) { }

    getUserRoles(userRoles: UserRole[]) {
        if (this.statusService.common.userRoles.length === 0) {  
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
        else {
            userRoles.push(...this.statusService.common.userRoles);
        }
    }

    createUserRole(userRoles: UserRole[]) {
        let userRole = new UserRole();
        userRole.active = true;
        userRole.isEnable = true;
        userRoles.push(userRole);

        userRoles = [];
        this.getUserRoles(userRoles);
    }

    saveUserRole(userRole: UserRole, roles: UserRole[]) {
        userRole.isEnable = false;
        userRole.createdBy = this.statusService.common.userDetail.username;
        userRole.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callAPIwithData(this.addUserRole, userRole).
            subscribe(
                response => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.common.userRoles = [];
                    roles = [];
                    this.getUserRoles(roles);
                },
                error => {
                    this.messageService.add({ severity: "error", summary: '', detail: CommonMessage.ERROR.SERVER_ERROR });
                }
            );
    }

    updateUserRole(userRole: UserRole, roles: UserRole[]) {
        userRole.isEnable = false;
        userRole.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callPutAPIwithData(this.updateRole, userRole).
            subscribe(
                response => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    this.statusService.common.userRoles = [];
                    roles = [];
                    this.getUserRoles(roles);
                },
                error => {
                    this.messageService.add({ severity: "error", summary: '', detail: CommonMessage.ERROR.SERVER_ERROR });
                }
            );
    }
} 
