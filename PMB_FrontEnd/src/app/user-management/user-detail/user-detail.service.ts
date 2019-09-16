import { Injectable } from "@angular/core";
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { UserDetail } from './user-detail.model';
import { MessageService } from 'primeng/primeng';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { UserRole } from '../user-role/user-role.model';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Injectable()
export class UserDetailService {

    userListUrl = API_URL.user_api_URLs.ALL_USER;

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private apiCallService: ApiCallService) { }

    getUserDetailList(userList: UserDetail[]) {
        const requestData = {
            millId: this.statusService.common.selectedMill.millId
        }

        this.apiCallService.callGetAPIwithData(this.userListUrl, requestData).
            subscribe(
                (users: UserDetail[]) => {
                    userList.push(...users);
                },
                (error: any) => {
                    console.log("Error in UserDetail");
                });

    }

    createUserDetailForm(userDetail: UserDetail): FormGroup {
        let millRoles = this.formBuilder.array([]);
        userDetail.millRoles.forEach(millRole => {
            millRoles.controls.push(
                new FormControl({
                    selectedMill: new FormControl(millRole.selectedMill),
                    selectedUserRole: new FormControl(millRole.selectedUserRole),
                })
            )
        });

        let userDetailForm = this.formBuilder.group({
            firstName: new FormControl(userDetail.firstName, [Validators.required]),
            lastName: new FormControl(userDetail.lastName, [Validators.required]),
            email: new FormControl(userDetail.email, [Validators.required, Validators.email]),
            phone: new FormControl(userDetail.phone),
            address: new FormControl(userDetail.address),
            active: new FormControl(userDetail.active),
            country: new FormControl(userDetail.country),
            countryList: this.formBuilder.array([]),
            department: new FormControl(userDetail.department),
            departmentList: this.formBuilder.array([]),
            userRoles: this.formBuilder.array([]),
            mills: this.formBuilder.array([]),
            millRoles: millRoles
        });

        this.commonService.getAllCountry(userDetailForm);
        this.commonService.getAllDepartment(userDetailForm);
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm);

        return userDetailForm;
    }

    addMillRole(userDetailForm: FormGroup) {
        let millRoles: any = userDetailForm.controls.millRoles;
        millRoles.controls.push(
            new FormControl({
                selectedMill: new FormControl(null),
                selectedUserRole: new FormControl(null),
            })
        );
    }
    
    updateUserDetail(user: UserDetail, users: UserDetail[]) {
        // const userDetail = users.find((user) => user.userId === user.userId)
        // userDetail.isReadOnly = false;
        // this.messageService.add({ severity: "success", summary: '', detail: "Updated Successfully" });
    }

    getAllMills(userDetailForm: FormGroup) {
        if (this.statusService.common.mills.length === 0) {
            this.commonService.getAllMills().
                subscribe(
                    (mills: MillDetail[]) => {
                        const millList: any = userDetailForm.controls.mills;
                        const millControl = millList.controls;
                        mills.forEach(mill => {
                            millControl.push(new FormControl(mill));
                        });
                        this.statusService.common.mills = mills;
                    },
                    (error: any) => {
                        if (error.status == "0") {
                            alert(CommonMessage.ERROR.SERVER_ERROR)
                        }
                    });
        }
        else {
            const millList = this.statusService.common.mills;
            const mills: any = userDetailForm.controls.mills;
            const millControl = mills.controls;
            millList.forEach(mill => {
                millControl.push(new FormControl(mill));
            });
        }
    }

    getAllUserRole(userDetailForm: FormGroup) {
        if (this.statusService.common.activeUserRoles.length === 0) {
            this.commonService.getAllUserRole(true).
                subscribe(
                    (roleList: UserRole[]) => {
                        const userRoles: any = userDetailForm.controls.userRoles;
                        const userRoleControl = userRoles.controls;
                        roleList.forEach(userRole => {
                            userRoleControl.push(new FormControl(userRole));
                        });
                        this.statusService.common.activeUserRoles = roleList;
                    },
                    (error: any) => {
                        console.log("error in user role");
                    }
                );
        }
        else {
            const roleList = this.statusService.common.activeUserRoles;
            const userRoles: any = userDetailForm.controls.userRoles;
            const userRoleControl = userRoles.controls;
            roleList.forEach(userRole => {
                userRoleControl.push(new FormControl(userRole));
            });
        }
    }

}