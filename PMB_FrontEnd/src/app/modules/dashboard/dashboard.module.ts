import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxSelectModule } from 'ngx-select-ex';
import { HttpClientModule } from '@angular/common/http';
import { SelectDropDownModule } from 'ngx-select-dropdown';
import { NgxGaugeModule } from 'ngx-gauge';
import { GaugeChartModule } from 'angular-gauge-chart';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { ProductionDashboardComponent } from './production-dashboard/production-dashboard.component';
import { KappaNumberPredictionComponent } from './kappa-number-prediction/kappa-number-prediction.component';
import { CoreModule } from '../core/core.module';
import { ConsumptionDashboardComponent } from './consumption-dashboard/consumption-dashboard.component';
import { PrimeNgModule } from '../shared/primeng-modules';

@NgModule({
  declarations: [
    DashboardComponent,
    ProductionDashboardComponent,
    ConsumptionDashboardComponent,
    KappaNumberPredictionComponent,
  ],
  imports: [
    GaugeChartModule,
    StorageServiceModule,
    NgxChartsModule,
    NgxSelectModule,
    CoreModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    SelectDropDownModule,
    NgxGaugeModule,
    DashboardRoutingModule,
    PrimeNgModule
  ],
})
export class DashboardModule { }
