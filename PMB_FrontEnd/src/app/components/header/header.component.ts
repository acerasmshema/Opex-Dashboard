import { Component, OnInit } from '@angular/core';
import { TranslateService} from '../../services/translate/translate.service';
import { LocalStorageService } from '../../services/localStorage/local-storage.service';
import { LoginService } from '../../services/login/login.service';
import { Router } from '@angular/router';
import { Compiler } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  providers:[LocalStorageService]
})
export class HeaderComponent implements OnInit {
user:any;
loginId:any;
  constructor(private _runtimeCompiler: Compiler,private router: Router,private loginService:LoginService,private translate:TranslateService,private localStorageService:LocalStorageService ) { }

  ngOnInit() {
    this.user=this.localStorageService.fetchUserName();
  }
  setLang(lang: string) {
    this.translate.use(lang);
  }

  logOut(){
    this.loginId=this.localStorageService.fetchloginId();
    var data={
      loginId:this.loginId
    }
    this.loginService.logOut(data).subscribe((data: any) => {
      });
      this.router.navigateByUrl('login');
      

  }

}
