import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRole } from './user-role.model';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Injectable()
export class UserRoleService {

    constructor(private statusService: StatusService,
        private apiCallService: ApiCallService) { }

    //Api call and error handling
    getUserRoles(userRoles: UserRole[]) {
        let roles = ["Admin", "Deparment Head", "BCID", "MillOps"];
        for (let index = 1; index < 5; index++) {
            let userRole = new UserRole();
            userRole.userRoleId = index;
            userRole.sNo = index;
            userRole.roleName = roles[index - 1];
            userRole.status = true;
            userRole.isReadOnly = true;
            userRoles.push(userRole);
        }
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
