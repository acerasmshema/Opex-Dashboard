import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { AnnualTarget } from './annual-target';

@Injectable()
export class AnnualConfigService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    getAnnualTargets(): AnnualTarget[] {
        let annualTargets = [];

        for (let index = 0; index < 10; index++) {
            const annualTarget = new AnnualTarget();
            annualTarget.year = 2019 + index ;
            annualTarget.buType = "Kraft";
            annualTarget.workingDays  = 350;
            annualTarget.target = "2,900,000";
            annualTargets.push(annualTarget);
        }
        return annualTargets;
    }

}

