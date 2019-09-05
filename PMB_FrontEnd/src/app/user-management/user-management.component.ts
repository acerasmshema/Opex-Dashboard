import { Component, OnInit } from '@angular/core';
import { StatusService } from '../shared/service/status.service';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  constructor(private statusService: StatusService) { }

  ngOnInit() {
  }

}
