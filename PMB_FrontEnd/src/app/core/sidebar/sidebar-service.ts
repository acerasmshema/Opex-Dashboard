import { Injectable } from "@angular/core";
import { SidebarForm } from './sidebar-form';
import { SidebarRequest } from './sidebar-request';
import { MasterData } from '../../shared/constant/MasterData';
import { AppConstants } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from '../../shared/service/api/api-call.service';
import { StatusService } from '../../shared/service/status.service';

@Injectable()
export class SidebarService {

    kpiTypeUrl = AppConstants.apiURLs.KPI_TYPE_URL;
    
    constructor(private apiCallService: ApiCallService,
        private statusService: StatusService) { }

    getDashboardSidebarForm(sidebarRequestData: SidebarRequest): SidebarForm {
        let sidebarForm = new SidebarForm();
        sidebarForm.collapsed = true;
        sidebarForm.hide = true;
        sidebarForm.countries = MasterData.country;
        sidebarForm.mills = [this.statusService.common.selectedMill];
        sidebarForm.buisnessUnits = this.statusService.common.buTypes;
        sidebarForm.processLines = this.statusService.common.processLines;
        sidebarForm.isActive = false;
        sidebarForm.pushRightClass = 'push-right';
        sidebarForm.kpiCategoryId = sidebarRequestData.kpiCategoryId;
        sidebarForm.kpiTypes = [];
        sidebarForm.frequencies = MasterData.dashboardFrequencies;

        return sidebarForm;
    }

    getBenchmarkSidebarForm(sidebarRequestData: SidebarRequest): SidebarForm {
        let sidebarForm = new SidebarForm();
        sidebarForm.collapsed = true;
        sidebarForm.hide = true;
        sidebarForm.buisnessUnits = this.statusService.common.buTypes;
        sidebarForm.isActive = false;
        sidebarForm.pushRightClass = 'push-right';
        sidebarForm.kpiCategoryId = sidebarRequestData.kpiCategoryId;
        sidebarForm.mills = this.statusService.common.mills;
      
        sidebarForm.frequencies = [
            { name: 'Monthly', code: '1' },
            { name: 'Quarterly', code: '2' },
            { name: 'Yearly', code: '3' }
        ];

        return sidebarForm;
    }

    getKpiTypes(requestData: any): any {
        return this.apiCallService.callGetAPIwithData(this.kpiTypeUrl, requestData);
    }
}