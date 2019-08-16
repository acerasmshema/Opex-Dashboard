import { Component, OnInit } from '@angular/core';
import { StatusService } from '../shared/service/status.service';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { DashboardService } from './dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [DashboardService]
})
export class DashboardComponent implements OnInit {

  disbaleTab: boolean = true;
  processUnitLegends: any[] = [];
  showTabs: boolean = false;

  constructor(private statusService: StatusService,
    private dashboardService: DashboardService) { }

  ngOnInit() {
    this.processUnitLegends = this.getProcessUnitLegends();
    document.getElementById("select_mill").style.display = "block";
 
    setTimeout(() => {
      this.disbaleTab = false;
    }, 5000);
  }

  openSidebar(event: any) {
    let kpiCategoryId = "";
    let showSideBar = false;

    const index = event.index;
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
    sidebarRequest.isShow = showSideBar;
    sidebarRequest.kpiCategoryId = "" + kpiCategoryId;
    sidebarRequest.type = "dashboard";
    this.statusService.sidebarSubject.next(sidebarRequest);
    this.statusService.kpiCategoryUpdate.next(sidebarRequest.kpiCategoryId);
  }

  getProcessUnitLegends(): any {
    let millId = this.statusService.selectedMill.millId;
    const requestData = {
      millId: millId
    }
    this.dashboardService.getProcessLines(requestData).
      subscribe((processLines: any) => {
        this.statusService.processLineMap.set(millId, processLines);
        let sidebarRequest = new SidebarRequest();
        sidebarRequest.isShow = false;
        this.statusService.sidebarSubject.next(sidebarRequest);
        this.processUnitLegends = processLines;
        this.showTabs = true;
      });

  }
}

