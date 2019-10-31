import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ConsumptionThreshold } from './consumption-threshold';
import { ConsumptionConfig } from './consumption-config.model';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { MessageService } from 'primeng/primeng';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Injectable()
export class ConsumptionConfigurationService {

    kpiTypeUrl = API_URL.apiURLs.KPI_TYPE_URL;
    processLineTargetUrl = API_URL.threshold_api_URLs.PROCESS_LINE_TARGET;
    updateProcessLineTargetUrl = API_URL.threshold_api_URLs.UPDATE_PROCESS_LINE_TARGET;
    addProcessLineTargetUrl = API_URL.threshold_api_URLs.CREATE_PROCESS_LINE_TARGET;

    constructor(private statusService: StatusService,
        private commonService: CommonService,
        private datePipe: DatePipe,
        private messageService: MessageService,
        private apiCallService: ApiCallService) { }

    getConsumptionThresholds(consumptionThresholds: ConsumptionThreshold[], byTypeId: string, kpiId: string) {
        this.statusService.spinnerSubject.next(true);

        let requestData = {
            millId: this.statusService.common.selectedMill.millId,
            kpiId: kpiId,
            byTypeId: byTypeId
        };

        this.apiCallService.callGetAPIwithData(this.processLineTargetUrl, requestData)
            .subscribe(
                (consumptionThresholdList: ConsumptionThreshold[]) => {
                    consumptionThresholdList.forEach(consumptionThreshold => {
                        if (!consumptionThreshold.isDefault)
                            consumptionThreshold.processLineSortName = consumptionThreshold.processLine.processLineCode;
                        else
                            consumptionThreshold.processLineSortName = consumptionThreshold.processLine.processLineCode + ' (DEFAULT)';

                        consumptionThresholds.push(consumptionThreshold);
                    });
                    this.statusService.spinnerSubject.next(false);
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }


    getConsumptionConfig(consumptionConfig: ConsumptionConfig) {
        consumptionConfig.kpiCategories = [
            { kpiCategoryId: 2, kpiCategoryName: "Chemical" },
            { kpiCategoryId: 3, kpiCategoryName: "Utility" },
            { kpiCategoryId: 4, kpiCategoryName: "Wood" },
        ];
        this.commonService.getAllBuType(consumptionConfig);
    }

    getKpiDetails(kpiCategoryId: string, consumptionConfig: ConsumptionConfig) {
        this.statusService.spinnerSubject.next(true);
        const requestData = {
            "kpiCategoryId": kpiCategoryId
        };
        this.apiCallService.callGetAPIwithData(this.kpiTypeUrl, requestData)
            .subscribe(
                (kpiTypes: any) => {
                    consumptionConfig.kpis = [];
                    kpiTypes.forEach(kpiType => {
                        kpiType.kpiList.forEach(kpi => {
                            consumptionConfig.kpis.push(kpi);
                        });
                    });
                    this.statusService.spinnerSubject.next(false);
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }

    addConsumptionTarget(consumptionThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let consumptionThreshold = new ConsumptionThreshold();
        consumptionThreshold.buType = consumptionThresholdForm.controls.buType.value;
        consumptionThreshold.threshold = consumptionThresholdForm.controls.threshold.value;
        consumptionThreshold.processLine = consumptionThresholdForm.controls.processLine.value;
        consumptionThreshold.startDate = this.datePipe.transform(consumptionThresholdForm.controls.startDate.value, 'yyyy-MM-dd');
        consumptionThreshold.endDate = this.datePipe.transform(consumptionThresholdForm.controls.endDate.value, 'yyyy-MM-dd');
        consumptionThreshold.millId = consumptionThresholdForm.controls.millId.value;
        consumptionThreshold.kpiId = consumptionThresholdForm.controls.kpiId.value;
        consumptionThreshold.createdBy = consumptionThresholdForm.controls.createdBy.value;
        consumptionThreshold.updatedBy = this.statusService.common.userDetail.username;
        consumptionThreshold.isDefault = consumptionThresholdForm.controls.isDefault.value;

        this.apiCallService.callAPIwithData(this.addProcessLineTargetUrl, consumptionThreshold).
            subscribe(
                response => {
                    consumptionThresholdForm.controls.dateError.setValue('');
                    consumptionThresholdForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Consumption Threshold " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshConsumtionTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        consumptionThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }

    updateConsumptionTarget(consumptionThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let consumptionThreshold = new ConsumptionThreshold();
        consumptionThreshold.processLineTargetThresholdId = consumptionThresholdForm.controls.processLineTargetThresholdId.value;
        consumptionThreshold.buType = consumptionThresholdForm.controls.buType.value;
        consumptionThreshold.threshold = consumptionThresholdForm.controls.threshold.value;
        consumptionThreshold.processLine = consumptionThresholdForm.controls.processLine.value;
        consumptionThreshold.millId = consumptionThresholdForm.controls.millId.value;
        consumptionThreshold.kpiId = consumptionThresholdForm.controls.kpiId.value;
        consumptionThreshold.createdBy = consumptionThresholdForm.controls.createdBy.value;
        consumptionThreshold.isDefault = consumptionThresholdForm.controls.isDefault.value;
        consumptionThreshold.updatedBy = this.statusService.common.userDetail.username;

        let startDate = consumptionThresholdForm.controls.startDate.value.toString();
        let endDate = consumptionThresholdForm.controls.endDate.value.toString();

        if (startDate.length > 10) {
            consumptionThreshold.startDate = this.datePipe.transform(startDate, 'yyyy-MM-dd');
        }
        else {
            let tempStartDate = startDate.split("-");
            consumptionThreshold.startDate = tempStartDate[2] + '-' + tempStartDate[1] + '-' + tempStartDate[0];
        }
        if (endDate.length > 10) {
            consumptionThreshold.endDate = this.datePipe.transform(endDate, 'yyyy-MM-dd');
        }
        else {
            let tempEndDate = endDate.split("-");
            consumptionThreshold.endDate = tempEndDate[2] + '-' + tempEndDate[1] + '-' + tempEndDate[0];
        }

        this.apiCallService.callPutAPIwithData(this.updateProcessLineTargetUrl, consumptionThreshold).
            subscribe(
                response => {
                    consumptionThresholdForm.controls.dateError.setValue('');
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Consumption Threshold " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    consumptionThresholdForm.controls.show.setValue(false);
                    this.statusService.refreshConsumtionTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        consumptionThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }
}