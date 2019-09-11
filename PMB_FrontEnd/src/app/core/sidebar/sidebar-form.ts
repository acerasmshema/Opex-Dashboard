import { MillDetail } from 'src/app/shared/models/mill-detail.model';

export class SidebarForm {
    type: string;
    countries: any;
    mills: MillDetail[] = [];
    buisnessUnits: any;
    processLines: any[];
    kpiTypes: any;
    frequencies: any;
    hide: boolean;
    isActive: boolean;
    collapsed: boolean;
    pushRightClass: string;
    kpiCategoryId: string;
    selectedValue: any;
    dateError: boolean;
    millsError: boolean;
    dateErrorMessage: string;
    millsErrorMessage: string;
}