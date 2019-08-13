import { ApiCallService } from '../shared/service/APIService/ApiCall.service';
import { AppConstants } from 'src/app/app.constant';
import { Injectable } from '@angular/core';

@Injectable()
export class DashboardService {

    allProcessLines = AppConstants.apiURLs.ALL_PROCESS_LINES_URL;

    constructor(private apiCallService: ApiCallService) { }

    getProcessLines(millIdData: any) {
        return this.apiCallService.callGetAPIwithData(this.allProcessLines, millIdData);
    }
}