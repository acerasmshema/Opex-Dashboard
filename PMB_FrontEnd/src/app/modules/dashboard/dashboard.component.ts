import { Component, OnInit } from '@angular/core';
import { StatusService } from '../shared/service/status.service';
import { SidebarRequest } from '../core/sidebar/sidebar-request';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
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

}

