import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ProcessLineThreshold } from './process-line-threshold';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { DatePipe } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/primeng';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Injectable()
export class ProcessLineTargetService {

    processLineTargetUrl = API_URL.threshold_api_URLs.PROCESS_LINE_TARGET;
    updateProcessLineTargetUrl = API_URL.threshold_api_URLs.UPDATE_PROCESS_LINE_TARGET;
    addProcessLineTargetUrl = API_URL.threshold_api_URLs.CREATE_PROCESS_LINE_TARGET;

    constructor(private statusService: StatusService,
        private commonService: CommonService,
        private datePipe: DatePipe,
        private messageService: MessageService,
        private apiCallService: ApiCallService) { }

    getProcessLineThresholds(processLineThresholds: ProcessLineThreshold[]) {
        this.statusService.spinnerSubject.next(true);
        let requestData = {
            millId: this.statusService.common.selectedMill.millId,
            kpiId: "1"
        };

        this.apiCallService.callGetAPIwithData(this.processLineTargetUrl, requestData)
            .subscribe(
                (processLineThresholdList: ProcessLineThreshold[]) => {
                    processLineThresholdList.forEach(processLineThreshold => {
                        if (!processLineThreshold.isDefault)
                            processLineThreshold.buTypeSortName = processLineThreshold.buType.buTypeName;
                        else
                            processLineThreshold.buTypeSortName = processLineThreshold.buType.buTypeName + ' (DEFAULT)';

                        processLineThreshold.processLineSortName = processLineThreshold.processLine.processLineCode;
                        processLineThresholds.push(processLineThreshold);
                    });
                    this.statusService.spinnerSubject.next(false); 
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }

    addProcessLineTarget(processLineThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let processLineThreshold = new ProcessLineThreshold();
        processLineThreshold.buType = processLineThresholdForm.controls.buType.value;
        processLineThreshold.maximum = processLineThresholdForm.controls.maximum.value;
        processLineThreshold.threshold = processLineThresholdForm.controls.threshold.value;
        processLineThreshold.processLine = processLineThresholdForm.controls.processLine.value;
        processLineThreshold.startDate = this.datePipe.transform(processLineThresholdForm.controls.startDate.value, 'yyyy-MM-dd');
        processLineThreshold.endDate = this.datePipe.transform(processLineThresholdForm.controls.endDate.value, 'yyyy-MM-dd');
        processLineThreshold.millId = processLineThresholdForm.controls.millId.value;
        processLineThreshold.kpiId = processLineThresholdForm.controls.kpiId.value;
        processLineThreshold.createdBy = processLineThresholdForm.controls.createdBy.value;
        processLineThreshold.updatedBy = this.statusService.common.userDetail.username;
        processLineThreshold.isDefault = processLineThresholdForm.controls.isDefault.value;

        this.apiCallService.callAPIwithData(this.addProcessLineTargetUrl, processLineThreshold).
            subscribe(
                response => {
                    processLineThresholdForm.controls.dateError.setValue('');
                    processLineThresholdForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Process Line Threshold " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshProcessLineTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        processLineThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }

    updateProcessLineTarget(processLineThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let processLineThreshold = new ProcessLineThreshold();
        processLineThreshold.processLineTargetThresholdId = processLineThresholdForm.controls.processLineTargetThresholdId.value;
        processLineThreshold.buType = processLineThresholdForm.controls.buType.value;
        processLineThreshold.maximum = processLineThresholdForm.controls.maximum.value;
        processLineThreshold.threshold = processLineThresholdForm.controls.threshold.value;
        processLineThreshold.processLine = processLineThresholdForm.controls.processLine.value;
        processLineThreshold.millId = processLineThresholdForm.controls.millId.value;
        processLineThreshold.kpiId = processLineThresholdForm.controls.kpiId.value;
        processLineThreshold.createdBy = processLineThresholdForm.controls.createdBy.value;
        processLineThreshold.isDefault = processLineThresholdForm.controls.isDefault.value;
        processLineThreshold.updatedBy = this.statusService.common.userDetail.username;
        let startDate = processLineThresholdForm.controls.startDate.value.toString();
        let endDate = processLineThresholdForm.controls.endDate.value.toString();

        if (startDate.length > 10) {
            processLineThreshold.startDate = this.datePipe.transform(startDate, 'yyyy-MM-dd');
        }
        else {
            let tempStartDate = startDate.split("-");
            processLineThreshold.startDate = tempStartDate[2] + '-' + tempStartDate[1] + '-' + tempStartDate[0];
        }
        if (endDate.length > 10) {
            processLineThreshold.endDate = this.datePipe.transform(endDate, 'yyyy-MM-dd');
        }
        else {
            let tempEndDate = endDate.split("-");
            processLineThreshold.endDate = tempEndDate[2] + '-' + tempEndDate[1] + '-' + tempEndDate[0];
        }

        this.apiCallService.callPutAPIwithData(this.updateProcessLineTargetUrl, processLineThreshold).
            subscribe(
                response => {
                    processLineThresholdForm.controls.dateError.setValue('');
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Process Line Threshold " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    processLineThresholdForm.controls.show.setValue(false);
                    this.statusService.refreshProcessLineTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        processLineThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }
}