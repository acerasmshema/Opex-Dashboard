import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';
import { ConsumptionModel } from '../../shared/models/Consumption.model';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  consumptionKpiUrl = AppConstants.apiURLs.CONSUMPTION_API_URL;
  consumptionGridKpiUrl = AppConstants.apiURLs.CONSUMPTION_GRID_API_URL;
  consumptionKpiGridUrl = AppConstants.apiURLs.CONSUMPTION_API_GRID_URL;

  constructor(private apiCallService: ApiCallService) { }

  public getDataforKpi(data: object) {
    return this.apiCallService.callAPIwithData(this.consumptionKpiUrl, data);
  }

  public getKpiGridData(data: object) {
    return this.apiCallService.callAPIwithData(this.consumptionKpiGridUrl, data);
  }

  public getDataForGrid(data: object) {
    return this.apiCallService.callGetAPIwithData(this.consumptionGridKpiUrl, data);
  }

  getHeader(kpiCategoryId: string): string {
    let headerName = "";
    switch (kpiCategoryId) {
      case "2":
        headerName = "Chemical Consumption - Y'Day";
        break;
      case "3":
        headerName = "Utility Consumption - Y'Day";
        break;
      case "4":
        headerName = "Wood Consumption - Y'Day";
        break;

      default:
        break;
    }
    return headerName;
  }

  createChart(kpiId: number, kpiName: string): ConsumptionModel {
    let ccm = new ConsumptionModel();
    ccm.kpiName = kpiName;
    ccm.kpiId = kpiId;
    ccm.colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#660000', '#9933FF', '#99FF99', '#FFFF99', '#FF9999'] };
    ccm.xAxis = true;
    ccm.yAxis = true;
    ccm.showDataLabel = false;
    ccm.xAxis = true;
    ccm.checked = false;
    ccm.legend = false;
    ccm.barPadding = 2;
    ccm.groupPadding = 0;
    ccm.chartType = "bar";
    ccm.showXAxisLabel = false;
    ccm.showYAxisLabel = false;
    ccm.gradient = "gradient";
    ccm.xAxisLabel = "";
    ccm.yAxisLabel = "";
    ccm.showKpiType = true;

    return ccm;
  }

}
