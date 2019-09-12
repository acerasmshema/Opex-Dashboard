import { Injectable } from '@angular/core';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRole } from './user-role.model';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Injectable()
export class UserRoleService {

    constructor(private statusService: StatusService,
        private commonService: CommonService,
        private apiCallService: ApiCallService) { }

    getUserRoles(userRoles: UserRole[]) {
        this.commonService.getAllUserRole(userRoles, true);
    }

    //Api call and error handling
    createUserRole(userRoles: UserRole[]) {
        let userRole = new UserRole();
        userRole.active = true;
        userRole.isEnable = true;
        userRoles.push(userRole);

        userRoles = [];
        this.getUserRoles(userRoles);
    }

    //Api call and error handling
    saveUserRole(userRole: UserRole) {
        userRole.isEnable = true;
    }
} 
