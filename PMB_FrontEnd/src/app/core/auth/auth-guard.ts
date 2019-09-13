import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'src/app/shared/service/localStorage/local-storage.service';
import { StatusService } from 'src/app/shared/service/status.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    constructor(private router: Router,
        private statusService: StatusService,
        private localStorageService: LocalStorageService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
        console.log(state.url)
        let user = this.localStorageService.fetchUserDetail();
        if (!user) {
            this.router.navigateByUrl('/login');
        }
        else {
            let userDetail = this.statusService.common.userDetail;
            if (!userDetail) {
                this.statusService.common.userDetail = user;
                this.statusService.common.selectedMill = this.statusService.common.userDetail.millRoles[0].selectedMill;
            }
            if (state.url === '/user' && !this.statusService.common.selectedRole) {
                const roleName = this.statusService.common.userDetail.millRoles[0].selectedUserRole.roleName;
                if (roleName !== "Admin" && roleName !== 'Senior Management') {
                    this.router.navigateByUrl('/login');
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
    }

}