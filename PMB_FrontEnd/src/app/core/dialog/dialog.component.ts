import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MessageService, Panel } from 'primeng/primeng';
import { Subscription } from 'rxjs';
import { StatusService } from '../../shared/service/status.service';
import { AnnotationDialog } from './annotation-dialog';
import { DialogService } from './dialog.service';
import { ConsumptionGridView } from '../../dashboard/consumption-dashboard/consumption-grid-view';
import { MaintenanceDays } from './maintenance-days';
import { ProductionService } from '../../dashboard/production-dashboard/production.service';
import { MasterData } from '../../shared/constant/MasterData';
import { Table } from 'primeng/table';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';
import { FormGroup } from '@angular/forms';
import { UserRoleService } from 'src/app/setup/user-management/user-role/user-role.service';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit, OnDestroy {

  @ViewChild(Table) consumptionGrid: Table;
  @ViewChild(Panel) maintenancePanel: Panel;
  @ViewChild(Panel) annotationPanel: Panel;

  dialogSubscription: Subscription;

  public annotationDialog: AnnotationDialog;
  public consumptionGridView: ConsumptionGridView;
  public maintenanceDays: MaintenanceDays;
  public userDetailForm: FormGroup;
  public processLineThresholdForm: FormGroup;
  public productionThresholdForm: FormGroup;
  public annualTargetForm: FormGroup;
  public consumptionThresholdForm: FormGroup;
  public userRoleForm: FormGroup;
  public campaignForm: FormGroup;
  public dialogName: string;

  public annotationsCols = [
    { field: 'annotationDate', header: 'Date' },
    { field: 'userId', header: 'User' },
    { field: 'processLines', header: 'Process Line' },
    { field: 'description', header: 'Description' }
  ];

  constructor(private dialogService: DialogService,
    private statusService: StatusService,
    private messageService: MessageService,
    private productionService: ProductionService,
    private userRoleService: UserRoleService,
    private commonService: CommonService,
    private validationService: ValidationService) { }

  ngOnInit() {
    this.dialogSubscription = this.statusService.dialogSubject.
      subscribe((data: any) => {

        let dialogName = data.dialogName;
        if (dialogName === 'annotation') {
          this.annotationDialog = new AnnotationDialog();
          this.annotationDialog.annotationKpiId = data.annotationKpiId;
          this.annotationDialog.annotationDate = data.annotationDate;
          this.annotationDialog.dashboardName = data.dashboardName;
          this.annotationDialog.processLinesForAnnotation = this.statusService.common.processLines;
          this.getAnnotationData(this.annotationDialog.annotationKpiId);
          this.annotationDialog.displayAnnotations = true;

          setTimeout(() => {
            this.annotationPanel.collapsed = true;
          }, 1);
        }
        else if (dialogName === 'consumptionGridView') {
          setTimeout(() => {
            this.consumptionGrid.reset();
          }, 10);
          this.consumptionGridView = data.consumptionGridView;
        }
        else if (dialogName === 'maintenanceDays') {
          this.openSettingIcon(data);
        }
        else if (dialogName === 'addUser') {
          if (this.userDetailForm !== undefined)
            this.userDetailForm.reset();
          this.userDetailForm = this.dialogService.createUserForm();
        }
        else if (dialogName === 'userRole') {
          if (this.userRoleForm !== undefined)
            this.userRoleForm.reset();
          this.userRoleForm = this.dialogService.createUserRoleForm(data.userRole);
        }
        else if (dialogName === 'campaign') {
          if (this.campaignForm !== undefined)
            this.campaignForm.reset();
          this.campaignForm = this.dialogService.createCampaignForm(data.campaign);
        }
        else if (dialogName === 'processLineThreshold') {
          if (this.processLineThresholdForm !== undefined)
            this.processLineThresholdForm.reset();
          this.processLineThresholdForm = this.dialogService.createProcessLineThresholdForm(data.processLineThreshold);
        }
        else if (dialogName === 'productionThreshold') {
          if (this.productionThresholdForm !== undefined)
            this.productionThresholdForm.reset();
          this.productionThresholdForm = this.dialogService.createProductionThresholdForm(data.productionThreshold);
        }
        else if (dialogName === 'annualTarget') {
          if (this.annualTargetForm !== undefined)
            this.annualTargetForm.reset();
          this.annualTargetForm = this.dialogService.createAnnualTargetForm(data.annualTarget);
        }
        else if (dialogName === 'consumptionThreshold') {
          if (this.consumptionThresholdForm !== undefined)
            this.consumptionThresholdForm.reset();
          this.consumptionThresholdForm = this.dialogService.createConsumptionThresholdForm(data.consumptionThreshold);
        }
        this.dialogName = dialogName;
      },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  annotationCollapse() {
    this.annotationDialog.collapsed = !this.annotationDialog.collapsed;
  }

  maintenanceCollapse() {
    this.maintenanceDays.collapsed = !this.maintenanceDays.collapsed;
  }

  public getAnnotationData(kpiId) {
    const kpiData = {
      millId: this.statusService.common.selectedMill.millId,
      buTypeId: '1',
      kpiId: kpiId,
      annotationDate: this.annotationDialog.annotationDate
    };
    this.dialogService.fetchAnnotation(kpiData).
      subscribe((annotationsLines: any) => {
        this.annotationDialog.annotationsLines = annotationsLines;
      },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public createAnnotation() {
    let isError = false;
    this.annotationDialog.annotationLines = "";
    if (this.annotationDialog.annotationProcessLines.length === 0) {
      this.annotationDialog.isProcessLineError = true;
      this.annotationDialog.processLineErrorMessage = CommonMessage.ERROR.PROCESS_LINE_VALIDATION;
      isError = true;
    }
    if (this.annotationDialog.annotationDescription === undefined || this.annotationDialog.annotationDescription == "") {
      this.annotationDialog.isDescriptionError = true;
      this.annotationDialog.descriptionErrorMessage = CommonMessage.ERROR.ADD_DESCRIPTION_VALIDATION;
      isError = true;
    }

    if (!isError) {
      this.annotationDialog.isDescriptionError = false;
      this.annotationDialog.isProcessLineError = false;

      this.annotationDialog.annotationProcessLines.forEach(element => {
        this.annotationDialog.annotationLines = this.annotationDialog.annotationLines.concat(element['processLineName'], ', ');
      });
      this.annotationDialog.annotationLines = this.annotationDialog.annotationLines.replace(/,\s*$/, "");

      const data = {
        millId: this.statusService.common.selectedMill.millId,
        buTypeId: '1',
        kpiId: this.annotationDialog.annotationKpiId,
        annotationDate: this.annotationDialog.annotationDate,
        processLines: this.annotationDialog.annotationLines,
        description: this.annotationDialog.annotationDescription,
        userLoginId: this.statusService.common.userDetail.userId
      };

      this.dialogService.createAnnotation(data).
        subscribe((data: any) => {
          if (data == null) {
            this.showMessage("success", "", CommonMessage.SUCCESS.ANNOTATION_SAVED);
            this.annotationDialog.annotationDescription = "";
            this.annotationDialog.annotationProcessLines = [];
            this.getAnnotationData(this.annotationDialog.annotationKpiId);

            const dashboardName = this.annotationDialog.dashboardName;
            if (dashboardName === 'consumption')
              this.statusService.updateChartSubject.next(dashboardName);
            if (dashboardName === 'production')
              this.statusService.updateChartSubject.next(dashboardName);

          } else {
            this.showMessage("error", "", CommonMessage.ERROR.ANNOTATION_ERROR);
          }
        },
          (error: any) => {
            this.commonService.handleError(error);
          });
    }
  }

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  public onAddMaintenanceDays() {
    let isError = false;
    if (this.maintenanceDays.dateValue == undefined) {
      this.maintenanceDays.isDateError = true;
      this.maintenanceDays.dateErrorMessage = CommonMessage.ERROR.DATE_ERROR;
      isError = true;
    }
    if (this.maintenanceDays.textAreaValue == undefined || this.maintenanceDays.textAreaValue == null || this.maintenanceDays.textAreaValue === '') {
      this.maintenanceDays.remarksErrorMessage = CommonMessage.ERROR.REMARKS_ERROR;
      this.maintenanceDays.isRemarksError = true;
      isError = true;
    }
    if (!isError) {
      this.maintenanceDays.isRemarksError = false;
      this.maintenanceDays.isDateError = false;

      let maintanenceListDataNew = [];
      const date = this.maintenanceDays.dateValue.getDate();
      const month = this.maintenanceDays.dateValue.getMonth() + 1;
      const year = this.maintenanceDays.dateValue.getFullYear();
      const totalDate = year + "-" + month + "-" + date;
      maintanenceListDataNew.push(totalDate);

      const requestData = {
        millId: this.statusService.common.selectedMill.millId,
        buId: 1,
        createdBy: 1,
        updatedBy: 1,
        remarks: this.maintenanceDays.textAreaValue,
        active: true,
        maintenanceDays: maintanenceListDataNew
      };
      this.productionService.saveMaintenanceDays(requestData).
        subscribe((response: any) => {
          if (response == null) {
            this.showMessage("success", "", "Maintenance date " + CommonMessage.SUCCESS.ADD_SUCCESS);
            this.maintenanceDays.textAreaValue = "";
            this.maintenanceDays.dateValue = null;
            this.viewMaintenanceDays();
            this.statusService.projectTargetSubject.next();
          }
        },
          (error: any) => {
            this.statusService.spinnerSubject.next(false);
            this.commonService.handleError(error);
          });
    }
  }

  public viewMaintenanceDays() {
    const requestData = {
      millId: this.statusService.common.selectedMill.millId,
      buId: "1"
    };
    this.productionService.getMaintenanceData(requestData).
      subscribe(
        (response: any) => {
          this.maintenanceDays.maintanenceDayModel = response;
        },
        (error: any) => {
          this.commonService.handleError(error);
        }
      );
  }

  public delMaintanenceDays() {
    let newdeleteDate = [];
    this.maintenanceDays.selectedMaintenanceDay.forEach(element => {
      newdeleteDate.push(element.id);
    });
    const requestData = {
      ids: newdeleteDate
    };

    this.productionService.deleteMaintenanceDays(requestData).
      subscribe(
        (response: any) => {
          this.showMessage("success", "", CommonMessage.SUCCESS.DELETE_SUCCESS);
          this.viewMaintenanceDays();
          this.statusService.projectTargetSubject.next();
        },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public addTargetDays() {
    let isError = false;

    if (this.maintenanceDays.targetDays == undefined || this.maintenanceDays.targetDays == null) {
      this.maintenanceDays.targetDaysErrorMessage = CommonMessage.ERROR.TARGET_DAYS_ERROR;
      this.maintenanceDays.isTargetDaysError = true;
      isError = true;
    }
    if (this.maintenanceDays.targetDays <= 0) {
      this.maintenanceDays.targetDaysErrorMessage = CommonMessage.ERROR.TARGET_DAYS_GREATER_THAN_ZERO;
      this.maintenanceDays.isTargetDaysError = true;
      isError = true;
    }
    if (!isError) {
      this.maintenanceDays.isTargetDaysError = false;
      const requestData = {
        millId: this.statusService.common.selectedMill.millId,
        buId: 1,
        kpiCategoryId: 1,
        noOfTargetDays: +this.maintenanceDays.targetDays
      };
      this.productionService.updateMaintanenceTargetDays(requestData).
        subscribe(
          (data: any) => {
            if (data == null) {
              this.showMessage("success", "", CommonMessage.SUCCESS.TARGET_CHANGED_SUCCESS);
              this.statusService.projectTargetSubject.next();
            }
          },
          (error: any) => {
            this.commonService.handleError(error);
          });
    }
  }


  public onRowEditInit(rowData) {
    return rowData.remarks;
  }

  public onRowEditSave(rowData) {
    let rowData_ID = [];
    let rowDataIds = rowData.id.toString();
    rowData_ID.push(rowDataIds);

    const datas = {
      "ids": rowData_ID,
      "remarks": rowData.remarks,
      "updatedBy": 1
    }

    this.productionService.updateMaintenanceDaysRemarks(datas).subscribe((datas: any) => {
      this.messageService.add({ severity: "success", summary: '', detail: CommonMessage.SUCCESS.UPDATE_SUCCESS });
    },
      (error: any) => {
        this.commonService.handleError(error);
      });

  }

  public onRowEditCancel() {
    this.viewMaintenanceDays();
  }

  public openSettingIcon(maintenanceData: any) {
    this.viewMaintenanceDays();
    this.maintenanceDays = new MaintenanceDays();
    this.maintenanceDays.maintanenceDaysColumn = MasterData.maintanenceDaysColumn;
    this.maintenanceDays.targetDays = maintenanceData.targetDays;
    this.maintenanceDays.show = !this.maintenanceDays.show;
    setTimeout(() => {
      this.maintenancePanel.collapsed = true;
    }, 1);
  }

  onTargetDaysValidation() {
    this.validationService.targetDaysValidation(this.maintenanceDays);
  }

  onRemarksValidation() {
    this.validationService.remarksValidation(this.maintenanceDays);
  }

  onDateValidation() {
    this.validationService.dateValidation(this.maintenanceDays);
  }

  onPorocessLineValidation() {
    this.validationService.processLineValidation(this.annotationDialog);
  }

  onDescriptionValidation() {
    this.validationService.descriptionValidation(this.annotationDialog);
  }

  onAddMillRole() {
    this.dialogService.addMillRole(this.userDetailForm);
  }

  onDeleteMillRole(index: number) {
    if (index > 0) {
      let millRoles: any = this.userDetailForm.controls.millRoles;
      millRoles.removeAt(index);
      let millControls = millRoles.controls;
      millControls[millControls.length - 1].value.selectedMill.enable();
      millControls[millControls.length - 1].value.selectedUserRole.enable();
    }
  }

  onCreateUser() {
    if (this.userDetailForm.invalid)
      return;
    this.dialogService.createNewUser(this.userDetailForm);
  }

  onCountryChange(countryId: string) {
    if (countryId !== '') {
      let country = this.statusService.common.countryList.find(country => country.countryId === countryId);
      this.userDetailForm.controls.selectedCountry.setValue(country);
    } else {
      this.userDetailForm.controls.selectedCountry.setValue(null);
    }
  }

  onDepartmentChange(departmentId: string) {
    if (departmentId !== '') {
      const department = this.statusService.common.departmentList.find(department => department.departmentId === departmentId);
      this.userDetailForm.controls.selectedDepartment.setValue(department);
    } else {
      this.userDetailForm.controls.selectedDepartment.setValue(null);
    }
  }

  onMillChange(millId: string, millRole: FormGroup) {
    if (!this.validationService.validateMillExist(this.userDetailForm, millId)) {
      const mill = this.statusService.common.mills.find(mill => mill.millId === millId);
      millRole.value.selectedMill.setValue(mill);
      millRole.value.millError.setValue("");
    }
    else {
      millRole.value.millError.setValue("2");
    }
  }

  onUserRoleChange(userRoleId: string, millRole: FormGroup) {
    const userRole = this.statusService.common.activeUserRoles.find(role => role.userRoleId === userRoleId);
    millRole.value.selectedUserRole.setValue(userRole);
    millRole.value.roleError.setValue("");
  }

  onUserDetailCancel() {
    this.userDetailForm.controls.show.setValue(false);
  }

  onUserRoleCancel() {
    this.userRoleForm.controls.show.setValue(false);
  }

  onUserRoleSubmit() {
    if (this.userRoleForm.invalid)
      return;

    if (this.userRoleForm.controls.operation.value === "Add") {
      this.userRoleService.addUserRole(this.userRoleForm);
    } else {
      this.userRoleService.updateUserRole(this.userRoleForm);
    }
  }

  onInputChange(value: any) {
    let formControl: any = this.userDetailForm.get(value);
    this.validationService.trimValue(formControl);
  }

  onConfigCancel() {
    this.campaignForm.controls.show.setValue(false);
  }

  ngOnDestroy() {
    this.dialogSubscription.unsubscribe();
    this.dialogName = null;
  }

}
