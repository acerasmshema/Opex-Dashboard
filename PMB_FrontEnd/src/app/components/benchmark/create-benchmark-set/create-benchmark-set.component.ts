import { Component, OnInit } from '@angular/core';
import { Mill } from '../../../models/Mill';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MasterDataService } from '../../../services/masterData/master-data.service';
import { BenchmarkService } from '../../../services/benchmark/benchmark.service';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { ToastModule } from 'primeng/toast';


@Component({
  selector: 'app-create-benchmark-set',
  templateUrl: './create-benchmark-set.component.html',
  styleUrls: ['./create-benchmark-set.component.scss'],
  providers: [MessageService]
})
export class CreateBenchmarkSetComponent implements OnInit {

  createSetForm: FormGroup;
  locations: Location[];
  businessUnits: Location[];
  plSets: Location[];
  msgs: Message[] = [];
  locationId: number;
  businessUnitId: number;
  plSetId: number;


  constructor(private fb: FormBuilder, private benchmarkService: BenchmarkService, private masterDataService: MasterDataService, private messageService: MessageService) {
    this.createSetForm = fb.group(
      {
        setName: "",
        setDescription: "",
        setLocation: "",
        setBusinessUnit: "",
        setPLSetup: ""
      });
    this.getAllLocations();
  }

  ngOnInit() {
  }

  public saveSet(data: any) {

    if (data['setName'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Set Name cannot be blank');
      return null;
    } 
    if (data['setName'].length > 20) {
      this.showMessage('warn', 'unsuccessful :', 'Set Name cannot be more than 20 character');
      return null;
    }
    if (data['setDescription'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Set Description cannot be blank');
      return null;
    }
    if (data['setDescription'].length > 100) {
      this.showMessage('warn', 'unsuccessful :', 'Set Description cannot be more than 100 character');
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
    if (data['setPLSetup'] == "") {
      this.showMessage('warn', 'unsuccessful :', 'Please select Production Line Setup');
      return null;
    }
    data['setLocation'] = this.locationId;
    data['setBusinessUnit'] = this.businessUnitId;
    data['setPLSetup'] = this.plSetId;
    this.benchmarkService.saveSet(data).subscribe((data: any) => {
      if(data==null){
        this.showMessage('error', 'unsuccessful :', 'SET could not be created');
      }else{
        this.showMessage('success', 'Successful :', 'SET created Successfully');
        this.createSetForm = this.fb.group(
          {
            setName: "",
            setDescription: "",
            setLocation: "",
            setBusinessUnit: "",
            setPLSetup: ""
          });
        this.getAllLocations();
      }
    });
  }

  public getAllLocations() {
   /*  this.masterDataService.getAllLocations().subscribe((data: any) => {
      this.locations = data;
    }); */

  }

  public getBusinessUnit(locationId: string) {
   /*  this.masterDataService.getBusinessUnit(locationId).subscribe((data: any) => {
      this.businessUnits = data;
    }); */
  }

  public getPLSets(businessUnit: string) {
    /* this.masterDataService.getPlSets(businessUnit).subscribe((data: any) => {
      this.plSets = data;
    }); */
  }

  public onChangeLocation(event) {
    this.locationId = event.value['locationId'];
    this.getBusinessUnit(event.value['locationId']);
  }

  public onChangeBusinessUnit(event) {
    this.businessUnitId = event.value['businessUnitId'];
    this.getPLSets(event.value['businessUnitId']);
  }

  public onChangePLSet(event) {
    this.plSetId = event.value['code'];
    console.log(event.value['code']);
  }

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }
}