import { ApiCallService } from 'src/app/services/APIService/ApiCall.service';
import { Injectable,EventEmitter,Output } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable()
export class DataServiceService {



  fetchBarData(data: string): any {
    return this.apiCallService.callGetAPIwithOutData("http://localhost:8080/api/production/barQuantity");

  }
  fetchSeriesData(data: string): any {
    return this.apiCallService.callGetAPIwithOutData("http://localhost:8080/api/production/seriesQuantity");
  }

  public fetchData(data: string){
    return this.apiCallService.callGetAPIwithOutData("http://localhost:8080/api/production");

 } 

 public fetchDateFilterData(data:any){
  return this.apiCallService.callGetAPIwithData("http://localhost:8080/api/production/date_range", data);
 }

 public fetchGridData(data: any){
  return this.apiCallService.callGetAPIwithOutData("http://localhost:8080/api/production/grid_data");
  } 

  
 constructor(private apiCallService:ApiCallService) {  }
}

