import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRole } from './user-role.model';

@Injectable()
export class UserRoleService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    getUserRoles(userRoles: UserRole[]) {
        userRoles = this.statusService.common.userRoles;
    }

    //Api call and error handling
    createUserRole(userRoles: UserRole[]) {
        let userRole = new UserRole();
        userRole.sNo = userRoles.length + 1;
        userRole.status = true;
        userRole.isReadOnly = false;
        userRoles.push(userRole);

        userRoles = [];
        this.getUserRoles(userRoles);
    }

    //Api call and error handling
    saveUserRole(userRole: UserRole) {
        userRole.isReadOnly = true;
    }
} 
