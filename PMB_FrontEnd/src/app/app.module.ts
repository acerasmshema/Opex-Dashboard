export function setupTranslateFactory(
  service: TranslateService): Function {
  return () => service.use('en');
}

import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
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
import { GoogleChartsModule } from 'angular-google-charts';
import { GaugeChartModule } from 'angular-gauge-chart';
import { CommonModule, DatePipe } from '@angular/common';
import { StorageServiceModule } from 'ngx-webstorage-service';
import { ApiCallService } from './modules/shared/service/APIService/ApiCall.service';
import { TranslateService } from './modules/shared/service/translate/translate.service';
import { LoginComponent } from './modules/shared/login/login.component';
import { CoreModule } from './modules/core/core.module';
import { SharedBootstrapModule } from './modules/shared/shared-bootstrap.module';
import { RequestHelperService } from './modules/shared/service/request-helper.service';
import { StatusService } from './modules/shared/service/status.service';
import { PrimeNgModule } from './modules/shared/primeng-modules';
import { MessageService } from 'primeng/primeng';
import { LocalStorageService } from './modules/shared/service/localStorage/local-storage.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    SidebarModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
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
    GoogleChartsModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: setupTranslateFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [ApiCallService,
    RequestHelperService,
    DatePipe,
    MessageService,
    LocalStorageService,
    TranslateService,
    StatusService],
  bootstrap: [AppComponent]
})


export class AppModule {

}
