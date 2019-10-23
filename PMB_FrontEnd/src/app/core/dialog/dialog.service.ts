import { Injectable } from '@angular/core';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { ApiCallService } from 'src/app/shared/service/api/api-call.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { FormControl, Validators, FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { CommonService } from 'src/app/shared/service/common/common.service';
import { MillDetail } from 'src/app/shared/models/mill-detail.model';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/primeng';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { UserRole } from 'src/app/setup/user-management/user-role/user-role.model';
import { UserDetail } from 'src/app/setup/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/setup/user-management/user-detail/mill-role.model';
import { CampaignModel } from 'src/app/setup/campaign-management/campaign-model';
import { ProcessLineThreshold } from 'src/app/setup/threshold-management/production-configuration/process-line-target/process-line-threshold';
import { ProductionThreshold } from 'src/app/setup/threshold-management/production-configuration/production-target/production-threshold';
import { ConsumptionThreshold } from 'src/app/setup/threshold-management/consumption-configuration/consumption-threshold';
import { AnnualTarget } from 'src/app/setup/threshold-management/production-configuration/annual-configuration/annual-target';
import { ConsumptionTable } from 'src/app/dashboard/consumption-dashboard/consumption-table';
import { MasterData } from 'src/app/shared/constant/MasterData';

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;
    userURL = API_URL.user_api_URLs.CREATE_USER;
    allProcessLines = API_URL.apiURLs.ALL_PROCESS_LINES_URL;
    kpiUrl = API_URL.apiURLs.CONSUMPTION_GRID_API_URL;

    constructor(private apiCallService: ApiCallService,
        private commonService: CommonService,
        private formBuilder: FormBuilder,
        private messageService: MessageService,
        private validationService: ValidationService,
        private statusService: StatusService) { }

    public createAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.saveAnnotation, data);
    }

    public fetchAnnotation(data: any) {
        return this.apiCallService.callAPIwithData(this.findAnnotation, data);
    }

    createUserForm(): FormGroup {
        let millRoles = this.formBuilder.array([
            new FormControl({
                userRoles: this.formBuilder.array([]),
                mills: this.formBuilder.array([]),
                selectedMill: new FormControl({ value: '', disabled: false }),
                selectedUserRole: new FormControl({ value: '', disabled: false }),
                millError: new FormControl(''),
                roleError: new FormControl(''),
            })
        ]);

        let userDetailForm = this.formBuilder.group({
            show: new FormControl(true),
            totalMills: new FormControl(1),
            firstName: new FormControl("", [Validators.required]),
            lastName: new FormControl("", [Validators.required]),
            address: new FormControl(""),
            username: new FormControl("", { validators: [Validators.required], asyncValidators: [this.validationService.forbiddenUsername.bind(this)], updateOn: 'blur' }),
            password: new FormControl("", [Validators.required, Validators.minLength(8)]),
            confirmPassword: new FormControl("", [Validators.required]),
            phone: new FormControl(""),
            selectedCountry: new FormControl(null),
            countryList: this.formBuilder.array([]),
            selectedDepartment: new FormControl(null),
            departmentList: this.formBuilder.array([]),
            email: new FormControl("", { validators: [Validators.required, Validators.email], asyncValidators: [this.validationService.forbiddenEmail.bind(this)], updateOn: 'blur' }),
            validateEmail: new FormControl(""),
            millRoles: millRoles,
        },
            { validator: this.validationService.mustMatchPassword('password', 'confirmPassword') }
        );

        userDetailForm.controls.email.valueChanges.
            subscribe((event) => {
                if (event !== null)
                    userDetailForm.get('email').setValue(event.toLowerCase(), { emitEvent: false });
            });

        userDetailForm.controls.username.valueChanges.
            subscribe((event) => {
                if (event !== null)
                    userDetailForm.get('username').setValue(event.toLowerCase().trim(), { emitEvent: false });
            });

        this.commonService.getAllCountry(userDetailForm);
        this.commonService.getAllDepartment(userDetailForm);
        this.getAllMills(userDetailForm);
        this.getAllUserRole(userDetailForm, true);

        return userDetailForm;
    }

    createUserRoleForm(userRole: UserRole): FormGroup {
        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(userRole.operation),
            userRoleId: new FormControl(userRole.userRoleId),
            createdBy: new FormControl(userRole.createdBy),
            validateRole: new FormControl(userRole.roleName),
            roleName: new FormControl(userRole.roleName, { validators: [Validators.required], asyncValidators: [this.validationService.forbiddenUserRole.bind(this)], updateOn: 'blur' }),
            description: new FormControl(userRole.description),
            userExistError: new FormControl(''),
            active: new FormControl(userRole.active),
        });
    }

    createProcessLineThresholdForm(processLineThreshold: ProcessLineThreshold): FormGroup {
        let maximum = (processLineThreshold.maximum !== null) ? processLineThreshold.maximum : '';
        let options = {
            hasNeedle: true,
            needleColor: 'black',
            needleUpdateSpeed: 1000,
            arcColors: ["red", "yellow", "green"],
            arcDelimiters: [],
            rangeLabel: ['0', maximum + ''],
            needleStartValue: 50,
        };

        let processLineThresholdForm = this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(processLineThreshold.operation),
            processLineTargetThresholdId: new FormControl(processLineThreshold.processLineTargetThresholdId),
            buType: new FormControl(processLineThreshold.buType, Validators.required),
            buTypeList: this.formBuilder.array([]),
            createdBy: new FormControl(processLineThreshold.createdBy),
            processLine: new FormControl(processLineThreshold.processLine),
            processLineList: this.formBuilder.array([]),
            threshold: new FormControl(processLineThreshold.threshold, Validators.required),
            isDefault: new FormControl(processLineThreshold.isDefault),
            maximum: new FormControl(processLineThreshold.maximum, Validators.required),
            startDate: new FormControl(processLineThreshold.startDate, Validators.required),
            endDate: new FormControl(processLineThreshold.endDate, Validators.required),
            millId: new FormControl(processLineThreshold.millId),
            kpiId: new FormControl(processLineThreshold.kpiId),
            canvasWidth: new FormControl(220),
            needleValue: new FormControl(0),
            bottomLabel: new FormControl(processLineThreshold.threshold),
            options: new FormControl(options),
        },
            {
                validator: [this.validationService.maximumValidation('threshold', 'maximum'), this.validationService.thresholdValidation('threshold', 'maximum')]
            });
        this.commonService.getAllBuType(processLineThresholdForm);
        this.getProcessLines(processLineThresholdForm);
        return processLineThresholdForm;
    }

    createProductionThresholdForm(productionThreshold: ProductionThreshold): FormGroup {
        let maximum = (productionThreshold.maximum !== null) ? productionThreshold.maximum : '';
        let options = {
            hasNeedle: true,
            needleColor: 'black',
            needleUpdateSpeed: 1000,
            arcColors: ["red", "yellow", "green"],
            arcDelimiters: [],
            rangeLabel: ['0', maximum + ''],
            needleStartValue: 50,
        }

        let productionThresholdForm = this.formBuilder.group(
            {
                show: new FormControl(true),
                operation: new FormControl(productionThreshold.operation),
                productionThresholdId: new FormControl(productionThreshold.productionThresholdId),
                buType: new FormControl(productionThreshold.buType, Validators.required),
                buTypeList: this.formBuilder.array([]),
                isDefault: new FormControl(productionThreshold.isDefault),
                millId: new FormControl(productionThreshold.millId),
                kpiId: new FormControl(productionThreshold.kpiId),
                createdBy: new FormControl(productionThreshold.createdBy),
                updatedBy: new FormControl(productionThreshold.updatedBy),
                threshold: new FormControl(productionThreshold.threshold, Validators.required),
                maximum: new FormControl(productionThreshold.maximum, Validators.required),
                startDate: new FormControl(productionThreshold.startDate, Validators.required),
                endDate: new FormControl(productionThreshold.endDate, Validators.required),
                canvasWidth: new FormControl(220),
                needleValue: new FormControl(0),
                bottomLabel: new FormControl(productionThreshold.threshold),
                options: new FormControl(options),
            },
            {
                validator: [this.validationService.maximumValidation('threshold', 'maximum'), this.validationService.thresholdValidation('threshold', 'maximum')]
            });

        this.commonService.getAllBuType(productionThresholdForm);

        if (productionThreshold.operation === "Edit")
            this.changeGaugeThreshold(productionThreshold.threshold, productionThresholdForm);

        return productionThresholdForm;
    }

    createAnnualTargetForm(annualTarget: AnnualTarget): FormGroup {
        let annualTargetForm = this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(annualTarget.operation),
            buType: new FormControl(annualTarget.buType, Validators.required),
            workingDays: new FormControl(annualTarget.workingDays, Validators.required),
            year: new FormControl(annualTarget.year, Validators.required),
            years: this.formBuilder.array(MasterData.years),
            annualTarget: new FormControl(annualTarget.annualTarget, Validators.required),
            annualConfigurationId: new FormControl(annualTarget.annualConfigurationId),
            buTypeList: this.formBuilder.array([]),
            isDefault: new FormControl(annualTarget.isDefault),
            millId: new FormControl(annualTarget.millId),
            kpiId: new FormControl(annualTarget.kpiId),
            createdBy: new FormControl(annualTarget.createdBy),
            updatedBy: new FormControl(annualTarget.updatedBy),
        },
            {
                validator: [this.validationService.workingDaysValidation('workingDays')]
            });

        this.commonService.getAllBuType(annualTargetForm);
        
        return annualTargetForm;
    }

    createConsumptionThresholdForm(consumptionThreshold: ConsumptionThreshold): FormGroup {
        let consumptionThresholdForm = this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(consumptionThreshold.operation),
            processLineTargetThresholdId: new FormControl(consumptionThreshold.processLineTargetThresholdId),
            kpiCategory: new FormControl(consumptionThreshold.kpiCategory, Validators.required),
            processLine: new FormControl(consumptionThreshold.processLine, Validators.required),
            buType: new FormControl(consumptionThreshold.buType, Validators.required),
            processLineList: this.formBuilder.array([]),
            kpiCategoryList: this.formBuilder.array([]),
            buTypeList: this.formBuilder.array([]),
            kpiList: this.formBuilder.array([]),
            isDefault: new FormControl(consumptionThreshold.isDefault),
            millId: new FormControl(consumptionThreshold.millId),
            kpiId: new FormControl(consumptionThreshold.kpiId),
            createdBy: new FormControl(consumptionThreshold.createdBy),
            threshold: new FormControl(consumptionThreshold.threshold, Validators.required),
            startDate: new FormControl(consumptionThreshold.startDate, Validators.required),
            endDate: new FormControl(consumptionThreshold.endDate, Validators.required),
        });

        this.commonService.getAllBuType(consumptionThresholdForm);
        this.getKpiCategoryList(consumptionThresholdForm);
        if (consumptionThreshold.kpiCategory !== undefined) {
            this.getKpiDetails(consumptionThreshold.kpiCategory.kpiCategoryId, consumptionThresholdForm, "" + consumptionThreshold.kpiId);
        }

        return consumptionThresholdForm;
    }

    createCampaignForm(campaign: CampaignModel): FormGroup {
        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(campaign.operation),
            millName: new FormControl(this.statusService.common.selectedMill.millName),
            campaignId: new FormControl(campaign.campaignId),
            createdBy: new FormControl(campaign.createdBy),
            validateCampaignName: new FormControl(campaign.campaignName),
            campaignName: new FormControl(campaign.campaignName),
            startDate: new FormControl(campaign.startDate),
            endDate: new FormControl(campaign.endDate),
            buType: new FormControl(campaign.buType),
            active: new FormControl(campaign.active),
        });
    }

    addMillRole(userDetailForm: FormGroup) {
        let millRoles: any = userDetailForm.controls.millRoles;
        let millControls = millRoles.controls;

        if (millControls[millControls.length - 1].value.selectedMill.value === '') {
            millControls[millControls.length - 1].value.millError.setValue("1");
        }
        if (millControls[millControls.length - 1].value.selectedUserRole.value === '') {
            millControls[millControls.length - 1].value.roleError.setValue("1");
        }
        if (millControls[millControls.length - 1].value.selectedMill.value !== '' && millControls[millControls.length - 1].value.selectedUserRole.value !== '') {
            millControls.push(
                new FormControl({
                    userRoles: this.formBuilder.array([]),
                    mills: this.formBuilder.array([]),
                    selectedMill: new FormControl({ value: '', disabled: false }),
                    selectedUserRole: new FormControl({ value: '', disabled: false }),
                    millError: new FormControl(''),
                    roleError: new FormControl(''),
                })
            );
            this.getAllMills(userDetailForm);
            this.getAllUserRole(userDetailForm, true);

            millControls[millControls.length - 2].value.selectedMill.disable();
            millControls[millControls.length - 2].value.selectedUserRole.disable();
        }
    }

    getAllMills(userDetailForm: FormGroup) {
        if (this.statusService.common.mills.length === 0) {
            this.commonService.getAllMills().
                subscribe(
                    (mills: MillDetail[]) => {
                        const millRoles: any = userDetailForm.controls.millRoles;
                        const millControl = millRoles.controls[0].value.mills.controls;
                        mills.forEach(mill => {
                            millControl.push(new FormControl(mill));
                        });
                        this.statusService.common.mills = mills;
                        userDetailForm.controls.totalMills.setValue(mills.length);
                    },
                    (error: any) => {
                        this.commonService.handleError(error);
                    });
        }
        else {
            const millList = this.statusService.common.mills;
            const millRoles: any = userDetailForm.controls.millRoles;
            const millControl: any = millRoles.controls[millRoles.length - 1].value.mills.controls;
            millList.forEach(mill => {
                millControl.push(new FormControl(mill));
            });
            userDetailForm.controls.totalMills.setValue(millList.length);
        }
    }

    getAllUserRole(userDetailForm: FormGroup, activeUserRoles) {
        this.commonService.getAllUserRole(activeUserRoles).
            subscribe(
                (roleList: UserRole[]) => {
                    const millRoles: any = userDetailForm.controls.millRoles;
                    const userRoleControl = millRoles.controls[millRoles.controls.length - 1].value.userRoles.controls;
                    roleList.forEach(userRole => {
                        userRoleControl.push(new FormControl(userRole));
                    });
                    this.statusService.common.activeUserRoles = roleList;
                },
                (error: any) => {
                    this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                }
            );
    }

    createNewUser(userDetailForm: FormGroup) {
        if (!this.validationService.valdiateUserMillRole(userDetailForm)) {
            this.statusService.spinnerSubject.next(true);

            let millRoles: MillRole[] = [];
            let millRoleList: any = userDetailForm.controls.millRoles;
            millRoleList.controls.forEach(control => {
                let millRole = new MillRole();
                millRole.selectedMill = control.value.selectedMill.value;
                millRole.selectedUserRole = control.value.selectedUserRole.value;
                millRoles.push(millRole);
            });

            let userDetail = new UserDetail();
            userDetail.username = userDetailForm.controls.username.value;
            userDetail.password = btoa(userDetailForm.controls.password.value);
            userDetail.firstName = userDetailForm.controls.firstName.value;
            userDetail.lastName = userDetailForm.controls.lastName.value;
            userDetail.email = userDetailForm.controls.email.value;
            userDetail.phone = userDetailForm.controls.phone.value;
            userDetail.address = userDetailForm.controls.address.value;
            userDetail.country = userDetailForm.controls.selectedCountry.value;
            userDetail.department = userDetailForm.controls.selectedDepartment.value;
            userDetail.millRoles = millRoles;
            userDetail.createdBy = this.statusService.common.userDetail.username;
            userDetail.updatedBy = this.statusService.common.userDetail.username;

            this.apiCallService.callAPIwithData(this.userURL, userDetail).
                subscribe(
                    (response: any) => {
                        this.messageService.add({ severity: "success", summary: '', detail: 'User created ' + CommonMessage.SUCCESS.ADD_SUCCESS });
                        this.statusService.refreshUserList.next(true);
                        userDetailForm.controls.show.setValue(false);
                        this.statusService.spinnerSubject.next(false);
                    },
                    (error: any) => {
                        this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                        this.statusService.spinnerSubject.next(false);
                    }
                );

        }
    }

    getProcessLines(form: FormGroup) {
        const requestData = {
            millId: this.statusService.common.selectedMill.millId
        }
        this.apiCallService.callGetAPIwithData(this.allProcessLines, requestData).
            subscribe(
                (processLines: any) => {
                    const processLineList: any = form.controls.processLineList;
                    let processLineControl = processLineList.controls;
                    processLines.forEach(processLine => {
                        processLineControl.push(new FormControl(processLine));
                    });
                },
                (error: any) => {
                    this.commonService.handleError(error);
                }
            );
    }

    getKpiCategoryList(form: FormGroup) {
        const kpiCategoryList: any = form.controls.kpiCategoryList;
        let kpiCategoryControl = kpiCategoryList.controls;
        kpiCategoryControl.push(new FormControl({ kpiCategoryId: 2, kpiCategoryName: "Chemical" }));
        kpiCategoryControl.push(new FormControl({ kpiCategoryId: 3, kpiCategoryName: "Utility" }));
        kpiCategoryControl.push(new FormControl({ kpiCategoryId: 4, kpiCategoryName: "Wood" }));
    }

    getKpiDetails(kpiCategoryId: string, consumptionThresholdForm: FormGroup, kpiId?: string) {
        const requestData = {
            kpiCategoryId: "" + kpiCategoryId,
            millId: this.statusService.common.selectedMill.millId
        };
        this.apiCallService.callGetAPIwithData(this.kpiUrl, requestData).
            subscribe(
                (consumptionsTable: ConsumptionTable[]) => {
                    const kpiList: any = consumptionThresholdForm.controls.kpiList;
                    let kpiListControl = kpiList.controls;
                    consumptionsTable.forEach(table => {
                        kpiListControl.push(new FormControl(table));
                    });
                    if (kpiId !== undefined)
                        this.getKpiProcessLines(kpiId, consumptionThresholdForm);
                },
                (error: any) => {
                    this.commonService.handleError(error);
                }
            );
    }

    getKpiProcessLines(kpiId: string, form: FormGroup) {
        const kpiList: any = form.controls.kpiList;
        const kpi = kpiList.controls.find((kpi) => kpi.value.kpiId === +kpiId).value;
        const processLineList: any = form.controls.processLineList;
        let processLineControl = processLineList.controls;

        kpi.series.forEach(processLine =>
            processLineControl.push(new FormControl(processLine))
        )
        console.log(processLineControl)
    }

    changeGaugeThreshold(thresholdValue: number, form: FormGroup) {
        let maximum = +form.controls.maximum.value;
        if (maximum > 0 && +thresholdValue > 0 && thresholdValue < maximum) {
            form.controls.bottomLabel.setValue(thresholdValue);
            let thresholdPercentage = (+thresholdValue * 100) / maximum;
            form.controls.needleValue.setValue(thresholdPercentage);
            form.controls.options.value.arcDelimiters = [(thresholdPercentage * 0.95), thresholdPercentage];
        }
    }

    changeGaugeMaximum(maximumValue: string, form: FormGroup) {
        let thresholdValue = form.controls.threshold.value;
        form.controls.options.value.rangeLabel = ['0', maximumValue];

        if (+maximumValue > 0 && thresholdValue > 0 && thresholdValue < +maximumValue) {
            this.changeGaugeThreshold(thresholdValue, form);
        }
    }
}