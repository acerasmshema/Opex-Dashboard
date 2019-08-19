import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { LoginService } from '../../shared/login/login.service';
import { StatusService } from '../../shared/service/status.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [LocalStorageService]
})
export class HeaderComponent implements OnInit {

  user: any;
  loginId: any;
  mills: any = [];

  constructor(private router: Router,
    private loginService: LoginService,
    private statusService: StatusService,
    private translate: TranslateService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
    this.getMills();
  }

  setLang(lang: string) {
    this.translate.use(lang);
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
  
  onChangeMill(millId: string) {
    if(this.statusService.common.selectedMill.millId !== millId) {
      this.statusService.changeMill.next(millId);
    }
  }

  getMills() {
    this.statusService.common.mills =  [
      { millId: "1", millName: 'Kerinci' },
      { millId: "2", millName: 'Rizaho' },
  ];
    this.mills = this.statusService.common.mills;
    this.statusService.common.selectedMill =  { millId: "1", millName: 'Kerinci' }
  }
  
}
