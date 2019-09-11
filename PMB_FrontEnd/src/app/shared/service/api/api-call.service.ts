import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ApiCallService {

  constructor(private httpClient: HttpClient, private _router: Router) { }

  public callGetAPIwithOutData(url: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.httpClient.get(url, httpOptions);
  }

  public callGetAPIwithData(url: string, data: object) {

    const mergedHeaders = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*',
      ...data
    };
    const httpOptions = {
      headers: new HttpHeaders(mergedHeaders)
    };
    return this.httpClient.get(url, httpOptions);
  }

  public callPutAPIwithData(url: string, data: object) {

    const mergedHeaders = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Access-Control-Allow-Origin': '*'
    };
    const httpOptions = {
      headers: new HttpHeaders(mergedHeaders)
    };
    return this.httpClient.put(url, data, httpOptions);
  }

  public callAPIwithData(url: string, data: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.httpClient.post(url, data, httpOptions);
  }

  errorHandler(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ProgressEvent) {    
    alert("Server Error");
    } else {
      return "e";
    }
    
    
  }

}
