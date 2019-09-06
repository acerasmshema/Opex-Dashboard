import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';

@Injectable()
export class HeaderService {

    allMills = API_URL.apiURLs.ALL_MILLS_URL;
    allBuTypes = API_URL.apiURLs.ALL_BU_TYPE_URL;

    constructor(private apiCallService: ApiCallService) { }

    getAllBuType(): any {
        return this.apiCallService.callGetAPIwithOutData(this.allBuTypes);
    }

    getAllMills(data: any): any {
        return this.apiCallService.callGetAPIwithData(this.allMills, data);
    }
}