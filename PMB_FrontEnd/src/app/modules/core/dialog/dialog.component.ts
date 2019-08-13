import { Component, OnInit, OnDestroy } from '@angular/core';
import { MessageService } from 'primeng/primeng';
import { Subscription } from 'rxjs';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { AnnotationDialog } from './annotation-dialog';
import { DialogService } from './dialog.service';


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
  providers: [DialogService, MessageService, LocalStorageService]
})
export class DialogComponent implements OnInit, OnDestroy {

  dialogSubscription: Subscription;

  public displayAnnotations: boolean = false;
  public annotationDialog: AnnotationDialog;
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
    private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.dialogSubscription = this.statusService.dialogSubject.
      subscribe((data: any) => {
        let dialgName = data["dialogName"];
        if (dialgName === 'annotation') {
          this.annotationDialog = new AnnotationDialog();
          this.annotationDialog.annotationKpiId = data["annotationKpiId"];
          this.annotationDialog.annotationDate = data["annotationDate"];
          this.annotationDialog.dashboardName = data["dashboardName"];
          this.annotationDialog.processLinesForAnnotation = this.statusService.processLineMap.get(this.statusService.selectedMill.millId);
          this.getAnnotationData(this.annotationDialog.annotationKpiId);
          this.displayAnnotations = true;
          this.dialogName = data["dialogName"];
        }
      });
  }

  createAnnotationBeforeToggle() {
    this.annotationDialog.findAnnotationCollapsed = !this.annotationDialog.findAnnotationCollapsed;
  }

  findAnnotationBeforeToggle() {
    this.annotationDialog.createAnnotationCollapsed = !this.annotationDialog.createAnnotationCollapsed;
  }

  public getAnnotationData(kpiId) {
    const kpiData = {
      millId: this.statusService.selectedMill.millId,
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
      this.showMessage("error", "", "");
    }
    if (this.annotationDialog.annotationDescription == "") {
      this.showMessage("error", "", "");
    }

    this.annotationDialog.annotationProcessLines.forEach(element => {
      this.annotationDialog.annotationLines = this.annotationDialog.annotationLines.concat(element['processLineName'], ', ');
    });
    this.annotationDialog.annotationLines = this.annotationDialog.annotationLines.replace(/,\s*$/, "");

    const data = {
      millId: this.statusService.selectedMill.millId,
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

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  ngOnDestroy() {
    this.dialogSubscription.unsubscribe();
    this.displayAnnotations = false;
    this.dialogName = null;
  }

}
