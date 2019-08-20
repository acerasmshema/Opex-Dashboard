import { Injectable } from '@angular/core';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';
import { AppConstants } from 'src/app/app.constant';

@Injectable()
export class HeaderService {

    allMills = AppConstants.apiURLs.ALL_MILLS_URL;
    allBuTypes = AppConstants.apiURLs.ALL_BU_TYPE_URL;

    constructor(private apiCallService: ApiCallService) { }

    getAllBuType(): any {
        return this.apiCallService.callGetAPIwithOutData(this.allBuTypes);
    }

    getAllMills(data: any): any {
        return this.apiCallService.callGetAPIwithData(this.allMills, data);
    }
}