export function setupTranslateFactory(
  service: TranslateService): Function {
  return () => service.use('en');
}



import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ComboChartComponent } from './components/graphs/combo-chart/combo-chart.component';
import { ComboSeriesVerticalComponent } from './components/graphs/combo-chart/combo-series-vertical.component';
import { SidebarModule } from 'ng-sidebar';
import { NgxSelectModule } from 'ngx-select-ex';
import { SharedBootstrapModule } from './shared/shared-bootstrap.module';
import { ContentComponent } from './components/graphs/content/content.component';
import { DateRangePickerModule } from '@syncfusion/ej2-angular-calendars';
import { ApiCallService } from './services/APIService/ApiCall.service';
import { DataServiceService } from './services/data/data-service.service';
import { RequestHelperService } from './services/request-helper.Service';
import { DataTableModule, CheckboxModule } from "primeng/primeng";
import { DialogModule } from 'primeng/primeng';
import { DropdownModule } from 'primeng/dropdown';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { HeaderComponent } from './components/header/header.component';
import { BarChartComponent } from './components/graphs/bar-chart/bar-chart.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateService } from './services/translate/translate.service';
import { TranslatePipe } from './pipes/translate.pipe';
import { SelectDropDownModule } from 'ngx-select-dropdown';
import { BarLineComboChartComponent } from './components/graphs/bar-line-combo-chart/bar-line-combo-chart.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { BenchmarkSetupComponent } from './components/benchmark/benchmark-setup/benchmark-setup.component';
import { BenchmarkSidePanelComponent } from './components/benchmark/benchmark-side-panel/benchmark-side-panel.component';
import { MasterDataSetupComponent } from './components/masterData/master-data-setup/master-data-setup.component';
import { MasterDataSidePanelComponent } from './components/masterData/master-data-side-panel/master-data-side-panel.component';
import { CreateBenchmarkSetComponent } from './components/benchmark/create-benchmark-set/create-benchmark-set.component';
import { SearchBenchmarkSetComponent } from './components/benchmark/search-benchmark-set/search-benchmark-set.component';
import { LayoutComponent } from './components/layout/layout.component';
import { CalendarModule } from 'primeng/calendar';
import { CreateBenchmarkComponent } from './components/benchmark/create-benchmark/create-benchmark.component';
import { SearchBenchmarkComponent } from './components/benchmark/search-benchmark/search-benchmark.component';
import { NgxGaugeModule } from 'ngx-gauge';
import { GoogleChartsModule } from 'angular-google-charts';
import { GaugeChartModule } from 'angular-gauge-chart';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TabViewModule } from 'primeng/tabview';
import { TabbedPanelComponent } from './components/tabbed-panel/tabbed-panel.component';
import { InputSwitchModule } from 'primeng/inputswitch';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import {PanelModule} from 'primeng/panel';
import { StorageServiceModule } from 'ngx-webstorage-service';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { ProductionDashboardComponent } from './components/dashboard/production-dashboard/production-dashboard.component';
import { ChemicalConsumptionDashboardComponent } from './components/dashboard/chemical-consumption-dashboard/chemical-consumption-dashboard.component';
import { UtilitiesConsumptionDashboardComponent } from './components/dashboard/utilities-consumption-dashboard/utilities-consumption-dashboard.component';
import { WoodConsumptionDashboardComponent } from './components/dashboard/wood-consumption-dashboard/wood-consumption-dashboard.component';
import { OrderByPipe } from './pipes/order-by.pipe';
import { ThreeXThreeTableComponent } from './three-xthree-table/three-xthree-table.component';
import { ConditionalMonitoringComponent } from './components/dashboard/conditional-monitoring/conditional-monitoring.component';
import { KappaNumberPredictionComponent } from './components/dashboard/kappa-number-prediction/kappa-number-prediction.component';

@NgModule({
  declarations: [

    AppComponent,
    ContentComponent,
    ComboSeriesVerticalComponent,
    ComboChartComponent,
    HeaderComponent,
    BarChartComponent,
    FooterComponent,
    LoginComponent,
    TranslatePipe,
    BarLineComboChartComponent,
    SidebarComponent,
    BenchmarkSetupComponent,
    BenchmarkSidePanelComponent,
    MasterDataSetupComponent,
    MasterDataSidePanelComponent,
    CreateBenchmarkSetComponent,
    LayoutComponent,
    SearchBenchmarkSetComponent,
    CreateBenchmarkComponent,
    SearchBenchmarkComponent,
    TabbedPanelComponent,
    ProductionDashboardComponent,
    ChemicalConsumptionDashboardComponent,
    UtilitiesConsumptionDashboardComponent,
    WoodConsumptionDashboardComponent,
    OrderByPipe,
    ThreeXThreeTableComponent,
    ConditionalMonitoringComponent,
    KappaNumberPredictionComponent
    
  ],
  imports: [
    BrowserModule, SidebarModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    GaugeChartModule,
    PanelModule,
    StorageServiceModule,
    TabViewModule,
    RadioButtonModule,
    NgxChartsModule,
    NgxSelectModule,
    InputSwitchModule,
    DropdownModule,
    MultiSelectModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    DateRangePickerModule,
    InputTextareaModule,
    DataTableModule,
    CheckboxModule,
    DialogModule,
    TableModule,
    ToastModule,
    CalendarModule,
    SharedBootstrapModule,
    MessagesModule,
    MessageModule,
    HttpClientModule,
    SelectDropDownModule,
    ProgressSpinnerModule,
    NgxGaugeModule,
    ConfirmDialogModule,
    GoogleChartsModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: setupTranslateFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [ApiCallService, RequestHelperService, TranslateService],
  bootstrap: [AppComponent]
})


export class AppModule {

}
