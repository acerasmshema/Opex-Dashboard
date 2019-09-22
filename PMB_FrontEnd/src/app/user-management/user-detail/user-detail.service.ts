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
import { ValidationService } from 'src/app/shared/service/validation/validation.service';

@Injectable()
export class UserDetailService {

    userListUrl = API_URL.user_api_URLs.ALL_USER;
    updateUserURL = API_URL.user_api_URLs.UPDATE_USER;

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private validationService: ValidationService,
        private apiCallService: ApiCallService) { }

    getUserDetailList(userList: UserDetail[]) {
        let millId = this.statusService.common.selectedMill.millId;
        const requestData = {
            millId: millId
        }

        this.apiCallService.callGetAPIwithData(this.userListUrl, requestData).
            subscribe(
                (users: UserDetail[]) => {
                    users.forEach(user => {
                        const millRoles = user.millRoles;
                        for (let index = 0; index < millRoles.length; index++) {
                            const millRole = millRoles[index];
                            if (millRole.selectedMill.millId === millId) {
                                user.millRoleSortName = millRole.selectedUserRole.roleName;
                            }
                        }
                    });
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
                    userRoles: this.formBuilder.array([]),
                    mills: this.formBuilder.array([]),
                    millError: new FormControl(''),
                    roleError: new FormControl(''),
                })
            )
        });

        let userDetailForm = this.formBuilder.group({
            firstName: new FormControl(userDetail.firstName, [Validators.required]),
            lastName: new FormControl(userDetail.lastName, [Validators.required]),
            validateEmail: new FormControl(userDetail.email),
            email: new FormControl(userDetail.email, { validators: [Validators.required, Validators.email], asyncValidators: [this.validationService.forbiddenEmail.bind(this)], updateOn: 'blur' }),
            phone: new FormControl(userDetail.phone),
            address: new FormControl(userDetail.address),
            active: new FormControl(userDetail.active),
            country: new FormControl(userDetail.country),
            countryList: this.formBuilder.array([]),
            department: new FormControl(userDetail.department),
            departmentList: this.formBuilder.array([]),
            disableSelect: new FormControl(true),
            totalMills: new FormControl(1),
            millRoles: millRoles
        });

        userDetailForm.controls.email.valueChanges.
            subscribe((event) => {
                userDetailForm.get('email').setValue(event.toLowerCase(), { emitEvent: false });
            });

        this.commonService.getAllCountry(userDetailForm);
        this.commonService.getAllDepartment(userDetailForm);
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm);

        return userDetailForm;
    }

    addMillRole(userDetailForm: FormGroup) {
        let millRoles: any = userDetailForm.controls.millRoles;
        let millControls = millRoles.controls;
        millControls.push(
            new FormControl({
                userRoles: this.formBuilder.array([]),
                mills: this.formBuilder.array([]),
                selectedMill: new FormControl(''),
                selectedUserRole: new FormControl(''),
                millError: new FormControl(''),
                roleError: new FormControl(''),
            })
        );
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm);
    }

    getAllMills(userDetailForm: FormGroup) {
        if (this.statusService.common.mills.length === 0) {
            this.commonService.getAllMills().
                subscribe(
                    (mills: MillDetail[]) => {
                        const millRoles: any = userDetailForm.controls.millRoles;
                        const millControls = millRoles.controls;
                        millControls.forEach(millControl => {
                            let controls = millControl.value.mills.controls;
                            mills.forEach(mill => {
                                controls.push(new FormControl(mill));
                            });
                        });

                        this.statusService.common.mills = mills;
                        userDetailForm.controls.totalMills.setValue(mills.length);
                    },
                    (error: any) => {
                        if (error.status == "0") {
                            alert(CommonMessage.ERROR.SERVER_ERROR)
                        }
                    });
        }
        else {
            const millList = this.statusService.common.mills;
            const millRoles: any = userDetailForm.controls.millRoles;
            const millControls = millRoles.controls;
            millControls.forEach(millControl => {
                let controls = millControl.value.mills.controls;
                millList.forEach(mill => {
                    controls.push(new FormControl(mill));
                });
            });
            userDetailForm.controls.totalMills.setValue(millList.length);
        }
    }

    getAllUserRole(userDetailForm: FormGroup) {
        this.commonService.getAllUserRole(true).
            subscribe(
                (roleList: UserRole[]) => {
                    const millRoles: any = userDetailForm.controls.millRoles;
                    const userRoleControls = millRoles.controls;
                    userRoleControls.forEach(userRoleControl => {
                        let controls = userRoleControl.value.userRoles.controls;
                        roleList.forEach(userRole => {
                            controls.push(new FormControl(userRole));
                        });
                    });
                    this.statusService.common.activeUserRoles = roleList;
                },
                (error: any) => {
                    console.log("error in user role");
                }
            );
    }

    updateUser(userDetailForm: FormGroup, users: UserDetail[], userId: string) {
        if (!this.validationService.valdiateUserMillRole(userDetailForm)) {
            this.statusService.spinnerSubject.next(true);

            let millRoles: MillRole[] = [];
            let millRoleList: any = userDetailForm.controls.millRoles;
            millRoleList.controls.forEach(control => {
                let millRole = new MillRole();
                millRole.millRoleId = (control.value.millRoleId != undefined) ? control.value.millRoleId.value : null;
                millRole.selectedMill = control.value.selectedMill.value;
                millRole.selectedUserRole = control.value.selectedUserRole.value;
                millRoles.push(millRole);
            });

            const userDetail = users.find((user) => user.userId === userId)
            userDetail.firstName = userDetailForm.controls.firstName.value;
            userDetail.lastName = userDetailForm.controls.lastName.value;
            userDetail.email = userDetailForm.controls.email.value;
            userDetail.phone = userDetailForm.controls.phone.value;
            userDetail.address = userDetailForm.controls.address.value;
            userDetail.country = userDetailForm.controls.country.value;
            userDetail.millRoles = millRoles;
            userDetail.department = userDetailForm.controls.department.value;
            userDetail.updatedBy = this.statusService.common.userDetail.username;
            userDetail.active = userDetailForm.controls.active.value;

            this.apiCallService.callPutAPIwithData(this.updateUserURL, userDetail).
                subscribe(
                    (response: any) => {
                        userDetailForm.disable();
                        userDetailForm.controls.disableSelect.setValue(true);
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

}