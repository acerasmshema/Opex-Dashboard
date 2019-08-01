
import { ComboChartComponent } from 'src/app/components/graphs/combo-chart/combo-chart.component';
import { ComboSeriesVerticalComponent } from 'src/app/components/graphs/combo-chart/combo-series-vertical.component';
declare var APP_VERSION: string;
import { Component, OnInit, ViewEncapsulation, EventEmitter, Output, HostListener, OnChanges } from '@angular/core';
import { Location, LocationStrategy, HashLocationStrategy } from '@angular/common';
import * as shape from 'd3-shape';
import * as d3 from 'd3';
import chartGroups from 'src/assets/data/chartTypes';
import { DataServiceService } from 'src/app/services/data/data-service.service';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { single, multi, yearly,monthly } from 'src/assets/data/data';
import { barChart, lineChartSeries } from 'src/assets/data/combo-chart-data';
import { DialogModule } from 'primeng/primeng';
import { Router } from '@angular/router';
import { TranslateService } from '../../../services/translate/translate.service';
import { TranslatePipe } from '../../../pipes/translate.pipe';
import { gridData} from 'src/assets/data/data1';
import { IToDoList } from '../../../models/refLine';
@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss'],
  providers: [DataServiceService]
})

export class ContentComponent implements OnInit {

  

  theme = 'dark';
  chartType: string;
  chartGroups: any[];
  chart: any;
  realTimeData: boolean = false;
  countries: any[];
  single: any[];
  multi: any[];
  enquiryForm: FormGroup;
  dateData: any[];
  dateDataWithRange: any[];
  calendarData: any[];
  statusData: any[];
  sparklineData: any[];
  timelineFilterBarData: any[];
  graph: { links: any[]; nodes: any[] };
  bubble: any;
  linearScale: boolean = false;
  range: boolean = false;

  view: any[];
  width: number;
  height: number;
  fitContainer: boolean = false;

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  legendTitle = 'Legend';
  legendPosition = 'right';
  showXAxisLabel = true;
  tooltipDisabled = false;
  xAxisLabel = 'Year';
  showYAxisLabel = true;
  yAxisLabel = 'kroduced Qty';
  showGridLines = true;
  innerPadding = '10%';
  barPadding = 8;
  groupPadding = 16;
  roundDomains = false;
  maxRadius = 10;
  minRadius = 3;
  showSeriesOnHover = true;
  roundEdges: boolean = true;
  animations: boolean = true;
  xScaleMin: any;
  xScaleMax: any;
  yScaleMin: number;
  yScaleMax: number;
  showDataLabel = false;
  trimXAxisTicks = true;
  trimYAxisTicks = true;
  maxXAxisTickLength = 16;
  maxYAxisTickLength = 16;
  private _ngxDefault;
  public items: string[] = ['Series1', 'Series2'];

  targetLine1: boolean = true;
  targetLine2: boolean = true;
cars:IToDoList[]=[];
clonedCars: { [s: string]: IToDoList; } = {};

  barChart: any[] = barChart;

  lineChartSeries: any[] = lineChartSeries;
  lineChartSeries1: any[];
  lineChartSeries2: any[];
  showChart: boolean = true;
  lineChartScheme = {
    name: 'coolthree',
    selectable: true,
    group: 'Ordinal',
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  comboBarScheme = {
    name: 'singleLightBlue',
    selectable: true,
    group: 'Ordinal',
    domain: ['#AAAAAA']
  };

  showRightYAxisLabel: boolean = true;
  yAxisLabelRight: string = 'Utilization';
  isOpen: boolean = false;
  // demos
  totalSales = 0;
  salePrice = 100;
  personnelCost = 100;

  mathText = '3 - 1.5*sin(x) + cos(2*x) - 1.5*abs(cos(x))';
  mathFunction: (o: any) => any;

  treemap: any[];
  treemapPath: any[] = [];
  sumBy: string = 'Size';

  // Reference lines
  showRefLines: boolean = true;
  showRefLabels: boolean = true;

  constructor(private fb: FormBuilder, public location: Location, private dataService: DataServiceService, private router: Router, private translateService: TranslateService) {

    this.enquiryForm = fb.group({ bar2Color: ['#AAAAAA'], bar3Color: ['#C7B42C'], bar4Color: ['#A10A28'], xAxisLabel: ['Year'], yAxisLabel: ['Produced Quantity'], targetLine1: this.targetLine1, targetLine2: this.targetLine2 });
    /* dataService.fetchBarData("Candy").subscribe((data: any) => {

      this.barChart = data;
    }); */

    this.barChart=single;

    this.lineChartSeries.splice(0, 2);

    dataService.fetchSeriesData("Max target").subscribe((data: any) => {
      this.lineChartSeries1 = data;
      this.lineChartSeries.push(this.lineChartSeries1);
    });

     dataService.fetchSeriesData("Min target").subscribe((data: any) => {
      this.lineChartSeries2 = data;
      this.lineChartSeries.push(this.lineChartSeries2);
    } 
    ); 
    Object.assign(this, {
      single,
      multi,
      chartGroups
    });
    //this.timelineFilterBarData = timelineFilterBarData();

  }


  ngOnInit() {
    const state = this.location.path(true);
    this.selectChart(state.length ? state : 'bar-vertical');

    setInterval(this.updateData.bind(this), 1000);

    if (!this.fitContainer) {
      this.applyDimensions();
    }
    this.cars=gridData;

  }

  updateData() {
    if (!this.realTimeData) {
      return;
    }



    const Year = this.countries[Math.floor(Math.random() * this.countries.length)];
    const add = Math.random() < 0.7;
    const remove = Math.random() < 0.5;

    if (remove) {
      if (this.single.length > 1) {
        const index = Math.floor(Math.random() * this.single.length);
        this.single.splice(index, 1);
        this.single = [...this.single];
      }

      if (this.multi.length > 1) {
        const index = Math.floor(Math.random() * this.multi.length);
        this.multi.splice(index, 1);
        this.multi = [...this.multi];
      }


    }

    if (add) {
      // single
      const entry = {
        name: Year.name,
        value: Math.floor(10000 + Math.random() * 50000)
      };
      this.single = [...this.single, entry];
    }

    const date = new Date(Math.floor(1473700105009 + Math.random() * 1000000000));
    for (const series of this.dateData) {
      series.series.push({
        name: date,
        value: Math.floor(2000 + Math.random() * 5000)
      });
    }
    this.dateData = [...this.dateData];
  }

  applyDimensions() {
    this.view = [this.width, this.height];
  }



  selectChart(chartSelector) {
    this.chartType = chartSelector = chartSelector.replace('/', '');
    this.location.replaceState(this.chartType);

    for (const group of this.chartGroups) {
      this.chart = group.charts.find(x => x.selector === chartSelector);
      if (this.chart) break;
    }

    this.linearScale = false;
    this.yAxisLabel = "Produced Qty";
    this.xAxisLabel = "Year";

    this.width=250;
    this.height = 250;



    if (!this.fitContainer) {
      this.applyDimensions();
    }
  }

  select(data) {
    
  }



  onLegendLabelClick(entry) {
    
  }








  generatePlotData() {
    if (!this.mathFunction) {
      return [];
    }
    const twoPi = 2 * Math.PI;
    const length = 25;
    const series = Array.apply(null, { length }).map((d, i) => {
      const x = i / (length - 1);
      const t = x * twoPi;
      return {
        name: ~~(x * 360),
        value: this.mathFunction(t)
      };
    });

    return [
      {
        name: this.mathText,
        series
      }
    ];
  }







  /*
  **
  Combo Chart
  **
  [yLeftAxisScaleFactor]="yLeftAxisScale" and [yRightAxisScaleFactor]="yRightAxisScale"
  exposes the left and right min and max axis values for custom scaling, it is probably best to
  scale one axis in relation to the other axis but for flexibility to scale either the left or
  right axis bowth were exposed.
  **
  */

  yLeftAxisScale(min, max) {
    return { min: `${min}`, max: `${max}` };
  }

  yRightAxisScale(min, max) {
    return { min: `${min}`, max: `${max}` };
  }

  yLeftTickFormat(data) {
    return `${data.toLocaleString()}`;
  }

  yRightTickFormat(data) {
    return `${data}%`;
  }
  /*
  **
  End of Combo Chart
  **
  */

  onSelect(event) {
    
  }

  dblclick(event) {
    
  }

  public doNgxDefault(): any {
    return this._ngxDefault;
  }

  private _opened: boolean = false;
  private _toggleSidebar() {
    this._opened = !this._opened;
  }
  public doSelect(event: string) {

  }

  display: boolean = false;
  showDialog() {
    this.display = !this.display;
  }


  public onClickMe(data: any) {
    this.comboBarScheme = {
      name: 'singleLightBlue',
      selectable: true,
      group: 'Ordinal',
      domain: [data.bar2Color]
    };


    this.lineChartScheme = {
      name: 'coolthree',
      selectable: true,
      group: 'Ordinal',
      domain: [data.bar2Color, data.bar3Color, data.bar4Color, data.bar2Color]
    };
    this.xAxisLabel = data.xAxisLabel;
    this.yAxisLabel = data.yAxisLabel;
    this.lineChartSeries = [];

    if (data.targetLine1) {
      this.lineChartSeries.push(this.lineChartSeries2);
    }
    if (data.targetLine2) {
      this.lineChartSeries.push(this.lineChartSeries1);
    }
    this.display = !this.display;

  }

  public updateAxisLabels(data) {
    this.xAxisLabel = data.xAxisLabel;
    this.yAxisLabel = data.yAxisLabel;
  }

display1: boolean = false;

  showDialog1() {
      this.display1 = !this.display1;
  }

  onRowEditInit(data: IToDoList) {
    this.clonedCars[data.value] = {...data};
}

onRowEditSave(data: IToDoList) {
     delete this.clonedCars[data.value];
     
    }


onRowEditCancel(data: IToDoList, index: number) {
    delete this.clonedCars[data.value];
}


public saveRefData(){
  
  let x = { "name": "Ref Line",
     "series" : this.cars}
  this.lineChartSeries.push(x); 
  Object.assign(this, {
    single,
    multi,
    chartGroups
  });
}
}