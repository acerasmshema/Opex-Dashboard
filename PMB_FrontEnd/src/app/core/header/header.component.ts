import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { StatusService } from '../../shared/service/status.service';
import { HeaderService } from './header.service';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';
import { LoginService } from 'src/app/profile/login/login.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user: any;
  loginId: any;
  mills: MillDetail[] = [];
  selectedMillName: string;

  constructor(private router: Router,
    private loginService: LoginService,
    private headerService: HeaderService,
    private statusService: StatusService,
    private translate: TranslateService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
    this.onGetAllMills();
    this.onGetAllBuType();
  }

  setLang(lang: string) {
    this.translate.use(lang);
  }

  logOut() {
    this.loginId = this.localStorageService.fetchloginId();
    var data = {
      loginId: this.loginId
    }
    this.loginService.logOut(data).
      subscribe((data: any) => {
       this.localStorageService.removeUserDetail();
      });
    this.router.navigateByUrl('login');
  }

  onChangeMill(millId: string) {
    if (this.statusService.common.selectedMill.millId !== millId) {
      this.statusService.kpiCategoryMap = new Map<string, any>();
      this.statusService.consumptionDetailMap = new Map<string, ConsumptionDetiail>();
      this.statusService.common.selectedMill = this.statusService.common.mills.find((mill) => mill.millId === millId);
      this.selectedMillName = this.statusService.common.selectedMill.millName;
      this.statusService.changeMill.next();
    }
  }

  onGetAllBuType() {
    this.headerService.getAllBuType().
      subscribe((buTypes: any) => {
        this.statusService.common.buTypes = buTypes;
      });

    }

  onGetAllMills() {
    const requestData = {
      countryIds: "1,2"
    }
    this.headerService.getAllMills(requestData).
      subscribe((mills: MillDetail[]) => {
        this.statusService.common.mills = mills;
        this.mills = this.statusService.common.mills;
      });
    let millDetail = new MillDetail();
    millDetail.millId = "1";
    millDetail.millName = "Kerinci";
    millDetail.countryId = '1';
    this.statusService.common.selectedMill = millDetail;
    this.selectedMillName = this.statusService.common.selectedMill.millName;
  }
}
