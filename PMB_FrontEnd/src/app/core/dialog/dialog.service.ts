import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';
import { StatusService } from 'src/app/shared/service/status.service';
import { UserRoleService } from 'src/app/user-management/user-role/user-role.service';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;

    constructor(private apiCallService: ApiCallService,
        private userRoleService: UserRoleService,
        private statusService: StatusService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }

    createUserForm(): UserDetail {
        let user = new UserDetail();
        user.showDialog = true;

        let millRoles: MillRole[] = [];
        let millRole = new MillRole();
        millRole.mills = this.statusService.common.mills;
        millRole.userRoles = [];
        this.userRoleService.getUserRoles(millRole.userRoles)
        millRoles.push(millRole);

        this.getDepartmentList(user);
        this.getCountryList(user);
        user.millRoles = millRoles;
        return user;
    }
    //Api Call and error Handling
    getDepartmentList(user: UserDetail) {
        user.departmentList = this.statusService.common.departmentList;
    }

    //Api Call and error Handling
    getCountryList(user: UserDetail) {
        user.countryList = this.statusService.common.countryList;
    }

}