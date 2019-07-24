import { Component, Input, OnInit,Output,EventEmitter } from '@angular/core';
import { DataServiceService } from 'src/app/services/data/data-service.service';
import { Model }   from 'src/app/models/model';
import { gridData} from 'src/assets/data/data1';
import { CardSeriesComponent } from '@swimlane/ngx-charts';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { Router, NavigationEnd } from '@angular/router';
import { Country } from 'src/app/models/Country';
import { Mill } from 'src/app/models/Mill';
import { BusinessUnit } from 'src/app/models/BusinessUnit';
import { ProcessLine } from 'src/app/models/ProcessLine';
import { KpiType } from 'src/app/models/KpiType';
import { MasterDataService } from 'src/app/services/masterData/master-data.service';
import { Message } from 'primeng/components/common/api';
import { MessageService } from 'primeng/components/common/messageservice';
import { ToastModule } from 'primeng/toast';
import {CalendarModule} from 'primeng/calendar';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { country,mill,businessUnit,processLines,frequencies } from '../../../assets/data/MasterData'; 
import { LocalStorageService } from '../../services/localStorage/local-storage.service';
import { CommonModel } from '../../models/CommonModel';
import { ChemicalConsumption } from '../../models/ChemicalConsumption'; 
import { ChemicalConsumptionEnquiry } from '../../models/ChemicalConsumptionEnquiry';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  providers: [MessageService,LocalStorageService]
})
export class SidebarComponent implements OnInit {
  chemicalConsumptionData = new ChemicalConsumption();
  isActive: boolean;
  collapsed: boolean;
  showMenu: string;
  pushRightClass: string;
  @Output() collapsedEvent = new EventEmitter<any>();
  countries :Country[];
  mills: Mill[];
  businessUnits: BusinessUnit[];
  processLines:any[];
  kpiTypes:KpiType[];
  selectedValue:CommonModel[]=[];
  selectedkpiTypes:KpiType[]=[];
  countryId:string;
  createSetForm: FormGroup;
  chemicalConsumptionEnquiry = new ChemicalConsumptionEnquiry();
  locations: Location[];
  plSets: Location[];
  msgs: Message[] = [];
  locationId: number;
  businessUnitId: number;
  plSetId: number;
  frequencies:any[];
  start:string="";
  end:string="";
  hide:boolean=true;
  cities2:KpiType[];
  @Input() kpiTypeId: string;
  
  constructor(private localStorageService:LocalStorageService,private router:Router,private fb: FormBuilder,  private masterDataService: MasterDataService, private messageService: MessageService) { 
    this.countries= country;
    this.mills=mill;
    this.businessUnits=businessUnit;
    this.processLines=processLines;
    this.frequencies = [
      { name: 'Daily', code: '0' },
      { name: 'Monthly', code: '1' },
      { name: 'Quarterly', code: '2' },
      { name: 'Yearly', code: '3' }
    ];

 
  
 
    
    this.router.events.subscribe(val => {
      if (
          val instanceof NavigationEnd &&
          window.innerWidth <= 992 &&
          this.isToggled()
      ) {
          this.toggleSidebar();
      }
  });
    
    
  }

  public getKpiType(kpiCategoryId){
    const requestData = {
      kpiCategoryId : kpiCategoryId
    };

    this.masterDataService.getKpiType(requestData).subscribe((data: any) => {
     this.kpiTypes=data;
    });
  }

  public onChangeCountry() {
  }

  

 
  ngOnInit() {
    this.isActive = false;
    this.collapsed = true;
    this.showMenu = '';
    this.pushRightClass = 'push-right';

    if(this.localStorageService.fetchUserRole()=="Mills Operation"){
      this.chemicalConsumptionEnquiry.selectedValue = this.frequencies.find(frequency => frequency.name === 'Daily');   
    }else{
      this.chemicalConsumptionEnquiry.selectedValue = this.frequencies.find(frequency => frequency.name === 'Monthly');
    }
    
    this.getKpiType(this.kpiTypeId);    
  }
  

 


  eventCalled() {
      this.isActive = !this.isActive;
  }

  addExpandClass(element: any) {
      if (element === this.showMenu) {
          this.showMenu = '0';
      } else {
          this.showMenu = element;
      }
  }

  toggleCollapsed() {
    
      this.collapsed = !this.collapsed;
      this.hide = !this.hide;
      this.chemicalConsumptionEnquiry.collapsed=this.collapsed.toString();
      this.collapsedEvent.emit(this.chemicalConsumptionEnquiry);
  }

  isToggled(): boolean {
      const dom: Element = document.querySelector('body');
      return dom.classList.contains(this.pushRightClass);
  }

  toggleSidebar() {
      const dom: any = document.querySelector('body');
      dom.classList.toggle(this.pushRightClass);
      
      
  }
  



  

  getState(currentMenu) {

    if (currentMenu.active) {
      return 'down';
    } else {
      return 'up';
    }
  }

   searchData(){
     if(this.chemicalConsumptionEnquiry.date==null){
      alert("Please select date range");
      return null;
     }
     console.log(this.chemicalConsumptionEnquiry.KPITypes);
     if(this.chemicalConsumptionEnquiry.KPITypes==undefined || this.chemicalConsumptionEnquiry.KPITypes.length==0){
      this.chemicalConsumptionEnquiry.KPITypes=this.kpiTypes;
     }
    this.chemicalConsumptionEnquiry.collapsed="null";
    this.collapsedEvent.emit(this.chemicalConsumptionEnquiry);
  } 

}





