import { MillDetail } from './mill-detail.model';

export class SearchKpiData {
    mill: string;
    mills: MillDetail[] = [];
    country: string;
    buisnessUnit: any;
    type: string;
    startDate: string;
    endDate: string;
    kpiId: number;
    kpiName: string;
    frequency: any;
    processLines: any;
    kpiTypes: any;
    date: any;
}