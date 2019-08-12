import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { MessageService } from 'primeng/components/common/messageservice';
import { ConsumptionRequest } from './Consumption';
import { DatePipe } from '@angular/common';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { ConsumptionService } from './consumption.service';
import { ConsumptionModel } from '../../shared/models/Consumption.model';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';
import { SearchKpiData } from '../../shared/models/search-kpi-data';
import { ConsumptionTable } from './consumption-table';

@Component({
  selector: 'app-consumption-dashboard',
  templateUrl: './consumption-dashboard.component.html',
  styleUrls: ['./consumption-dashboard.component.scss'],
  providers: [MessageService, DatePipe, LocalStorageService]
})
export class ConsumptionDashboardComponent implements OnInit, OnDestroy {

  @Input() kpiCategoryId: string;


  public consumptionsMap: Map<string, ConsumptionModel[]>;
  public header: string;
  public consumptionTable: ConsumptionTable[] = [];
  kpiSubjectSubscription: Subscription;
  kpiCategorySubscription: Subscription;

  constructor(private localStorageService: LocalStorageService,
    private consumptionService: ConsumptionService,
    private messageService: MessageService,
    private statusService: StatusService,
    private datePipe: DatePipe) {
    this.consumptionsMap = new Map<string, ConsumptionModel[]>();
  }

  ngOnInit() {
    this.getConsumptionTable();
    this.kpiSubjectSubscription = this.statusService.kpiSubject.
      subscribe((searchKpiData: SearchKpiData) => {
        if (searchKpiData.type === 'dashboard') {
          this.filterCharts(searchKpiData);
        }
      });
    this.kpiCategorySubscription = this.statusService.kpiCategoryUpdate.
      subscribe((kpiCategoryId: string) => {
        this.kpiCategoryId = kpiCategoryId;
      });
  }

  public getConsumptionTable() {
    const requestData = {
      kpiCategoryId: this.kpiCategoryId,
      millId: "1"
    };

    this.consumptionService.getDataForGrid(requestData).
      subscribe((consumptionsTable: ConsumptionTable[]) => {
        this.consumptionTable = [];
        this.consumptionTable = consumptionsTable;

        let consumptions = [];
        consumptionsTable.forEach(kpi => {
          let consumptionModel = this.consumptionService.createChart(kpi.kpiId, kpi.kpiName + "  " + kpi.unit);
          consumptions.push(consumptionModel);
          this.showKpiCharts(consumptionModel.kpiId, consumptionModel.kpiName);
        });
        this.consumptionsMap.set(this.kpiCategoryId, consumptions);
        this.header = this.consumptionService.getHeader("" + this.kpiCategoryId);
      });
  }

  public filterCharts(searchKpiData: SearchKpiData) {
    searchKpiData.startDate = this.datePipe.transform(searchKpiData.date[0], 'yyyy-MM-dd');
    searchKpiData.endDate = this.datePipe.transform(searchKpiData.date[1], 'yyyy-MM-dd');

    this.consumptionsMap.delete(this.kpiCategoryId);
    let consumptions = [];
    searchKpiData.kpiTypes.forEach(kpiType => {
      kpiType.kpiList.forEach(kpi => {
        let consumptionModel = this.consumptionService.createChart(kpi.kpiId, kpi.kpiName + "  " + kpi.unit);
        consumptions.push(consumptionModel);
        searchKpiData.kpiId = kpi.kpiId;
        this.updateChart(searchKpiData);
      });
    });
    this.consumptionsMap.set(this.kpiCategoryId, consumptions);
  }

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  public changeChartType(event: any, kpiId: number) {
    const consumptions = this.consumptionsMap.get(this.kpiCategoryId);
    let consumtionModel = consumptions.find((ccm) => ccm.kpiId === kpiId);
    if (event.checked) {
      consumtionModel.chartType = "stack";
      consumtionModel.groupPadding = 2;
    } else {
      consumtionModel.chartType = "bar";
      consumtionModel.groupPadding = 0
    }
  }

  showKpiCharts(kpiId: number, kpiName: string) {
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
    this.updateChart(searchKpiData);
  }

  updateChart(searchKpiData: SearchKpiData) {
    let processLinesHeads = [];
    searchKpiData.processLines.forEach(pl => {
      processLinesHeads.push(pl["processLineCode"]);
    });

    let consumptionRequest = new ConsumptionRequest();
    consumptionRequest.startDate = searchKpiData.startDate;
    consumptionRequest.endDate = searchKpiData.endDate;
    consumptionRequest.kpiId = searchKpiData.kpiId;
    consumptionRequest.kpiCategoryId = this.kpiCategoryId;
    consumptionRequest.frequency = searchKpiData.frequency["code"];
    consumptionRequest.processLines = processLinesHeads;

    this.consumptionService.getDataforKpi(consumptionRequest).
      subscribe((data: any) => {
        const consumptions = this.consumptionsMap.get(this.kpiCategoryId);
        let consumption = consumptions.find((con) => con.kpiId === consumptionRequest.kpiId);
        if (consumption !== undefined) {
          consumption.data = data;
        }
      });
  }

  ngOnDestroy() {
    this.kpiSubjectSubscription.unsubscribe();
    this.kpiCategorySubscription.unsubscribe();
  }
}
