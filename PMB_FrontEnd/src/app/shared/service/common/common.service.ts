import { Injectable } from "@angular/core";
import { ApiCallService } from '../api/api-call.service';
import { StatusService } from '../status.service';
import { API_URL } from '../../constant/API_URLs';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';
import { FormGroup } from '@angular/forms';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { Country } from '../../models/country.model';
import { Department } from 'src/app/user-management/user-detail/department.model';
import { MillDetail } from '../../models/mill-detail.model';
import { CommonMessage } from '../../constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';

@Injectable()
export class CommonService {

    allMills = API_URL.apiURLs.ALL_MILLS_URL;
    allBuTypes = API_URL.apiURLs.ALL_BU_TYPE_URL;
    allCountry = API_URL.user_api_URLs.ALL_COUNTRY;
    allDepartment = API_URL.user_api_URLs.ALL_DEPARTMENT;
    allUserRole = API_URL.user_api_URLs.ALL_USER_ROLE;

    constructor(private apiCallService: ApiCallService,
        private messageService: MessageService,
        private statusService: StatusService) { }

    public getAllMills(form: any) {
        if (this.statusService.common.mills.length === 0) {
            const requestData = {
                countryIds: "46,135"
            }
            this.apiCallService.callGetAPIwithData(this.allMills, requestData).
                subscribe(
                    (mills: MillDetail[]) => {
                        this.statusService.common.mills = mills;
                        if (form !== null)
                            form.mills = mills;
                    },
                    (error: any) => {
                        this.statusService.spinnerSubject.next(false);
                        if (error.status == "0") {
                            alert(CommonMessage.ERROR.SERVER_ERROR)
                        } else {
                            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                        }
                    });
        } else if (form !== null) {
            form.mills = this.statusService.common.mills;
        }

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

    public getAllCountry(): any {
        if (this.statusService.common.countryList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allCountry).
                subscribe(
                    (countries: Country[]) => {
                        this.statusService.common.countryList = countries;
                        console.log(countries);
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
    }

    public getAllDepartment(): any {
        if (this.statusService.common.departmentList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allDepartment).
                subscribe(
                    (departments: Department[]) => {

                        this.statusService.common.departmentList = departments;
                        console.log(departments);
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
    }

    public getAllUserRole() {
        if (this.statusService.common.userRoles.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allUserRole)
                .subscribe(
                    (roleList: UserRole[]) => {
                        this.statusService.common.userRoles = roleList;
                    },
                    (error: any) => {
                        console.log("error in user role");
                    }

                );
        }
    }
}