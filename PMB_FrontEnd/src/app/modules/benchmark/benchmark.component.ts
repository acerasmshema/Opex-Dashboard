import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';
import { ConsumptionModel } from '../shared/models/consumption-model';

@Component({
  selector: 'app-benchmark',
  templateUrl: './benchmark.component.html',
  styleUrls: ['./benchmark.component.scss']
})
export class BenchmarkComponent implements OnInit {
  processUnitLegends: any[] = [];

  constructor(private statusService: StatusService) { }

  consumptions: ConsumptionModel[] = [];
  show: boolean;
  
  pl: any = ["FL1", "FL2", "FL3", "KRC", "PL11", "PL12", "RZ"];
  count = 0;


  boundFormatDataLabel = this.formatDataLabel.bind(this);

  public formatDataLabel(value, event: any) {
    if (this.count == this.pl.length) {
      this.count = 0;
    }
    let temp = this.pl[this.count];
    this.count++;

    return temp;
  };

  ngOnInit() {
    document.getElementById("select_mill").style.display = "none";

    this.getConsumption(this.data1, "Kraft Pulp Wood Chip Conversion (BDt/ADt)");
    setTimeout(() => {
      let nodes = document.getElementsByClassName("textDataLabel");
      for (let index = 0; index < nodes.length; index++) {
        const element: any = nodes[index];
        let x = element.getAttribute("x") - 10 ;
        element.setAttribute('transform', "none");
        element.setAttribute('x', x);
      }
      this.show = true;
    }, 200);

    let sidebarRequest = new SidebarRequest();
    sidebarRequest.isShow = true;
    sidebarRequest.type = "benchmark";
    this.statusService.sidebarSubject.next(sidebarRequest);
  }

  getConsumption(data: any, unitName: string) {

    let ccm = new ConsumptionModel();
    //ccm.kpiTypeId = kpiTypeId;
    ccm.data = data;
    ccm.view = [1237, 250];
    ccm.colorScheme = { domain: ['#6694d9', '#6694d9', '#6694d9', '#6694d9', '#f0d646', '#f0d646', '#f0d646'] };
    ccm.xAxis = true;
    ccm.yAxis = true;
    ccm.showDataLabel = true;
    ccm.xAxis = true;
    ccm.checked = false;
    ccm.legend = false;
    ccm.barPadding = 2;
    ccm.groupPadding = 4;
    ccm.chartType = "bar";
    ccm.showXAxisLabel = false;
    ccm.showYAxisLabel = false;
    ccm.gradient = "gradient";
    ccm.xAxisLabel = "";
    ccm.yAxisLabel = "";
    ccm.showKpiType = true;
    this.consumptions.push(ccm);
  }


  data1 = [
    {
      "name": "2017",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 2.659 },
        { "name": "PL12", "value": 1.673 },
        { "name": "RZ", "value": 2.672 },
      ]
    },
    {
      "name": "2018Q1",
      "series": [
        { "name": "FL1", "value": 2.048 },
        { "name": "FL2", "value": 1.840 },
        { "name": "FL3", "value": 1.902 },
        { "name": "KRC", "value": 1.913 },
        { "name": "PL11", "value": 1.059 },
        { "name": "PL12", "value": 1.613 },
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
        { "name": "PL11", "value": 1.619 },
        { "name": "PL12", "value": 1.173 },
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


}
