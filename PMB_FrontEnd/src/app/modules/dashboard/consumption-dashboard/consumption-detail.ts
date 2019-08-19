import { ConsumptionModel } from '../../shared/models/consumption-model';
import { SearchKpiData } from '../../shared/models/search-kpi-data';

export class ConsumptionDetiail {
    consumptions: ConsumptionModel[];
    isRefreshed: boolean;
    searchKpiData: SearchKpiData;
}