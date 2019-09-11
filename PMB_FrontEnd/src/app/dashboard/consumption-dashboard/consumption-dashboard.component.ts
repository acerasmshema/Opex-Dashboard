import { Component, OnInit, Input } from '@angular/core';
import { ConsumptionService } from './consumption.service';
import { StatusService } from '../../shared/service/status.service';
import { ConsumptionTable } from './consumption-table';
import { ConsumptionDetiail } from './consumption-detail';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-consumption-dashboard',
  templateUrl: './consumption-dashboard.component.html',
  styleUrls: ['./consumption-dashboard.component.scss'],
})
export class ConsumptionDashboardComponent implements OnInit {

  @Input() kpiCategoryId: string;

  public consumptionsMap: Map<string, ConsumptionDetiail>;
  public header: string;
  public consumptionTable: ConsumptionTable[] = [];
 
  constructor(private consumptionService: ConsumptionService,
    private statusService: StatusService,
    private messageService:MessageService) {
  }

  ngOnInit() {
    this.consumptionsMap = this.statusService.consumptionDetailMap;
    this.getConsumptionTable();
  }

  public getConsumptionTable() {
    const requestData = {
      kpiCategoryId: this.kpiCategoryId,
      millId: this.statusService.common.selectedMill.millId
    };

    this.consumptionService.getDataForGrid(requestData).
      subscribe((consumptionsTable: ConsumptionTable[]) => {
        this.consumptionTable = [];
        this.consumptionTable = consumptionsTable;

        let consumptions = [];
        consumptionsTable.forEach(kpi => {
          let consumptionModel = this.consumptionService.createChart(kpi.kpiId, kpi.kpiName + "  (" + kpi.unit + ")");
          consumptions.push(consumptionModel);
          this.consumptionService.showKpiCharts(consumptionModel.kpiId, consumptionModel.kpiName, this.kpiCategoryId);
        });
        let consumptionDetail = new ConsumptionDetiail();
        consumptionDetail.consumptions = consumptions;
        this.statusService.consumptionDetailMap.set(this.kpiCategoryId, consumptionDetail);
        this.header = this.consumptionService.getHeader("" + this.kpiCategoryId);
      },
      (error: any) => {
        this.statusService.spinnerSubject.next(false);
        if(error.status=="0"){
        alert(CommonMessage.ERROR.SERVER_ERROR)
        }else{
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
      }
    });
  }

  public showGridDialog(kpiId: number, title: string) {
    this.consumptionService.getKpiGridData(kpiId, title, this.kpiCategoryId);
  }

  public changeChartType(event: any, kpiId: number) {
    this.consumptionService.changeChartType(event, kpiId, this.kpiCategoryId);
  }
}
