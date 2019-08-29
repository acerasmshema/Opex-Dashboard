import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { LoginService } from './login.service';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService, LoginService, LocalStorageService]
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  msgs: Message[] = [];

  constructor(private localStorageService: LocalStorageService,
    private fb: FormBuilder,
    private router: Router,
    private messageService: MessageService,
    private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginForm = this.fb.group({ loginId: "", userPassword: "" });
    this.localStorageService.emptyLocalStorage();
  }

  validateUser(data: any) {

    if (data.loginId == "") {
      this.messageService.add({ severity: 'error', summary: '', detail: 'Username is required' });
      return null;
    }
    if (data.userPassword == "") {
      this.messageService.add({ severity: 'error', summary: '', detail: 'Password is required' });
      return null;
    }

    this.loginService.validateUser(data).subscribe((data: any) => {

      if (data == "e") {
        this.messageService.add({ severity: 'error', summary: '', detail: 'Invalid user credentials' });
        return null;
      } else {
        this.localStorageService.storeUserDetails(data.userName, data.userRole, data.loginId);
        this.router.navigateByUrl("home/dashboard");
      }
    });
  }

}
