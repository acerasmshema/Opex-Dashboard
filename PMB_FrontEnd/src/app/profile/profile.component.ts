import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = false;
    sidebarRequest.type = "user-management";
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

}
