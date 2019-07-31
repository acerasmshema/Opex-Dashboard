import { ApiCallService } from 'src/app/services/APIService/ApiCall.service';
import { Injectable,EventEmitter,Output } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BenchmarkService {

  constructor(private apiCallService:ApiCallService) { }

  public saveSet(data:any){
    return this.apiCallService.callAPIwithData("http://localhost:8081/benchmark/PLSet/saveSet",data);
  } 

  public searchSet(data:any){
    return this.apiCallService.callAPIwithData("http://localhost:8081/benchmark/PLSet/searchSet",data);
  }
}
