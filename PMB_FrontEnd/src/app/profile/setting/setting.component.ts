import { Component, OnInit } from '@angular/core';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.scss']
})
export class SettingComponent implements OnInit {

  constructor(private commonService: CommonService) { }

  ngOnInit() {
    this.commonService.getAllDepartment();
    this.commonService.getAllCountry();
  }

}
