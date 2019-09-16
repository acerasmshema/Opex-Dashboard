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
  isShow: boolean;

  constructor(private router: Router,
    private loginService: LoginService,
    private statusService: StatusService,
    private translate: TranslateService,
    private commonService: CommonService,
    private localStorageService: LocalStorageService,
    private messageService: MessageService) { }

  ngOnInit() {
    let userDetail = this.statusService.common.userDetail;
    if (userDetail !== undefined) {
      userDetail.millRoles.forEach(millRole => {
        this.mills.push(millRole.selectedMill);
      });

      this.statusService.common.selectedMill = this.mills[0];
      this.statusService.common.selectedRole = userDetail.millRoles[0].selectedUserRole;
      this.selectedMillName = this.mills[0].millName;
      const roleName = this.statusService.common.selectedRole.roleName;
      this.isShow = (roleName === "Admin" || roleName === 'Senior Management') ? true : false;
      this.user = userDetail.firstName;
    }
  }

  setLang(lang: string) {
    this.translate.use(lang);
  }

  logOut() {
    this.loginId = this.statusService.common.userDetail.username;
    const requestData = {
      loginId: this.loginId
    }
    this.loginService.logOut(requestData).
      subscribe(
        (response: any) => {
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

    this.commonService.clearStatus();
    this.router.navigateByUrl('login');
  }

  onChangeMill(millId: string) {
    if (this.statusService.common.selectedMill.millId !== millId) {
      this.statusService.kpiCategoryMap = new Map<string, any>();
      this.statusService.consumptionDetailMap = new Map<string, ConsumptionDetiail>();

      let millRoles = this.statusService.common.userDetail.millRoles;
      millRoles.forEach(millRole => {
        if (millRole.selectedMill.millId === millId) {
          this.statusService.common.selectedMill = millRole.selectedMill;
          this.selectedMillName = this.statusService.common.selectedMill.millName;
          this.statusService.common.selectedRole = millRole.selectedUserRole;
          this.isShow = (this.statusService.common.selectedRole.roleName === "Admin" || this.statusService.common.selectedRole.roleName === "Senior Management") ? true : false;
        }
      });
      if (this.isShow && window.location.pathname === '/user') {
        this.statusService.refreshUserList.next(true);
      }
      else if (window.location.pathname === '/user') {
        this.router.navigateByUrl('/home');
      }
      else {
        this.statusService.changeMill.next();
      }
    }
  }
}
