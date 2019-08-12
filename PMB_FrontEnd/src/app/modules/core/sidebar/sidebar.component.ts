import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { MessageService } from 'primeng/components/common/messageservice';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { StatusService } from '../../shared/service/status.service';
import { Subscription } from 'rxjs';
import { SidebarRequest } from './sidebar-request';
import { SidebarForm } from './sidebar-form';
import { SearchKpiData } from '../../shared/models/search-kpi-data';
import { SidebarService } from './sidebar-service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  providers: [MessageService, LocalStorageService, SidebarService]
})
export class SidebarComponent implements OnInit, OnDestroy {
  sidebarSubscription: Subscription;
  public sidebarForm: SidebarForm;
  public searchKpiData: SearchKpiData;
  public showSidebar: boolean;
  private kpiCategoryMap = new Map<string, any>();

  constructor(private router: Router,
    private sidebarService: SidebarService,
    private localStorageService: LocalStorageService,
    private statusService: StatusService) { }

  ngOnInit() {
    this.sidebarSubscription = this.statusService.sidebarSubject.
      subscribe((sidebarRequestData: SidebarRequest) => {
        if (sidebarRequestData.isShow) {
          this.sidebarForm = (sidebarRequestData.type === "dashboard") ?
            this.sidebarService.getDashboardSidebarForm(sidebarRequestData) :
            this.sidebarService.getBenchmarkSidebarForm(sidebarRequestData);

          this.sidebarForm.type = sidebarRequestData.type;
          let kpiTypes = this.kpiCategoryMap.get(sidebarRequestData.kpiCategoryId);
          if (kpiTypes === undefined || kpiTypes === null)
            this.getKpiDetails(sidebarRequestData.kpiCategoryId);
          else
            this.sidebarForm.kpiTypes = kpiTypes;

          this.searchKpiData = new SearchKpiData();
          this.searchKpiData.frequency = (this.localStorageService.fetchUserRole() == "Mills Operation") ?
            this.sidebarForm.frequencies.find(frequency => frequency.name === 'Daily') :
            this.sidebarForm.frequencies.find(frequency => frequency.name === 'Monthly');
        }

        let sidebarSize = (sidebarRequestData.isShow) ? "collapse" : "hide"
        this.statusService.sidebarSizeSubject.next(sidebarSize);
        this.showSidebar = sidebarRequestData.isShow;
      });

    this.router.events.subscribe(val => {
      if (val instanceof NavigationEnd && window.innerWidth <= 992 && this.isToggled())
        document.querySelector('body').classList.toggle(this.sidebarForm.pushRightClass);
    });
  }

  getKpiDetails(kpiCategoryId: string) {
    const requestData = {
      "kpiCategoryId": kpiCategoryId
    };
    this.sidebarService.getKpiTypes(requestData)
      .subscribe((kpiTypes: any) => {
        this.kpiCategoryMap.set(kpiCategoryId, kpiTypes);
        this.sidebarForm.kpiTypes = kpiTypes;
      });
  }

  toggleCollapsed() {
    this.sidebarForm.collapsed = !this.sidebarForm.collapsed;
    let sidebarSize = (!this.sidebarForm.collapsed) ? "show" : "collapse"
    this.statusService.sidebarSizeSubject.next(sidebarSize);
    this.sidebarForm.hide = !this.sidebarForm.hide;
  }

  isToggled(): boolean {
    const dom: Element = document.querySelector('body');
    return dom.classList.contains(this.sidebarForm.pushRightClass);
  }

  searchData(type: string) {
    if (this.searchKpiData.date === undefined || this.searchKpiData.date === null) {
      alert("Please select date range");
    }
    else {
      if (this.searchKpiData.kpiTypes === undefined || this.searchKpiData.kpiTypes.length === 0) {
        this.searchKpiData.kpiTypes = this.sidebarForm.kpiTypes;
      }
      if (this.searchKpiData.processLines === undefined || this.searchKpiData.processLines.length === 0) {
        this.searchKpiData.processLines = [];
      }
      this.searchKpiData.type = type;
      this.statusService.kpiSubject.next(this.searchKpiData);
    }
  }

  ngOnDestroy() {
    this.sidebarSubscription.unsubscribe();
    this.kpiCategoryMap = null;
    this.showSidebar = false;
  }

}