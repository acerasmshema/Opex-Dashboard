import { Injectable } from '@angular/core';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../service/APIService/ApiCall.service';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl = AppConstants.apiURLs.LOGIN_URL;
  logoutUrl = AppConstants.apiURLs.LOGIN_URL;

  constructor(private apiCallService: ApiCallService) { }

  public validateUser(data: object) {
    return this.apiCallService.callAPIwithData(this.loginUrl, data);
  }

  public logOut(data: object) {
    return this.apiCallService.callPutAPIwithData(this.logoutUrl, data);
  }
}
