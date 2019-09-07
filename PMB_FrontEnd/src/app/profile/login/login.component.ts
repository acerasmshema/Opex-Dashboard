import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { LoginService } from './login.service';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  msgs: Message[] = [];

  constructor(private localStorageService: LocalStorageService,
    private fb: FormBuilder,
    private router: Router,
    private statusService: StatusService,
    private messageService: MessageService,
    private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginForm = this.fb.group({ loginId: "", userPassword: "" });
    this.localStorageService.emptyLocalStorage();
  }

  validateUser(data: any) {
    if (data.loginId == "") {
      this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR.USERNAME_VALIDATION });
      return null;
    }
    if (data.userPassword == "") {
      this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR.PASSWORD_VALIDATION });
      return null;
    }
    this.statusService.spinnerSubject.next(true);
    this.loginService.validateUser(data).
      subscribe((data: any) => {
        this.statusService.spinnerSubject.next(false);

        if (data == "e") {
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR.INVALID_USER });
        } else {
          this.localStorageService.storeUserDetails(data.userName, data.userRole, data.loginId);
          this.router.navigateByUrl("/home");
        }
      });
  }

}
