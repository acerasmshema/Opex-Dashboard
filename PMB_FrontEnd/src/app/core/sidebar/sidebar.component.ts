import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';
import { SidebarRequest } from './sidebar-request';
import { SidebarForm } from './sidebar-form';
import { SearchKpiData } from '../../shared/models/search-kpi-data';
import { SidebarService } from './sidebar-service';
import { ConsumptionService } from '../../dashboard/consumption-dashboard/consumption.service';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';
import { HeaderService } from '../header/header.service';
import { BenchmarkService } from '../../benchmark/benchmark.service';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit, OnDestroy {

  public sidebarForm: SidebarForm;
  public searchKpiData: SearchKpiData;
  public showSidebar: boolean;

  sidebarSubscription: Subscription;

  constructor(private router: Router,
    private sidebarService: SidebarService,
    private consumptionService: ConsumptionService,
    private benchmarkService: BenchmarkService,
    private localStorageService: LocalStorageService,
    private headerService: HeaderService,
    private statusService: StatusService,
    private messageService:MessageService) {
  }

  ngOnInit() {
    this.sidebarSubscription = this.statusService.sidebarSubject.
      subscribe((sidebarRequestData: SidebarRequest) => {
        if (sidebarRequestData.isShow) {
          if (sidebarRequestData.type === "dashboard") {
            this.sidebarForm = this.sidebarService.getDashboardSidebarForm(sidebarRequestData);

            let kpiTypes = this.statusService.kpiCategoryMap.get(sidebarRequestData.kpiCategoryId);
            if (kpiTypes === undefined || kpiTypes === null)
              this.getKpiDetails(sidebarRequestData.kpiCategoryId);
            else
              this.sidebarForm.kpiTypes = kpiTypes;

            const consumptionDetail = this.statusService.consumptionDetailMap.get(sidebarRequestData.kpiCategoryId);
            if (consumptionDetail !== undefined && consumptionDetail.searchKpiData !== undefined) {
              this.searchKpiData = consumptionDetail.searchKpiData;
            }
            else {
              this.searchKpiData = new SearchKpiData();
              this.searchKpiData.frequency = (this.localStorageService.fetchUserRole() == "Mills Operation") ?
                this.sidebarForm.frequencies.find(frequency => frequency.name === 'Daily') :
                this.sidebarForm.frequencies.find(frequency => frequency.name === 'Monthly');
            }
          }
          else {
            this.onGetAllMills();
            this.onGetAllBuType();
            this.getBenchmarkKpiDetail();
            this.sidebarForm = this.sidebarService.getBenchmarkSidebarForm(sidebarRequestData);
            this.searchKpiData = new SearchKpiData();
            this.searchKpiData.frequency = this.sidebarForm.frequencies.find(frequency => frequency.name === 'Monthly');

            setTimeout(() => {
              this.toggleCollapsed();
            }, 20);
          }
          this.sidebarForm.type = sidebarRequestData.type;
        }

        let sidebarSize = (sidebarRequestData.isShow) ? "collapse" : "hide"
        this.statusService.sidebarSizeSubject.next(sidebarSize);
        this.showSidebar = sidebarRequestData.isShow;
      },
      (error: any) => {
        this.statusService.spinnerSubject.next(false);
        if(error.status=="0"){
        alert(CommonMessage.ERROR.SERVER_ERROR)
        }else{
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
      }
    });

    this.router.events.subscribe(val => {
      if (val instanceof NavigationEnd && window.innerWidth <= 992 && this.isToggled())
        document.querySelector('body').classList.toggle(this.sidebarForm.pushRightClass);
    });
  }

  getBenchmarkKpiDetail() {
    const requestData = {
      "kpiCategoryId": ["2", "3", "4"]
    };
    this.sidebarService.getKpiTypes(requestData)
      .subscribe((kpiTypes: any) => {
        this.sidebarForm.kpiTypes = kpiTypes;
      },
      (error: any) => {
        this.statusService.spinnerSubject.next(false);
        if(error.status=="0"){
        alert(CommonMessage.ERROR.SERVER_ERROR)
        }else{
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
      }
    });
  }

  getKpiDetails(kpiCategoryId: any) {
    const requestData = {
      "kpiCategoryId": kpiCategoryId
    };
    this.sidebarService.getKpiTypes(requestData)
      .subscribe((kpiTypes: any) => {
        this.statusService.kpiCategoryMap.set(kpiCategoryId, kpiTypes);
        this.sidebarForm.kpiTypes = kpiTypes;
      },
      (error: any) => {
        this.statusService.spinnerSubject.next(false);
        if(error.status=="0"){
        alert(CommonMessage.ERROR.SERVER_ERROR)
        }else{
          this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
      }
    });
  }

  toggleCollapsed() {
    this.sidebarForm.collapsed = !this.sidebarForm.collapsed;
    let sidebarSize = (!this.sidebarForm.collapsed) ? "show" : "collapse"
    this.sidebarForm.hide = !this.sidebarForm.hide;
    this.statusService.sidebarSizeSubject.next(sidebarSize);

    if (this.sidebarForm.collapsed) {
      this.sidebarForm.dateError = false;
      this.sidebarForm.millsError = false;
    }

    if (this.sidebarForm.type === 'dashboard') {
      setTimeout(() => {
        this.consumptionService.refreshDahboard(this.sidebarForm.kpiCategoryId);
        let consumptionDetailMap = this.statusService.consumptionDetailMap;
        consumptionDetailMap.forEach((consumptionDetiail: ConsumptionDetiail, key: string) => {
          consumptionDetiail.isRefreshed = !this.sidebarForm.collapsed;
        });
      }, 200);
    }
    else if (this.sidebarForm.type === 'benchmark') {
      this.benchmarkService.refreshBenchmark();
    }
  }

  isToggled(): boolean {
    const dom: Element = document.querySelector('body');
    return dom.classList.contains(this.sidebarForm.pushRightClass);
  }

  public onSearchData(type: string) {

    if (type === 'dashboard') {
      if (this.searchKpiData.date === undefined || this.searchKpiData.date === null) {
        this.sidebarForm.dateError = true;
        this.sidebarForm.dateErrorMessage = CommonMessage.ERROR.DATE_ERROR;
      }
      else {
        this.statusService.spinnerSubject.next(true);
        this.sidebarForm.dateError = false;
  
        if (this.searchKpiData.kpiTypes === undefined || this.searchKpiData.kpiTypes.length === 0) {
          this.searchKpiData.kpiTypes = this.sidebarForm.kpiTypes;
        }
        if (this.searchKpiData.processLines === undefined || this.searchKpiData.processLines.length === 0) {
          this.searchKpiData.processLines = [];
        }
        this.searchKpiData.type = type;
        const consumptionDetail = this.statusService.consumptionDetailMap.get(this.sidebarForm.kpiCategoryId);
        consumptionDetail.searchKpiData = this.searchKpiData;
        this.consumptionService.filterCharts(this.searchKpiData, this.sidebarForm.kpiCategoryId);
      }
    }
    else if (type === 'benchmark') {
      let isError = false;
      if (this.searchKpiData.date === undefined || this.searchKpiData.date === null) {
        this.sidebarForm.dateError = true;
        this.sidebarForm.dateErrorMessage = CommonMessage.ERROR.DATE_ERROR;
        isError = true;
      }
      if (this.searchKpiData.mills === undefined || this.searchKpiData.mills.length < 2) {
        this.sidebarForm.millsError = true;
        this.sidebarForm.millsErrorMessage = CommonMessage.ERROR.MILLS_SELECT;
        isError = true;
      }
      if(!isError) {
        this.statusService.spinnerSubject.next(true);
        this.sidebarForm.dateError = false;
        this.sidebarForm.millsError = false;

        if (this.searchKpiData.kpiTypes === undefined || this.searchKpiData.kpiTypes.length === 0) {
          this.searchKpiData.kpiTypes = this.sidebarForm.kpiTypes;
        }
        if (this.searchKpiData.processLines === undefined || this.searchKpiData.processLines.length === 0) {
          this.searchKpiData.processLines = [];
        }
        this.statusService.benchmarkSubject.next(this.searchKpiData);
      }
    }
  }

  onGetAllBuType() {
    if (this.statusService.common.buTypes.length === 0) {
      this.headerService.getAllBuType().
        subscribe((buTypes: any) => {
          this.statusService.common.buTypes = buTypes;
          this.sidebarForm.buisnessUnits = buTypes;
        },
        (error: any) => {
          this.statusService.spinnerSubject.next(false);
          if(error.status=="0"){
          alert(CommonMessage.ERROR.SERVER_ERROR)
          }else{
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
        }
      });
    }
  }

  onGetAllMills() {
    if (this.statusService.common.mills.length === 0) {
      const requestData = {
        countryIds: "1,2"
      }
      this.headerService.getAllMills(requestData).
        subscribe((mills: any) => {
          this.statusService.common.mills = mills;
          this.sidebarForm.mills = mills;
        },
        (error: any) => {
          this.statusService.spinnerSubject.next(false);
          if(error.status=="0"){
          alert(CommonMessage.ERROR.SERVER_ERROR)
          }else{
            this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
        }
      });
    }
  }

  onMillValidation() {
    let mills = this.searchKpiData.mills;
    if (mills !== undefined) {
      if (mills.length < 2 && !this.sidebarForm.millsError) {
        this.sidebarForm.millsError = true;
      } else {
        this.sidebarForm.millsError = false;
      }
    }
  }

  onDateValidation() {
    const datePicker: any = document.getElementById("daterangepicker_input");
    if (datePicker !== null && datePicker.value !== "" && this.sidebarForm.dateError) {
      this.sidebarForm.dateError = false;
    }
  }

  ngOnDestroy() {
    this.sidebarSubscription.unsubscribe();
    this.showSidebar = false;
  }

}