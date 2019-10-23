import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductionTargetService } from './production-target.service';
import { ProductionThreshold } from './production-threshold';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { StatusService } from 'src/app/shared/service/status.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-production-target',
  templateUrl: './production-target.component.html',
  styleUrls: ['./production-target.component.scss']
})
export class ProductionTargetComponent implements OnInit, OnDestroy {

  productionThresholds: ProductionThreshold[] = [];
  cols = MasterData.productionThresholdCols;
  productionTargetSubscription: Subscription;

  constructor(private productionTargetService: ProductionTargetService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.productionThresholds = [];
    this.productionTargetService.getProductionThresholds(this.productionThresholds);

    this.productionTargetSubscription = this.statusService.refreshProductionTargetList.
      subscribe((isRefresh: boolean) => {
        this.productionThresholds = [];
        this.productionTargetService.getProductionThresholds(this.productionThresholds);
      })
  }

  onCreate() {
    let productionThreshold = new ProductionThreshold();
    productionThreshold.buType = '';
    productionThreshold.threshold = null;
    productionThreshold.maximum = null;
    productionThreshold.startDate = ''
    productionThreshold.endDate = '';
    productionThreshold.millId = this.statusService.common.selectedMill.millId;
    productionThreshold.kpiId = 1;
    productionThreshold.createdBy = this.statusService.common.userDetail.username;
    productionThreshold.updatedBy = this.statusService.common.userDetail.username;
    productionThreshold.operation = "Add";

    const data = {
      dialogName: "productionThreshold",
      productionThreshold: productionThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  onEdit(productionThresholdId: string) {
    const productionThreshold = this.productionThresholds.find((productionThreshold) => productionThreshold.productionThresholdId === productionThresholdId)
    productionThreshold.operation = "Edit";

    const data = {
      dialogName: "productionThreshold",
      productionThreshold: productionThreshold
    }
    this.statusService.dialogSubject.next(data);
  }

  ngOnDestroy() {
    this.productionTargetSubscription.unsubscribe();
  }
}
