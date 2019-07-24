import { Component,Input, OnInit, SimpleChange, ViewChild, AfterViewInit } from '@angular/core';
import { SidebarComponent } from '../../sidebar/sidebar.component';
import { hideSpinner } from '@syncfusion/ej2-popups';
import { MessageService } from 'primeng/components/common/messageservice';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ChemicalConsumption } from '../../../models/ChemicalConsumption';
import { DatePipe, DecimalPipe } from '@angular/common';
import { ChemicalConsumptionService } from '../../../services/ChemicalConsumption/chemical-consumption.service';
import { KpiType } from 'src/app/models/KpiType';
import { MasterDataService } from 'src/app/services/masterData/master-data.service';
import { ChemicalConsumptionEnquiry } from '../../../models/ChemicalConsumptionEnquiry';
import { LocalStorageService } from '../../../services/localStorage/local-storage.service';
import { frequencies,processLines } from '../../../../assets/data/MasterData';

@Component({
  selector: 'app-chemical-consumption-dashboard',
  templateUrl: './chemical-consumption-dashboard.component.html',
  styleUrls: ['./chemical-consumption-dashboard.component.scss'],
  providers: [MessageService, DatePipe,LocalStorageService,ChemicalConsumptionService]
})
export class ChemicalConsumptionDashboardComponent implements OnInit {

  @ViewChild(SidebarComponent) child;
  chemicalConsumptionRequest: ChemicalConsumption = new ChemicalConsumption;
  variable1: string = '7%';
  variable2: string = '93%';
  frequencies: any;
  checked1: boolean = true;
  checked2: boolean = true;
  checked3: boolean = true;
  checked4: boolean = true;
  checked5: boolean = true;
  checked6: boolean = true;
  view: any[] = [537,250];
  checked7: boolean = true;
  checked8: boolean = true;
  checked9: boolean = true;
  checked10: boolean = true;
  checked11: boolean = true;
  cols: any[];
  virtualCols:any;
  AnnotationDialog: boolean = false;
  kpiType2show: boolean = false;
  kpiType3show: boolean = false;
  kpiType4show: boolean = false;
  kpiType5show: boolean = false;
  kpiType6show: boolean = false;
  kpiType7show: boolean = false;
  kpiType8show: boolean = false;
  GridDialog: boolean = false;
  kpiTypData:any[]=[];
  kpiTypes:KpiType[]=[];
  div1StackChart: boolean = false;
  div1BarChart: boolean = true;
  div2StackChart: boolean = false;
  div2BarChart: boolean = true;
  div3StackChart: boolean = false;
  div3BarChart: boolean = true;
  div4StackChart: boolean = false;
  div4BarChart: boolean = true;
  div5StackChart: boolean = false;
  div5BarChart: boolean = true;
  div6StackChart: boolean = false;
  div6BarChart: boolean = true;
  div7StackChart: boolean = false;
  div7BarChart: boolean = true;
  div8StackChart: boolean = false;
  div8BarChart: boolean = true;
  div9StackChart: boolean = false;
  div9BarChart: boolean = true;
  div10StackChart: boolean = false;
  div10BarChart: boolean = true;
  div11StackChart: boolean = false;
  div11BarChart: boolean = true;
  createAnnotationForm: FormGroup;
  cities2: any[] = [];
  chemicalConsumptionEnquiry = new ChemicalConsumptionEnquiry();
  private activeEntries: any[] = [];
  productonLines: any[]=[];
  colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#660000'] };
  title: string = "";

  constructor(private localStorageService:LocalStorageService,private masterDataService:MasterDataService,private chemicalConsumptionService: ChemicalConsumptionService, private messageService: MessageService, private fb: FormBuilder, private datePipe: DatePipe) {
    this.createAnnotationForm = fb.group(
      {
        AnnotationDate: "",
        AnnotationText: ""
      });
      
      this.kpiTypData = [
        {kpiTypeId: 8, kpiTypeName: "White Liquor"},
        {kpiTypeId: 2, kpiTypeName: "CIO2"},
    {kpiTypeId: 3, kpiTypeName: "Defoamer"},
    {kpiTypeId: 4, kpiTypeName: "H2O2"},
    {kpiTypeId: 5, kpiTypeName: "NaOH"},
    {kpiTypeId: 6, kpiTypeName: "Oxygen"},
    {kpiTypeId: 7, kpiTypeName: "Sulfuric_Acid (H2SO4)"}
    
      ];
    
      this.frequencies = frequencies;
    
      this.virtualCols = {
        date: {header: 'Date', field: 'date'} ,
        fl1: {header: 'FL1', field: 'fl1'} ,
        fl2: {header: 'FL2', field: 'fl2'} ,
        fl3: {header: 'FL3', field: 'fl3'} ,
        pcd: {header: 'PCD', field: 'pcd'} ,
        pd1: {header: 'PD1', field: 'pd1'} ,
        pd2: {header: 'PD2', field: 'pd2'} ,
        pd3: {header: 'PD3', field: 'pd3'} ,
        pd4: {header: 'PD4', field: 'pd4'} }
     ;
    
     this.processLines = processLines;
     
}


startDate:string="";
endDate:string="";

ngOnInit() {  
    this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() ).toString() + '-' + (new Date().getDate()-1) ;
    this.endDate =   this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    this.chemicalConsumptionEnquiry.KPITypes=this.kpiTypData;
    this.chemicalConsumptionEnquiry.PLines=[];
    this.chemicalConsumptionEnquiry.date=[];
    this.chemicalConsumptionEnquiry.date.push(this.startDate,this.endDate)
    var frequency;
    if(this.localStorageService.fetchUserRole()=="Mills Operation"){
       frequency={name: "Daily", code: "0"};
      this.chemicalConsumptionEnquiry.selectedValue=frequency; 
    }else{
       frequency={name: "Monthly", code: "1"};
      this.chemicalConsumptionEnquiry.selectedValue=frequency;
    }
    this.showKpiCharts(this.chemicalConsumptionEnquiry);
    
    this.checked1 = false;
    this.checked2 = false;
    this.checked3 = false;
    this.checked4 = false;
    this.checked5 = false;
    this.checked6 = false;
    this.checked7 = false;
    this.checked8 = false;
    this.checked9 = false;
    this.checked10 = false;
    this.checked11 = false;
  }

  public getKpiType(kpiCategoryId){
    const requestData = {
      kpiCategoryId : kpiCategoryId
    };

    this.masterDataService.getKpiType(requestData).subscribe((data: any) => {
     this.kpiTypes=this.kpiTypData;
    });
  }

  public showAnnotationDialog(title: string) {
    this.title = "Add Annotation ( " + title + " )";
    this.AnnotationDialog = !this.AnnotationDialog;
  }


  public showGridDialog(kpiId, title) {
    this.productonLines=[];
    this.title = title;
    this.chemicalConsumptionRequest.kpiId=kpiId;
    this.chemicalConsumptionService.getKpiGridData(this.chemicalConsumptionRequest).subscribe((data: any) => {
      
      this.cols=[];
      for(var k in data[0][0]) {
        this.cols.push(this.virtualCols[k]);  
      }
      this.cols.sort(function(a, b) {
        var nameA = a.header.toUpperCase(); // ignore upper and lowercase
        var nameB = b.header.toUpperCase(); // ignore upper and lowercase
        if (nameA < nameB) {
          return -1;
        }
        if (nameA > nameB) {
          return 1;
        }
      return 0;
      });

      
      this.productonLines=data[0];
      });
    this.GridDialog = !this.GridDialog;
  }

  public showMessage(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }


  public saveAnnotation(data: any) {
    if (data['AnnotationDate'] == "") {
      this.showMessage('error', 'UnSuccessful :', 'Date Cannot be blank');
      return null;
    } 
    if (data['AnnotationText'] == "") {
      this.showMessage('error', 'UnSuccessful :', 'Text Cannot be blank');
      return null;
    }

    this.showMessage('success', 'Successful :', 'Saved Successfully');
    this.showAnnotationDialog("");
  }


  public changeChartTypeDiv1(event) {
    this.div1StackChart = !this.div1StackChart;
    this.div1BarChart = !this.div1BarChart;
  }

  public changeChartTypeDiv2(event) {
    this.div2StackChart = !this.div2StackChart;
    this.div2BarChart = !this.div2BarChart;
  }

  public changeChartTypeDiv3(event) {
    this.div3StackChart = !this.div3StackChart;
    this.div3BarChart = !this.div3BarChart;
  }

  public changeChartTypeDiv4(event) {
    this.div4StackChart = !this.div4StackChart;
    this.div4BarChart = !this.div4BarChart;
  }

  public changeChartTypeDiv5(event) {
    this.div5StackChart = !this.div5StackChart;
    this.div5BarChart = !this.div5BarChart;
  }

  public changeChartTypeDiv6(event) {
    this.div6StackChart = !this.div6StackChart;
    this.div6BarChart = !this.div6BarChart;
  }

  public changeChartTypeDiv7(event) {
    this.div7StackChart = !this.div7StackChart;
    this.div7BarChart = !this.div7BarChart;
  }

  public changeChartTypeDiv8(event) {
    this.div8StackChart = !this.div8StackChart;
    this.div8BarChart = !this.div8BarChart;
  }

public changeChartTypeDiv9(event) {
    this.div9StackChart = !this.div9StackChart;
    this.div9BarChart = !this.div9BarChart;
  }

  public changeChartTypeDiv10(event) {
    this.div10StackChart = !this.div10StackChart;
    this.div10BarChart = !this.div10BarChart;
  }

  public changeChartTypeDiv11(event) {
    this.div11StackChart = !this.div11StackChart;
    this.div11BarChart = !this.div11BarChart;
  }

  processLines: any[] = [];
  kpiId2Data: any[] = [];
  kpiId3Data: any[] = [];
  kpiId4Data: any[] = [];
  kpiId5Data: any[] = [];
  kpiId6Data: any[] = [];
  kpiId7Data: any[] = [];
  kpiId8Data: any[] = [];
  kpiId9Data: any[] = [];
  kpiId10Data: any[] = [];
  kpiId11Data: any[] = [];
  kpiId12Data: any[] = [];
  talkBack(event) {
    if (event.collapsed=="true") {
      this.variable1 = '7%';
      this.variable2 = '93%';
      this.view = [537,250];
    } else {
      this.variable1 = '21%';
      this.variable2 = '79%';
      this.view = [450,250];
    }
if(event.collapsed=="null"){
    this.showKpiCharts(event);
}

  }

 
  showKpiCharts(filterData) {
    
     this.kpiType2show = false;
    this.kpiType3show = false;
    this.kpiType4show = false;
    this.kpiType5show = false;
    this.kpiType6show = false;
    this.kpiType7show = false;
    this.kpiType8show = false; 
    this.processLines = [];
    if (filterData.PLines == null) {
      this.processLines = [];
    } else {
      filterData.PLines.forEach(element => {
        this.processLines.push(element.header);
      });
    }
    this.chemicalConsumptionRequest.frequency = filterData.selectedValue['code'];
    this.chemicalConsumptionRequest.startDate = this.datePipe.transform(filterData.date[0], 'yyyy-MM-dd');
    this.chemicalConsumptionRequest.endDate = this.datePipe.transform(filterData.date[1], 'yyyy-MM-dd');
    this.chemicalConsumptionRequest.processLines = this.processLines;
    filterData.KPITypes.forEach(element => {

      if (element.kpiTypeId == 8) {
        this.kpiType8show = !this.kpiType8show;
        this.chemicalConsumptionRequest.kpiId = "11";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId11Data = data;
        });
       /*  this.chemicalConsumptionRequest.kpiId = "12";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId12Data = data;
        }); */
      }

      if (element.kpiTypeId == 2) {
        this.kpiType2show = !this.kpiType2show
        this.chemicalConsumptionRequest.kpiId = "2";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId2Data = data;
        });
      /*   this.chemicalConsumptionRequest.kpiId = "3";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId3Data = data;
        }); */
      }

      if (element.kpiTypeId == 3) {
        this.kpiType3show = !this.kpiType3show;
        this.chemicalConsumptionRequest.kpiId = "4";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId4Data = data;
        });
      }

      if (element.kpiTypeId == 4) {
        this.kpiType4show = !this.kpiType4show;
        this.chemicalConsumptionRequest.kpiId = "5";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId5Data = data;
        });
      }

      if (element.kpiTypeId == 5) {
        this.kpiType5show = !this.kpiType5show
        this.chemicalConsumptionRequest.kpiId = "6";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId6Data = data;
        });
        this.chemicalConsumptionRequest.kpiId = "7";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId7Data = data;
        });
       /*  this.chemicalConsumptionRequest.kpiId = "8";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId8Data = data;
        }); */
      }

      if (element.kpiTypeId == 6) {
        this.kpiType6show = !this.kpiType6show
        this.chemicalConsumptionRequest.kpiId = "9";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId9Data = data;
        });
      }

      if (element.kpiTypeId == 7) {
        this.kpiType7show = !this.kpiType7show
        this.chemicalConsumptionRequest.kpiId = "10";
        this.chemicalConsumptionService.getDataforKpi(this.chemicalConsumptionRequest).subscribe((data: any) => {
          this.kpiId10Data = data;
        });
      }
    });
  }

}
