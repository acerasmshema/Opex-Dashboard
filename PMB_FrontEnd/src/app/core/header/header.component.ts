import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { StatusService } from '../../shared/service/status.service';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';
import { CommonMessage } from '../../shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';
import { LoginService } from 'src/app/profile/login/login.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { CommonService } from 'src/app/shared/service/common/common.service';

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
    private commonService: CommonService,
    private statusService: StatusService,
    private translate: TranslateService,
    private localStorageService: LocalStorageService,
    private messageService: MessageService) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
    this.onGetAllMills();
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
      },
        (error: any) => {
          this.statusService.spinnerSubject.next(false);
          if (error.status == "0") {
            alert(CommonMessage.ERROR.SERVER_ERROR)
          } else {
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
          }
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

  onGetAllMills() {
    let millDetail = new MillDetail();
    millDetail.millId = "1";
    millDetail.millName = "Kerinci";
    millDetail.countryId = '1';
    this.statusService.common.selectedMill = millDetail;
    this.selectedMillName = this.statusService.common.selectedMill.millName;
  }
}
