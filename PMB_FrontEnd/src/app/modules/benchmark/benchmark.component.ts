import { Component, OnInit, OnDestroy } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';
import { ConsumptionModel } from '../shared/models/consumption-model';
import { BenchmarkService } from './benchmark.service';
import { SearchKpiData } from '../shared/models/search-kpi-data';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.scss'],
  providers: [BenchmarkService]
})
export class BenchmarkComponent implements OnInit, OnDestroy {
  public isShowBenchmark: boolean = false;
  public benchmarkList: ConsumptionModel[] = [];
  benchmarkSubscription: Subscription;

  constructor(private statusService: StatusService,
    private becnhmarkService: BenchmarkService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "none";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = true;
    sidebarRequest.type = "benchmark";
    this.statusService.sidebarSubject.next(sidebarRequest);

    this.benchmarkSubscription = this.statusService.benchmarkSubject.
      subscribe((searchKpiData: SearchKpiData) => {
        this.statusService.benchmarkList = [];
        this.benchmarkList = this.statusService.benchmarkList;
        this.becnhmarkService.filterCharts(searchKpiData);
        this.isShowBenchmark = true;
      })
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
