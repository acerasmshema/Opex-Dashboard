import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';

@Component({
  selector: 'app-kappa-number-prediction',
  templateUrl: './kappa-number-prediction.component.html',
  styleUrls: ['./kappa-number-prediction.component.scss']
})
export class KappaNumberPredictionComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = false;
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

}
