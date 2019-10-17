import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from 'src/app/core/sidebar/sidebar-request';
import { StatusService } from 'src/app/shared/service/status.service';
import { ConsumptionConfigurationService } from './consumption-configuration.service';
import { ConsumptionThreshold } from './consumption-threshold';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { ConsumptionConfig } from './consumption-config.model';

@Component({
  selector: 'app-consumption-configuration',
  templateUrl: './consumption-configuration.component.html',
  styleUrls: ['./consumption-configuration.component.scss']
})
export class ConsumptionConfigurationComponent implements OnInit {

  consumptionThresholds: ConsumptionThreshold[] = [];
  cols = MasterData.consumptionThresholdCols;
  consumptionConfig: ConsumptionConfig;
  selectedBuTypeId: string;
  selectedKPIId: string;
  selectedKPICategory: string;

  constructor(private statusService: StatusService,
    private consumptionConfigService: ConsumptionConfigurationService) {
    this.consumptionConfig = new ConsumptionConfig();
  }

  ngOnInit() {
    document.getElementById("select_mill").style.display = "block";

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.showSidebar = false;
    sidebarRequest.type = "profile";
    this.statusService.sidebarSubject.next(sidebarRequest);

    this.consumptionConfigService.getConsumptionConfig(this.consumptionConfig);
  }

  onSearch() {
    this.consumptionThresholds = [];
    this.consumptionConfigService.getConsumptionThresholds(this.consumptionThresholds, this.selectedBuTypeId, this.selectedKPIId);
  }

  onCreate() {
    let consumptionThreshold = new ConsumptionThreshold();
    consumptionThreshold.buType = '';
    consumptionThreshold.processLine = ''
    consumptionThreshold.threshold = null;
    consumptionThreshold.startDate = ''
    consumptionThreshold.endDate = '';
    consumptionThreshold.createdBy = this.statusService.common.userDetail.username;
    consumptionThreshold.updatedBy = this.statusService.common.userDetail.username;
    consumptionThreshold.operation = "Add";

    const data = {
      dialogName: "consumptionThreshold",
      consumptionThreshold: consumptionThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(consumptionThresholdId: string) {
    const consumptionThreshold = this.consumptionThresholds.find((consumptionThreshold) => consumptionThreshold.consumptionThresholdId === consumptionThresholdId)
    consumptionThreshold.kpiCategory = this.selectedKPICategory;
    consumptionThreshold.kpi = this.consumptionConfig.kpis.find((kpi) => kpi.kpiId === +this.selectedKPIId);
    consumptionThreshold.operation = "Edit";
    
    const data = {
      dialogName: "consumptionThreshold",
      consumptionThreshold: consumptionThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onKpiCategoryChange(value: string) {    
    this.selectedKPICategory = this.consumptionConfig.kpiCategories.find((kpiCategory) => kpiCategory.kpiCategoryId === +value)
    this.consumptionConfigService.getKpiDetails(value, this.consumptionConfig);
  }

  onBuTypeChange(buTypeId: string) {
    this.selectedBuTypeId = buTypeId;
  }

  onKPIChange(kpiId: string) {
    this.selectedKPIId = kpiId;
  }
}
