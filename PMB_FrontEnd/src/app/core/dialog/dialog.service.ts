import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { FormControl, Validators, FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';
import { MessageService } from 'primeng/primeng';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { Observable } from 'rxjs';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;
    userURL = API_URL.user_api_URLs.CREATE_USER;

    constructor(private apiCallService: ApiCallService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private messageService: MessageService,
        private validationService: ValidationService,
        private statusService: StatusService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }

    createUserForm(): FormGroup {
        let millRoles = this.formBuilder.array([
            new FormControl({
                userRoles: this.formBuilder.array([]),
                mills: this.formBuilder.array([]),
                selectedMill: new FormControl(''),
                selectedUserRole: new FormControl(''),
            })
        ]);

        let userDetailForm = this.formBuilder.group({
            show: new FormControl(true),
            totalMills: new FormControl(1),
            firstName: new FormControl("", [Validators.required]),
            lastName: new FormControl("", [Validators.required]),
            address: new FormControl(""),
            username: new FormControl("", [Validators.required]),
            password: new FormControl("", [Validators.required]),
            confirmPassword: new FormControl("", [Validators.required]),
            phone: new FormControl(""),
            selectedCountry: new FormControl(''),
            countryList: this.formBuilder.array([]),
            selectedDepartment: new FormControl('', [Validators.required]),
            departmentList: this.formBuilder.array([]),
            email: new FormControl("", [Validators.required, Validators.email]),
            millRoles: millRoles,
        },
            { validator: this.validationService.mustMatchPassword('password', 'confirmPassword') }
        );

        this.commonService.getAllCountry(userDetailForm);
        this.commonService.getAllDepartment(userDetailForm);
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm, true);

        return userDetailForm;
    }

    addMillRole(userDetailForm: FormGroup) {
        let millRoles: any = userDetailForm.controls.millRoles;
        millRoles.controls.push(
            new FormControl({
                userRoles: this.formBuilder.array([]),
                mills: this.formBuilder.array([]),
                selectedMill: new FormControl(''),
                selectedUserRole: new FormControl(''),
            })
        );
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm, true);
    }

    getAllMills(userDetailForm: FormGroup) {
        if (this.statusService.common.mills.length === 0) {
            this.commonService.getAllMills().
                subscribe(
                    (mills: MillDetail[]) => {
                        const millRoles: any = userDetailForm.controls.millRoles;
                        const millControl = millRoles.controls[0].value.mills.controls;
                        mills.forEach(mill => {
                            millControl.push(new FormControl(mill));
                        });
                        this.statusService.common.mills = mills;
                        userDetailForm.controls.totalMills.setValue(mills.length);
                    },
                    (error: any) => {
                        this.statusService.spinnerSubject.next(false);
                        if (error.status == "0") {
                            alert(CommonMessage.ERROR.SERVER_ERROR)
                        }
                    });
        }
        else {
            const millList = this.statusService.common.mills;
            const millRoles: any = userDetailForm.controls.millRoles;
            const millControl: any = millRoles.controls[millRoles.length - 1].value.mills.controls;
            millList.forEach(mill => {
                millControl.push(new FormControl(mill));
            });
            userDetailForm.controls.totalMills.setValue(millList.length);
        }
    }

    getAllUserRole(userDetailForm: FormGroup, activeUserRoles) {
        if (this.statusService.common.activeUserRoles.length === 0) {
            this.commonService.getAllUserRole(activeUserRoles).
                subscribe(
                    (roleList: UserRole[]) => {
                        const millRoles: any = userDetailForm.controls.millRoles;
                        const userRoleControl = millRoles.controls[0].value.userRoles.controls;
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
            const millRoles: any = userDetailForm.controls.millRoles;
            const userRoleControl = millRoles.controls[millRoles.length - 1].value.userRoles.controls;
            roleList.forEach(userRole => {
                userRoleControl.push(new FormControl(userRole));
            });
        }
    }

    createNewUser(userDetailForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let millRoles: MillRole[] = [];
        let millRoleList: any = userDetailForm.controls.millRoles;
        millRoleList.controls.forEach(control => {
            let millRole = new MillRole();
            millRole.selectedMill = control.value.selectedMill.value;
            millRole.selectedUserRole = control.value.selectedUserRole.value;
            millRoles.push(millRole);
        });

        let userDetail = new UserDetail();
        userDetail.username = userDetailForm.controls.username.value;
        userDetail.password = userDetailForm.controls.password.value;
        userDetail.firstName = userDetailForm.controls.firstName.value;
        userDetail.lastName = userDetailForm.controls.lastName.value;
        userDetail.email = userDetailForm.controls.email.value;
        userDetail.phone = userDetailForm.controls.phone.value;
        userDetail.address = userDetailForm.controls.address.value;
        userDetail.country = userDetailForm.controls.selectedCountry.value;
        userDetail.department = userDetailForm.controls.selectedDepartment.value;
        userDetail.millRoles = millRoles;
        userDetail.createdBy = this.statusService.common.userDetail.username;
        userDetail.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callAPIwithData(this.userURL, userDetail).
            subscribe(
                (response: any) => {
                    this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshUserList.next(true);
                    userDetailForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                },
                (error: any) => {
                    console.log("Error");
                    this.statusService.spinnerSubject.next(false);
                }
            );
    }

}