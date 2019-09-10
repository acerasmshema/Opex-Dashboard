import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';
import { StatusService } from 'src/app/shared/service/status.service';
import { MaintenanceDays } from './maintenance-days';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { AnnotationDialog } from './annotation-dialog';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;

    constructor(private apiCallService: ApiCallService,
        private statusService: StatusService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }

    createUserForm(): UserDetail {
        let user = new UserDetail();
        user.showDialog = true;

        let millRoles: MillRole[] = [];
        let millRole = new MillRole();
        millRole.mills = this.statusService.common.mills;
        millRole.userRoles = this.statusService.common.userRoles;
        millRoles.push(millRole);

        user.millRoles = millRoles;
        return user;
    }

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


}