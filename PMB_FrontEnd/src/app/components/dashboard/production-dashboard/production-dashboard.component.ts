import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ProductonLine } from '../../../models/ProductionLine';
import { ColorHelper } from '@swimlane/ngx-charts';
import { ProductionService } from '../../../services/production/production.service';
import { ProductionRequest } from '../../../models/ProductionRequest';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import * as shape from 'd3-shape';
import { CommonModel } from '../../../models/CommonModel';
import { LocalStorageService } from '../../../services/localStorage/local-storage.service';
import { ProductionEnquiry } from '../../../models/ProductionEnquiry';
import * as $ from "jquery";
import { MessageService } from 'primeng/components/common/messageservice';
import { maintanenceDaysColumn, processLineColumns, annotationsCols, frequencies, processLines } from '../../../../assets/data/MasterData';

@Component({
  selector: 'app-production-dashboard',
  templateUrl: './production-dashboard.component.html',
  styleUrls: ['./production-dashboard.component.scss'],
  providers: [MessageService, DatePipe, DecimalPipe, LocalStorageService]
})
export class ProductionDashboardComponent implements OnInit {

  maintanenceDaysColumn: any[];
  maintanenceDayModel: any[];
  selectedMaintenanceDay: any[];
  createMaintanencePanelMainCollapsed: boolean = false;
  createMaintanencePanelNextCollapsed: boolean = true;
  dateValue: Date;
  textAreaValue: String;
  tarGetAreaValue: number;
  productonLines: ProductonLine[] = [];
  annotationsLines: any[];
  displayAnnotations: boolean = false;
  productionEnquiryData = new ProductionEnquiry();
  selectedValue: CommonModel[] = [];
  createAnnotationCollapsed: boolean = true;
  findAnnotationCollapsed: boolean = false;
  cols: any[];
  annotationsCols: any[];
  productionLinesTargetData: any[];
  showStackChart: boolean = true;
  showStackAreaChart: boolean = false;
  productionRequest: ProductionRequest = new ProductionRequest;
  enquiryForm: FormGroup;
  checked: boolean = true;
  checked1: boolean = false;
  colorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#AAAAAA'] };
  refLines = [{ value: 42500, name: 'Maximum' }, { value: 37750, name: 'Average' }, { value: 33000, name: 'Minimum' }];
  gridHide: boolean = true;
  gradient: boolean = false;
  public colors: ColorHelper;
  public chartNames: string[];
  data: any = {};
  multi: any[];
  multiStack: any[];
  multiLineData: any[];
  multiLineDataForLine: any[] = [];
  highlights: any[];
  highlights_copy: any[];
  pl1Data: any[];
  pl2Data: any[];
  pl3Data: any[];
  pl4Data: any[];
  pl5Data: any[];
  pl6Data: any[];
  pl7Data: any[];
  pl8Data: any[];
  startDate: string = '';
  endDate: string = '';
  ytdProductionValue: string = "";
  ytdAnnulaTargetValue: string = "";
  lineChartLineInterpolation = shape.curveMonotoneX;
  annotationProcessLines: string[] = [];
  annotationDescription: string = "";
  lineChartColorSchemeForPLines = { domain: ['#2581c5', '#333333'] };
  lineChartColorSchemeProduction = { domain: ['#BA4A00', '#2E8B57'] };
  lineChartColorScheme = { domain: ['#2581c5', '#48D358', '#F7C31A', '#660000', '#9933FF', '#99FF99', '#FFFF99', '#FF9999'] };
  frequencies: any[];
  processLines: any[];
  cars: any[] = [];
  hidden: boolean = false;
  hidden1: boolean = true;
  dynamicdata: string;
  gaugeType = "semi";
  gaugeValue = 28.3;
  gaugeLabel = "Speed";
  gaugeAppendText = "km/hr";
  columns: number[];
  public canvasWidth = 200
  public productionYDayNeedleValue = 90
  public productionYDayNeedleValuePL1 = 90
  public productionYDayNeedleValuePL2 = 90
  public productionYDayNeedleValuePL3 = 90
  public productionYDayNeedleValuePL4 = 90
  public productionYDayNeedleValuePL5 = 90
  public productionYDayNeedleValuePL6 = 90
  public productionYDayNeedleValuePL7 = 90
  public productionYDayNeedleValuePL8 = 90
  public pl1needleValue = 75
  public pl2needleValue = 20
  public pl3needleValue = 40
  public pl4needleValue = 60
  public pl5needleValue = 70
  public pl6needleValue = 35
  public pl7needleValue = 80
  public pl8needleValue = 40
  public centralLabel = '';
  public centralLabel1 = '';
  public bottomLabel1 = '2,200';
  public bottomLabel2 = '800';
  public bottomLabel3 = '1,000';
  public bottomLabel4 = '300';
  public bottomLabel5 = '1,400';
  public bottomLabel6 = '700';
  public bottomLabel7 = '2,400';
  public bottomLabel8 = '1,200';
  public productionYDayActualValue = '0.00';
  public productionYDayActualValuePL1 = '0.00';
  public productionYDayActualValuePL2 = '0.00';
  public productionYDayActualValuePL3 = '0.00';
  public productionYDayActualValuePL4 = '0.00';
  public productionYDayActualValuePL5 = '0.00';
  public productionYDayActualValuePL6 = '0.00';
  public productionYDayActualValuePL7 = '0.00';
  public productionYDayActualValuePL8 = '0.00';
  public productonYDaySpinner = true;
  public productonYDayChart = false;
  public productonYDayRefresh = false;
  public options = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: [], arcDelimiters: [], rangeLabel: [], needleStartValue: 0 }
  public options1 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options2 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options3 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options4 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options5 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options6 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options7 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  public options8 = { hasNeedle: true, needleColor: 'gray', needleUpdateSpeed: 1000, arcColors: ['yellow', 'GREEN'], arcDelimiters: [], rangeLabel: [], needleStartValue: 0, }
  nameFont = 15;
  public bottomLabelFont = 10;
  public name = "KERINCI PRODUCTION - Y'day (ADt/d)";
  public name1 = 'FL1 - Y\'day (ADt/d)';
  public name2 = 'FL2 - Y\'day (ADt/d)';
  public name3 = 'FL3 - Y\'day (ADt/d)';
  public name4 = 'PCD - Y\'day (ADt/d)';
  public name5 = 'PD1 - Y\'day (ADt/d)';
  public name6 = 'PD2 - Y\'day (ADt/d)';
  public name7 = 'PD3 - Y\'day (ADt/d)';
  public name8 = 'PD4 - Y\'day (ADt/d)';


  constructor(private localStorageService: LocalStorageService, private productionService: ProductionService, private datePipe: DatePipe, private fb: FormBuilder, private decimalPipe: DecimalPipe, private messageService: MessageService) {
    this.enquiryForm = this.fb.group({ lineChartDate: "", lineChartFrequency: "", lineChartPLines: "" });
    this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);
    this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
    this.getAnnotationDates();
    this.getProductionYTDData();
    this.getProjectedTarget();
    this.getProductionYDayData();
    this.getAnnualTarget();
    this.maintanenceDaysColumn = maintanenceDaysColumn;
    this.cols = processLineColumns;
    this.annotationsCols = annotationsCols
    this.frequencies = frequencies;
    this.processLines = processLines
    if (this.localStorageService.fetchUserRole() == "Mills Operation") {
      this.productionEnquiryData.selectedValue = this.frequencies.find(frequency => frequency.name === 'Daily');
    } else {
      this.productionEnquiryData.selectedValue = this.frequencies.find(frequency => frequency.name === 'Monthly');
    }
    this.productionEnquiryData.lineChartDate.push(this.startDate, this.endDate);
    this.getAllProductionLinesDateRangeDataTarget(this.startDate, this.endDate);
    this.viewMaintenanceDays();
    this.getStackBarChartData(this.startDate, this.endDate);
    this.getStackAreaChartData(this.startDate, this.endDate);
    this.getAllProductionLinesYDayData();
    setTimeout(() => { this.getAllProductionLinesDateRangeData(this.startDate, this.endDate); }, 5000)
  }

  ngOnInit() {
    this.findAnnotationCollapsed = !this.createAnnotationCollapsed;
    this.processLinesData = [];
    this.productionRequest.startDate = this.startDate;
    this.productionRequest.endDate = this.endDate;
    this.productionRequest.processLines = this.processLinesData;
    if (this.localStorageService.fetchUserRole() == "Mills Operation") {
      this.productionRequest.frequency = "0";
    } else {
      this.productionRequest.frequency = "1";
    }
    this.getSelectedProductionLinesDateRangeData(this.productionRequest);
    setTimeout(() => { this.getAllProductionLinesDateForGrid(this.productionRequest) }, 1000);
    this.storeLineChartData();
  }

  oneMonthData: any[];
  threeMonthData: any[];
  sixMonthData: any[];
  public storeLineChartData() {
    this.productionRequest.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);;
    this.productionRequest.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');;
    this.productionService.getAllProductionLinesDateRangeDataTarget(this.productionRequest).subscribe((data: any) => {
      this.oneMonthData = data;
    });
    this.productionRequest.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 2).toString() + '-' + (new Date().getDate() - 1);
    this.productionRequest.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');;
    this.productionService.getAllProductionLinesDateRangeDataTarget(this.productionRequest).subscribe((data: any) => {
      this.threeMonthData = data;
    });
    this.productionRequest.startDate = (new Date().getFullYear() - 1).toString() + '-' + (new Date().getMonth() + 7).toString() + '-' + (new Date().getDate() - 1);
    this.productionRequest.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');;
    this.productionService.getAllProductionLinesDateRangeDataTarget(this.productionRequest).subscribe((data: any) => {
      this.sixMonthData = data;
    });
  }

  public changeChartTyped1() {
    this.showStackAreaChart = !this.showStackAreaChart;
    this.showStackChart = !this.showStackChart;
  }

  valueArr: number[];
  colorArr: string[];
  public getProductionYDayData() {
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProductionYDayData(data).subscribe((data: any) => {
      this.valueArr = data['range'].split(',');
      this.colorArr = data['colorRange'].split(',');
      var totalAverageValue = (data['totalAverageValue']);
      this.productionYDayActualValue = parseFloat(totalAverageValue).toString();
      this.productionYDayNeedleValue = (totalAverageValue * 100) / Number(data['maxValue']);
      this.options.rangeLabel.push(data['minValue'].toString());
      this.options.rangeLabel.push(data['maxValue'].toString());
      this.valueArr.filter(item => this.options.arcDelimiters.push(Number(item)));
      this.options.arcColors.splice(0, this.options.arcColors.length);
      this.colorArr.filter(item => this.options.arcColors.push((item)));
    });
    this.productonYDaySpinner = !this.productonYDaySpinner;
    this.productonYDayChart = !this.productonYDayChart;
  }

  targetDays: string;
  targetValue: string;
  targetEndDate: string;
  public getProjectedTarget() {
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProjectedTarget(data).subscribe((data: any) => {
      this.targetDays = data['targetDays'];
      this.targetValue = data['projectedTarget'].toLocaleString('en-us');
      this.targetEndDate = data['endDate'];
      this.tarGetAreaValue = Number(this.targetDays);
    });
  }

  annualTarget: string;
  public getAnnualTarget() {
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getAnnualTarget(data).subscribe((data: any) => {
      this.annualTarget = data['annualTarget'].toLocaleString('en-us');
    });
  }

  public getProductionYTDData() {
    this.multiLineDataForLine = [];
    const data = { millId: '1', buId: '1', kpiCategoryId: '1', kpiId: '1' };
    this.productionService.getProductionYTDData(data).subscribe((data: any) => {
      this.multiLineDataForLine = [...this.multiLineDataForLine, data];
    });
    this.productionService.getProductionYTDTargetData(data).subscribe((data: any) => {
      this.multiLineDataForLine = [...this.multiLineDataForLine, data];
    });
  }

  public getStackBarChartData(startDate: string, endDate: string) {
    this.productionRequest.startDate = startDate;
    this.productionRequest.endDate = endDate;
    this.productionService.getStackBarChartData(this.productionRequest).subscribe((data: any) => {
      this.multi = data;
    });
  }

  public getStackAreaChartData(startDate: string, endDate: string) {
    this.productionRequest.startDate = startDate;
    this.productionRequest.endDate = endDate;
    this.productionService.getStackAreaChartData(this.productionRequest).subscribe((data: any) => {
      this.multiStack = data;
    });
  }

  valueArr1: number[];
  colorArr1: string[];
  public getAllProductionLinesYDayData() {
    this.productionService.getAllproductionLinesYDayData(this.productionRequest).subscribe((data: any) => {

      var totalAverageValuePL1 = data['dailyKpiPulp'][0]['value'];
      this.productionYDayActualValuePL1 = parseFloat(totalAverageValuePL1).toString();
      this.productionYDayNeedleValuePL1 = (totalAverageValuePL1 * 100) / Number(data['dailyKpiPulp'][0]['max']);
      this.options1.rangeLabel.push(data['dailyKpiPulp'][0]['min'].toString());
      this.options1.rangeLabel.push(data['dailyKpiPulp'][0]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][0]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][0]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options1.arcDelimiters.push(Number(item)));
      this.options1.arcColors.splice(0, this.options1.arcColors.length);
      this.colorArr1.filter(item => this.options1.arcColors.push((item)));

      var totalAverageValuePL2 = (data['dailyKpiPulp'][1]['value']);
      this.productionYDayActualValuePL2 = parseFloat(totalAverageValuePL2).toString();
      this.productionYDayNeedleValuePL2 = (totalAverageValuePL2 * 100) / Number(data['dailyKpiPulp'][1]['max']);
      this.options2.rangeLabel.push(data['dailyKpiPulp'][1]['min'].toString());
      this.options2.rangeLabel.push(data['dailyKpiPulp'][1]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][1]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][1]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options2.arcDelimiters.push(Number(item)));
      this.options2.arcColors.splice(0, this.options2.arcColors.length);
      this.colorArr1.filter(item => this.options2.arcColors.push((item)));

      var totalAverageValuePL3 = (data['dailyKpiPulp'][2]['value']);
      this.productionYDayActualValuePL3 = parseFloat(totalAverageValuePL3).toString();
      this.productionYDayNeedleValuePL3 = (totalAverageValuePL3 * 100) / Number(data['dailyKpiPulp'][2]['max']);
      this.options3.rangeLabel.push(data['dailyKpiPulp'][2]['min'].toString());
      this.options3.rangeLabel.push(data['dailyKpiPulp'][2]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][2]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][2]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options3.arcDelimiters.push(Number(item)));
      this.options3.arcColors.splice(0, this.options3.arcColors.length);
      this.colorArr1.filter(item => this.options3.arcColors.push((item)));

      var totalAverageValuePL4 = (data['dailyKpiPulp'][3]['value']);
      this.productionYDayActualValuePL4 = parseFloat(totalAverageValuePL4).toString();
      this.productionYDayNeedleValuePL4 = (totalAverageValuePL4 * 100) / Number(data['dailyKpiPulp'][3]['max']);
      this.options4.rangeLabel.push(data['dailyKpiPulp'][3]['min'].toString());
      this.options4.rangeLabel.push(data['dailyKpiPulp'][3]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][3]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][3]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options4.arcDelimiters.push(Number(item)));
      this.options4.arcColors.splice(0, this.options4.arcColors.length);
      this.colorArr1.filter(item => this.options4.arcColors.push((item)));

      var totalAverageValuePL5 = (data['dailyKpiPulp'][4]['value']);
      this.productionYDayActualValuePL5 = parseFloat(totalAverageValuePL5).toString();
      this.productionYDayNeedleValuePL5 = (totalAverageValuePL5 * 100) / Number(data['dailyKpiPulp'][4]['max']);
      this.options5.rangeLabel.push(data['dailyKpiPulp'][4]['min'].toString());
      this.options5.rangeLabel.push(data['dailyKpiPulp'][4]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][4]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][4]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options5.arcDelimiters.push(Number(item)));
      this.options5.arcColors.splice(0, this.options5.arcColors.length);
      this.colorArr1.filter(item => this.options5.arcColors.push((item)));

      var totalAverageValuePL6 = (data['dailyKpiPulp'][5]['value']);
      this.productionYDayActualValuePL6 = parseFloat(totalAverageValuePL6).toString();
      this.productionYDayNeedleValuePL6 = (totalAverageValuePL6 * 100) / Number(data['dailyKpiPulp'][5]['max']);
      this.options6.rangeLabel.push(data['dailyKpiPulp'][5]['min'].toString());
      this.options6.rangeLabel.push(data['dailyKpiPulp'][5]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][5]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][5]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options6.arcDelimiters.push(Number(item)));
      this.options6.arcColors.splice(0, this.options6.arcColors.length);
      this.colorArr1.filter(item => this.options6.arcColors.push((item)));

      var totalAverageValuePL7 = (data['dailyKpiPulp'][6]['value']);
      this.productionYDayActualValuePL7 = parseFloat(totalAverageValuePL7).toString();
      this.productionYDayNeedleValuePL7 = (totalAverageValuePL7 * 100) / Number(data['dailyKpiPulp'][6]['max']);
      this.options7.rangeLabel.push(data['dailyKpiPulp'][6]['min'].toString());
      this.options7.rangeLabel.push(data['dailyKpiPulp'][6]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][6]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][6]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options7.arcDelimiters.push(Number(item)));
      this.options7.arcColors.splice(0, this.options7.arcColors.length);
      this.colorArr1.filter(item => this.options7.arcColors.push((item)));

      var totalAverageValuePL8 = (data['dailyKpiPulp'][7]['value']);
      this.productionYDayActualValuePL8 = parseFloat(totalAverageValuePL8).toString();
      this.productionYDayNeedleValuePL8 = (totalAverageValuePL8 * 100) / Number(data['dailyKpiPulp'][7]['max']);
      this.options8.rangeLabel.push(data['dailyKpiPulp'][7]['min'].toString());
      this.options8.rangeLabel.push(data['dailyKpiPulp'][7]['max'].toString());
      this.valueArr1 = data['dailyKpiPulp'][7]['range'].split(',');
      this.colorArr1 = data['dailyKpiPulp'][7]['colorRange'].split(',');
      this.valueArr1.filter(item => this.options8.arcDelimiters.push(Number(item)));
      this.options8.arcColors.splice(0, this.options8.arcColors.length);
      this.colorArr1.filter(item => this.options8.arcColors.push((item)));
    });
  }



  public getAllProductionLinesDateRangeDataTarget(startDate: string, endDate: string) {
    this.productionRequest.startDate = startDate;
    this.productionRequest.endDate = endDate;
    this.productionService.getAllProductionLinesDateRangeDataTarget(this.productionRequest).subscribe((data: any) => {
      this.productionLinesTargetData = data;
    });
  }


  public getAllProductionLinesDateRangeData(startDate: string, endDate: string) {
    if (this.productionLinesTargetData == []) {
      this.getAllProductionLinesDateRangeDataTarget(startDate, endDate);
    }
    this.productionRequest.startDate = startDate;
    this.productionRequest.endDate = endDate;
    this.productionRequest.frequency = "0";
    this.productionService.getSelectedProductionLinesDateRangeData(this.productionRequest).subscribe((data: any) => {
      var pl1ChartData = [data[0]];
      this.pl1Data = (pl1ChartData);
      this.pl1Data.push(this.productionLinesTargetData[0]);
      var pl2ChartData = [data[1]];
      this.pl2Data = (pl2ChartData);
      this.pl2Data.push(this.productionLinesTargetData[1]);
      var pl3ChartData = [data[2]];
      this.pl3Data = (pl3ChartData);
      this.pl3Data.push(this.productionLinesTargetData[2]);
      var pl4ChartData = [data[3]];
      this.pl4Data = (pl4ChartData);
      this.pl4Data.push(this.productionLinesTargetData[3]);
      var pl5ChartData = [data[4]];
      this.pl5Data = (pl5ChartData);
      this.pl5Data.push(this.productionLinesTargetData[4]);
      var pl6ChartData = [data[5]];
      this.pl6Data = (pl6ChartData);
      this.pl6Data.push(this.productionLinesTargetData[5]);
      var pl7ChartData = [data[6]];
      this.pl7Data = (pl7ChartData);
      this.pl7Data.push(this.productionLinesTargetData[6]);
      var pl8ChartData = [data[7]];
      this.pl8Data = (pl8ChartData);
      this.pl8Data.push(this.productionLinesTargetData[7]);
    });
  }


  public getSelectedProductionLinesDateRangeData(productionRequest: ProductionRequest) {
    this.productionService.getSelectedProductionLinesDateRangeData(productionRequest).subscribe((data: any) => {
      console.log(data);
      this.multiLineData = data;
    });
  }

  public getAllProductionLinesDateForGrid(productionRequest: ProductionRequest) {
    this.productionService.getAllProductionLinesDateForGrid(productionRequest).subscribe((data: any) => {
      this.productonLines = data[0];

    });
  }

  public changeChartDuration(event) {
    if (event == "radio1") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getStackBarChartData(this.startDate, this.endDate);
      this.getStackAreaChartData(this.startDate, this.endDate);
      this.getStackAreaChartData(this.startDate, this.endDate);
    } else if (event == "radio2") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 2).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getStackBarChartData(this.startDate, this.endDate);
      this.getStackAreaChartData(this.startDate, this.endDate);
    } else if (event == "radio3") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 5).toString() + '-' + (new Date().getDate() + 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.getStackBarChartData(this.startDate, this.endDate);
      this.getStackAreaChartData(this.startDate, this.endDate);
    }
  }

  public changeChartDurationLineChart(event) {
    if (event == "radio1") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth()).toString() + '-' + (new Date().getDate() - 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.productionLinesTargetData = this.oneMonthData;
      this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
    } else if (event == "radio2") {
      this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 2).toString() + '-' + (new Date().getDate() - 1);
      this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      this.productionLinesTargetData = this.threeMonthData;
      this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
    } else if (event == "radio3") {
      if (new Date().getMonth() < 6) {
        this.startDate = (new Date().getFullYear() - 1).toString() + '-' + (new Date().getMonth() + 7).toString() + '-' + (new Date().getDate() - 1);
        this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
        this.productionLinesTargetData = this.sixMonthData;
        this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
      } else {
        this.startDate = new Date().getFullYear().toString() + '-' + (new Date().getMonth() - 5).toString() + '-' + (new Date().getDate() - 1);
        this.endDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
        this.productionLinesTargetData = this.sixMonthData;
        this.getAllProductionLinesDateRangeData(this.startDate, this.endDate);
      }

    }
  }

  processLinesData: any[] = [];
  public searchData() {
    if (this.productionEnquiryData.lineChartDate == null) {
      alert("Please select date range");
      return null;
    }
    var startDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[0], 'yyyy-MM-dd');
    var endDate = this.datePipe.transform(this.productionEnquiryData.lineChartDate[1], 'yyyy-MM-dd');
    this.productionRequest.startDate = startDate;
    this.productionRequest.endDate = endDate;
    this.productionRequest.frequency = this.productionEnquiryData.selectedValue['code'];
    if (this.productionEnquiryData.lineChartPLines.length > 0) {
      this.processLinesData = [];
      this.cols = [];
      this.cols.push({ field: 'date', header: 'Date' });
      this.productionEnquiryData.lineChartPLines.forEach(processLine => {
        this.processLinesData.push(processLine['header']);
        this.cols.push(processLine);
      });
    } else {
      this.processLinesData = [];
      this.cols = [
        { field: 'date', header: 'Date' },
        { field: 'fl1', header: 'FL1' },
        { field: 'fl2', header: 'FL2' },
        { field: 'fl3', header: 'FL3' },
        { field: 'pcd', header: 'PCD' },
        { field: 'pd1', header: 'PD1' },
        { field: 'pd2', header: 'PD2' },
        { field: 'pd3', header: 'PD3' },
        { field: 'pd4', header: 'PD4' }
      ];
    }
    this.productionRequest.processLines = this.processLinesData;
    this.getSelectedProductionLinesDateRangeData(this.productionRequest);
    this.getAllProductionLinesDateForGrid(this.productionRequest);
  }

  public openAnnotations() {
    this.displayAnnotations = !this.displayAnnotations;
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


  xAxisTickFormatting(val: any): string {
    return val.replace("-2019", "");
  }

  annotationDate: string = "";
  onSelect(event) {
    this.annotationDate = event.name;
    this.getAnnotationData();
    this.openAnnotations();
  }

  public getAnnotationData() {
    const data = { millId: '1', buTypeId: '1', kpiId: '1', annotationDate: this.annotationDate };
    this.productionService.fetchAnnotation(data).subscribe((data: any) => {
      this.annotationsLines = data;
    });
  }

  annotationDates: any[] = [];
  public getAnnotationDates() {
    const data = { millId: '1', buTypeId: '1', kpiId: '1', startDate: this.startDate, endDate: this.endDate };
    this.productionService.getAnnotationDates(data).subscribe((data: any) => {
      this.annotationDates = data['annotationDates'];

    });
  }

  annotationLines: string = "";
  public createAnnotation() {
    this.annotationLines="";
    var loginId = this.localStorageService.fetchloginId();
    var processLines;
    if (this.annotationProcessLines.length == 0) {
      alert("Please Select Process Lines");
      return null;
    }
    if (this.annotationDescription == "") {
      alert("Please add description");
      return null;
    }
    this.annotationProcessLines.forEach(element => {
      this.annotationLines = this.annotationLines.concat(element['header'], ' ,');
    });
    this.annotationLines = this.annotationLines.replace(/,\s*$/, "");
    const data = { millId: '1', buTypeId: '1', kpiId: '1', annotationDate: this.annotationDate, processLines: this.annotationLines, description: this.annotationDescription, userLoginId: loginId };
    this.productionService.createAnnotation(data).subscribe((data: any) => {
      if (data == null) {
        alert("Annotation saved successfully.")
        this.annotationDescription = "";
        this.annotationProcessLines = [];
        this.getAnnotationData();
        this.getAnnotationDates();
        this.searchData();
      } else {
        alert("Annotation could not be saved.")
        return null;
      }
    });
  }

  createAnnotationBeforeToggle() {
    this.findAnnotationCollapsed = !this.findAnnotationCollapsed;
  }

  findAnnotationBeforeToggle() {
    this.createAnnotationCollapsed = !this.createAnnotationCollapsed;
  }

  maintanenceListDataNew: any[];
  maintErrorMessage: string;
  public showError(severity: string, summary: string, detail: string) {
    this.messageService.add({ severity: severity, summary: summary, detail: detail });
  }

  public add() {
    if (this.dateValue == undefined) {
      this.showError("error", "Error Message", "Please select Date.");
      return;
    }
    if (this.textAreaValue == undefined || this.textAreaValue == null) {
      this.showError("error", "Error Message", "Please enter Remarks.");
      return;
    }
    this.maintanenceListDataNew = [];
    var date = this.dateValue.getDate();
    var month = this.dateValue.getMonth() + 1;
    var year = this.dateValue.getFullYear();
    var totalDate = year + "-" + month + "-" + date;
    this.maintanenceListDataNew.push(totalDate);
    var userRemark = this.textAreaValue;
    const data = {
      millId: 1, buId: 1, createdBy: 1, updatedBy: 1,
      remarks: userRemark, active: true, maintenanceDays: this.maintanenceListDataNew
    };
    this.productionService.saveMaintenanceDays(data).subscribe((data: any) => {
      if (data == null) {
        this.showError("success", "", "Added Successfully.");
        this.textAreaValue = "";
        this.dateValue = null;
        this.viewMaintenanceDays();
        this.getProjectedTarget();
      }
    });
  }

  maintanenceDayFullList: any[];
  public viewMaintenanceDays() {
    const data = { millId: "1", buId: "1" }; // later would need to change this to autoreceive ID
    this.productionService.getMaintenanceData(data).subscribe(
      (data: any) => {
        this.maintanenceDayModel = data;
      }
    )
  }

  msgs: any[];
  newdeleteDate: any[]
  public delMaintanenceDays() {
    this.newdeleteDate = [];
    this.selectedMaintenanceDay.forEach(element => {
      this.newdeleteDate.push(element.id);
    });

    const data = { ids: this.newdeleteDate };
    this.productionService.deleteMaintenanceDays(data).subscribe(
      (data: any) => {
        this.showError("success", "", "Deleted.");
        this.viewMaintenanceDays();
        this.getProjectedTarget();
      });
  }

  finalNoOfDays: Number;
  public addTargetDays() {
    if (this.tarGetAreaValue == undefined || this.tarGetAreaValue == null) {
      this.showError("error", "Error Message", "Please enter target days.");
      return;
    }
    if (this.tarGetAreaValue <= 0) {
      this.showError("error", "Error Message", "Please enter target days value greater than 0.");
      return;
    }
    var popUpNoOfTargetDays = Number(this.tarGetAreaValue);
    const data = { millId: 1, buId: 1, kpiCategoryId: 1, noOfTargetDays: popUpNoOfTargetDays };
    this.productionService.updateMaintanenceTargetDays(data).subscribe(
      (data: any) => {
        if (data == null) {
          alert("Added Successfully");
          this.getProjectedTarget();
        }
      });
  }

  displaySettingIcon: boolean = false;
  public openSettingIcon() {
    this.displaySettingIcon = !this.displaySettingIcon;
  }

}
