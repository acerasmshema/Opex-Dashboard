import { NgModule } from '@angular/core';
import { KappaNumberPredictionComponent } from './kappa-number-prediction.component';
import { KappaNumberPredictionRoutingModule } from './kappa-analytics-routing.module';
import { CoreModule } from '../core/core.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [KappaNumberPredictionComponent],
  imports: [
    CoreModule,
    HttpClientModule,
    KappaNumberPredictionRoutingModule,
    CoreModule,
  ]
})
export class kappaAnalyticsModule { }
