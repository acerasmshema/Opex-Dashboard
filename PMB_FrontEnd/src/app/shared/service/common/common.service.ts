import { Injectable } from "@angular/core";
import { ApiCallService } from '../api/api-call.service';
import { StatusService } from '../status.service';
import { API_URL } from '../../constant/API_URLs';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';
import { FormGroup } from '@angular/forms';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { Country } from '../../models/country.model';
import { Department } from 'src/app/user-management/user-detail/department.model';

@Injectable()
export class CommonService {

    allMills = API_URL.apiURLs.ALL_MILLS_URL;
    allBuTypes = API_URL.apiURLs.ALL_BU_TYPE_URL;
    allCountry = API_URL.user_api_URLs.ALL_COUNTRY;
    allDepartment = API_URL.user_api_URLs.ALL_DEPARTMENT;
    allUserRole = API_URL.user_api_URLs.ALL_USER_ROLE;

    constructor(private apiCallService: ApiCallService,
        private statusService: StatusService) { }

    public getAllMills(data: any): any {
        return this.apiCallService.callGetAPIwithData(this.allMills, data);
    }

    public getAllBuType(sidebarForm: SidebarForm) {
        if (this.statusService.common.buTypes.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allBuTypes)
                .subscribe(
                    (buTypes: any) => {
                        this.statusService.common.buTypes = buTypes;
                        if (sidebarForm !== null)
                            sidebarForm.buisnessUnits = buTypes;
                    },
                    (error: any) => {

                    });
        }
        else if (sidebarForm !== null) {
            sidebarForm.buisnessUnits = this.statusService.common.buTypes;
        }
    }

    public getAllCountry(userDetailForm: FormGroup): any {
        let countryList: any = userDetailForm.controls.countryList;

        if (this.statusService.common.countryList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allCountry).
                subscribe(
                    (countries: Country[]) => {
                        countryList.push(...countries);
                        this.statusService.common.countryList = countries;
                        console.log(countries);
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
        else {
            countryList = this.statusService.common.countryList;
        }
    }

    public getAllDepartment(userDetailForm: FormGroup): any {
        let departmentList: any = userDetailForm.controls.departmentList;

        if (this.statusService.common.departmentList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allDepartment).
                subscribe(
                    (departments: Department[]) => {
                        departmentList.push(...departments);
                        this.statusService.common.departmentList = departments;
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
        else {
            departmentList = this.statusService.common.departmentList;
        }
    }

    public getAllUserRole(userRoles: UserRole[]) {
        if (this.statusService.common.userRoles.length === 0) {
            let requestData: any = null;
            this.apiCallService.callGetAPIwithData(this.allUserRole, requestData)
                .subscribe(
                    (roleList: UserRole[]) => {
                        userRoles = roleList;
                        this.statusService.common.userRoles = roleList;
                    },
                    (error: any) => {
                        console.log("error in user role");
                    }

                );
        } else {
            userRoles = this.statusService.common.userRoles;
        }

    }
}