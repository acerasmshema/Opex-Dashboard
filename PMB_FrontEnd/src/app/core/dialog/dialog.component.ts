import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { MessageService, Panel } from 'primeng/primeng';
import { Subscription } from 'rxjs';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { AnnotationDialog } from './annotation-dialog';
import { DialogService } from './dialog.service';
import { ConsumptionGridView } from '../../dashboard/consumption-dashboard/consumption-grid-view';
import { MaintenanceDays } from './maintenance-days';
import { ProductionService } from '../../dashboard/production-dashboard/production.service';
import { MasterData } from '../../shared/constant/MasterData';
import { Table } from 'primeng/table';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { UserDetail } from 'src/app/user-management/user-detail/user-detail.model';
import { MillRole } from 'src/app/user-management/user-detail/mill-role.model';
import { ValidationService } from 'src/app/shared/service/validation/validation.service';

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
  public user: UserDetail;
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
    private validationService: ValidationService,
    private localStorageService: LocalStorageService) { }

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
          this.user = this.dialogService.createUserForm();
        }

        this.dialogName = dialogName;
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
      });
  }

  public createAnnotation() {
    let isError = false;
    this.annotationDialog.annotationLines = "";
    const loginId = this.localStorageService.fetchloginId();
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
        userLoginId: loginId
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
            this.showMessage("success", "", CommonMessage.SUCCESS.ADD_SUCCESS);
            this.maintenanceDays.textAreaValue = "";
            this.maintenanceDays.dateValue = null;
            this.viewMaintenanceDays();
            this.statusService.projectTargetSubject.next();
          }
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
        }
      )
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

    // this.productionService.updateMaintenanceDaysRemarks(datas).subscribe((datas: any) => {
    // });

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
    let millRole = new MillRole();
    millRole.millRoleId = Math.random();
    millRole.mills = this.statusService.common.mills;
    millRole.userRoles = this.statusService.common.userRoles;
    this.user.millRoles.push(millRole);
  }

  onDeleteMillRole(millRoleId: number) {
    this.user.millRoles = this.user.millRoles.filter((millRole) => millRole.millRoleId !== millRoleId);
  }

  ngOnDestroy() {
    this.dialogSubscription.unsubscribe();
    this.dialogName = null;
  }

}
