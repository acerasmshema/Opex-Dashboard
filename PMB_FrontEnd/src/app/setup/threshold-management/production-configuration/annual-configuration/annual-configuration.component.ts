import { Component, OnInit } from '@angular/core';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { AnnualTarget } from './annual-target';
import { AnnualConfigService } from './annual-configuration.service';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-annual-configuration',
  templateUrl: './annual-configuration.component.html',
  styleUrls: ['./annual-configuration.component.scss'],
  providers: [AnnualConfigService]
})
export class AnnualConfigurationComponent implements OnInit {

  annualTargets: AnnualTarget[] = [];
  cols = MasterData.annualConfigCols;

  constructor(private annualConfigService: AnnualConfigService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.annualTargets = this.annualConfigService.getAnnualTargets();
  }

  onCreate() {
    let annualTarget = new AnnualTarget();
    annualTarget.year = 2019;
    annualTarget.buType = "Kraft";
    annualTarget.workingDays  = 350;
    annualTarget.target = "2,90,00,0000"; 
    annualTarget.createdBy = this.statusService.common.userDetail.username;
    annualTarget.updatedBy = this.statusService.common.userDetail.username;
    annualTarget.operation = "Add";

    const data = {
      dialogName: "annualTarget",
      annualTarget: annualTarget
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(annualTargetId: string) {
    const annualTarget = this.annualTargets.find((annualTarget) => annualTarget.annualTargetId === annualTargetId)
    annualTarget.operation = "Edit";

    const data = {
      dialogName: "annualTarget",
      annualTarget: annualTarget
    }
    this.statusService.dialogSubject.next(data);
  }

}
