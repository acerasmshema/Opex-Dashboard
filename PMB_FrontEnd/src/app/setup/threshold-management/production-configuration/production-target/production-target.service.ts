import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ProductionThreshold } from './production-threshold';

@Injectable()
export class ProductionTargetService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    getProductionThresholds(): ProductionThreshold[] {
        let productionThresholds = [];

        for (let index = 0; index < 10; index++) {
            const productionThreshold = new ProductionThreshold();
            productionThreshold.productionThresholdId = index + "" ;
            productionThreshold.buType = "Kraft";
            productionThreshold.maximum = index + 10;
            productionThreshold.threshold = index + 5;
            productionThreshold.startDate = "11-11-2019";
            productionThreshold.endDate = "11-12-2019";
            productionThresholds.push(productionThreshold);
        }
   
        return productionThresholds;
    }

}