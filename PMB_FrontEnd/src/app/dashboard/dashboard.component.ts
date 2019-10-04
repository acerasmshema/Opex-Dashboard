import { Component, OnInit, OnDestroy } from '@angular/core';
import { StatusService } from '../shared/service/status.service';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { DashboardService } from './dashboard.service';
import { Subscription } from 'rxjs';
import { ConsumptionService } from './consumption-dashboard/consumption.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  disableTab: boolean = true;
  processUnitLegends: any[] = [];
  showTabs: boolean = false;
  cacheMap: Map<string, boolean>;
  selected: boolean = true;
  selectedMillName: string;

  millSubscription: Subscription;
  tabsSubscription: Subscription;

  constructor(private statusService: StatusService,
    private consumptionService: ConsumptionService,
    private dashboardService: DashboardService,
    private messageService:MessageService) {

    this.cacheMap = new Map<string, boolean>();
    this.cacheMap.set("1", true);
    this.cacheMap.set("2", false);
    this.cacheMap.set("3", false);
    this.cacheMap.set("4", false);
  }

  ngOnInit() {
    this.openDashboard();

    this.tabsSubscription = this.statusService.enableTabs.
      subscribe((isEnable: boolean) => {
        if (isEnable)
          this.disableTab = false;
      });

    this.millSubscription = this.statusService.changeMill.
      subscribe(() => {
        this.selected = false;
        this.cacheMap.set("1", false);
        this.cacheMap.set("2", false);
        this.cacheMap.set("3", false);
        this.cacheMap.set("4", false);
        this.processUnitLegends = this.getProcessUnitLegends(true);
      });
  }

  openDashboard() {
    this.processUnitLegends = this.getProcessUnitLegends(false);
    document.getElementById("select_mill").style.display = "block";
  }

  openSidebar(event: any) {
    let kpiCategoryId = "";
    let showSideBar = false;

    const index = event.index;
    let cacheKpiCategoryId = (index + 1).toString();
    this.cacheMap.set(cacheKpiCategoryId, true);
    switch (index) {
      case 1:
      case 2:
      case 3:
        kpiCategoryId = index + 1;
        showSideBar = true;
        break;
      default:
        break;
    }
    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = showSideBar;
    sidebarRequest.kpiCategoryId = "" + kpiCategoryId;
    sidebarRequest.type = "dashboard";
    this.statusService.sidebarSubject.next(sidebarRequest);
    const consumptionDetailMap = this.statusService.consumptionDetailMap;
    if (consumptionDetailMap !== undefined) {
      const consumptionDetail = consumptionDetailMap.get(sidebarRequest.kpiCategoryId);
      if (consumptionDetail !== undefined) {
        this.consumptionService.refreshDahboard(sidebarRequest.kpiCategoryId);
        consumptionDetail.isRefreshed = false;
      }
    }
  }

  getProcessUnitLegends(isMillChange: boolean): any {
    let millId = this.statusService.common.selectedMill.millId;
    const requestData = {
      millId: millId
    }
    this.dashboardService.getProcessLines(requestData).
      subscribe((processLines: any) => {
        this.statusService.common.processLines = processLines;
        let sidebarRequest = new SidebarRequest();
        sidebarRequest.showSidebar = false;
        this.statusService.sidebarSubject.next(sidebarRequest);
        this.processUnitLegends = processLines;
        this.showTabs = true;

        setTimeout(() => {
          this.selectedMillName = this.statusService.common.selectedMill.millName;
        }, 200);

        if (isMillChange) {
          setTimeout(() => {
            this.disableTab = true;
            const productionTab: any = document.getElementsByClassName("production-dashboard-id")[0];
            const productionTabId = productionTab.childNodes[0].id;
            document.getElementById(productionTabId).click();
            this.selected = true;
          }, 200);
        }
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

  ngOnDestroy() {
    this.millSubscription.unsubscribe();
    this.tabsSubscription.unsubscribe();
  }
}

