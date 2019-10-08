import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { StatusService } from '../../shared/service/status.service';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';
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
  mills: MillDetail[] = [];
  selectedMillName: string;
  showTabs: boolean;

  constructor(private router: Router,
    private loginService: LoginService,
    private statusService: StatusService,
    private translate: TranslateService,
    private commonService: CommonService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    let userDetail = this.statusService.common.userDetail;
    if (userDetail !== undefined) {
      userDetail.millRoles.forEach(millRole => {
        this.mills.push(millRole.selectedMill);
      });

      this.statusService.common.selectedMill = this.mills[0];
      this.statusService.common.selectedRole = userDetail.millRoles[0].selectedUserRole;
      this.selectedMillName = this.mills[0].millName;
      this.showTabs = this.statusService.common.selectedRole.showUserManagement;
      this.user = userDetail.firstName;
    }
  }

  setLang(lang: string) {
    this.translate.use(lang);
  }

  logOut() {
    const requestData = {
      username: this.statusService.common.userDetail.username
    }
    this.loginService.logOut(requestData).
      subscribe(
        (response: any) => {
          
        },
        (error: any) => {
          this.commonService.handleError(error);
        });

    this.localStorageService.removeUserDetail();
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
          this.showTabs = this.showTabs = this.statusService.common.selectedRole.showUserManagement;
        }
      });
      if (this.showTabs && window.location.pathname === '/user') {
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
