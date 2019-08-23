import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { KappaNumberPredictionComponent } from './kappa-number-prediction.component';
import { KappaNumberPredictionRoutingModule } from './kappaAnalytics-routing.module';
import { CoreModule } from '../core/core.module';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxSelectModule } from 'ngx-select-ex';
import { HttpClientModule } from '@angular/common/http';
import { NgxGaugeModule } from 'ngx-gauge';
import { PrimeNgModule } from '../shared/primeng-modules';

@NgModule({
  declarations: [KappaNumberPredictionComponent],
  imports: [
    NgxChartsModule,
    NgxSelectModule,
    CoreModule,
    HttpClientModule,
    NgxGaugeModule,
    KappaNumberPredictionRoutingModule,
    PrimeNgModule,
    CommonModule,
    CoreModule,
  ]
})
export class kappaAnalyticsModule { }
