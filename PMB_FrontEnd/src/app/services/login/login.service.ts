import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/services/APIService/ApiCall.service';
import { AppConstants } from '../../app.constant';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loginUrl=AppConstants.apiURLs.LOGIN_URL;
  logoutUrl=AppConstants.apiURLs.LOGIN_URL;

  constructor(private apiCallService:ApiCallService) { }

  public validateUser(data:object){
    
    return this.apiCallService.callAPIwithData(this.loginUrl,data);
  } 
  
  public logOut(data:object){
    return this.apiCallService.callPutAPIwithData(this.logoutUrl,data);
  } 
 
}
