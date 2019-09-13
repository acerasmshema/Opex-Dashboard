import { Injectable } from "@angular/core";
import { ApiCallService } from '../api/api-call.service';
import { StatusService } from '../status.service';
import { API_URL } from '../../constant/API_URLs';
import { UserRole } from 'src/app/user-management/user-role/user-role.model';
import { FormGroup, FormControl } from '@angular/forms';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { Country } from '../../models/country.model';
import { Department } from 'src/app/user-management/user-detail/department.model';
import { MillDetail } from '../../models/mill-detail.model';
import { CommonMessage } from '../../constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';

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

    public getAllMills(millForm: any) {
        if (this.statusService.common.mills.length === 0) {
            const requestData = {
                countryIds: "46,104"
            }
            this.apiCallService.callGetAPIwithData(this.allMills, requestData).
                subscribe(
                    (mills: MillDetail[]) => {
                        if (millForm instanceof SidebarForm) {
                            millForm.mills = mills;
                        }
                        else if (millForm instanceof MillRole) {
                            millForm.mills = mills;
                        }
                        this.statusService.common.mills = mills;
                    },
                    (error: any) => {
                        this.statusService.spinnerSubject.next(false);
                        if (error.status == "0") {
                            alert(CommonMessage.ERROR.SERVER_ERROR)
                        } else {
                            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                        }
                    });
        }
        else {
            if (millForm instanceof SidebarForm) {
                millForm.mills = this.statusService.common.mills;
            }
            else if (millForm instanceof MillRole) {
                millForm.mills = this.statusService.common.mills;
            }
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

    public getAllCountry(userDetailForm: any) {
        if (this.statusService.common.countryList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allCountry).
                subscribe(
                    (countries: Country[]) => {
                        if (userDetailForm instanceof FormGroup) {
                            const countryList: any = userDetailForm.controls.countryList;
                            let countryControl = countryList.controls;
                            countries.forEach(country => {
                                countryControl.push(new FormControl(country));
                            });
                        }
                        this.statusService.common.countryList = countries;
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
        else {
            const countries = this.statusService.common.countryList;
            const countryList: any = userDetailForm.controls.countryList;
            let countryControl = countryList.controls;
            countries.forEach(country => {
                countryControl.push(new FormControl(country));
            });
        }
    }

    public getAllDepartment(userDetailForm: any) {
        if (this.statusService.common.departmentList.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allDepartment).
                subscribe(
                    (departments: Department[]) => {
                        if (userDetailForm instanceof FormGroup) {
                            const departmentList: any = userDetailForm.controls.departmentList;
                            let departmentControl = departmentList.controls;
                            departments.forEach(department => {
                                departmentControl.push(new FormControl(department));
                            });
                        }
                        this.statusService.common.departmentList = departments;
                    },
                    (error: any) => {
                        console.log("Error handling")
                    }
                );
        }
        else {
            const departments = this.statusService.common.departmentList;
            const departmentList: any = userDetailForm.controls.departmentList;
            let departmentControl = departmentList.controls;
            departments.forEach(department => {
                departmentControl.push(new FormControl(department));
            });
        }
    }


    public getAllUserRole(userRoles: UserRole[], activeAll: boolean) {
        if (this.statusService.common.userRoles.length === 0) {
            const requestData = {
                activeRoles: "" + activeAll
            }
            this.apiCallService.callGetAPIwithData(this.allUserRole, requestData)
                .subscribe(
                    (roleList: UserRole[]) => {
                        userRoles.push(...roleList);
                        this.statusService.common.userRoles = roleList;
                    },
                    (error: any) => {
                        console.log("error in user role");
                    }

                );
        }
        else {
            userRoles.push(...this.statusService.common.userRoles);
        }
    }

    clearStatus() {
        this.statusService.common.buTypes = [];
        this.statusService.common.countryList = [];
        this.statusService.common.userRoles = [];
        this.statusService.common.mills = [];
        this.statusService.common.departmentList = [];
        this.statusService.common.processLines = [];
        this.statusService.common.selectedMill = null;
        this.statusService.common.selectedRole = null;
        this.statusService.common.userDetail = null;
    }
}