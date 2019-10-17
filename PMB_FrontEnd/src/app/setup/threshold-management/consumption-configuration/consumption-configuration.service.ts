import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ConsumptionThreshold } from './consumption-threshold';
import { ConsumptionConfig } from './consumption-config.model';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { FormGroup, FormControl } from '@angular/forms';

@Injectable()
export class ConsumptionConfigurationService {

    kpiTypeUrl = API_URL.apiURLs.KPI_TYPE_URL;
    processLineTargetUrl = API_URL.threshold_api_URLs.PROCESS_LINE_TARGET;

    constructor(private statusService: StatusService,
        private commonService: CommonService,
        private apiCallService: ApiCallService) { }

    getConsumptionThresholds(consumptionThresholds: ConsumptionThreshold[], byTypeId: string, kpiId: string) {
        let requestData = {
            millId: this.statusService.common.selectedMill.millId,
            kpiId: kpiId,
            byTypeId: byTypeId
        };

        this.apiCallService.callGetAPIwithData(this.processLineTargetUrl, requestData)
            .subscribe(
                (consumptionThresholdList: ConsumptionThreshold[]) => {
                    consumptionThresholds.push(...consumptionThresholdList);
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
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }
}