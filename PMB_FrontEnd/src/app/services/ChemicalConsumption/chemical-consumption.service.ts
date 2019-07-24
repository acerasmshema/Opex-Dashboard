import { Injectable } from '@angular/core';
import { ApiCallService } from '../APIService/ApiCall.service';
import { AppConstants } from '../../app.constant';

@Injectable({
  providedIn: 'root'
})
export class ChemicalConsumptionService {
  
  chemicalConsumptionKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_API_URL;
  chemicalConsumptionGridKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_GRID_API_URL;
  chemicalConsumptionKpiGridUrl = AppConstants.apiURLs.CHEMICAL_CONSUMPTION_API_GRID_URL;

  constructor(private apiCallService: ApiCallService) { }

  public getDataforKpi(data: object) {
    return this.apiCallService.callAPIwithData(this.chemicalConsumptionKpiUrl, data);
  }

  public getKpiGridData(data: object) {
    return this.apiCallService.callAPIwithData(this.chemicalConsumptionKpiGridUrl, data);
  }

  public getDataForGrid(data:object) {
    return this.apiCallService.callGetAPIwithData(this.chemicalConsumptionGridKpiUrl, data);
  }
  
}
