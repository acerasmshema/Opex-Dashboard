import { HttpClient, HttpRequest, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Headers, Http, BaseRequestOptions, } from '@angular/http';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { RequestHelperService } from '../request-helper.service';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

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
    return this.httpClient.get(url, httpOptions).pipe(catchError(this.errorHandler));
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
    return this.httpClient.get(url, httpOptions).pipe(catchError(this.errorHandler));
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
    return this.httpClient.put(url, data, httpOptions).pipe(catchError(this.errorHandler));
  }

  public callAPIwithData(url: string, data: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
    return this.httpClient.post(url, data, httpOptions).pipe(catchError(this.errorHandler));
  }


  errorHandler(error: HttpErrorResponse) {
   /*  if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
      return "e";
    } else {
      console.error(error.error.message);
      return "e";
    } */
    return "e";
  }

}
