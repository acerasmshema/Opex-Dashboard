import { Component, OnInit } from '@angular/core';
import { StatusService } from '../shared/service/status.service';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { CommonService } from '../shared/service/common/common.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  constructor(private statusService: StatusService,
    private commonService: CommonService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = false;
    sidebarRequest.type = "user-management";
    this.statusService.sidebarSubject.next(sidebarRequest);

    //this.commonService.getAllCountry();
    //this.commonService.getAllMills(null);
    //this.commonService.getAllDepartment();
    //this.commonService.getAllUserRole();
  }

}
