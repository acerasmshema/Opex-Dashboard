import { Component, OnInit } from '@angular/core';
import { ProductionTargetService } from './production-target.service';
import { ProductionThreshold } from './production-threshold';
import { MasterData } from 'src/app/shared/constant/MasterData';
import { StatusService } from 'src/app/shared/service/status.service';

@Component({
  selector: 'app-production-target',
  templateUrl: './production-target.component.html',
  styleUrls: ['./production-target.component.scss'],
  providers: [ProductionTargetService]
})
export class ProductionTargetComponent implements OnInit {

  productionThresholds: ProductionThreshold[] = [];
  cols = MasterData.productionThresholdCols;

  constructor(private productionTargetService: ProductionTargetService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.productionThresholds = this.productionTargetService.getProductionThresholds();
  }

  onCreate() {
    let productionThreshold = new ProductionThreshold();
    productionThreshold.buType = '';
    productionThreshold.threshold = null;
    productionThreshold.maximum = null;
    productionThreshold.startDate = ''
    productionThreshold.endDate = '';
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

}