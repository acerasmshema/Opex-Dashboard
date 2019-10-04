import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimeNgModule } from '../shared/primeng-modules';
import { SetupRoutingModule } from './setup-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CoreModule } from '../core/core.module';
import { GaugeChartModule } from 'angular-gauge-chart';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    CoreModule,
    ReactiveFormsModule,
    HttpClientModule,
    SetupRoutingModule,
    PrimeNgModule,
    GaugeChartModule,
  ]
})
export class SetupModule { }
