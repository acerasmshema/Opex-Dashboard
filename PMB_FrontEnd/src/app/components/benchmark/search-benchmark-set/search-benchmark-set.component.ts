import { Component, OnInit } from '@angular/core';
import { Mill } from '../../../models/Mill';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MasterDataService } from '../../../services/masterData/master-data.service';
import { BenchmarkService } from '../../../services/benchmark/benchmark.service';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { ToastModule } from 'primeng/toast';
import { PLSet } from '../../../models/PLSet';
import {TableModule} from 'primeng/table';

@Component({
  selector: 'app-search-benchmark-set',
  templateUrl: './search-benchmark-set.component.html',
  styleUrls: ['./search-benchmark-set.component.scss'],
  providers: [MessageService]
})
export class SearchBenchmarkSetComponent implements OnInit {

  searchSetForm: FormGroup;
  locations: Location[];
  businessUnits: Location[];
  msgs: Message[] = [];
  locationId: number=-1;
  businessUnitId: number=-1;
  plSetId: number;
  plSets:PLSet[];

  constructor(private fb: FormBuilder, private benchmarkService: BenchmarkService, private masterDataService: MasterDataService, private messageService: MessageService) {
    this.searchSetForm = fb.group(
      {
        setName: "",
        setLocation: "",
        setBusinessUnit: "",
        });

    this.getAllLocations();
   // this.searchAllSets();
  }

  ngOnInit() {
  }

  public searchSet(data: any) {

    if (data['setName'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Set Name cannot be blank');
      return null;
    }  
     if (data['setLocation'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Please select Location');
      return null;
    } 
    if (data['setBusinessUnit'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Please select Business Unit');
      return null;
    }
    
    data['setLocation'] = this.locationId;
    data['setBusinessUnit'] = this.businessUnitId;
    
    this.benchmarkService.searchSet(data).subscribe((data: any) => {
      if(data==null){
        this.showMessage('error', 'unsuccessful :', 'SET could not be created');
      }else{
        this.plSets=data;
        console.log(this.plSets);
      }
    });
  }

/* public searchAllSets(){
  this.benchmarkService.searchAllSets().subscribe((data: any) => {
      this.plSets=data;
  });
} */

  public getAllLocations() {
  /*   this.masterDataService.getAllLocations().subscribe((data: any) => {
      this.locations = data;
    }); */

  }

  public getBusinessUnit(locationId: string) {
  /*   this.masterDataService.getBusinessUnit(locationId).subscribe((data: any) => {
      this.businessUnits = data;
    }); */
  }

  public onChangeLocation(event) {
    this.locationId = event.value['locationId'];
    this.getBusinessUnit(event.value['locationId']);
  }

  public onChangeBusinessUnit(event) {
    this.businessUnitId = event.value['businessUnitId'];
  }

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }
}
