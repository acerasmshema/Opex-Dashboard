import { Injectable } from "@angular/core";
import { SidebarForm } from './sidebar-form';
import { SidebarRequest } from './sidebar-request';
import { MasterData } from '../../shared/constant/MasterData';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from '../../shared/service/api/api-call.service';
import { StatusService } from '../../shared/service/status.service';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';

@Injectable()
export class SidebarService {

    kpiTypeUrl = API_URL.apiURLs.KPI_TYPE_URL;

    constructor(private apiCallService: ApiCallService,
        private messageService: MessageService,
        private commonService: CommonService,
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

    getMills(sidebarForm: SidebarForm) {
        if (this.statusService.common.mills.length === 0) {
            this.commonService.getAllMills().
                subscribe(
                    (mills: MillDetail[]) => {
                        sidebarForm.mills = mills;
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
            sidebarForm.mills = this.statusService.common.mills;
        }
    }
}
