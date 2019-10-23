import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/do';
import { Observable } from 'rxjs/Observable';
import { LocalStorageService } from 'src/app/shared/service/localStorage/local-storage.service';
import { Router } from '@angular/router';
import { StatusService } from 'src/app/shared/service/status.service';
@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private localStorageService: LocalStorageService,
        private statusService: StatusService,
        private router: Router) { }

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
                        const authorizationToken = event.headers.get("Authorization");
                        if (authorizationToken !== undefined && authorizationToken !== null) {
                            let userDetail = this.statusService.common.userDetail;
                            if (userDetail !== undefined && userDetail !== null) {
                                userDetail.token = authorizationToken;
                                this.localStorageService.storeUserDetails(userDetail);
                            }
                        }
                    }
                },
                (err: any) => {
                    if (err instanceof HttpErrorResponse) {
                        if (err.status === 0 || err.status === 401) {
                            alert("Session Timeout")
                            this.localStorageService.removeUserDetail();
                            this.router.navigateByUrl('login');
                        }
                    }
                });
    }
}