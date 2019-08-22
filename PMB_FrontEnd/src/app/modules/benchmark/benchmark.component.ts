import { Component, OnInit } from '@angular/core';
import { SidebarRequest } from '../core/sidebar/sidebar-request';
import { StatusService } from '../shared/service/status.service';
import { ConsumptionModel } from '../shared/models/consumption-model';
import { ConsumptionGridView } from '../dashboard/consumption-dashboard/consumption-grid-view';

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

    this.getConsumption(this.data1);

    setTimeout(() => {
      let nodes = document.getElementsByClassName("textDataLabel");
      for (let index = 0; index < nodes.length; index++) {
        const element: any = nodes[index];
        let x = element.getAttribute("x") - 10;
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

  getConsumption(data: any) {

    let ccm = new ConsumptionModel();
    ccm.data = data;
    ccm.kpiName = "Kraft Pulp Wood Chip Conversion (BDt/ADt)";
    ccm.view = [1237, 250];
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
    ccm.colorScheme = { domain: ['blue','blue','blue','blue', 'red','red','red'] };

    this.consumptions.push(ccm);
  }

  data1 = [
    {
      "name": "2017",
      "series": [
        { "name": "FL1", "value": 2.48 },
        { "name": "FL2", "value": 1.84 },
        { "name": "FL3", "value": 1.92 },
        { "name": "KRC", "value": 1.91 },
        { "name": "PL11", "value": 2.59 },
        { "name": "PL12", "value": 1.63 },
        { "name": "RZ", "value": 2.62 },
      ]
    },
    {
      "name": "2018Q1",
      "series": [
        { "name": "FL1", "value": 2.81 },
        { "name": "FL2", "value": 1.20 },
        { "name": "FL3", "value": 1.02 },
        { "name": "KRC", "value": 1.13 },
        { "name": "PL11", "value": 1.59 },
        { "name": "PL12", "value": 1.63 },
        { "name": "RZ", "value": 1.72 },
      ]
    },
    {
      "name": "2018Q2",
      "series": [
        { "name": "FL1", "value": 2.12 },
        { "name": "FL2", "value": 1.40 },
        { "name": "FL3", "value": 2.92 },
        { "name": "KRC", "value": 1.13 },
        { "name": "PL11", "value": 1.61 },
        { "name": "PL12", "value": 1.31 },
        { "name": "RZ", "value": 1.72 },
      ]
    },
    {
      "name": "2018Q3",
      "series": [
        { "name": "FL1", "value": 1.48 },
        { "name": "FL2", "value": 1.24 },
        { "name": "FL3", "value": 2.92 },
        { "name": "KRC", "value": 1.13 },
        { "name": "PL11", "value": 1.69 },
        { "name": "PL12", "value": 1.63 },
        { "name": "RZ", "value": 1.67 },
      ]
    },
  ];


  showGridDialog(kpiId: string, kpiName: string) {
    console.log(kpiId + "   " + kpiName)

    let consumptionGridView = new ConsumptionGridView();
    consumptionGridView.show = true;
    consumptionGridView.paginator = true;
    consumptionGridView.scrollable = true;
    consumptionGridView.rows = 10;
    consumptionGridView.title = kpiName;

    consumptionGridView.columnNames.push({ header: "DATE", field: "DATE" });
    this.data1[0].series.forEach(processLine => {
      consumptionGridView.columnNames.push({ header: processLine.name, field: processLine.name });
    });

    let gridsData = [];
    this.data1.forEach(processDetail => {
      let grid = {};
      grid["DATE"] = processDetail.name;
      processDetail.series.forEach(processline => {
        grid[processline.name] = processline.value;
      });
      gridsData.push(grid);
    });
    consumptionGridView.gridData = gridsData;

    const data = {
      dialogName: "consumptionGridView",
      consumptionGridView: consumptionGridView
    }
    this.statusService.dialogSubject.next(data);
  }
}
