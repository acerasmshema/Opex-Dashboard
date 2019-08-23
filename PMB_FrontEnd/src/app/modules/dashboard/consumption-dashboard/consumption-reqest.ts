export class ConsumptionRequest {
    countryId: string ;
    millId: string;
    buId: string = "1";
    kpiCategoryId: string;
    kpiId: number;
    processLines: any[];
    startDate: string;
    endDate: string;
    frequency: string;
    date: any;
}