export function setupTranslateFactory(
  service: TranslateService): Function {
  return () => service.use('en');
}

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SidebarModule } from 'ng-sidebar';
import { NgxSelectModule } from 'ngx-select-ex';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { SelectDropDownModule } from 'ngx-select-dropdown';
import { NgxGaugeModule } from 'ngx-gauge';
import { GaugeChartModule } from 'angular-gauge-chart';
import { CommonModule, DatePipe } from '@angular/common';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { TranslateService } from './shared/service/translate/translate.service';
import { CoreModule } from './core/core.module';
import { SharedBootstrapModule } from './shared/shared-bootstrap.module';
import { StatusService } from './shared/service/status.service';
import { PrimeNgModule } from './shared/primeng-modules';
import { MessageService } from 'primeng/primeng';
import { LocalStorageService } from './shared/service/localStorage/local-storage.service';
import { ApiCallService } from './shared/service/api/api-call.service';
import { ProductionService } from './dashboard/production-dashboard/production.service';
import { ConsumptionService } from './dashboard/consumption-dashboard/consumption.service';
import { DialogService } from './core/dialog/dialog.service';
import { SidebarService } from './core/sidebar/sidebar-service';
import { BenchmarkService } from './benchmark/benchmark.service';
import { DashboardService } from './dashboard/dashboard.service';
import { LoginComponent } from './profile/login/login.component';
import { LoginService } from './profile/login/login.service';
import { UserDetailService } from './user-management/user-detail/user-detail.service';
import { UserRoleService } from './user-management/user-role/user-role.service';
import { ValidationService } from './shared/service/validation/validation.service';
import { CommonService } from './shared/service/common/common.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    SidebarModule.forRoot(),
    AppRoutingModule,
    CoreModule,
    CommonModule,
    GaugeChartModule,
    StorageServiceModule,
    NgxChartsModule,
    NgxSelectModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    SharedBootstrapModule,
    HttpClientModule,
    SelectDropDownModule,
    NgxGaugeModule,
    PrimeNgModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: setupTranslateFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    CommonService,
    LoginService,
    DashboardService,
    ProductionService,
    ConsumptionService,
    DialogService,
    SidebarService,
    BenchmarkService,
    ApiCallService,
    DatePipe,
    MessageService,
    LocalStorageService,
    TranslateService,
    StatusService,
    UserDetailService,
    UserRoleService,
    ValidationService
  ],
  bootstrap: [AppComponent]
})


export class AppModule {

}
