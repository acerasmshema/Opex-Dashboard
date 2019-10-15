import { Component, OnInit } from '@angular/core';
import { ProcessLineThreshold } from './process-line-threshold';
import { ProcessLineTargetService } from './process-line-target.service';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-process-line-target',
  templateUrl: './process-line-target.component.html',
  styleUrls: ['./process-line-target.component.scss'],
  providers: [ProcessLineTargetService]
})
export class ProcessLineTargetComponent implements OnInit {

  processLineThresholds: ProcessLineThreshold[] = [];
  cols = MasterData.processLineTargetCols;

  constructor(private processLineTargetService: ProcessLineTargetService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.processLineThresholds = this.processLineTargetService.getProcessLineThresholds();
  }

  onCreate() {
    let processLineThreshold = new ProcessLineThreshold();
    processLineThreshold.buType = '';
    processLineThreshold.processLine = ''
    processLineThreshold.threshold = null;
    processLineThreshold.maximum = null;
    processLineThreshold.startDate = ''
    processLineThreshold.endDate = '';
    processLineThreshold.createdBy = this.statusService.common.userDetail.username;
    processLineThreshold.updatedBy = this.statusService.common.userDetail.username;
    processLineThreshold.operation = "Add";

    const data = {
      dialogName: "processLineThreshold",
      processLineThreshold: processLineThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(processLineThresholdId: string) {
    const processLineThreshold = this.processLineThresholds.find((processLineThreshold) => processLineThreshold.processLineThresholdId === processLineThresholdId)
    processLineThreshold.operation = "Edit";

    const data = {
      dialogName: "processLineThreshold",
      processLineThreshold: processLineThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

}
