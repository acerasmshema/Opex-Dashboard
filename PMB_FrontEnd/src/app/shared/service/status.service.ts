import { Injectable } from "@angular/core";
import { Subject } from 'rxjs';
import { SidebarRequest } from '../../core/sidebar/sidebar-request';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';
import { CommonModel } from '../models/common-model';
import { SearchKpiData } from '../models/search-kpi-data';
import { ConsumptionModel } from '../models/consumption-model';

@Injectable()
export class StatusService {
    public spinnerSubject: Subject<boolean> = new Subject<boolean>();
    public sidebarSubject: Subject<SidebarRequest> = new Subject<SidebarRequest>();
    public sidebarSizeSubject: Subject<string> = new Subject<string>();
    public dialogSubject: Subject<any> = new Subject<any>();
    public updateChartSubject: Subject<string> = new Subject<string>();
    public changeMill: Subject<any> = new Subject<any>();
    public projectTargetSubject: Subject<any> = new Subject<any>();
    public enableTabs: Subject<boolean> = new Subject<boolean>();
    public benchmarkSubject: Subject<SearchKpiData> = new Subject<SearchKpiData>();
    public refreshUserList: Subject<boolean> = new Subject<boolean>();
    public refreshProductionTargetList: Subject<boolean> = new Subject<boolean>();
    public refreshAnnualTargetList: Subject<boolean> = new Subject<boolean>();
    public refreshProcessLineTargetList: Subject<boolean> = new Subject<boolean>();
    public refreshConsumtionTargetList: Subject<boolean> = new Subject<boolean>();
    
    public kpiCategoryMap: Map<string, any> = new Map<string, any>();
    public consumptionDetailMap: Map<string, ConsumptionDetiail> = new Map<string, ConsumptionDetiail>();
    public benchmarkList: ConsumptionModel[] = [];
    public common: CommonModel = new CommonModel();
    public isSpin: boolean = false;
    public selectedProductionTab = "PRODUCTION";
}
