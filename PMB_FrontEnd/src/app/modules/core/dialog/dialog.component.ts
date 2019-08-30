import { Component, OnInit, OnDestroy } from '@angular/core';
import { MessageService } from 'primeng/primeng';
import { Subscription } from 'rxjs';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { AnnotationDialog } from './annotation-dialog';
import { DialogService } from './dialog.service';
import { ConsumptionGridView } from '../../dashboard/consumption-dashboard/consumption-grid-view';
import { MaintenanceDays } from './maintenance-days';
import { ProductionService } from '../../dashboard/production-dashboard/production.service';
import { MasterData } from '../../shared/constant/MasterData';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
  providers: [DialogService, MessageService, LocalStorageService, ProductionService]
})
export class DialogComponent implements OnInit, OnDestroy {

  dialogSubscription: Subscription;

  public annotationDialog: AnnotationDialog;
  public consumptionGridView: ConsumptionGridView;
  public maintenanceDays: MaintenanceDays;
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
        }
        else if (dialogName === 'consumptionGridView') {
          this.consumptionGridView = data.consumptionGridView;
        }
        else if (dialogName === 'maintenanceDays') {
          this.openSettingIcon(data);
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
    this.annotationDialog.annotationLines = "";
    const loginId = this.localStorageService.fetchloginId();
    if (this.annotationDialog.annotationProcessLines.length === 0) {
      this.showMessage("error", "Error Message", "Please select process lines.");
      return;
    }
    if (this.annotationDialog.annotationDescription === undefined || this.annotationDialog.annotationDescription == "") {
      this.showMessage("error", "Error Message", "Please add description.");
      return;
    }

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
          this.showMessage("success", "", "Annotation saved successfully.");
          this.annotationDialog.annotationDescription = "";
          this.annotationDialog.annotationProcessLines = [];
          this.getAnnotationData(this.annotationDialog.annotationKpiId);

          const dashboardName = this.annotationDialog.dashboardName;
          if (dashboardName === 'consumption')
            this.statusService.updateChartSubject.next(dashboardName);
          if (dashboardName === 'production')
            this.statusService.updateChartSubject.next(dashboardName);

        } else {
          this.showMessage("error", "", "Annotation could not be saved.");
        }
      });
  }


  public deleteAnnotationList() {
    this.annotationDialog.newAnnotationDeleteList = [];
    this.annotationDialog.annotationsLines.forEach(annotation => {
      this.annotationDialog.annotations_annoID = annotation["annotationId"];
      //this.annotationDialog.annotations_userID = annotation["userId"];
    });

    const annotationData = {
      "annotationId": this.annotationDialog.annotations_annoID,
      "userId": this.annotationDialog.currentLoggedIdUserID
    };

    this.annotationDialog.newAnnotationDeleteList.push(annotationData);
    const dataany = this.annotationDialog.newAnnotationDeleteList;
    this.dialogService.deleteAnnotationLists(dataany).
      subscribe((dataany: any) => {
        this.showError("success", "", "Deleted sucessfully");
        this.getAnnotationData(this.annotationDialog.annotationKpiId);
      });
  }
  

  
  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  ngOnDestroy() {
    this.dialogSubscription.unsubscribe();
    this.dialogName = null;
  }

  public add() {
    if (this.maintenanceDays.dateValue == undefined) {
      this.showError("error", "Error Message", "Please select Date.");
      return;
    }
    if (this.maintenanceDays.textAreaValue == undefined || this.maintenanceDays.textAreaValue == null) {
      this.showError("error", "Error Message", "Please enter Remarks.");
      return;
    }
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
          this.showError("success", "", "Added Successfully.");
          this.maintenanceDays.textAreaValue = "";
          this.maintenanceDays.dateValue = null;
          this.viewMaintenanceDays();
          this.statusService.projectTargetSubject.next();
        }
      });
  }

  public viewMaintenanceDays() {
    const requestData = {
      millId: this.statusService.common.selectedMill.millId,
      buId: "1"
    };
    this.productionService.getMaintenanceData(requestData).
      subscribe(
        (response: any) => {
          console.log("Response: ", response);
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
          this.showError("success", "", "Deleted.");
          this.viewMaintenanceDays();
          this.statusService.projectTargetSubject.next();
        });
  }

  public addTargetDays() {
    if (this.maintenanceDays.targetDays == undefined || this.maintenanceDays.targetDays == null) {
      this.showError("error", "Error Message", "Please enter target days.");
      return;
    }
    if (this.maintenanceDays.targetDays <= 0) {
      this.showError("error", "Error Message", "Please enter target days value greater than 0.");
      return;
    }
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
            this.showError("success", "", "Target days changed successfully");
            this.statusService.projectTargetSubject.next();
          }
        });
  }

  public onRowEditInit(rowData){  
    return rowData.remarks;
  }

  public onRowEditSave(rowData){
    console.log("This are onRowEditSave", rowData);
    let rowData_ID =[];
    let  rowDataIds = rowData.id.toString();
    rowData_ID.push(rowDataIds);

    const datas ={
     "ids":rowData_ID,
     "remarks":rowData.remarks,
     "updatedBy":1
   }

   console.log("The data is : ",datas);
    this.productionService.updateMaintenanceDaysRemarks(datas).subscribe((datas: any) => {
      console.log("The data nw onRowEditSave ",datas);
      
    });
    console.log(this.viewMaintenanceDays());

  }
// make changes on this file
  public onRowEditCancel(rowData,ri){
    console.log("This are onRowEditCancel remarks", rowData.remarks);
    console.log("This are ri ",ri);    
    let cancelData = rowData;
    let cancelPerm = ""
    this.viewMaintenanceDays();


  }

  public showError(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  public openSettingIcon(maintenanceData: any) {
    this.viewMaintenanceDays();
 
    this.maintenanceDays = new MaintenanceDays();
    this.maintenanceDays.maintanenceDaysColumn = MasterData.maintanenceDaysColumn;
    this.maintenanceDays.targetDays = maintenanceData.targetDays;
    this.maintenanceDays.collapsed = false;
    this.maintenanceDays.show = !this.maintenanceDays.show;
  }
}
