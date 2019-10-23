import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProcessLineThreshold } from './process-line-threshold';
import { ProcessLineTargetService } from './process-line-target.service';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { StatusService } from 'src/app/shared/service/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-process-line-target',
  templateUrl: './process-line-target.component.html',
  styleUrls: ['./process-line-target.component.scss']
})
export class ProcessLineTargetComponent implements OnInit, OnDestroy {

  processLineThresholds: ProcessLineThreshold[] = [];
  cols = MasterData.processLineTargetCols;
  processLineThresholdSubscription: Subscription;

  constructor(private processLineTargetService: ProcessLineTargetService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.processLineThresholds = []
    this.processLineTargetService.getProcessLineThresholds(this.processLineThresholds);

    this.processLineThresholdSubscription = this.statusService.refreshProcessLineTargetList.
      subscribe((isRefresh: boolean) => {
        this.processLineThresholds = []
        this.processLineTargetService.getProcessLineThresholds(this.processLineThresholds);
      })
  }

  onCreate() {
    let processLineThreshold = new ProcessLineThreshold();
    processLineThreshold.buType = '';
    processLineThreshold.processLine = ''
    processLineThreshold.threshold = null;
    processLineThreshold.maximum = null;
    processLineThreshold.startDate = ''
    processLineThreshold.endDate = '';
    processLineThreshold.millId = this.statusService.common.selectedMill.millId;
    processLineThreshold.kpiId = 1;
    processLineThreshold.createdBy = this.statusService.common.userDetail.username;
    processLineThreshold.updatedBy = this.statusService.common.userDetail.username;
    processLineThreshold.operation = "Add";


    const data = {
      dialogName: "processLineThreshold",
      processLineThreshold: processLineThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(processLineTargetThresholdId: string) {
    const processLineThreshold = this.processLineThresholds.find((processLineThreshold) => processLineThreshold.processLineTargetThresholdId === processLineTargetThresholdId)
    processLineThreshold.operation = "Edit";

    const data = {
      dialogName: "processLineThreshold",
      processLineThreshold: processLineThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  ngOnDestroy() {
    this.processLineThresholdSubscription.unsubscribe();
  }
}
