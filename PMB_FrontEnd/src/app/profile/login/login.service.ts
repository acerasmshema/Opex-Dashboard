import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl = API_URL.apiURLs.LOGIN_URL;
  logoutUrl = API_URL.apiURLs.LOGOUT_URL;

  constructor(private fb: FormBuilder,
    private apiCallService: ApiCallService) { }

  createLoginForm(): FormGroup {
    return this.fb.group({
      username: new FormControl(""),
      password: new FormControl("")
    });
  }

  public validateUser(requestData: object) {
    return this.apiCallService.callAPIwithData(this.loginUrl, requestData);
  }

  public logOut(requestData: object) {
    return this.apiCallService.callPutAPIwithData(this.logoutUrl, requestData);
  }
}
