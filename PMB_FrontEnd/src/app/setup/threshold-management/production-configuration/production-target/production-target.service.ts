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
        this.statusService.spinnerSubject.next(true);
        
        let requestData = {
            millId: this.statusService.common.selectedMill.millId
        };
        this.apiCallService.callGetAPIwithData(this.productionTargetUrl, requestData)
            .subscribe(
                (productionThresholdList: ProductionThreshold[]) => {
                    productionThresholdList.forEach(productionThreshold => {
                        if (!productionThreshold.isDefault)
                            productionThreshold.buTypeSortName = productionThreshold.buType.buTypeName;
                        else
                            productionThreshold.buTypeSortName = productionThreshold.buType.buTypeName + ' (DEFAULT)';

                        productionThresholds.push(productionThreshold);
                    });
                    this.statusService.spinnerSubject.next(false);
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
        productionThreshold.isDefault = productionThresholdForm.controls.isDefault.value;
        productionThreshold.updatedBy = this.statusService.common.userDetail.username;

        this.apiCallService.callAPIwithData(this.addProductionTargetUrl, productionThreshold).
            subscribe(
                response => {
                    productionThresholdForm.controls.dateError.setValue('');
                    productionThresholdForm.controls.show.setValue(false);
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Production Threshold " + CommonMessage.SUCCESS.ADD_SUCCESS });
                    this.statusService.refreshProductionTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        productionThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
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
        productionThreshold.isDefault = productionThresholdForm.controls.isDefault.value;
        let startDate = productionThresholdForm.controls.startDate.value.toString();
        let endDate = productionThresholdForm.controls.endDate.value.toString();

        if (startDate.length > 10) {
            productionThreshold.startDate = this.datePipe.transform(startDate, 'yyyy-MM-dd');
        }
        else {
            let tempStartDate = startDate.split("-");
            productionThreshold.startDate = tempStartDate[2] + '-' + tempStartDate[1] + '-' + tempStartDate[0];
        }
        if (endDate.length > 10) {
            productionThreshold.endDate = this.datePipe.transform(endDate, 'yyyy-MM-dd');
        }
        else {
            let tempEndDate = endDate.split("-");
            productionThreshold.endDate = tempEndDate[2] + '-' + tempEndDate[1] + '-' + tempEndDate[0];
        }

        this.apiCallService.callPutAPIwithData(this.updateProductionTargetUrl, productionThreshold).
            subscribe(
                response => {
                    productionThresholdForm.controls.dateError.setValue('');
                    this.statusService.spinnerSubject.next(false);
                    this.messageService.add({ severity: "success", summary: '', detail: "Production Threshold " + CommonMessage.SUCCESS.UPDATE_SUCCESS });
                    productionThresholdForm.controls.show.setValue(false);
                    this.statusService.refreshProductionTargetList.next(true);
                },
                error => {
                    if (error.error.status === "1016") {
                        this.statusService.spinnerSubject.next(false);
                        productionThresholdForm.controls.dateError.setValue(CommonMessage.ERROR_CODES[error.error.status]);
                    }
                    else {
                        this.commonService.handleError(error);
                    }
                }
            );
    }

}