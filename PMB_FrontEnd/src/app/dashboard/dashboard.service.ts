import { API_URL } from 'src/app/shared/constant/API_URLs';
import { Injectable } from '@angular/core';
import { ApiCallService } from '../shared/service/api/api-call.service';

@Injectable()
export class DashboardService {

    allProcessLines = API_URL.apiURLs.ALL_PROCESS_LINES_URL;

    constructor(private apiCallService: ApiCallService) { }

    getProcessLines(millIdData: any) {
        return this.apiCallService.callGetAPIwithData(this.allProcessLines, millIdData);
    }
}