import { Injectable } from "@angular/core";
import { MaintenanceDays } from 'src/app/core/dialog/maintenance-days';
import { CommonMessage } from '../../constant/Common-Message';
import { AnnotationDialog } from 'src/app/core/dialog/annotation-dialog';
import { SearchKpiData } from '../../models/search-kpi-data';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { API_URL } from '../../constant/API_URLs';
import { FormControl } from '@angular/forms';
import { ApiCallService } from '../api/api-call.service';
import { Observable } from 'rxjs';

@Injectable()
export class ValidationService {

    validateEmailURL = API_URL.user_api_URLs.VALIDATE_EMAIL;

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
            this.apiCallService.callGetAPIwithData(API_URL.user_api_URLs.VALIDATE_EMAIL, requestData)
                .subscribe(
                    response => resolve({ 'emailExit': false }),
                    error => resolve({ 'emailExit': true })
                )
        });
    }

}