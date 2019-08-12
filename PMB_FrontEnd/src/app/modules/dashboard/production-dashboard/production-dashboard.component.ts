import { Component, OnInit } from '@angular/core';
import { ProductionRequest } from './ProductionRequest';
import { DatePipe, DecimalPipe } from '@angular/common';
import * as shape from 'd3-shape';
import { ProductionEnquiry } from './ProductionEnquiry';
import { MessageService } from 'primeng/components/common/messageservice';
import { LocalStorageService } from '../../shared/service/localStorage/local-storage.service';
import { ProductionService } from './production.service';
import { MasterData } from '../../shared/constant/MasterData';
import { ProductionBar } from './production-bar';
import { ProductionLine } from './production-LINE';
import { ProducionLineForm } from './production-line-form';
import { ProductionLineView } from './production-line-view';
import { StatusService } from '../../shared/service/status.service';
import * as $ from "jquery";

@Component({
  selector: 'app-production-dashboard',
  templateUrl: './production-dashboard.component.html',
  styleUrls: ['./production-dashboard.component.scss'],
  providers: [MessageService, DatePipe, DecimalPipe, LocalStorageService]
})
export class ProductionDashboardComponent implements OnInit {

  millName: string = "KERINCI";
  yesterdayProductionData: ProductionLine;
  prodLines: ProductionLine[] = [];
  annualChart: ProductionBar;
  monthlyChart: ProductionBar;
  prodLineChart: ProductionLine;
  producionLineForm: ProducionLineForm;
  productionEnquiryData: ProductionEnquiry;
  productionLineView: ProductionLineView;
  annotationDates: any = [];
  startDate: string = '';
  endDate: string = '';

  constructor(private localStorageService: LocalStorageService,
    private statusService: StatusService,
    private productionService: ProductionService,
    private datePipe: DatePipe) {
    this.productionEnquiryData = new ProductionEnquiry();
    this.productionLineView = new ProductionLineView();
    this.annualChart = new ProductionBar();
  }

  ngOnInit() {
    this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);
    this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');

    this.setProductionLineForm();
    if (this.localStorageService.fetchUserRole() == "Mills Operation") {
      this.productionEnquiryData.selectedValue = this.producionLineForm.frequencies.find(frequency => frequency.name === 'Daily');
    } else {
      this.productionEnquiryData.selectedValue = this.producionLineForm.frequencies.find(frequency => frequency.name === 'Monthly');
    }
    this.productionEnquiryData.lineChartDate.push(this.startDate, this.endDate);
    const frequency = this.productionEnquiryData.selectedValue['code'];

    this.getProductionYTDData();
    this.getProjectedTarget();
    this.getProductionYDayData();
    this.getAnnualTarget();
    this.getAnnotationDates();
    this.getMonthlyChartData(this.startDate, this.endDate, "stack-bar");
    this.getAllProductionLinesYDayData(this.startDate, this.endDate, []);
    this.getSelectedProductionLinesDateRangeData(this.startDate, this.endDate, true, [], frequency);
  }

  setProductionLineForm() {
    this.producionLineForm = new ProducionLineForm();
    this.producionLineForm.frequencies = MasterData.dashboardFrequencies;
    this.producionLineForm.startDate = this.startDate;
    this.producionLineForm.endDate = this.endDate;
  }

  public changeMonthlyChartType() {
    this.monthlyChart.chartType = (this.monthlyChart.chartType === 'stack-bar') ? 'stack-area' : 'stack-bar';
  }

  public getProductionYDayData() {
    this.yesterdayProductionData = new ProductionLine();
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProductionYDayData(data).
      subscribe((data: any) => {
        let valueArr = data['range'].split(',');
        let colorArr = data['colorRange'].split(',');
        const totalAverageValue = (data['totalAverageValue']);

        this.yesterdayProductionData.productionYDayActualValue = parseFloat(totalAverageValue).toString();
        this.yesterdayProductionData.productionYDayNeedleValue = (totalAverageValue * 100) / Number(data['maxValue']);
        this.yesterdayProductionData.options.rangeLabel.push(data['minValue'].toString());
        this.yesterdayProductionData.options.rangeLabel.push(data['maxValue'].toString());
        valueArr.filter(item => this.yesterdayProductionData.options.arcDelimiters.push(Number(item)));
        this.yesterdayProductionData.options.arcColors.splice(0, this.yesterdayProductionData.options.arcColors.length);
        this.yesterdayProductionData.canvasWidth = 275;
        colorArr.filter(item => this.yesterdayProductionData.options.arcColors.push((item)));
        this.yesterdayProductionData.show = true;
      });
  }

  public getProjectedTarget() {
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProjectedTarget(data).subscribe((data: any) => {
      this.annualChart.targetDays = data['targetDays'];
      this.annualChart.targetValue = data['projectedTarget'].toLocaleString('en-us');
      this.annualChart.targetEndDate = data['endDate'];
    });
  }

  public getAnnualTarget() {
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getAnnualTarget(data).subscribe((data: any) => {
      this.annualChart.annualTarget = data['annualTarget'].toLocaleString('en-us');
    });
  }

  public getProductionYTDData() {
    this.annualChart.colorScheme = { domain: ['#BA4A00', '#2E8B57'] };
    this.annualChart.xAxis = true;
    this.annualChart.yAxis = true;
    this.annualChart.showXAxisLabel = false;
    this.annualChart.showYAxisLabel = false;
    this.annualChart.showDataLabel = false;
    this.annualChart.legend = false;
    this.annualChart.annualData = [];

    const requestData = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProductionYTDData(requestData).
      subscribe((actualData: any) => {

        this.productionService.getProductionYTDTargetData(requestData).
          subscribe((targetData: any) => {
            this.annualChart.annualData = [...this.annualChart.annualData, targetData];
          });
        this.annualChart.annualData = [...this.annualChart.annualData, actualData];
      });
  }

  public getMonthlyChartData(startDate: string, endDate: string, chartType: string) {
    this.monthlyChart = new ProductionBar();
    this.monthlyChart.colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A'] };
    this.monthlyChart.xAxis = true;
    this.monthlyChart.yAxis = true;
    this.monthlyChart.showXAxisLabel = false;
    this.monthlyChart.showYAxisLabel = false;
    this.monthlyChart.showDataLabel = false;
    this.monthlyChart.barPadding = 2;
    this.monthlyChart.chartType = chartType;

    let frequency = this.productionEnquiryData.selectedValue['code'];
    let productionRequest = this.getProductionRequest(startDate, endDate, [], frequency);

    this.productionService.getStackBarChartData(productionRequest).
      subscribe((data: any) => {
        this.monthlyChart.stackBar = data;
      });
    this.productionService.getStackAreaChartData(productionRequest).
      subscribe((data: any) => {
        this.monthlyChart.stackArea = data;
      });
  }

  public getAllProductionLinesYDayData(startDate: string, endDate: string, processLines: any) {
    const frequency = this.productionEnquiryData.selectedValue['code'];
    let productionRequest = this.getProductionRequest(startDate, endDate, [], frequency);

    this.productionService.getAllproductionLinesYDayData(productionRequest).
      subscribe((data: any) => {
        if (data !== "e") {

          let processLines = data.dailyKpiPulp;
          let processLinesNames = this.statusService.processLineMap.get("1");
          for (let index = 0; index < processLines.length; index++) {
            const processLine = processLines[index];
            let prodLine = new ProductionLine();
            let totalAverageValuePL1 = processLine.value;
            prodLine.productionYDayActualValue = "" + totalAverageValuePL1;
            prodLine.productionYDayNeedleValue = (totalAverageValuePL1 * 100) / +processLine.max;
            prodLine.options.rangeLabel.push("" + processLine.min);
            prodLine.options.rangeLabel.push("" + processLine.max);
            prodLine.canvasWidth = 200;
            prodLine.fontSize = 15;
            let value = processLine.range.split(',');
            let color = processLine.colorRange.split(',');
            value.filter(item => prodLine.options.arcDelimiters.push(+item));
            prodLine.options.arcColors.splice(0, prodLine.options.arcColors.length);
            color.filter(item => prodLine.options.arcColors.push((item)));

            prodLine.productionLineData = [];
            prodLine.xScaleMin = 0;
            prodLine.yAxis = true;
            prodLine.xAxis = false;
            prodLine.legend = false;
            prodLine.xAxis = false;
            prodLine.showXAxisLabel = false;
            prodLine.showYAxisLabel = false;
            prodLine.yAxisLabel = "";
            prodLine.xAxisLabel = "";
            prodLine.processLineCode = processLinesNames[index].processLineCode + ' - Y\'day (ADt/d)';
            prodLine.colorScheme = { domain: ['#2581c5', '#333333'] };
            this.prodLines.push(prodLine);
          }

          this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
        }
      });
  }

  public getAllProductionLinesDateRangeData(startDate: string, endDate: string) {
    let productionRequest = this.getProductionRequest(startDate, endDate, [], "0");
    this.prodLines.forEach(prodLine => {
      prodLine.productionLineData = [];
    });

    this.productionService.getSelectedProductionLinesDateRangeData(productionRequest).
      subscribe((processLines: any) => {

        this.productionService.getAllProductionLinesDateRangeDataTarget(productionRequest).
          subscribe((plTargetData: any) => {
            for (let index = 0; index < plTargetData.length; index++) {
              let prodLine = this.prodLines[index]
              prodLine.productionLineData = [...prodLine.productionLineData, plTargetData[index]];
            }
          });

        for (let index = 0; index < processLines.length; index++) {
          let prodLine = this.prodLines[index]
          prodLine.productionLineData = [...prodLine.productionLineData, processLines[index]];
        }
      });
  }


  public getSelectedProductionLinesDateRangeData(startDate: string, endDate: string, showColumns: boolean, processLines: any, frequency: any) {
    let productionRequest = this.getProductionRequest(startDate, endDate, processLines, frequency);
    this.productionService.getSelectedProductionLinesDateRangeData(productionRequest).
      subscribe((data: any) => {
        this.producionLineForm.processLines = this.statusService.processLineMap.get("1");

        this.prodLineChart = new ProductionLine();
        this.prodLineChart.productionLineData = [];
        this.prodLineChart.xScaleMin = 0;
        this.prodLineChart.yAxis = true;
        this.prodLineChart.xAxis = true;
        this.prodLineChart.animations = true;
        this.prodLineChart.legend = false;
        this.prodLineChart.showRefLines = false;
        this.prodLineChart.showXAxisLabel = false;
        this.prodLineChart.showYAxisLabel = false;
        this.prodLineChart.showDataLabel = false;
        this.prodLineChart.yAxisLabel = "";
        this.prodLineChart.xAxisLabel = "";
        this.prodLineChart.lineChartLineInterpolation = shape.curveMonotoneX;
        this.prodLineChart.colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#660000', '#9933FF', '#99FF99', '#FFFF99', '#FF9999'] };
        this.prodLineChart.productionLineData = data;
      });
    this.getAllProductionLinesDateForGrid(productionRequest, showColumns);
  }

  public getAllProductionLinesDateForGrid(productionRequest: ProductionRequest, showColumns: boolean) {
    this.productionLineView.rows = 10;
    this.productionLineView.scrollable = true;
    this.productionLineView.paginator = true;
    this.productionLineView.productionLines = [];

    this.productionService.getAllProductionLinesDateForGrid(productionRequest).
      subscribe((data: any) => {
        if (showColumns) {
          let colNames = [];

          colNames.push({ processLineCode: 'DATE', processLineName: 'Date' });
          let columnsNames = this.statusService.processLineMap.get("1");
          columnsNames.forEach(columnName => {
            colNames.push({ processLineCode: columnName.processLineCode, processLineName: columnName.processLineName })
          });
          this.productionLineView.columnNames = colNames;
        }
        this.productionLineView.productionLines = data[0];
      });
  }

  public changeMonthlyChartDuration(event: any) {
    const chartType = this.monthlyChart.chartType;
    if (event == "radio1") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    } else if (event == "radio2") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 2).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    } else if (event == "radio3") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 5).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    }
  }

  public changeChartDurationLineChart(event) {
    let startDate;
    let endDate;
    if (event == "radio1") {
      startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);
      endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    } else if (event == "radio2") {
      startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 2).toString() + '-' + (new Date().getDate() - 1);
      endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    } else if (event == "radio3") {
      if (new Date().getMonth() < 6) {
        startDate = (new Date().getFullYear() - 1).toString() + '-' + (new Date().getMonth() + 7).toString() + '-' + (new Date().getDate() - 1);
        endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      } else {
        startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 5).toString() + '-' + (new Date().getDate() - 1);
        endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      }
    }
    this.getAllProductionLinesDateRangeData(startDate, endDate);
  }

  public searchData() {
    if (this.productionEnquiryData.lineChartDate !== null) {
      this.productionLineView.productionLines = [];
      this.productionLineView.columnNames = [];

      const startDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[0], 'yyyy-MM-dd');
      const endDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[1], 'yyyy-MM-dd');
      let frequency = this.productionEnquiryData.selectedValue['code'];
      this.productionLineView.columnNames.push({ processLineCode: 'DATE', processLineName: 'Date' });

      if (this.productionEnquiryData.lineChartPLines.length > 0) {
        this.productionEnquiryData.lineChartPLines.forEach(processLine => {
          this.productionLineView.productionLines.push(processLine['processLineCode']);
          this.productionLineView.columnNames.push(processLine);
        });
      }
      else {
        this.productionLineView.columnNames.push(...this.statusService.processLineMap.get("1"));
      }
      let processLines = this.productionLineView.productionLines;
      this.getSelectedProductionLinesDateRangeData(startDate, endDate, false, processLines, frequency);
    }
    else {
      alert("Please select date range");
    }
  }

  xAxisTickFormatting(val: any): string {
    return val.replace("-2019", "");
  }

  getProductionRequest(startDate: string, endDate: string, processLines: any, frequency: string): ProductionRequest {
    let productionRequest = new ProductionRequest();
    productionRequest.startDate = startDate;
    productionRequest.endDate = endDate;
    productionRequest.processLines = processLines;
    productionRequest.frequency = frequency;

    return productionRequest;
  }

  displaySettingIcon: boolean = false;
  public openSettingIcon() {
    this.displaySettingIcon = !this.displaySettingIcon;
  }

  public xAxisTickFormattingFn = this.dateTickFormatting.bind(this);
  dateTickFormatting(val: any): string {
    $("g.tick.ng-star-inserted text:contains('*')").css("fill", "red");
    $("g.tick.ng-star-inserted text:contains('*')").css("font-weight", "bold");
    var dateTick;
    if (this.annotationDates.length > 0 && this.annotationDates.includes(val)) {
      dateTick = "*" + val;
    } else {
      dateTick = val;
    }
    return dateTick;
  }

  public getAnnotationDates() {
    const data = { millId: '1', buTypeId: '1', kpiId: '1', startDate: this.startDate, endDate: this.endDate };
    this.productionService.getAnnotationDates(data).subscribe((data: any) => {
      this.annotationDates = data['annotationDates'];
    });
  }
}
