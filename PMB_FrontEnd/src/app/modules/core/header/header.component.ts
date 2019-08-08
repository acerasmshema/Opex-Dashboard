import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { TranslateService } from '../../shared/service/translate/translate.service';
import { LoginService } from '../../shared/login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers: [LocalStorageService]
})
export class HeaderComponent implements OnInit {
  user: any;
  loginId: any;
  
  constructor(private router: Router,
    private loginService: LoginService,
    private translate: TranslateService,
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.user = this.localStorageService.fetchUserName();
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

}
