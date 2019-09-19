import { Injectable } from "@angular/core";
import { MaintenanceDays } from 'src/app/core/dialog/maintenance-days';
import { CommonMessage } from '../../constant/Common-Message';
import { AnnotationDialog } from 'src/app/core/dialog/annotation-dialog';
import { SearchKpiData } from '../../models/search-kpi-data';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { API_URL } from '../../constant/API_URLs';
import { FormControl, FormGroup } from '@angular/forms';
import { ApiCallService } from '../api/api-call.service';
import { Observable } from 'rxjs';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';

@Injectable()
export class ValidationService {

    constructor(private apiCallService: ApiCallService) { }

    targetDaysValidation(maintenanceDays: MaintenanceDays) {
        if (maintenanceDays.targetDays <= 0) {
            maintenanceDays.targetDaysErrorMessage = CommonMessage.ERROR.TARGET_DAYS_GREATER_THAN_ZERO;
            maintenanceDays.isTargetDaysError = true;
        }
        else {
            maintenanceDays.isTargetDaysError = false;
        }
    }

    remarksValidation(maintenanceDays: MaintenanceDays) {
        if (maintenanceDays.textAreaValue == undefined || maintenanceDays.textAreaValue == null || maintenanceDays.textAreaValue === '') {
            maintenanceDays.remarksErrorMessage = CommonMessage.ERROR.REMARKS_ERROR;
            maintenanceDays.isRemarksError = true;
        }
        else {
            maintenanceDays.isRemarksError = false;
        }
    }

    dateValidation(maintenanceDays: MaintenanceDays) {
        const datePicker: any = document.getElementById("daterangepicker_input");
        if (datePicker !== null && datePicker.value !== "" && maintenanceDays.isDateError) {
            maintenanceDays.isDateError = false;
        }
    }

    processLineValidation(annotationDialog: AnnotationDialog) {
        if (annotationDialog.annotationProcessLines.length === 0) {
            annotationDialog.isProcessLineError = true;
            annotationDialog.processLineErrorMessage = CommonMessage.ERROR.PROCESS_LINE_VALIDATION;
        }
        else {
            annotationDialog.isProcessLineError = false;
        }
    }

    descriptionValidation(annotationDialog: AnnotationDialog) {
        if (annotationDialog.annotationDescription == undefined || annotationDialog.annotationDescription == null || annotationDialog.annotationDescription === '') {
            annotationDialog.descriptionErrorMessage = CommonMessage.ERROR.ADD_DESCRIPTION_VALIDATION;
            annotationDialog.isDescriptionError = true;
        }
        else {
            annotationDialog.isDescriptionError = false;
        }
    }

    millValidation(searchKpiData: SearchKpiData, sidebarForm: SidebarForm) {
        let mills = searchKpiData.mills;
        if (mills !== undefined) {
            if (mills.length < 2 && !sidebarForm.millsError) {
                sidebarForm.millsError = true;
            } else {
                sidebarForm.millsError = false;
            }
        }
    }

    sidebarDateValidation(sidebarForm: SidebarForm) {
        const datePicker: any = document.getElementById("daterangepicker_input");
        if (datePicker !== null && datePicker.value !== "" && sidebarForm.dateError) {
            sidebarForm.dateError = false;
        }
    }

    forbiddenEmail(control: FormControl): Promise<any> | Observable<any> {
        let requestData = {
            email: control.value
        }
        return new Promise(resolve => {
            if (control.parent !== undefined) {
                let userControl: any = control.parent.controls;
                if (userControl.validateEmail.value.toLowerCase() !== control.value.toLowerCase()) {
                    this.apiCallService.callGetAPIwithData(API_URL.user_api_URLs.VALIDATE_EMAIL, requestData)
                        .subscribe(
                            response => resolve(null),
                            error => resolve({ 'emailExit': true })
                        )
                }
                else {
                    resolve(null);
                }
            }
            else {
                resolve(null);
            }
        });
    }

    forbiddenUsername(control: FormControl): Promise<any> | Observable<any> {
        let requestData = {
            username: control.value
        }
        return new Promise(resolve => {
            this.apiCallService.callGetAPIwithData(API_URL.user_api_URLs.VALIDATE_USERNAME, requestData)
                .subscribe(
                    response => resolve(null),
                    error => resolve({ 'usernameExit': true })
                )
        });
    }

    forbiddenUserRole(control: FormControl): Promise<any> | Observable<any> {
        let requestData = {
            roleName: control.value
        }
        return new Promise(resolve => {
            if (control.parent !== undefined) {
                let roleControl: any = control.parent.controls;
                if (roleControl.validateRole.value.toLowerCase() !== control.value.toLowerCase()) {
                    this.apiCallService.callGetAPIwithData(API_URL.user_api_URLs.VALIDATE_USERROLE, requestData)
                        .subscribe(
                            response => resolve(null),
                            error => resolve({ 'userRoleExit': true })
                        )
                }
                else {
                    resolve(null);
                }
            }
            else {
                resolve(null);
            }
        });
    }

    mustMatchPassword(controlName: string, matchingControlName: string) {
        return (formGroup: FormGroup) => {
            const control = formGroup.controls[controlName];
            const matchingControl = formGroup.controls[matchingControlName];

            if (matchingControl.errors && !matchingControl.errors.mustMatch) {
                return;
            }

            if (control.value !== matchingControl.value) {
                matchingControl.setErrors({ mustMatch: true });
            } else {
                matchingControl.setErrors(null);
            }
        }
    }

    newPasswordValidation(controlName: string, matchingControlName: string) {
        return (formGroup: FormGroup) => {
            const control = formGroup.controls[controlName];
            const matchingControl = formGroup.controls[matchingControlName];

            if (matchingControl.errors && !matchingControl.errors.mustMatch) {
                return;
            }

            if (control.value === matchingControl.value) {
                matchingControl.setErrors({ notMatch: true });
            } else {
                matchingControl.setErrors(null);
            }
        }
    }

    valdiateUserMillRole(userDetailForm: FormGroup): boolean {
        let inValid = false;

        let millRoles: any = userDetailForm.controls.millRoles;
        let millControls = millRoles.controls;

        if (millControls[millControls.length - 1].value.selectedMill.value === '') {
            millControls[millControls.length - 1].value.millError.setValue("1");
            inValid = true;
        }
        if (millControls[millControls.length - 1].value.selectedUserRole.value === '') {
            millControls[millControls.length - 1].value.roleError.setValue("1");
            inValid = true;
        }

        return inValid;
    }
}