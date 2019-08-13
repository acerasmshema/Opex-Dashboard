import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { MessageService } from 'primeng/components/common/messageservice';
import { ConsumptionService } from './consumption.service';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';
import { ConsumptionTable } from './consumption-table';
import { ConsumptionDetiail } from './consumption-detail';

@Component({
  selector: 'app-consumption-dashboard',
  templateUrl: './consumption-dashboard.component.html',
  styleUrls: ['./consumption-dashboard.component.scss'],
})
export class ConsumptionDashboardComponent implements OnInit, OnDestroy {

  @Input() kpiCategoryId: string;

  public consumptionsMap: Map<string, ConsumptionDetiail>;
  public header: string;
  public consumptionTable: ConsumptionTable[] = [];
  kpiCategorySubscription: Subscription;

  constructor(private consumptionService: ConsumptionService,
    private messageService: MessageService,
    private statusService: StatusService) {
    this.consumptionsMap = this.statusService.consumptionDetailMap;
  }

  ngOnInit() {
    this.getConsumptionTable();
    this.kpiCategorySubscription = this.statusService.kpiCategoryUpdate.
      subscribe((kpiCategoryId: string) => {
        this.kpiCategoryId = kpiCategoryId;
        const consumptionDetailMap = this.statusService.consumptionDetailMap;
        if (consumptionDetailMap !== undefined) {
          const consumptionDetail = consumptionDetailMap.get(this.kpiCategoryId);
          if (consumptionDetail !== undefined && consumptionDetail.isRefreshed) {
            this.consumptionService.refreshDahboard(this.kpiCategoryId);
            consumptionDetail.isRefreshed = false;
          }
        }
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
          this.consumptionService.showKpiCharts(consumptionModel.kpiId, consumptionModel.kpiName, this.kpiCategoryId);
        });
        let consumptionDetail = new ConsumptionDetiail();
        consumptionDetail.consumptions = consumptions;
        this.statusService.consumptionDetailMap.set(this.kpiCategoryId, consumptionDetail);
        this.header = this.consumptionService.getHeader("" + this.kpiCategoryId);
      });
  }


  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  public changeChartType(event: any, kpiId: number) {
    this.consumptionService.changeChartType(event, kpiId, this.kpiCategoryId);
  }


  onResize(event: any) {
    this.consumptionService.refreshDahboard(this.kpiCategoryId);
  }

  ngOnDestroy() {
    this.kpiCategorySubscription.unsubscribe();
  }
}
