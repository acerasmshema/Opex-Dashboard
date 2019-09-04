import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from '../../shared/service/api/api-call.service';

@Injectable({
  providedIn: 'root'
})
export class ProductionService {

  ydayProcessLines = API_URL.apiURLs.PROCESS_LINES_URL;
  projectedData = API_URL.apiURLs.PROJECTED_DATA_URL;
  ydayAllProcessLines = API_URL.apiURLs.YTD_ALL_PROCESS_LINES_URL;
  kpiPulpArea = API_URL.apiURLs.KPI_PULP_AREA_URL;
  kpiPulp = API_URL.apiURLs.KPI_PULP_URL;
  ytdProcessLineTarget = API_URL.apiURLs.YTD_PROCESS_LINE_TARGET_URL;
  drProcessLine = API_URL.apiURLs.DR_ALL_PROCESS_LINES_URL;
  allProcessLinesTarget = API_URL.apiURLs.ALL_PROCESS_LINES_TARGET_URL;
  drSelectedProcessLine = API_URL.apiURLs.DR_SELECTED_PROCESS_LINES_URL;
  kpiGrid = API_URL.apiURLs.DOWNLOAD_DATA_GRID_URL;
  saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
  getMaintenanceDay = API_URL.apiURLs.GET_MAINTENANCE_DAYS_URL;
  saveMaintenanceDay = API_URL.apiURLs.SAVE_MAINTENANCE_DAYS_URL;
  deleteMaintenanceDay = API_URL.apiURLs.DELETE_MAINTENANCE_DAYS_URL;
  saveTargetDay = API_URL.apiURLs.SAVE_TARGET_DAYS_URL;
  annotationDates = API_URL.apiURLs.ANNOTATION_DATES_URL;
  productionGridKpiUrl = API_URL.apiURLs.CONSUMPTION_GRID_API_URL;

  constructor(private apiCallService: ApiCallService) { }

  public getProductionYDayData(data: object) {
    return this.apiCallService.callGetAPIwithData(this.ydayProcessLines, data);
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