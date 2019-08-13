import { Injectable } from "@angular/core";
import { Subject } from 'rxjs';
import { SidebarRequest } from '../../core/sidebar/sidebar-request';
import { ConsumptionDetiail } from '../../dashboard/consumption-dashboard/consumption-detail';

@Injectable()
export class StatusService {
    public sidebarSubject: Subject<SidebarRequest> = new Subject<SidebarRequest>();
    public sidebarSizeSubject: Subject<string> = new Subject<string>();
    public kpiCategoryUpdate: Subject<string> = new Subject<string>();
    public dialogSubject: Subject<any> = new Subject<any>();
    public updateChartSubject: Subject<string> = new Subject<string>();

    public processLineMap = new Map<string, any>();
    public consumptionDetailMap: Map<string, ConsumptionDetiail> = new Map<string, ConsumptionDetiail>();
    public mills: any = [];
    public selectedMill: any = { millId: "1", millName: 'Kerinci' };
}
