import { Injectable } from "@angular/core";
import { Subject } from 'rxjs';
import { SidebarRequest } from '../../core/sidebar/sidebar-request';
import { SearchKpiData } from '../models/search-kpi-data';

@Injectable()
export class StatusService {
    public sidebarSubject: Subject<SidebarRequest> = new Subject<SidebarRequest>();
    public sidebarSizeSubject: Subject<string> = new Subject<string>();    
    public kpiSubject: Subject<SearchKpiData> = new Subject<SearchKpiData>(); 
    public kpiCategoryUpdate: Subject<string> = new Subject<string>(); 
    
    public processLineMap =  new Map<string, any>();
    public millId: string = "1";
}
