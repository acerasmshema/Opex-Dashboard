import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { AnnualTarget } from './annual-target';
import { AnnualConfigService } from './annual-configuration.service';
import { StatusService } from 'src/app/shared/service/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-annual-configuration',
  templateUrl: './annual-configuration.component.html',
  styleUrls: ['./annual-configuration.component.scss']
})
export class AnnualConfigurationComponent implements OnInit, OnDestroy {

  annualTargets: AnnualTarget[] = [];
  cols = MasterData.annualConfigCols;
  annualTargetSubscription: Subscription;

  constructor(private annualConfigService: AnnualConfigService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.annualTargets = [];
    this.annualConfigService.getAnnualTargets(this.annualTargets);

    this.annualTargetSubscription = this.statusService.refreshAnnualTargetList.
      subscribe((isRefresh: boolean) => {
        this.annualTargets = [];
        this.annualConfigService.getAnnualTargets(this.annualTargets);
      })
  }

  onCreate() {
    let annualTarget = new AnnualTarget();
    annualTarget.year = null;
    annualTarget.buType = "";
    annualTarget.workingDays = null;
    annualTarget.annualTarget = '';
    annualTarget.millId = this.statusService.common.selectedMill.millId;
    annualTarget.kpiId = 1;
    annualTarget.createdBy = this.statusService.common.userDetail.username;
    annualTarget.updatedBy = this.statusService.common.userDetail.username;
    annualTarget.operation = "Add";

    const data = {
      dialogName: "annualTarget",
      annualTarget: annualTarget
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(annualConfigurationId: string) {
    const annualTarget = this.annualTargets.find((annualTarget) => annualTarget.annualConfigurationId === annualConfigurationId)
    annualTarget.operation = "Edit";

    const data = {
      dialogName: "annualTarget",
      annualTarget: annualTarget
    }
    this.statusService.dialogSubject.next(data);
  }

  ngOnDestroy() {
    this.annualTargetSubscription.unsubscribe();
  }
}
