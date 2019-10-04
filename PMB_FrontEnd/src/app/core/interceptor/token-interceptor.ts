import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/do';
import { Observable } from 'rxjs/Observable';
import { LocalStorageService } from 'src/app/shared/service/localStorage/local-storage.service';
import { Router } from '@angular/router';
@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private localStorageService: LocalStorageService, private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let user = this.localStorageService.fetchUserDetail();

        request = request.clone({
            setHeaders: {
                Authorization: (user !== null) ? `Bearer ${user.token}` : ""
            }
        });


        return next.handle(request)
            .do(
                (event: any) => {
                    if (event instanceof HttpResponse) {
                        console.log(event.headers);
                    }
                },
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        this.router.navigateByUrl('login');
                    }
                });
    }
}