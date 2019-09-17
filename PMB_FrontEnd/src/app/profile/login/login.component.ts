import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { LoginService } from './login.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { LocalStorageService } from 'src/app/shared/service/localStorage/local-storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  msgs: Message[] = [];

  constructor(private router: Router,
    private statusService: StatusService,
    private messageService: MessageService,
    private localStorageService: LocalStorageService,
    private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginForm = this.loginService.createLoginForm();
  }

  onLogin() {
    const username = this.loginForm.controls.loginId.value;
    const password = this.loginForm.controls.userPassword.value;
    if (username === "") {
      this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR.USERNAME_VALIDATION });
      return null;
    }
    if (password === "") {
      this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR.PASSWORD_VALIDATION });
      return null;
    }

    this.statusService.spinnerSubject.next(true);
    const requestData = {
      loginId: username,
      userPassword: btoa(password)
    }
    this.loginService.validateUser(requestData).
      subscribe(
        (userDetail: UserDetail) => {
          this.statusService.common.userDetail = userDetail;
          this.localStorageService.storeUserDetails(userDetail);
          this.statusService.spinnerSubject.next(false);
          this.router.navigateByUrl("home/dashboard");
        },
        (error: any) => {
          this.statusService.spinnerSubject.next(false);
          if (error.status == "0") {
            alert(CommonMessage.ERROR.SERVER_ERROR)
          } else {
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
          }
        }
      );
  }

}
