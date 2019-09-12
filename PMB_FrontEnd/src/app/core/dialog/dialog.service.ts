import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';
import { StatusService } from 'src/app/shared/service/status.service';
import { FormControl, Validators, FormBuilder, FormGroup } from '@angular/forms';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;

    constructor(private apiCallService: ApiCallService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private statusService: StatusService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }

    createUserForm(): FormGroup {
        let millRoles: MillRole[] = [];
        let millRole = new MillRole();
        millRole.mills = this.statusService.common.mills;
        millRole.userRoles = this.statusService.common.userRoles;
        millRoles.push(millRole);

        let userDetailForm = this.formBuilder.group({
            show: new FormControl(true),
            firstName: new FormControl("", [Validators.required, Validators.max(10)]),
            lastName: new FormControl(""),
            address: new FormControl(""),
            phone: new FormControl(""),
            countryList: this.formBuilder.array(this.statusService.common.countryList),
            departmentList: this.formBuilder.array(this.statusService.common.departmentList),
            email: ['', [Validators.required, Validators.email]],
            millRoles: this.formBuilder.array(millRoles),
        });

        return userDetailForm ;
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