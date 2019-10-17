import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ProcessLineThreshold } from './process-line-threshold';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Injectable()
export class ProcessLineTargetService {

    processLineTargetUrl = API_URL.threshold_api_URLs.PROCESS_LINE_TARGET;

    constructor(private statusService: StatusService,
        private commonService: CommonService,
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

}