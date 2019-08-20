import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptionsArgs, RequestOptions,ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class RequestHelperService {

    constructor(private http: Http) {}


    private extractData(res: Response) {
        let body;
        if (res.text()) {
            console.log("yes m here");
            if(res.headers.get("refreshedToken")){
                localStorage.setItem("authToken",res.headers.get("refreshedToken"));
            }
            body = res.json(); 
        }
        return body || {};
    }

    private extractDataReport(res: Response) {
        let blob: Blob = res.blob();
        
       return blob;
    }
  private handleError(error: any) 
  {
      //console.log("yoyo");
     // console.log(error);
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        const errMsg = (error.message) ? error.message :
        error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(error);
    }

  public request(url: string,
                   options?: RequestOptionsArgs): Promise<Response> {
        return this.http.request(url, options)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    public requestGet(url: string): Promise<Response> {
        return this.http.get(url)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    public requestPost(url: string, data: any, headers: Headers): Observable<any> {

        return this.http.post(url, data, {headers:headers}).map(this.extractData).catch(this.handleError);
    }

    public downloadReport(url: string, data: any, headers: Headers): Observable<any> {

        return this.http.post(url, data, {headers:headers,responseType: ResponseContentType.Blob}).map(this.extractDataReport);
    }


    public requestPut(url: string,
                      data: any,
                      options?: RequestOptionsArgs): Promise<Response> {
        return this.http.put(url, data, options)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    public requestDelete(url: string): Promise<Response> {
        return this.http.delete(url)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

}