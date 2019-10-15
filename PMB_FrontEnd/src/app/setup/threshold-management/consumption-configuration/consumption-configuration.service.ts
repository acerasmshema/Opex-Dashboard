import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ConsumptionThreshold } from './consumption-threshold';

@Injectable()
export class ConsumptionConfigurationService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    getConsumptionThresholds(): ConsumptionThreshold[] {
        let consumptionThresholds = [];

        for (let index = 0; index < 10; index++) {
            const consumptionThreshold = new ConsumptionThreshold();
            consumptionThreshold.consumptionThresholdId = index + "" ;
            consumptionThreshold.buType = "Kraft";
            consumptionThreshold.processLine = "FL1";
            consumptionThreshold.threshold = 100 + index;
            consumptionThreshold.startDate = "11-11-2019";
            consumptionThreshold.endDate = "11-12-2019";
            consumptionThresholds.push(consumptionThreshold);
        }
   
        return consumptionThresholds;
    }

}