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

@Injectable()
export class DialogService {

    saveAnnotation = API_URL.apiURLs.SAVE_ANNOTATION_URL;
    findAnnotation = API_URL.apiURLs.FIND_ANNOTATION_URL;
    userURL = API_URL.user_api_URLs.CREATE_USER;

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
        let options = {
            hasNeedle: true,
            needleColor: 'black',
            needleUpdateSpeed: 1000,
            arcColors:  ["red", "yellow", "green"],      
            arcDelimiters: [80, 90],
            rangeLabel: ['0', '9000'],
            needleStartValue: 50,
        };

        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(processLineThreshold.operation),
            buType: new FormControl(processLineThreshold.buType),
            createdBy: new FormControl(processLineThreshold.createdBy),
            processLine: new FormControl(processLineThreshold.processLine),
            threshold: new FormControl(processLineThreshold.threshold),
            maximum: new FormControl(processLineThreshold.maximum),
            startDate: new FormControl(processLineThreshold.startDate),
            endDate: new FormControl(processLineThreshold.endDate),
            canvasWidth: new FormControl(220),
            needleValue: new FormControl(80),
            bottomLabel: new FormControl(7500),
            options: new FormControl(options),
        });
    }

    createProductionThresholdForm(productionThreshold: ProductionThreshold): FormGroup {
        let options = {
            hasNeedle: true,
            needleColor: 'black',
            needleUpdateSpeed: 1000,
            arcColors:  ["red", "yellow", "green"],      
            arcDelimiters: [80, 90],
            rangeLabel: ['0', '9000'],
            needleStartValue: 50,
        }

        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(productionThreshold.operation),
            buType: new FormControl(productionThreshold.buType),
            createdBy: new FormControl(productionThreshold.createdBy),
            threshold: new FormControl(productionThreshold.threshold),
            maximum: new FormControl(productionThreshold.maximum),
            startDate: new FormControl(productionThreshold.startDate),
            endDate: new FormControl(productionThreshold.endDate),
            canvasWidth: new FormControl(220),
            needleValue: new FormControl(80),
            bottomLabel: new FormControl(7500),
            options: new FormControl(options),
        });
    }

    createAnnualTargetForm(annualTarget: AnnualTarget): FormGroup {
        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(annualTarget.operation),
            buType: new FormControl(annualTarget.buType),
            createdBy: new FormControl(annualTarget.createdBy),
            workingDays: new FormControl(annualTarget.workingDays),
            year: new FormControl(annualTarget.year),
            target: new FormControl(annualTarget.target),
        });
    }

    createConsumptionThresholdForm(consumptionThreshold: ConsumptionThreshold): FormGroup {
        return this.formBuilder.group({
            show: new FormControl(true),
            operation: new FormControl(consumptionThreshold.operation),
            kpiCategory: new FormControl(consumptionThreshold.kpiCategory),
            kpi: new FormControl(consumptionThreshold.kpi),
            processLine: new FormControl(consumptionThreshold.processLine),
            buType: new FormControl(consumptionThreshold.buType),
            createdBy: new FormControl(consumptionThreshold.createdBy),
            threshold: new FormControl(consumptionThreshold.threshold),
            startDate: new FormControl(consumptionThreshold.startDate),
            endDate: new FormControl(consumptionThreshold.endDate),
        });
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

}