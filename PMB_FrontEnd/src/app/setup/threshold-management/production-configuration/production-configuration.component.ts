import { Component, OnInit } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { SidebarRequest } from 'src/app/core/sidebar/sidebar-request';

@Component({
  selector: 'app-production-configuration',
  templateUrl: './production-configuration.component.html',
  styleUrls: ['./production-configuration.component.scss']
})
export class ProductionConfigurationComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = false;
    sidebarRequest.type = "profile";
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

  openTab(event: any) {
    const index = event.index;
    switch (index) {
      case 0:
        this.statusService.selectedProductionTab = 'PRODUCTION';
        break;
      case 1:
        this.statusService.selectedProductionTab = 'PROCESSLINE';
        break;
      case 2:
        this.statusService.selectedProductionTab = 'ANNUAL';
        break;

      default:
        break;
    }
  }
}
