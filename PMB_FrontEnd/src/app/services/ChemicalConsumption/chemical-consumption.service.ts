import { Injectable } from '@angular/core';
import { ApiCallService } from '../APIService/ApiCall.service';
import { AppConstants } from '../../app.constant';

@Injectable({
  providedIn: 'root'
})
export class ChemicalConsumptionService {
  
  chemicalConsumptionKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_API_URL;
  chemicalConsumptionLineKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_LINE_API_URL;
  chemicalConsumptionTargetLineKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_LINE_TARGET_API_URL
  chemicalConsumptionGridKpiUrl=AppConstants.apiURLs.CHEMICAL_CONSUMPTION_GRID_API_URL;
  chemicalConsumptionKpiGridUrl = AppConstants.apiURLs.CHEMICAL_CONSUMPTION_API_GRID_URL;
  annotationDates=AppConstants.apiURLs.ANNOTATION_DATES_URL;
  saveAnnotation=AppConstants.apiURLs.SAVE_ANNOTATION_URL;
  findAnnotation=AppConstants.apiURLs.FIND_ANNOTATION_URL;

  constructor(private apiCallService: ApiCallService) { }

  public getDataforKpi(data: object) {
    return this.apiCallService.callAPIwithData(this.chemicalConsumptionKpiUrl, data);
  }  

  public getLineDataforKpi(data: object) {
  return this.apiCallService.callAPIwithData(this.chemicalConsumptionLineKpiUrl, data);
  } 

  public getTargetLineDataforKpi(data: object) {
    return this.apiCallService.callAPIwithData(this.chemicalConsumptionTargetLineKpiUrl, data);
    } 
  

  public getKpiGridData(data: object) {
    return this.apiCallService.callAPIwithData(this.chemicalConsumptionKpiGridUrl, data);
  }

  public getDataForGrid(data:object) {
    return this.apiCallService.callGetAPIwithData(this.chemicalConsumptionGridKpiUrl, data);
  }

  public getAnnotationDates(data:object){
    return this.apiCallService.callAPIwithData(this.annotationDates,data)
  }

  public createAnnotation(data:any){
    return this.apiCallService.callAPIwithData(this.saveAnnotation,data);
  }

  public fetchAnnotation(data:any){
    return this.apiCallService.callAPIwithData(this.findAnnotation,data);
  }
  
}
