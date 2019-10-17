import { Injectable } from "@angular/core";
import { ApiCallService } from '../api/api-call.service';
import { StatusService } from '../status.service';
import { API_URL } from '../../constant/API_URLs';
import { FormGroup, FormControl } from '@angular/forms';
import { SidebarForm } from 'src/app/core/sidebar/sidebar-form';
import { Country } from '../../models/country.model';
import { Department } from 'src/app/setup/user-management/user-detail/department.model';
import { Observable } from 'rxjs';
import { CommonMessage } from '../../constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { ConsumptionConfig } from 'src/app/setup/threshold-management/consumption-configuration/consumption-config.model';

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

    public getAllMills(): Observable<any> {
        const requestData = {
            countryIds: "1,2"
        }
        return this.apiCallService.callGetAPIwithData(this.allMills, requestData);
    }

    public getAllBuType(object: any) {
        if (this.statusService.common.buTypes.length === 0) {
            this.apiCallService.callGetAPIwithOutData(this.allBuTypes)
                .subscribe(
                    (buTypes: any) => {
                        this.statusService.common.buTypes = buTypes;
                        if (object instanceof SidebarForm) {
                            object.buisnessUnits = buTypes;
                            setTimeout(() => {
                                this.setDropDownFont();
                            }, 100);
                        }
                        else if (object instanceof ConsumptionConfig) {
                            object.buTypes = buTypes;
                        }
                        else if (object instanceof FormGroup) {
                            const buTypeList: any = object.controls.buTypeList;
                            let buTypeControl = buTypeList.controls;
                            buTypes.forEach(buType => {
                                buTypeControl.push(new FormControl(buType));
                            });
                        }
                    },
                    (error: any) => {
                        this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                    });
        }
        else if (object !== null) {
            if (object instanceof SidebarForm) {
                object.buisnessUnits = this.statusService.common.buTypes;
            }
            else if (object instanceof ConsumptionConfig) {
                object.buTypes = this.statusService.common.buTypes;
            }
            else if (object instanceof FormGroup) {
                const buTypeList: any = object.controls.buTypeList;
                let buTypeControl = buTypeList.controls;
                const buTypes = this.statusService.common.buTypes;
                buTypes.forEach(buType => {
                    buTypeControl.push(new FormControl(buType));
                });
            }
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
                        this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
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
                            let departmentControls = departmentList.controls;
                            departments.forEach(department => {
                                departmentControls.push(new FormControl(department));
                            });
                        }
                        this.statusService.common.departmentList = departments;
                    },
                    (error: any) => {
                        this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                    }
                );
        }
        else {
            const departments = this.statusService.common.departmentList;
            const departmentList: any = userDetailForm.controls.departmentList;
            let departmentControls = departmentList.controls;
            departments.forEach(department => {
                departmentControls.push(new FormControl(department));
            });
        }
    }

    public getAllUserRole(activeUserRoles: boolean): Observable<any> {
        const requestData = {
            activeRoles: "" + activeUserRoles
        }
        return this.apiCallService.callGetAPIwithData(this.allUserRole, requestData);
    }

    public clearStatus() {
        this.statusService.common.buTypes = [];
        this.statusService.common.countryList = [];
        this.statusService.common.mills = [];
        this.statusService.common.departmentList = [];
        this.statusService.common.processLines = [];
        this.statusService.common.selectedMill = null;
        this.statusService.common.selectedRole = null;
        this.statusService.common.userDetail = null;
    }

    setDropDownFont() {
        let elements: any = document.getElementsByClassName("ui-dropdown-label ui-inputtext ui-corner-all");
        if (elements !== undefined) {
            for (let index = 0; index < elements.length; index++) {
                const element = elements[index];
                element.style.fontSize = "12px";
            }
        }
    }

    handleError(error: any) {
        this.statusService.spinnerSubject.next(false);
        if (error.status !== 0)
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
    }
}