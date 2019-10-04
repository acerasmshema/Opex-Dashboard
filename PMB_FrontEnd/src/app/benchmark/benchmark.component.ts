import { Component, OnInit, OnDestroy } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';
import { ConsumptionModel } from '../shared/models/consumption-model';
import { BenchmarkService } from './benchmark.service';
import { SearchKpiData } from '../shared/models/search-kpi-data';
import { Subscription } from 'rxjs';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.scss']
})
export class BenchmarkComponent implements OnInit, OnDestroy {
  public benchmarkList: ConsumptionModel[] = [];
  benchmarkSubscription: Subscription;

  constructor(private statusService: StatusService,
    private becnhmarkService: BenchmarkService,
    private messageService: MessageService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "none";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = true;
    sidebarRequest.type = "benchmark";
    this.statusService.sidebarSubject.next(sidebarRequest);

    this.benchmarkSubscription = this.statusService.benchmarkSubject.
      subscribe(
        (searchKpiData: SearchKpiData) => {
          this.statusService.benchmarkList = [];
          this.benchmarkList = this.statusService.benchmarkList;
          this.becnhmarkService.filterCharts(searchKpiData);
        },
        (error: any) => {
          this.statusService.spinnerSubject.next(false);
          if (error.status == "0") {
            alert(CommonMessage.ERROR.SERVER_ERROR)
          }
          else {
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
          }
        });
  }

  boundFormatDataLabel = this.formatDataLabel.bind(this);
  public formatDataLabel() {
    return '';
  }

  public showGridDialog(kpiId: string, kpiName: string, isDaily: boolean) {
    this.becnhmarkService.downloadBenchmarkData(+kpiId, kpiName, isDaily);
  }

  ngOnDestroy() {
    this.benchmarkSubscription.unsubscribe();
  }
}
