import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BenchmarkComponent } from './benchmark.component';
import { BenchmarkRoutingModule } from './benchmark-routing.module';
import { CoreModule } from '../core/core.module';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxSelectModule } from 'ngx-select-ex';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxGaugeModule } from 'ngx-gauge';
import { PrimeNgModule } from '../shared/primeng-modules';

@NgModule({
  declarations: [BenchmarkComponent],
  imports: [
    NgxChartsModule,
    NgxSelectModule,
    CoreModule,
    HttpClientModule,
    NgxGaugeModule,
    BenchmarkRoutingModule,
    PrimeNgModule,
    CommonModule,
    CoreModule,
  ]
})
export class BenchmarkModule { }
