import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductionConfigurationComponent } from './production-configuration/production-configuration.component';
import { ConsumptionConfigurationComponent } from './consumption-configuration/consumption-configuration.component';
import { ProductionTargetComponent } from './production-configuration/production-target/production-target.component';
import { ProcessLineTargetComponent } from './production-configuration/process-line-target/process-line-target.component';
import { ThresholdManagementRoutingModule } from './threshold-management-routing.module';
import { PrimeNgModule } from 'src/app/shared/primeng-modules';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CoreModule } from 'src/app/core/core.module';
import { GaugeChartModule } from 'angular-gauge-chart';

@NgModule({
  declarations: [ProductionConfigurationComponent,
    ConsumptionConfigurationComponent,
    ProductionTargetComponent,
    ProcessLineTargetComponent],
  imports: [
    CommonModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    PrimeNgModule,
    GaugeChartModule,
    ThresholdManagementRoutingModule,
  ]
})
export class ThresholdManagementModule { }
