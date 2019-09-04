import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';

@Injectable({
  providedIn: 'root'
})
export class ProductionService {

  ydayProcessLines = AppConstants.apiURLs.PROCESS_LINES_URL;
  annualTarget = AppConstants.apiURLs.ANNUAL_TARGET_URL;
  projectedData = AppConstants.apiURLs.PROJECTED_DATA_URL;
  ydayAllProcessLines = AppConstants.apiURLs.YTD_ALL_PROCESS_LINES_URL;
  kpiPulpArea = AppConstants.apiURLs.KPI_PULP_AREA_URL;
  kpiPulp = AppConstants.apiURLs.KPI_PULP_URL;
  ytdProcessLineTarget = AppConstants.apiURLs.YTD_PROCESS_LINE_TARGET_URL;
  drProcessLine = AppConstants.apiURLs.DR_ALL_PROCESS_LINES_URL;
  allProcessLinesTarget = AppConstants.apiURLs.ALL_PROCESS_LINES_TARGET_URL;
  drSelectedProcessLine = AppConstants.apiURLs.DR_SELECTED_PROCESS_LINES_URL;
  kpiGrid = AppConstants.apiURLs.DOWNLOAD_DATA_GRID_URL;
  saveAnnotation = AppConstants.apiURLs.SAVE_ANNOTATION_URL;
  getMaintenanceDay = AppConstants.apiURLs.GET_MAINTENANCE_DAYS_URL;
  saveMaintenanceDay = AppConstants.apiURLs.SAVE_MAINTENANCE_DAYS_URL;
  deleteMaintenanceDay = AppConstants.apiURLs.DELETE_MAINTENANCE_DAYS_URL;
  updateMaintanenceDaysRemarks=AppConstants.apiURLs.UPDATE_MAINTENANCE_DAYS_REMARKS;
  saveTargetDay = AppConstants.apiURLs.SAVE_TARGET_DAYS_URL;
  annotationDates = AppConstants.apiURLs.ANNOTATION_DATES_URL;
  productionGridKpiUrl = AppConstants.apiURLs.CONSUMPTION_GRID_API_URL;

  constructor(private apiCallService: ApiCallService) { }

  public getProductionYDayData(data: object) {
    return this.apiCallService.callGetAPIwithData(this.ydayProcessLines, data);
  }

  public getAnnualTarget(data: object) {
    return this.apiCallService.callGetAPIwithData(this.annualTarget, data);
  }

  public getProjectedTarget(data: object) {
    return this.apiCallService.callGetAPIwithData(this.projectedData, data);
  }

  public getAllproductionLinesYDayData(data: any) {
    return this.apiCallService.callAPIwithData(this.ydayAllProcessLines, data);
  }

  public getStackBarChartData(data: any) {
    return this.apiCallService.callAPIwithData(this.kpiPulpArea, data);
  }

  public getStackAreaChartData(data: any) {
    return this.apiCallService.callAPIwithData(this.kpiPulp, data);
  }

  public getProductionYTDTargetData(data: any) {
    return this.apiCallService.callGetAPIwithData(this.ytdProcessLineTarget, data);
  }

  public getAllProductionLinesDateRangeData(data: any) {
    return this.apiCallService.callAPIwithData(this.drProcessLine, data);
  }
  public getAllProductionLinesDateRangeDataTarget(data: any) {
    return this.apiCallService.callAPIwithData(this.allProcessLinesTarget, data);
  }

  public getSelectedProductionLinesDateRangeData(data: any) {
    return this.apiCallService.callAPIwithData(this.drSelectedProcessLine, data);
  }

  public getMaintenanceData(data: any) {
    return this.apiCallService.callGetAPIwithData(this.getMaintenanceDay, data);
  }

  public saveMaintenanceDays(data: object) {
    return this.apiCallService.callAPIwithData(this.saveMaintenanceDay, data);
  }

  public deleteMaintenanceDays(data: object) {
    return this.apiCallService.callAPIwithData(this.deleteMaintenanceDay, data);
  }

  public updateMaintanenceTargetDays(data: object) {
    return this.apiCallService.callAPIwithData(this.saveTargetDay, data)
  }

  public updateMaintenanceDaysRemarks(data:object){
    return this.apiCallService.callPutAPIwithData(this.updateMaintanenceDaysRemarks,data);
  } 


  public getAnnotationDates(data: object) {
    return this.apiCallService.callAPIwithData(this.annotationDates, data)
  }

  public errorMsgMaintenanceData(data: any) {
    return this.apiCallService.errorHandler(data);
  }

  public getDataForGrid(data: object) {
    return this.apiCallService.callGetAPIwithData(this.productionGridKpiUrl, data);
  }

}