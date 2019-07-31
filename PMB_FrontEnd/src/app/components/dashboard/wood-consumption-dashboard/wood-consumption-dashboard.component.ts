import { Component, OnInit, SimpleChange, ViewChild, AfterViewInit } from '@angular/core';
import { SidebarComponent } from '../../sidebar/sidebar.component';
import { hideSpinner } from '@syncfusion/ej2-popups';
import { MessageService } from 'primeng/components/common/messageservice';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ProductonLine } from '../../../models/ProductionLine';
import { multi1, multi2, multi3, multi4, ProductonLineData1, ProductonLineData2, ProductonLineData3, ProductonLineData4 } from '../../../../assets/data/data1';
import { WoodConsumption } from '../../../models/WoodConsumption';
import { DatePipe, DecimalPipe } from '@angular/common';
import { ChemicalConsumptionService } from '../../../services/ChemicalConsumption/chemical-consumption.service';
import { KpiType } from 'src/app/models/KpiType';
import { MasterDataService } from 'src/app/services/masterData/master-data.service';
import { WoodConsumptionEnquiry } from '../../../models/WoodConsumptionEnquiry';
import { LocalStorageService } from '../../../services/localStorage/local-storage.service';
import { maintanenceDaysColumn,processLineColumns,annotationsCols,frequencies,processLines } from '../../../../assets/data/MasterData';

@Component({
  selector: 'app-wood-consumption-dashboard',
  templateUrl: './wood-consumption-dashboard.component.html',
  styleUrls: ['./wood-consumption-dashboard.component.scss'],
  providers: [MessageService, DatePipe,LocalStorageService]
})
export class WoodConsumptionDashboardComponent implements OnInit {

  @ViewChild(SidebarComponent) child;
  woodConsumptionRequest: WoodConsumption = new WoodConsumption;
  variable1: string = '7%';
  variable2: string = '93%';
  frequencies: any[];
  checked1: boolean = true;
  checked2: boolean = true;
  checked3: boolean = true;
  checked4: boolean = true;
  checked5: boolean = true;
  checked6: boolean = true;
  view: any[] = [537,250];
  cols: any[];
  virtualCols:any;
  AnnotationDialog: boolean = false;
  kpiType12show: boolean = false;
  kpiType13show: boolean = false;
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
  chemicalPl: any[] = [];
  woodConsumptionEnquiry = new WoodConsumptionEnquiry();
  productonLines: ProductonLine[];
  colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#660000'] };
  title: string = "";

  
  constructor(private localStorageService:LocalStorageService,private masterDataService:MasterDataService,private chemicalConsumptionService: ChemicalConsumptionService, private messageService: MessageService, private fb: FormBuilder, private datePipe: DatePipe) {
    this.createAnnotationForm = fb.group(
      {
        AnnotationDate: "",
        AnnotationText: ""
      });
      this.frequencies = frequencies;
  this.processLines = processLines;
}

startDate:string="";
endDate:string="";

ngOnInit() {
  this.kpiTypData = [
    {kpiTypeId: 12, kpiTypeName: "Wood Chip"},
    {kpiTypeId: 13, kpiTypeName: "Yield"}
  ];
  
  this.cols = [
    { field: 'date', header: 'Date' },
    { field: 'fl1', header: 'FL1' },
    { field: 'fl2', header: 'FL2' },
    { field: 'fl3', header: 'FL3' }
  ];

  
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
  
    this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() ).toString() + '-' + (new Date().getDate()-1) ;
    this.endDate =   this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    this.woodConsumptionEnquiry.KPITypes=this.kpiTypData;
    this.woodConsumptionEnquiry.PLines=[];
    this.woodConsumptionEnquiry.date=[];
    this.woodConsumptionEnquiry.date.push(this.startDate,this.endDate)
    
    if(this.localStorageService.fetchUserRole()=="Mills Operation"){
      var frequency={name: "Daily", code: "0"};
      this.woodConsumptionEnquiry.selectedValue=frequency; 
    }else{
      var frequency={name: "Monthly", code: "1"};
      this.woodConsumptionEnquiry.selectedValue=frequency; 
    }
    this.showKpiCharts(this.woodConsumptionEnquiry);
    
    this.checked1 = false;
    this.checked2 = false;
    this.checked3 = false;
    this.checked4 = false;
    this.checked5 = false;
    this.checked6 = false;
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
    this.woodConsumptionRequest.kpiId=kpiId;
    this.chemicalConsumptionService.getKpiGridData(this.woodConsumptionRequest).subscribe((data: any) => {
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
    } if (data['AnnotationText'] == "") {
      this.showMessage('error', 'UnSuccessful :', 'Text Cannot be blank');
      return null;
    }

    this.showMessage('success', 'Successful :', 'Saved Successfully');
    this.showAnnotationDialog("");
  }


  public chaneChartTypeDiv1(event) {
    this.div1StackChart = !this.div1StackChart;
    this.div1BarChart = !this.div1BarChart;
  }

  public chaneChartTypeDiv2(event) {
    this.div2StackChart = !this.div2StackChart;
    this.div2BarChart = !this.div2BarChart;
  }

  public chaneChartTypeDiv3(event) {
    this.div3StackChart = !this.div3StackChart;
    this.div3BarChart = !this.div3BarChart;
  }

  public chaneChartTypeDiv4(event) {
    this.div4StackChart = !this.div4StackChart;
    this.div4BarChart = !this.div4BarChart;
  }

  public chaneChartTypeDiv5(event) {
    this.div5StackChart = !this.div5StackChart;
    this.div5BarChart = !this.div5BarChart;
  }

  public chaneChartTypeDiv6(event) {
    this.div6StackChart = !this.div6StackChart;
    this.div6BarChart = !this.div6BarChart;
  }

  public chaneChartTypeDiv7(event) {
    this.div7StackChart = !this.div7StackChart;
    this.div7BarChart = !this.div7BarChart;
  }

  public chaneChartTypeDiv8(event) {
    this.div8StackChart = !this.div8StackChart;
    this.div8BarChart = !this.div8BarChart;
  }

public chaneChartTypeDiv9(event) {
    this.div9StackChart = !this.div9StackChart;
    this.div9BarChart = !this.div9BarChart;
  }

  public chaneChartTypeDiv10(event) {
    this.div10StackChart = !this.div10StackChart;
    this.div10BarChart = !this.div10BarChart;
  }

  public chaneChartTypeDiv11(event) {
    this.div11StackChart = !this.div11StackChart;
    this.div11BarChart = !this.div11BarChart;
  }

  processLines: any[] = [];
  kpiId18Data: any[] = [];
  kpiId19Data: any[] = [];
  kpiId20Data: any[] = [];
  kpiId21Data: any[] = [];
  kpiId22Data: any[] = [];
  kpiId23Data: any[] = [];

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
    console.log(filterData);
    this.kpiType12show = false;
    this.kpiType13show = false;
    this.processLines = [];
    if (filterData.PLines == null) {
      this.processLines = [];
    } else {
      filterData.PLines.forEach(element => {
        this.processLines.push(element.header);
      });
    }
    this.woodConsumptionRequest.frequency = filterData.selectedValue['code'];
    this.woodConsumptionRequest.startDate = this.datePipe.transform(filterData.date[0], 'yyyy-MM-dd');
    this.woodConsumptionRequest.endDate = this.datePipe.transform(filterData.date[1], 'yyyy-MM-dd');
    this.woodConsumptionRequest.processLines = this.processLines;
   /*  console.log(filterData.KPITypes);
    if (filterData.KPITypes == null || filterData.KPITypes==[]) {
      console.log("asd");
      filterData.KPITypes=this.kpiTypes;
    }
console.log(filterData); */
    filterData.KPITypes.forEach(element => {
      if (element.kpiTypeId == 12) {
        this.kpiType12show = !this.kpiType12show
        this.woodConsumptionRequest.kpiId = "18";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          console.log(data)
          this.kpiId18Data = data;
        });
        this.woodConsumptionRequest.kpiId = "19";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          console.log(data)
          this.kpiId19Data = data;
        });
        this.woodConsumptionRequest.kpiId = "20";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          this.kpiId20Data = data;
        });
      }

      if (element.kpiTypeId == 13) {
        this.kpiType13show = !this.kpiType13show;
        this.woodConsumptionRequest.kpiId = "21";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          this.kpiId21Data = data;
        });
        this.woodConsumptionRequest.kpiId = "22";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          this.kpiId22Data = data;
        });
        this.woodConsumptionRequest.kpiId = "23";
        this.chemicalConsumptionService.getDataforKpi(this.woodConsumptionRequest).subscribe((data: any) => {
          this.kpiId23Data = data;
        });
      }
    });
  }

}
