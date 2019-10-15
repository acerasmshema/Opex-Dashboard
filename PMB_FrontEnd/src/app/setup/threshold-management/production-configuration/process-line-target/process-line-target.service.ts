import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ProcessLineThreshold } from './process-line-threshold';

@Injectable()
export class ProcessLineTargetService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    getProcessLineThresholds(): ProcessLineThreshold[] {
        let processLineThresholds = [];

        for (let index = 0; index < 10; index++) {
            const processLineThreshold = new ProcessLineThreshold();
            processLineThreshold.processLineThresholdId = index + "" ;
            processLineThreshold.buType = "Kraft";
            processLineThreshold.processLine = "FL1";
            processLineThreshold.maximum = 122;
            processLineThreshold.threshold = 100;
            processLineThreshold.startDate = "11-11-2019";
            processLineThreshold.endDate = "11-12-2019";
            processLineThresholds.push(processLineThreshold);
        }
   
        return processLineThresholds;
    }

}