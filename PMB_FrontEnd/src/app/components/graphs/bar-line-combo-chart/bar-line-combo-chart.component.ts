import { Component, NgModule, Input, Output, EventEmitter, OnInit,OnChanges,SimpleChanges } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxChartsModule,ViewDimensions,calculateViewDimensions } from '@swimlane/ngx-charts';
import { single,multi } from 'src/assets/data/data';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { DataServiceService } from 'src/app/services/data/data-service.service';
import { DateRangePickerModule } from '@syncfusion/ej2-angular-calendars';
import { DataTableModule } from 'primeng/primeng';
import { DialogModule } from 'primeng/primeng';
import { MultiSelectModule } from 'primeng/multiselect';
import * as shape from 'd3-shape';

@Component({
  selector: 'app-bar-line-combo-chart',
  templateUrl: './bar-line-combo-chart.component.html',
  styleUrls: ['./bar-line-combo-chart.component.scss']
})
export class BarLineComboChartComponent implements OnInit {

  multi: any[];
  single:any[];
  public start: Date = new Date("01/01/2017");
  public end: Date = new Date("03/12/2019");
  public items: string[] = ['South', 'North', 'West', 'East'];
  enquiryForm: FormGroup;
  @Input() heading: string;
  @Input() color: string;
  @Output() event = new EventEmitter();
  @Input() dynamicdata: any[];
  sales: any[];
  cols: any[];
  view: any[] = [1000,500];
  resultData: any;
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Year';
  showYAxisLabel = true;
  yAxisLabel = 'Quantity';
  private _ngxDefaultTimeout;
  private _ngxDefaultInterval;
  private _ngxDefault;
  colorScheme = { domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'] };
  gridHide : boolean=true;
  lineChartColorScheme = { domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA'] };
  dims: ViewDimensions=({
    width: 10,
    height: 10,
    xOffset:10
  });
  

 
  lineChartLineInterpolation = shape.curveMonotoneX;
  ngOnInit() {
    this.sales = [];
    var sale: any = this.sales[0];

    var keys = [], name,key;
    for (name in sale) {
if(name!= 'Region'){
      keys.push(name);
    }}
    this.cols = [
      { field: 'Region', header: 'Region' }
    ];
    keys.forEach(contact => {
      this.cols.push({'field':contact,'header':contact});
    });
    console.log(this.cols);
  }
  constructor(private fb: FormBuilder, private dataService: DataServiceService) {


    this.enquiryForm = fb.group({ bar1Color: ['#5AA454'], bar2Color: ['#A10A28'], bar3Color: ['#C7B42C'], bar4Color: ['#AAAAAA'], xAxisLabel: ['Year'], yAxisLabel: ['Quantity'],region:[''] });
    dataService.fetchData("all").subscribe((data: any) => {
      this.multi = data;
    });

    dataService.fetchBarData("Candy").subscribe((data: any) => {
      this.single = data;
    }); 
    
    Object.assign(this, {single, multi })
  }

  public onClickMe(data: any) {
    this.colorScheme = {
      domain: [data.bar1Color, data.bar2Color, data.bar3Color, data.bar4Color]
    };
    this.display = !this.display;
    
  }

  public doNgxDefault(): any {
    return this._ngxDefault;
  }
public colorPickerSelect(){
  console.log("asdasdasdas");
}

  public doSelect(event:string) {
    this.dataService.fetchData(event).subscribe((data: any) => {
      this.multi = data;
    });
    Object.assign(this, { multi })
  }

  public onSelect(event:any) {
    this.dataService.fetchGridData(event.name).subscribe((data: any) =>{
      console.log(data);
      this.gridHide=false;
    this.sales = data;
    var sale: any = this.sales[0];

    var keys = [], name,key;
    for (name in sale) {
if(name!= 'Region'){
      keys.push(name);
    }}
    this.cols = [
      { field: 'Region', header: 'Region' }
    ];
    keys.forEach(contact => {
      this.cols.push({'field':contact,'header':contact});
    });
    });
    }

  public dateTickFormatting(val: any) {
    return val / 1000 + "K";
  }

  public onChange(args:any,val:any) {
    
    var startDate = args.startDate;
    var endDate = args.endDate;
    let x = { startDate,endDate }; 
    const myObjStr = JSON.stringify(x);
    this.dataService.fetchDateFilterData(myObjStr).subscribe((data: any) => {
      this.multi = data;
    });
    Object.assign(this, { multi })

  }

  public doRemove(event){
    this.dataService.fetchData("all").subscribe((data: any) => {
      this.multi = data;
    });
    Object.assign(this, { multi })
  }
  private _opened: boolean = false;
  private _toggleSidebar() {
    this._opened = !this._opened;
  }

  display: boolean = false;

  showDialog() {
      this.display = !this.display;
  }

  public updateAxisLabels(data){
    this.xAxisLabel = data.xAxisLabel;
    this.yAxisLabel = data.yAxisLabel;
  }

 
  ngOnChanges(changes: SimpleChanges) {

       this.dataService.fetchDateFilterData(changes.dynamicdata.currentValue).subscribe((data: any) => {
          this.multi = data;
        });
        Object.assign(this, { multi })
      }

      public yAxisTickFormatting(val: any) {
        return val;
      }

      

      

}
