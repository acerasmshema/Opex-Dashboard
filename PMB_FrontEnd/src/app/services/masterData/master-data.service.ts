import { ApiCallService } from 'src/app/services/APIService/ApiCall.service';
import { Injectable, EventEmitter, Output } from '@angular/core';
import { AppConstants } from '../../app.constant';

@Injectable({
  providedIn: 'root'
})
export class MasterDataService {
  kpiTypeUrl=AppConstants.apiURLs.KPI_TYPE_URL;
  processLinesUrl=AppConstants.apiURLs.PROCESS_LINES_BY_KPI_ID_URL;

  constructor(private apiCallService: ApiCallService) { }

  getKpiType(kpiCategoryId: object): any {
    return this.apiCallService.callGetAPIwithData(this.kpiTypeUrl, kpiCategoryId);
  }

  fetchProcessLineByKpiId(kpiId: object): any {
    return this.apiCallService.callGetAPIwithData(this.processLinesUrl, kpiId);
  }
}

