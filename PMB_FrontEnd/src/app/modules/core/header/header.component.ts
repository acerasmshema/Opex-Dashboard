import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { LoginService } from '../../shared/login/login.service';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';
import { SidebarRequest } from '../sidebar/sidebar-request';
import { StatusService } from '../../shared/service/status.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [LocalStorageService]
})
export class HeaderComponent implements OnInit {

  allProcessLines = AppConstants.apiURLs.ALL_PROCESS_LINES_URL;

  user: any;
  loginId: any;
  processUnitLegends: any[] = [];

  constructor(private router: Router,
    private apiCallService: ApiCallService,
    private loginService: LoginService,
    private translate: TranslateService,
    private statusService: StatusService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
    this.processUnitLegends = this.getProcessUnitLegends();
  }
  setLang(lang: string) {
    this.translate.use(lang);
  }

  getProcessUnitLegends(): any {
    let millId = "1";
    const requestData = {
      millId: millId
    }
    this.getProcessLines(requestData).
      subscribe((processLines: any) => {
        this.statusService.processLineMap.set(millId, processLines);
        let sidebarRequest = new SidebarRequest();
        sidebarRequest.isShow = false;
        this.statusService.sidebarSubject.next(sidebarRequest);
        this.processUnitLegends = processLines;
      });
  }


  getProcessLines(millIdData: any) {
    return this.apiCallService.callGetAPIwithData(this.allProcessLines, millIdData);
  }

  logOut() {
    this.loginId = this.localStorageService.fetchloginId();
    var data = {
      loginId: this.loginId
    }
    this.loginService.logOut(data).subscribe((data: any) => {
      console.log("Log Out");
    });
    this.router.navigateByUrl('login');
  }

}
