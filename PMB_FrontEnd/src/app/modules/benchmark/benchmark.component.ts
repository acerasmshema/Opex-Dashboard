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

  data = [
    {
      "name": "2017",
      "series": [
        { "name": "FL1", "value": "" },
        { "name": "FL2", "value": "" },
        { "name": "FL3", "value": '' },
        { "name": "KRC", "value": "3.646" },
        { "name": "PL11", "value": "" },
        { "name": "PL12", "value": "" },
        { "name": "RZ", "value": "" },
      ]
    },
    {
      "name": "2018Q1",
      "series": [
        { "name": "FL1", "value": "" },
        { "name": "FL2", "value": "" },
        { "name": "FL3", "value": '' },
        { "name": "KRC", "value": "3.748" },
        { "name": "PL11", "value": "" },
        { "name": "PL12", "value": "" },
        { "name": "RZ", "value": "" },
      ]
    },
    {
      "name": "2018Q2",
      "series": [
        { "name": "FL1", "value": "" },
        { "name": "FL2", "value": "" },
        { "name": "FL3", "value": '' },
        { "name": "KRC", "value": "3.146" },
        { "name": "PL11", "value": "" },
        { "name": "PL12", "value": "" },
        { "name": "RZ", "value": "" },
      ]
    },
    {
      "name": "2018Q3",
      "series": [
        { "name": "FL1", "value": "" },
        { "name": "FL2", "value": "" },
        { "name": "FL3", "value": '' },
        { "name": "KRC", "value": "3.146" },
        { "name": "PL11", "value": "" },
        { "name": "PL12", "value": "" },
        { "name": "RZ", "value": "" },
      ]
    },
    
  ];


  data1 = [
    {
      "name": "2017",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
    {
      "name": "2018Q1",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
    {
      "name": "2018Q2",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
    {
      "name": "2018Q3",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
  ];

  data2 = [
    {
      "name": "2017",
      "series": [
        { "name": "FL1", "value": 2.018 },
        { "name": "FL2", "value": 2.840 },
        { "name": "FL3", "value": 1.302 },
        { "name": "KRC", "value": 2.113 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 2.673 },
        { "name": "RZ", "value": 1.972 },
      ]
    },
    {
      "name": "2018Q1",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
    {
      "name": "2018Q2",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.540 },
        { "name": "FL3", "value": 1.602 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 2.659 },
        { "name": "PL12", "value": 1.973 },
        { "name": "RZ", "value": 2.172 },
      ]
    },
    {
      "name": "2018Q3",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 1.672 },
      ]
    },
  ];

  getProcessUnitLegends(): any {
    let processUnitLegends = [
      { name: "FL1", color: "rgb(39,10,169,0.4)" },
      { name: "FL2", color: "rgb(39,10,169,0.6)" },
      { name: "FL3", color: "rgb(39,10,169,0.8)" },
      { name: "KRC", color: "rgb(39,10,169)" },
      { name: "PL11", color: 'rgb(243, 202, 17,.4)'},
      { name: "PL12", color: 'rgb(243, 202, 17,.6)' },
      { name: "RZ", color: "rgb(243, 202, 17,.9)" },
    ];

    return processUnitLegends;
  }

}
