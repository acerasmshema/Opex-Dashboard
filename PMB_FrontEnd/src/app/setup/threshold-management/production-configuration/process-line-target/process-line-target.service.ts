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
        let requestData = {
            millId: this.statusService.common.selectedMill.millId,
            kpiId: "1"
        };

        this.apiCallService.callGetAPIwithData(this.processLineTargetUrl, requestData)
            .subscribe(
                (processLineThresholdList: ProcessLineThreshold[]) => {
                    processLineThresholds.push(...processLineThresholdList);
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
                    processLineThresholdForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Process Line Threshold " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshProcessLineTargetList.next(true);
                },
                error => {
                    this.commonService.handleError(error);
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
        try {
            processLineThreshold.startDate = this.datePipe.transform(processLineThresholdForm.controls.startDate.value, 'yyyy-MM-dd');
        } catch (error) {
            processLineThreshold.startDate = processLineThresholdForm.controls.startDate.value;
        }
        try {
            processLineThreshold.endDate = this.datePipe.transform(processLineThresholdForm.controls.endDate.value, 'yyyy-MM-dd');
        } catch (error) {
            processLineThreshold.endDate = processLineThresholdForm.controls.endDate.value;
        }


        this.apiCallService.callPutAPIwithData(this.updateProcessLineTargetUrl, processLineThreshold).
            subscribe(
                response => {
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Process Line Threshold " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    processLineThresholdForm.controls.show.setValue(false);
                    this.statusService.refreshProcessLineTargetList.next(true);
                },
                error => {
                    this.commonService.handleError(error);
                }
            );
    }
}