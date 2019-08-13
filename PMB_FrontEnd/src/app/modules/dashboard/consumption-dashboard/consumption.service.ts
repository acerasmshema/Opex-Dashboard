import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';
import { ConsumptionModel } from '../../shared/models/Consumption.model';
import { SearchKpiData } from '../../shared/models/search-kpi-data';
import { DatePipe } from '@angular/common';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { ConsumptionRequest } from './Consumption';
import { StatusService } from '../../shared/service/status.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  consumptionKpiUrl = AppConstants.apiURLs.CONSUMPTION_API_URL;
  consumptionGridKpiUrl = AppConstants.apiURLs.CONSUMPTION_GRID_API_URL;
  consumptionKpiGridUrl = AppConstants.apiURLs.CONSUMPTION_API_GRID_URL;

  constructor(private apiCallService: ApiCallService, private statusService: StatusService,
    private datePipe: DatePipe, private localStorageService: LocalStorageService) { }

  public filterCharts(searchKpiData: SearchKpiData, kpiCategoryId: string) {
    searchKpiData.startDate = this.datePipe.transform(searchKpiData.date[0], 'yyyy-MM-dd');
    searchKpiData.endDate = this.datePipe.transform(searchKpiData.date[1], 'yyyy-MM-dd');

    let consumptionDetail = this.statusService.consumptionDetailMap.get(kpiCategoryId);
    let consumptions = [];
    searchKpiData.kpiTypes.forEach(kpiType => {
      kpiType.kpiList.forEach(kpi => {
        let consumptionModel = this.createChart(kpi.kpiId, kpi.kpiName + "  " + kpi.unit);
        consumptions.push(consumptionModel);
        searchKpiData.kpiId = kpi.kpiId;
        this.updateChart(searchKpiData, kpiCategoryId);
      });
    });
    consumptionDetail.consumptions = consumptions;
    this.statusService.consumptionDetailMap.set(kpiCategoryId, consumptionDetail);
  }


  showKpiCharts(kpiId: number, kpiName: string, kpiCategoryId: string) {
    let searchKpiData = new SearchKpiData();
    let startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);
    searchKpiData.startDate = this.datePipe.transform(startDate, 'yyyy-MM-dd');
    searchKpiData.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    searchKpiData.kpiName = kpiName;
    searchKpiData.kpiId = kpiId;
    searchKpiData.processLines = [];
    searchKpiData.frequency = (this.localStorageService.fetchUserRole() == "Mills Operation") ?
      { name: "Daily", code: "0" } :
      { name: "Monthly", code: "1" };
    this.updateChart(searchKpiData, kpiCategoryId);
  }

  updateChart(searchKpiData: SearchKpiData, kpiCategoryId: string) {
    let processLinesHeads = [];
    searchKpiData.processLines.forEach(pl => {
      processLinesHeads.push(pl["processLineCode"]);
    });

    let consumptionRequest = new ConsumptionRequest();
    consumptionRequest.startDate = searchKpiData.startDate;
    consumptionRequest.endDate = searchKpiData.endDate;
    consumptionRequest.kpiId = searchKpiData.kpiId;
    consumptionRequest.kpiCategoryId = kpiCategoryId;
    consumptionRequest.frequency = searchKpiData.frequency["code"];
    consumptionRequest.processLines = processLinesHeads;

    this.getDataforKpi(consumptionRequest).
      subscribe((data: any) => {
        let consumptionDetail = this.statusService.consumptionDetailMap.get(kpiCategoryId);
        const consumptions = consumptionDetail.consumptions;
        if (consumptions != undefined) {
          let consumption = consumptions.find((con) => con.kpiId === consumptionRequest.kpiId);
          if (consumption !== undefined) {
            let width = document.getElementById('' + consumption.kpiId).clientWidth;
            if (width === 0) {
              setTimeout(() => {
                let width = document.getElementById('' + consumption.kpiId).clientWidth;
                consumption.view = [width, 250];
              }, 300);
            } else {
              consumption.view = [width, 250];
            }
            consumption.data = data;
          }
        }
      });
  }

  public changeChartType(event: any, kpiId: number, kpiCategoryId: string) {
    let consumptionDetail = this.statusService.consumptionDetailMap.get(kpiCategoryId);
    const consumptions = consumptionDetail.consumptions;
    let consumtionModel = consumptions.find((ccm) => ccm.kpiId === kpiId);
    if (event.checked) {
      consumtionModel.chartType = "stack";
      consumtionModel.groupPadding = 0
    } else {
      consumtionModel.chartType = "bar";
      consumtionModel.groupPadding = 2;
    }
  }

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

  refreshDahboard(kpiCategoryId: string) {
    let consumptionDetail = this.statusService.consumptionDetailMap.get(kpiCategoryId);
    const consumptions = consumptionDetail.consumptions;
    if (consumptions !== undefined) {
      consumptions.forEach(consumption => {
        let width = document.getElementById('' + consumption.kpiId).clientWidth;
        if (width === 0) {
          setTimeout(() => {
            let width = document.getElementById('' + consumption.kpiId).clientWidth;
            consumption.view = [width, 250];
          }, 300);
        } else {
          consumption.view = [width, 250];
        }
      });
    }
  }

}
