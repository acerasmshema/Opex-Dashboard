import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { FormControl, Validators, FormBuilder, FormGroup } from '@angular/forms';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;

    constructor(private apiCallService: ApiCallService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
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
                mills: this.formBuilder.array([])
            })
        ]);

        let userDetailForm = this.formBuilder.group({
            show: new FormControl(true),
            firstName: new FormControl("", [Validators.required, Validators.max(10)]),
            lastName: new FormControl(""),
            address: new FormControl(""),
            username: new FormControl(""),
            password: new FormControl(""),
            confirmPassword: new FormControl(""),
            phone: new FormControl(""),
            countryList: this.formBuilder.array([]),
            departmentList: this.formBuilder.array([]),
            email: ['', [Validators.required, Validators.email]],
            millRoles: millRoles,
        });

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
                mills: this.formBuilder.array([])
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

}