import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';
import { ConsumptionModel } from '../shared/models/Consumption.model';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.scss']
})
export class BenchmarkComponent implements OnInit {
  processUnitLegends: any[] = [];
  constructor(private statusService: StatusService) { }

  consumptions: ConsumptionModel[] = [];

  ngOnInit() {
    this.processUnitLegends = this.getProcessUnitLegends();

    this.getConsumption(this.data, "ClO2 - Consumption (kg/ADt)");
    this.getConsumption(this.data1, "White Liquor (m3/ADt)");
    this.getConsumption(this.data2, "Oxygen (kg/ADt)");

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = true;
    sidebarRequest.type = "benchmark";
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

  getConsumption(data: any, unitName: string) {

    let ccm = new ConsumptionModel();
    //ccm.kpiTypeId = kpiTypeId;
    ccm.kpiName = unitName;
    ccm.data = data;
    ccm.view = [1437, 220];
    ccm.colorScheme = { domain: ['rgb(39,10,169,0.4)', 'rgb(39,10,169,0.6)', 'rgb(39,10,169,0.8)', 'rgb(39,10,169)', 'rgb(243, 202, 17,.4)', 'rgb(243, 202, 17,.6)', 'rgb(243, 202, 17,.9)'] };
    ccm.xAxis = true;
    ccm.yAxis = true;
    ccm.showDataLabel = false;
    ccm.xAxis = true;
    ccm.checked = false;
    ccm.legend = false;
    ccm.barPadding = 2;
    ccm.groupPadding = 5;
    ccm.chartType = "bar";
    ccm.showXAxisLabel = false;
    ccm.showYAxisLabel = true;
    ccm.gradient = "gradient";
    ccm.xAxisLabel = "";
    ccm.yAxisLabel = "BDt/ADt";
    ccm.showKpiType = true;
    this.consumptions.push(ccm);
  }

  columnNames = ['Year',
    'FL1', { role: 'annotation' },
    'FL2', { role: 'annotation' },
    'FL3', { role: 'annotation' },
    'KRC', { role: 'annotation' },
    'PL11', { role: 'annotation' },
    'PL12', { role: 'annotation' },
    'RZ', { role: 'annotation' },

  ];
  type = 'ColumnChart';

  options = {
    bar: { groupWidth: "97%", },
    colors: ['#4700b3', '#4700b3', '#4700b3', '#4700b3', '#e6e600', '#e6e600', '#e6e600'],
    legend: { position: 'none' },
    vAxis: {
      title: "BDt/ADt",
      gridlines: {
        count: 9,
        color:"white"
      },
      viewWindow: { max: 4 }
    },
    width: 1470,
    height: 300,
    annotations: {
      alwaysOutside: true,
      highContrast: false,
      textStyle: {
        fontName: 'Arial',
        fontSize: 13,
        bold: true,
        color: 'black',
      },
      stem: {
        length: 4,
        color: 'white',
      }

    },
  };

  data = [
    ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ'],
    ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ', 1.4],
  ];

  data1 = [
    ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ'],
    ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ'],
  ];
  data2 = [
    ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ'],
    ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ'],
    ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ'],
  ];



  getProcessUnitLegends(): any {
    let processUnitLegends = [
      { name: "KRC", color: "#4700b3" },
      { name: "RZ", color: "#e6e600" },
    ];

    return processUnitLegends;
  }

}


// import { Component, OnInit } from '@angular/core';
// import { SidebarRequest } from '../core/sidebar/sidebar-request';
// import { StatusService } from '../shared/service/status.service';
// import { ConsumptionModel } from '../shared/models/Consumption.model';

// @Component({
//   selector: 'app-benchmark',
//   templateUrl: './benchmark.component.html',
//   styleUrls: ['./benchmark.component.scss']
// })
// export class BenchmarkComponent implements OnInit {
//   processUnitLegends: any[] = [];
//   constructor(private statusService: StatusService) { }

//   consumptions: ConsumptionModel[] = [];

//   ngOnInit() {
//     this.processUnitLegends = this.getProcessUnitLegends();

//     this.getConsumption(this.data, "ClO2 - Consumption (kg/ADt)");
//     this.getConsumption(this.data1, "White Liquor (m3/ADt)");
//     this.getConsumption(this.data2, "Oxygen (kg/ADt)");

//     let sidebarRequest = new SidebarRequest();
//     sidebarRequest.isShow = true;
//     sidebarRequest.type = "benchmark";
//     this.statusService.sidebarSubject.next(sidebarRequest);
//   }

//   getConsumption(data: any, unitName: string) {

//     let ccm = new ConsumptionModel();
//     //ccm.kpiTypeId = kpiTypeId;
//     ccm.kpiName = unitName;
//     ccm.data = data;
//     ccm.view = [1437, 220];
//     ccm.colorScheme = { domain: ['rgb(39,10,169,0.4)', 'rgb(39,10,169,0.6)', 'rgb(39,10,169,0.8)', 'rgb(39,10,169)', 'rgb(243, 202, 17,.4)', 'rgb(243, 202, 17,.6)', 'rgb(243, 202, 17,.9)'] };
//     ccm.xAxis = true;
//     ccm.yAxis = true;
//     ccm.showDataLabel = false;
//     ccm.xAxis = true;
//     ccm.checked = false;
//     ccm.legend = false;
//     ccm.barPadding = 2;
//     ccm.groupPadding = 5;
//     ccm.chartType = "bar";
//     ccm.showXAxisLabel = false;
//     ccm.showYAxisLabel = true;
//     ccm.gradient = "gradient";
//     ccm.xAxisLabel = "";
//     ccm.yAxisLabel = "BDt/ADt";
//     ccm.showKpiType = true;
//     this.consumptions.push(ccm);
//   }

//   columnNames = ['Year',
//     'FL1', { role: 'annotation' },
//     'FL2', { role: 'annotation' },
//     'FL3', { role: 'annotation' },
//     'KRC', { role: 'annotation' },
//     'PL11', { role: 'annotation' },
//     'PL12', { role: 'annotation' },
//     'RZ', { role: 'annotation' },
//     'PL21', { role: 'annotation' },
//     'PL22', { role: 'annotation' },
//     'PL23', { role: 'annotation' },
//     'XYZ', { role: 'annotation' },

//   ];
//   type = 'ColumnChart';

//   options = {
//     bar: { groupWidth: "97%", },
//     colors: ['#4700b3', '#4700b3', '#4700b3', '#4700b3', '#e6e600', '#e6e600', '#e6e600', 'red', 'red', 'red', 'red'],
//     legend: { position: 'none' },
//     vAxis: {
//       title: "BDt/ADt",
//       gridlines: {
//         count: 9,
//         color:"white"
//       },
//       viewWindow: { max: 4 }
//     },
//     width: 1470,
//     height: 300,
//     annotations: {
//       alwaysOutside: true,
//       highContrast: false,
//       textStyle: {
//         fontName: 'Arial',
//         fontSize: 13,
//         bold: true,
//         color: 'black',
//       },
//       stem: {
//         length: 4,
//         color: 'white',
//       }

//     },
//   };

//   data = [
//     ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.24, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.9, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ', 2.1, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ', 1.4, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//   ];

//   data1 = [
//     ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.24, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.9, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ', 2.1, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ', 1.4, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//   ];
//   data2 = [
//     ['2017', 1.94, 'FL1', 2.924, 'FL2', 1.425, 'FL3', 1.92, 'KRC', 2.44, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.24, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q1', 1.34, 'FL1', 1.324, 'FL2', 2.95, 'FL3', 1.114, 'KRC', 2.94, 'PL11', 1.25, 'PL12', 2.45, 'RZ', 1.9, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q2', 1.24, 'FL1', 2.34, 'FL2', 2.45, 'FL3', 2.94, 'KRC', 2.154, 'PL11', 1.55, 'PL12', 1.75, 'RZ', 2.1, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//     ['2018Q3', 2.942, 'FL1', 2.24, 'FL2', 1.15, 'FL3', 1.94, 'KRC', 1.24, 'PL11', 2.45, 'PL12', 1.55, 'RZ', 1.4, 'PL11', 2.924, 'PL22', 1.425, 'PL23', 1.92, 'XYZ'],
//   ];



//   getProcessUnitLegends(): any {
//     let processUnitLegends = [
//       { name: "KRC", color: "#4700b3" },
//       { name: "RZ", color: "#e6e600" },
//       { name: "XYZ", color: "RED" },
//     ];

//     return processUnitLegends;
//   }

// }
