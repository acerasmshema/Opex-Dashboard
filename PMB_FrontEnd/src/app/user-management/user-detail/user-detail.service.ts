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
import { MillRole } from './mill-role.model';

@Injectable()
export class UserDetailService {

    userListUrl = API_URL.user_api_URLs.ALL_USER;
    updateUserURL = API_URL.user_api_URLs.UPDATE_USER;

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
                    millRoleId: new FormControl(millRole.millRoleId),
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
            status: new FormControl(userDetail.active),
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

    updateUser(userDetailForm: FormGroup, userDetail: UserDetail) {
        this.statusService.spinnerSubject.next(true);

        let millRoles: MillRole[] = [];
        let millRoleList: any = userDetailForm.controls.millRoles;
        millRoleList.controls.forEach(control => {
            let millRole = new MillRole();
            millRole.millRoleId = control.value.millRoleId.value;
            millRole.selectedMill = control.value.selectedMill.value;
            millRole.selectedUserRole = control.value.selectedUserRole.value;
            millRoles.push(millRole);
        });

        userDetail.firstName = userDetailForm.controls.firstName.value;
        userDetail.lastName = userDetailForm.controls.lastName.value;
        userDetail.email = userDetailForm.controls.email.value;
        userDetail.phone = userDetailForm.controls.phone.value;
        userDetail.address = userDetailForm.controls.address.value;
        userDetail.country = userDetailForm.controls.country.value;
        userDetail.millRoles = millRoles;
        userDetail.department = userDetailForm.controls.department.value;
        userDetail.updatedBy = this.statusService.common.userDetail.username;
        userDetail.active = userDetailForm.controls.status.value;

        this.apiCallService.callPutAPIwithData(this.updateUserURL, userDetail).
            subscribe(
                (response: any) => {
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
                },
                (error: any) => {
                    this.statusService.spinnerSubject.next(false);
                    console.log("Error")
                }
            );
    }

}