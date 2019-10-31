import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { AnnualTarget } from './annual-target';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { MessageService } from 'primeng/primeng';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { FormGroup } from '@angular/forms';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';

@Injectable()
export class AnnualConfigService {

    annualCongigUrl = API_URL.threshold_api_URLs.ANNUAL_CONFIG;
    updateAnnualConfigUrl = API_URL.threshold_api_URLs.UPDATE_ANNUAL_CONFIG;
    anualConfigUrl = API_URL.threshold_api_URLs.UPDATE_ANNUAL_CONFIG;
    addAnnualConfigUrl = API_URL.threshold_api_URLs.CREATE_ANNUAL_CONFIG;

    constructor(private statusService: StatusService,
        private messageService: MessageService,
        private commonService: CommonService,
        private apiCallService: ApiCallService) { }

    getAnnualTargets(annualTargets: AnnualTarget[]) {
        this.statusService.spinnerSubject.next(true);
        
        let requestData = {
            millId: this.statusService.common.selectedMill.millId
        };

        this.apiCallService.callGetAPIwithData(this.annualCongigUrl, requestData)
            .subscribe(
                (annualConfigList: AnnualTarget[]) => {
                    annualConfigList.forEach(annualConfig => {
                        if (!annualConfig.isDefault) {
                            annualConfig.buTypeSortName = annualConfig.buType.buTypeName;
                        }
                        else {
                            annualConfig.buTypeSortName = annualConfig.buType.buTypeName + ' (DEFAULT)';
                        }
                        annualTargets.push(annualConfig);
                    });
                    
                    this.statusService.spinnerSubject.next(false); 
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }

    addAnnualTarget(annualTargetForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let annualTarget = new AnnualTarget();
        annualTarget.buType = annualTargetForm.controls.buType.value;
        annualTarget.year = annualTargetForm.controls.year.value;
        annualTarget.workingDays = annualTargetForm.controls.workingDays.value;
        annualTarget.annualTarget = annualTargetForm.controls.annualTarget.value;
        annualTarget.createdBy = annualTargetForm.controls.createdBy.value;
        annualTarget.isDefault = annualTargetForm.controls.isDefault.value;
        annualTarget.updatedBy = this.statusService.common.userDetail.username;
        annualTarget.millId = annualTargetForm.controls.millId.value;
        annualTarget.kpiId = annualTargetForm.controls.kpiId.value;

        this.apiCallService.callAPIwithData(this.addAnnualConfigUrl, annualTarget).
            subscribe(
                response => {
                    annualTargetForm.controls.dateError.setValue('');
                    annualTargetForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Annual Target " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshAnnualTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        annualTargetForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }

    updateAnnualTarget(annualTargetForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let annualTarget = new AnnualTarget();
        annualTarget.annualConfigurationId = annualTargetForm.controls.annualConfigurationId.value;
        annualTarget.buType = annualTargetForm.controls.buType.value;
        annualTarget.year = annualTargetForm.controls.year.value;
        annualTarget.workingDays = annualTargetForm.controls.workingDays.value;
        annualTarget.annualTarget = annualTargetForm.controls.annualTarget.value;
        annualTarget.createdBy = annualTargetForm.controls.createdBy.value;
        annualTarget.isDefault = annualTargetForm.controls.isDefault.value;
        annualTarget.updatedBy = this.statusService.common.userDetail.username;
        annualTarget.millId = annualTargetForm.controls.millId.value;
        annualTarget.kpiId = annualTargetForm.controls.kpiId.value;

        this.apiCallService.callPutAPIwithData(this.updateAnnualConfigUrl, annualTarget).
            subscribe(
                response => {
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Annual Target " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    annualTargetForm.controls.show.setValue(false);
                    this.statusService.refreshAnnualTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        annualTargetForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }

}

