import { Injectable } from '@angular/core';
import { StatusService } from 'src/app/shared/service/status.service';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { ProductionThreshold } from './production-threshold';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { FormGroup } from '@angular/forms';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { DatePipe } from '@angular/common';

@Injectable()
export class ProductionTargetService {

    productionTargetUrl = API_URL.threshold_api_URLs.PRODUCTION_TARGET;
    updateProductionTargetUrl = API_URL.threshold_api_URLs.UPDATE_PRODUCTION_TARGET;
    addProductionTargetUrl = API_URL.threshold_api_URLs.CREATE_PRODUCTION_TARGET;

    constructor(private statusService: StatusService,
        private commonService: CommonService,
        private messageService: MessageService,
        private apiCallService: ApiCallService,
        private datePipe: DatePipe) { }

    getProductionThresholds(productionThresholds: ProductionThreshold[]) {
        let requestData = {
            millId: this.statusService.common.selectedMill.millId
        };

        this.apiCallService.callGetAPIwithData(this.productionTargetUrl, requestData)
            .subscribe(
                (productionThresholdList: ProductionThreshold[]) => {
                    productionThresholds.push(...productionThresholdList);
                },
                (error: any) => {
                    this.commonService.handleError(error);
                });
    }

    addProductionTarget(productionThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let productionThreshold = new ProductionThreshold();
        productionThreshold.buType = productionThresholdForm.controls.buType.value;
        productionThreshold.maximum = productionThresholdForm.controls.maximum.value;
        productionThreshold.threshold = productionThresholdForm.controls.threshold.value;
        productionThreshold.startDate = this.datePipe.transform(productionThresholdForm.controls.startDate.value, 'yyyy-MM-dd');
        productionThreshold.endDate = this.datePipe.transform(productionThresholdForm.controls.endDate.value, 'yyyy-MM-dd');
        productionThreshold.millId = productionThresholdForm.controls.millId.value;
        productionThreshold.kpiId = productionThresholdForm.controls.kpiId.value;
        productionThreshold.createdBy = productionThresholdForm.controls.createdBy.value;
        productionThreshold.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callAPIwithData(this.addProductionTargetUrl, productionThreshold).
            subscribe(
                response => {
                    productionThresholdForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Production Threshold " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshProductionTargetList.next(true);
                },
                error => {
                    this.commonService.handleError(error);
                }
            );
    }

    updateProductionTarget(productionThresholdForm: FormGroup) {
        this.statusService.spinnerSubject.next(true);

        let productionThreshold = new ProductionThreshold();
        productionThreshold.productionThresholdId = productionThresholdForm.controls.productionThresholdId.value;
        productionThreshold.buType = productionThresholdForm.controls.buType.value;
        productionThreshold.maximum = productionThresholdForm.controls.maximum.value;
        productionThreshold.threshold = productionThresholdForm.controls.threshold.value;
        productionThreshold.millId = productionThresholdForm.controls.millId.value;
        productionThreshold.kpiId = productionThresholdForm.controls.kpiId.value;
        productionThreshold.createdBy = productionThresholdForm.controls.createdBy.value;
        productionThreshold.updatedBy = this.statusService.common.userDetail.username;
        try {
            productionThreshold.startDate = this.datePipe.transform(productionThresholdForm.controls.startDate.value, 'yyyy-MM-dd');
        } catch (error) {
            productionThreshold.startDate = productionThresholdForm.controls.startDate.value;
        }
        try {
            productionThreshold.endDate = this.datePipe.transform(productionThresholdForm.controls.endDate.value, 'yyyy-MM-dd');
        } catch (error) {
            productionThreshold.endDate = productionThresholdForm.controls.endDate.value;
        }


        this.apiCallService.callPutAPIwithData(this.updateProductionTargetUrl, productionThreshold).
            subscribe(
                response => {
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Production Threshold " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    productionThresholdForm.controls.show.setValue(false);
                    this.statusService.refreshProductionTargetList.next(true);
                },
                error => {
                    this.commonService.handleError(error);
                }
            );
    }

}