import { Injectable } from "@angular/core";
import { SidebarForm } from './sidebar-form';
import { SidebarRequest } from './sidebar-request';
import { MasterData } from '../../shared/constant/MasterData';
import { AppConstants } from 'src/app/app.constant';
import { ApiCallService } from '../../shared/service/APIService/ApiCall.service';
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
        sidebarForm.buisnessUnits = MasterData.businessUnit;
        sidebarForm.processLines = this.statusService.processLineMap.get(this.statusService.common.selectedMill.millId);
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
        sidebarForm.buisnessUnits = MasterData.businessUnit;
        sidebarForm.isActive = false;
        sidebarForm.pushRightClass = 'push-right';
        sidebarForm.kpiCategoryId = sidebarRequestData.kpiCategoryId;
        sidebarForm.kpiTypes = [
            { kpiId: "2", kpiTypeName: "ClO2 - Consumption (kg/ADt)" },
            { kpiId: "4", kpiTypeName: "Defoamer (kg/ADt)" },
            { kpiId: "5", kpiTypeName: "H2O2 (kg/ADt)" },
            { kpiId: "6", kpiTypeName: "NaOH Own (kg/ADt)" },
            { kpiId: "7", kpiTypeName: "NaOH Purchased (kg/ADt)" },
            { kpiId: "9", kpiTypeName: "Oxygen (kg/ADt)" },
            { kpiId: "10", kpiTypeName: "Sulphuric Acid H2SO4 (kg/ADt)" },
            { kpiId: "11", kpiTypeName: "White Liquor (m3/ADt)" },
            { kpiId: "13", kpiTypeName: "Power Consumption (MWh/ADt)" },
            { kpiId: "14", kpiTypeName: "Steam - LP" },
            { kpiId: "15", kpiTypeName: "Steam - MP (t/ADt)" },
            { kpiId: "17", kpiTypeName: "Water (M3/ADt)" },
            { kpiId: "18", kpiTypeName: "Wood Chip - Total (GMt/Adt)" },
            { kpiId: "19", kpiTypeName: "Chip Meter Avg (GMt/Adt)" },
            { kpiId: "20", kpiTypeName: "Consumption (GMt/Adt)" },
            { kpiId: "21", kpiTypeName: "Yield - Bleaching (%)" },
            { kpiId: "22", kpiTypeName: "Yield - Cooking (%)" },
            { kpiId: "23", kpiTypeName: "Yield - Screening (%)" },
        ];
        sidebarForm.mills = [
            { millId: "1", millName: 'Kerinci' },
            { millId: "2", millName: 'Rizaho' },
        ];
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