import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductionRequest } from './ProductionRequest';
import { DatePipe, DecimalPipe } from '@angular/common';
import * as shape from 'd3-shape';
import { ProductionEnquiry } from './ProductionEnquiry';
import { ProductionService } from './production.service';
import { MasterData } from '../../shared/constant/MasterData';
import { ProductionBar } from './production-bar';
import { ProductionLine } from './production-LINE';
import { ProducionLineForm } from './production-line-form';
import { ProductionLineView } from './production-line-view';
import { StatusService } from '../../shared/service/status.service';
import * as $ from "jquery";
import { Subscription } from 'rxjs';
import { ConsumptionTable } from '../consumption-dashboard/consumption-table';
import { MessageService } from 'primeng/components/common/messageservice';
import { CommonService } from 'src/app/shared/service/common/common.service';

@Component({
  selector: 'app-production-dashboard',
  templateUrl: './production-dashboard.component.html',
  styleUrls: ['./production-dashboard.component.scss'],
  providers: [DecimalPipe]
})
export class ProductionDashboardComponent implements OnInit, OnDestroy {

  yesterdayProductionData: ProductionLine;
  prodLines: ProductionLine[] = [];
  annualChart: ProductionBar;
  monthlyChart: ProductionBar;
  prodLineChart: ProductionLine;
  producionLineForm: ProducionLineForm;
  productionEnquiryData: ProductionEnquiry;
  productionLineView: ProductionLineView;
  annotationDates: any = [];
  thresholdTargetMap: any;
  startDate: string = '';
  endDate: string = '';
  updateChartSubscription: Subscription;
  projectTargetSubscription: Subscription;

  private annualChartRendered: boolean;
  private monthlyChartRendered: boolean;
  private productionChartRendered: boolean;
  private productionYDaySubscription: Subscription;
  private projectedTargetSubscription: Subscription;
  private productionYTDSubscription: Subscription;
  private stackBarChartSubscription: Subscription;
  private stackAreaChartSubscription: Subscription;
  private productionLinesYDaySubscription: Subscription;
  private selProdLineSubscription: Subscription;
  private allProdLineSubscription: Subscription;
  private selProdLinesDateSubscription: Subscription;
  private annotationDateSubscription: Subscription;
  private dataForGridSubscription: Subscription;

  constructor(private statusService: StatusService,
    private productionService: ProductionService,
    private datePipe: DatePipe,
    private commonService: CommonService) {
  }

  ngOnInit() {
    this.openProductionDashboard();

    this.updateChartSubscription = this.statusService.updateChartSubject.
      subscribe((dashboardName: string) => {
        if (dashboardName === 'production') {
          this.searchData(false);
        }
      });

    this.projectTargetSubscription = this.statusService.projectTargetSubject.
      subscribe(() => {
        this.getProjectedTarget(false);
      });
  }

  openProductionDashboard() {
    this.productionEnquiryData = new ProductionEnquiry();
    this.productionLineView = new ProductionLineView();
    this.annualChart = new ProductionBar();
    this.thresholdTargetMap = new Object();
    const currentDate = new Date();
    this.startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth()).toString() + '-' + (currentDate.getDate() - 1);
    this.endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');

    this.setProductionLineForm();
    if (this.statusService.common.selectedRole.roleName === "MillOps") {
      this.productionEnquiryData.selectedValue = this.producionLineForm.frequencies.find(frequency => frequency.name === 'Daily');
    } else {
      this.productionEnquiryData.selectedValue = this.producionLineForm.frequencies.find(frequency => frequency.name === 'Monthly');
    }
    this.productionEnquiryData.lineChartDate.push(this.startDate, this.endDate);
    const frequency = this.productionEnquiryData.selectedValue['code'];

    this.getProductionYTDData();
    this.getProjectedTarget(true);
    this.getProductionYDayData();
    this.getMonthlyChartData(this.startDate, this.endDate, "stack-bar");
    this.getAllProdYestData(this.startDate, this.endDate, []);
    this.getSelectedProductionLinesDateRangeData(this.startDate, this.endDate, [], frequency, true);
    this.getThresholdTarget();
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
    this.yesterdayProductionData.millName = this.statusService.common.selectedMill.millName;
    const data = { millId: this.statusService.common.selectedMill.millId, buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionYDaySubscription = this.productionService.getProductionYDayData(data).
      subscribe((data: any) => {

        const totalAverageValue = data['totalAverageValue'];
        const maxValue = +data['maxValue'];
        const threshold = +data['threshold'];

        this.yesterdayProductionData.productionYDayActualValue = totalAverageValue;
        this.yesterdayProductionData.canvasWidth = 275;
        this.yesterdayProductionData.options.needleColor = '#292823';
        this.yesterdayProductionData.options.rangeLabel.push(data['minValue'].toString());
        this.yesterdayProductionData.options.rangeLabel.push(data['maxValue'].toString());

        let thresholdPercentage = (+threshold * 100) / maxValue;
        this.yesterdayProductionData.productionYDayNeedleValue = (+totalAverageValue * 100) / maxValue;;
        this.yesterdayProductionData.options.arcDelimiters = [(thresholdPercentage * 0.95), thresholdPercentage];

        this.yesterdayProductionData.show = true;
      },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public getProjectedTarget(isAnnualTargetRequired: boolean) {
    const requestData = {
      millId: this.statusService.common.selectedMill.millId,
      buId: '1',
      kpiCategoryId: '1',
      kpiId: '1',
      annualTargetRequired: '' + isAnnualTargetRequired
    };
    this.projectedTargetSubscription = this.productionService.getProjectedTarget(requestData).
      subscribe((responseData: any) => {
        this.annualChart.targetDays = responseData['targetDays'];
        if(responseData['projectedTarget']!=null){
        this.annualChart.targetValue = responseData['projectedTarget'].toLocaleString('en-us');
        }
        this.annualChart.targetEndDate = responseData['endDate'];

        if (isAnnualTargetRequired) {
          let annualTarget = (+responseData['annualTarget'].toLocaleString('en-us') / 1000000);
          this.annualChart.annualTarget = annualTarget.toFixed(2) + "M";
        }
      },
        (error: any) => {
          this.commonService.handleError(error);
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

    const requestData = { millId: this.statusService.common.selectedMill.millId, buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionYTDSubscription = this.productionService.getProductionYTDTargetData(requestData).
      subscribe(
        (targetData: any) => {
          this.annualChart.annualData = targetData;
          this.annualChartRendered = true;
          this.enableTabs();
        },
        (error: any) => {
          this.commonService.handleError(error);
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

    this.stackBarChartSubscription = this.productionService.getStackBarChartData(productionRequest).
      subscribe((data: any) => {
        this.monthlyChart.stackBar = data;
      },
        (error: any) => {
          this.commonService.handleError(error);
        });
    this.stackAreaChartSubscription = this.productionService.getStackAreaChartData(productionRequest).
      subscribe(
        (data: any) => {
          this.monthlyChart.stackArea = data;
        },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public getAllProdYestData(startDate: string, endDate: string, processLines: any) {
    const frequency = this.productionEnquiryData.selectedValue['code'];
    let productionRequest = this.getProductionRequest(startDate, endDate, [], frequency);

    let processLinesNames = this.statusService.common.processLines;
    for (let index = 0; index < processLinesNames.length; index++) {
      let prodLine = new ProductionLine();
      prodLine.productionLineData = [];
      prodLine.xScaleMin = 0;
      prodLine.yAxis = true;
      prodLine.xAxis = false;
      prodLine.legend = false;
      prodLine.showXAxisLabel = false;
      prodLine.showYAxisLabel = false;
      prodLine.yAxisLabel = "";
      prodLine.xAxisLabel = "";
      prodLine.processLineCode = processLinesNames[index].processLineCode + ' - Y\'day (ADt/d)';
      prodLine.colorScheme = { domain: ['#2581c5', '#333333'] };
      prodLine.canvasWidth = 200;
      prodLine.fontSize = 15;

      this.prodLines.push(prodLine);
    }

    this.productionLinesYDaySubscription = this.productionService.getAllproductionLinesYDayData(productionRequest).
      subscribe((data: any) => {
        let gauges = data.dailyKpiPulp;
        for (let index = 0; index < gauges.length; index++) {
          const gauge = gauges[index];
          let prodLine = this.prodLines[index]
          let totalAverageValuePL1 = gauge.value;
          prodLine.productionYDayActualValue = "" + totalAverageValuePL1;
          prodLine.productionYDayNeedleValue = (totalAverageValuePL1 * 100) / +gauge.max;
          prodLine.options.rangeLabel.push("" + gauge.min);
          prodLine.options.rangeLabel.push("" + gauge.max);
          prodLine.options.needleColor = '#292823';

          let thresholdPercentage = (+gauge.threshold * 100) / +gauge.max;
          prodLine.options.arcDelimiters = [(thresholdPercentage * 0.95), thresholdPercentage];
        }
      },
        (error: any) => {
          this.commonService.handleError(error);
        });

    this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
  }

  public getAllProductionLinesDateRangeData(startDate: string, endDate: string) {
    let productionRequest = this.getProductionRequest(startDate, endDate, [], "0");
    this.prodLines.forEach(prodLine => {
      prodLine.productionLineData = [];
    });

    this.selProdLineSubscription = this.productionService.getSelectedProductionLinesDateRangeData(productionRequest).
      subscribe((processLines: any) => {
        this.allProdLineSubscription = this.productionService.getAllProductionLinesDateRangeDataTarget(productionRequest).
          subscribe((plTargetData: any) => {
            for (let index = 0; index < plTargetData.length; index++) {
              let prodLine = this.prodLines[index]
              prodLine.productionLineData.push(processLines[index]);
              prodLine.productionLineData.push(plTargetData[index]);
            }

            this.monthlyChartRendered = true;
            this.enableTabs();
          },
            (error: any) => {
              this.commonService.handleError(error);
            });
      },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public getSelectedProductionLinesDateRangeData(startDate: string, endDate: string, processLines: any, frequency: any, isGridRequest: boolean) {

    let productionRequest = this.getProductionRequest(startDate, endDate, processLines, frequency);
    this.selProdLinesDateSubscription = this.productionService.getSelectedProductionLinesDateRangeData(productionRequest).
      subscribe((prodLineResponse: any) => {
        this.producionLineForm.processLines = this.statusService.common.processLines;
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

        let domains = [];
        let processLines = this.statusService.common.processLines;
        prodLineResponse.forEach(plData => {
          let legendColor = processLines.find((line) => line.processLineCode === plData.name).legendColor;
          domains.push(legendColor);
        });
        this.prodLineChart.colorScheme = { domain: domains };

        if (productionRequest.frequency === "0") {
          this.annotationDateSubscription = this.productionService.getAnnotationDates(productionRequest).
            subscribe((annotationsData: any) => {
              this.annotationDates = annotationsData['annotationDates'];
              this.prodLineChart.productionLineData = prodLineResponse;
              this.productionChartRendered = true;
              this.enableTabs();
            },
              (error: any) => {
                this.statusService.spinnerSubject.next(false);
                this.annotationDates = [];
                this.prodLineChart.productionLineData = prodLineResponse;
                this.productionChartRendered = true;
                this.enableTabs();
              });
        } else {
          this.prodLineChart.productionLineData = prodLineResponse;
          this.productionChartRendered = true;
          this.enableTabs();
        }

        if (isGridRequest)
          this.getProdGrid(prodLineResponse);

      },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  public getProdGrid(prodLineResponse: any) {
    this.productionLineView.rows = 5;
    this.productionLineView.scrollable = false;
    this.productionLineView.paginator = true;
    this.productionLineView.productionLines = [];
    this.productionLineView.columnNames = [];

    let columnNames = [];
    columnNames.push({ header: "DATE", field: "DATE" });
    prodLineResponse.forEach(prodLine => {
      columnNames.push({ header: prodLine.name, field: prodLine.name });
    });
    this.productionLineView.columnNames = columnNames;

    let gridData = []
    let totalGrids = prodLineResponse[0].series.length;
    for (let index = 0; index < totalGrids; index++) {
      let grid = {};
      prodLineResponse.forEach(prodLine => {
        let processsLineName = prodLine.name;
        grid["DATE"] = prodLine.series[index].name;
        grid[processsLineName] = prodLine.series[index].value;
      });
      gridData.push(grid);
    }
    this.productionLineView.productionLines = gridData;
  }

  public changeMonthlyChartDuration(duration: string) {
    const chartType = this.monthlyChart.chartType;
    const currentDate = new Date();
    if (duration === "one-month-chart") {
      this.startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth()).toString() + '-' + (currentDate.getDate() + 1);
      this.endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    } else if (duration === "three-month-chart") {
      this.startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth() - 2).toString() + '-' + (currentDate.getDate() + 1);
      this.endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    } else if (duration === "six-month-chart") {
      this.startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth() - 5).toString() + '-' + (currentDate.getDate() + 1);
      this.endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
      this.getMonthlyChartData(this.startDate, this.endDate, chartType);
    }
  }

  public changeChartDurationLineChart(duration: string) {
    let startDate;
    let endDate;
    const currentDate = new Date();
    if (duration === "one-month-line-chart") {
      startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth()).toString() + '-' + (currentDate.getDate() - 1);
      endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
    } else if (duration === "three-month-line-chart") {
      startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth() - 2).toString() + '-' + (currentDate.getDate() - 1);
      endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
    } else if (duration === "six-month-line-chart") {
      if (currentDate.getMonth() < 6) {
        startDate = (currentDate.getFullYear() - 1).toString() + '-' + (currentDate.getMonth() + 7).toString() + '-' + (currentDate.getDate() - 1);
        endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
      } else {
        startDate = currentDate.getFullYear().toString() + '-' + (currentDate.getMonth() - 5).toString() + '-' + (currentDate.getDate() - 1);
        endDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd');
      }
    }
    this.getAllProductionLinesDateRangeData(startDate, endDate);
  }

  public searchData(isGridRequest: boolean) {
    if (this.productionEnquiryData.lineChartDate !== null) {
      const startDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[0], 'yyyy-MM-dd');
      const endDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[1], 'yyyy-MM-dd');
      let frequency = this.productionEnquiryData.selectedValue['code'];

      let processLines = [];
      if (this.productionEnquiryData.lineChartPLines.length > 0) {
        this.productionEnquiryData.lineChartPLines.forEach(processLine => {
          processLines.push(processLine['processLineCode']);
        });
      }
      this.getSelectedProductionLinesDateRangeData(startDate, endDate, processLines, frequency, isGridRequest);
    }
  }

  xAxisTickFormatting(val: any): string {
    return val.replace("-2019", "");
  }

  getProductionRequest(startDate: string, endDate: string, processLines: any, frequency: string): ProductionRequest {
    let productionRequest = new ProductionRequest();
    productionRequest.startDate = startDate;
    productionRequest.endDate = endDate;
    productionRequest.millId = this.statusService.common.selectedMill.millId;
    productionRequest.processLines = processLines;
    productionRequest.frequency = frequency;

    return productionRequest;
  }

  public openSettingIcon() {
    const reqeustData = {
      dialogName: "maintenanceDays",
      targetDays: this.annualChart.targetDays
    }
    this.statusService.dialogSubject.next(reqeustData);
  }

  onSelect(event) {
    const frequancy = this.productionEnquiryData.selectedValue["name"];
    if (frequancy === "Daily") {
      const data = {
        dialogName: "annotation",
        annotationKpiId: "1",
        annotationDate: event.name,
        dashboardName: "production"
      }
      this.statusService.dialogSubject.next(data);
    }
  }

  public xAxisTickFormattingFn = this.dateTickFormatting.bind(this);
  dateTickFormatting(val: any): string {
    $("g.tick.ng-star-inserted text:contains('*')").css("fill", "red");
    $("g.tick.ng-star-inserted text:contains('*')").css("font-weight", "bold");
    var dateTick;
    if (this.annotationDates !== null && this.annotationDates.length > 0 && this.annotationDates.includes(val)) {
      dateTick = "*" + val;
    } else {
      dateTick = val;
    }
    return dateTick;
  }

  enableTabs() {
    if (this.productionChartRendered && this.annualChartRendered && this.monthlyChartRendered) {
      setTimeout(() => {
        this.statusService.enableTabs.next(true);
        this.productionChartRendered = false;
        this.annualChartRendered = false;
        this.monthlyChartRendered = false;
      }, 100);
    }
  }

  public getThresholdTarget() {
    const requestData = {
      kpiCategoryId: "1",
      millId: this.statusService.common.selectedMill.millId
    };

    this.dataForGridSubscription = this.productionService.getDataForGrid(requestData).
      subscribe(
        (thresholdTarget: ConsumptionTable[]) => {
          thresholdTarget[0].series.forEach(processLine => {
            this.thresholdTargetMap[processLine.name] = processLine.target;
          });
        },
        (error: any) => {
          this.commonService.handleError(error);
        });
  }

  ngOnDestroy() {
    this.updateChartSubscription.unsubscribe();
    this.projectTargetSubscription.unsubscribe();

    if (this.productionYDaySubscription !== undefined)
      this.productionYDaySubscription.unsubscribe();
    if (this.projectedTargetSubscription !== undefined)
      this.projectedTargetSubscription.unsubscribe();
    if (this.productionYTDSubscription !== undefined)
      this.productionYTDSubscription.unsubscribe();
    if (this.stackBarChartSubscription !== undefined)
      this.stackBarChartSubscription.unsubscribe();
    if (this.stackAreaChartSubscription !== undefined)
      this.stackAreaChartSubscription.unsubscribe();
    if (this.productionLinesYDaySubscription !== undefined)
      this.productionLinesYDaySubscription.unsubscribe();
    if (this.selProdLineSubscription !== undefined)
      this.selProdLineSubscription.unsubscribe();
    if (this.allProdLineSubscription !== undefined)
      this.allProdLineSubscription.unsubscribe();
    if (this.selProdLinesDateSubscription !== undefined)
      this.selProdLinesDateSubscription.unsubscribe();
    if (this.annotationDateSubscription !== undefined)
      this.annotationDateSubscription.unsubscribe();
    if (this.dataForGridSubscription !== undefined)
      this.dataForGridSubscription.unsubscribe();
  }
}
