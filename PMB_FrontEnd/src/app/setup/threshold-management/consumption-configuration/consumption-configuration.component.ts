import { Component, OnInit, OnDestroy } from '@angular/core';
import { SidebarRequest } from 'src/app/core/sidebar/sidebar-request';
import { StatusService } from 'src/app/shared/service/status.service';
import { ConsumptionConfigurationService } from './consumption-configuration.service';
import { ConsumptionThreshold } from './consumption-threshold';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { ConsumptionConfig } from './consumption-config.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-consumption-configuration',
  templateUrl: './consumption-configuration.component.html',
  styleUrls: ['./consumption-configuration.component.scss']
})
export class ConsumptionConfigurationComponent implements OnInit, OnDestroy {

  consumptionThresholds: ConsumptionThreshold[] = [];
  cols = MasterData.consumptionThresholdCols;
  consumptionConfig: ConsumptionConfig;
  selectedBuTypeId: string;
  selectedKPIId: string;
  selectedKPICategory: string;
  consumptionThresholdSubscription: Subscription;

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

    this.consumptionThresholdSubscription = this.statusService.refreshConsumtionTargetList.
      subscribe((isRefresh: boolean) => {
        this.onSearch();
      })
  }

  onSearch() {
    this.consumptionThresholds = [];
    this.consumptionConfig.buTypesError = (this.selectedBuTypeId !== undefined) ? false : true;
    this.consumptionConfig.kpisError = (this.selectedKPIId !== undefined) ? false : true;
    this.consumptionConfig.kpiCategoriesError = (this.selectedKPICategory !== undefined) ? false : true;
    
    if (!this.consumptionConfig.buTypesError && !this.consumptionConfig.kpisError && !this.consumptionConfig.kpiCategoriesError)
      this.consumptionConfigService.getConsumptionThresholds(this.consumptionThresholds, this.selectedBuTypeId, this.selectedKPIId);
  }

  onCreate() {
    let consumptionThreshold = new ConsumptionThreshold();
    consumptionThreshold.buType = '';
    consumptionThreshold.processLine = ''
    consumptionThreshold.threshold = null;
    consumptionThreshold.startDate = ''
    consumptionThreshold.isDefault = false;
    consumptionThreshold.endDate = '';
    consumptionThreshold.millId = this.statusService.common.selectedMill.millId;
    consumptionThreshold.createdBy = this.statusService.common.userDetail.username;
    consumptionThreshold.updatedBy = this.statusService.common.userDetail.username;
    consumptionThreshold.operation = "Add";

    const data = {
      dialogName: "consumptionThreshold",
      consumptionThreshold: consumptionThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(processLineTargetThresholdId: string) {
    const consumptionThreshold = this.consumptionThresholds.find((consumptionThreshold) => consumptionThreshold.processLineTargetThresholdId === processLineTargetThresholdId)
    consumptionThreshold.kpiCategory = this.selectedKPICategory;
    consumptionThreshold.kpiId = this.consumptionConfig.kpis.find((kpi) => kpi.kpiId === +this.selectedKPIId).kpiId;
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
    this.consumptionConfig.kpiCategoriesError = false;
  }

  onBuTypeChange(buTypeId: string) {
    this.selectedBuTypeId = buTypeId;
    this.consumptionConfig.buTypesError = false;
  }

  onKPIChange(kpiId: string) {
    this.selectedKPIId = kpiId;
    this.consumptionConfig.kpisError = false;
  }

  ngOnDestroy() {
    this.consumptionThresholdSubscription.unsubscribe();
  }
}
