import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from 'src/app/core/sidebar/sidebar-request';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-consumption-configuration',
  templateUrl: './consumption-configuration.component.html',
  styleUrls: ['./consumption-configuration.component.scss']
})
export class ConsumptionConfigurationComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = false;
    sidebarRequest.type = "profile";
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

}
