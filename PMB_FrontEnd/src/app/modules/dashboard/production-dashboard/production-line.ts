export class ProductionLine {
    show: boolean = false;
    canvasWidth: number;
    nameFont: number;
    centralLabel: string;
    fontSize: number;
    productionYDayActualValue: string = "0.0";
    productionLineData: any;
    colorScheme: any;
    xAxis: boolean;
    yAxis: boolean;
    legend: boolean;
    showXAxisLabel: boolean;
    showYAxisLabel: boolean;
    showDataLabel: boolean;
    animations: boolean;
    showRefLines: boolean;
    xScaleMin: number;
    xAxisLabel: string;
    yAxisLabel: string;
    processLineCode: string;
    lineChartLineInterpolation: any;
    productonYDayRefresh: boolean;
    productionYDayNeedleValue: number = 90;
    options: any = {
        hasNeedle: true,
        needleColor: 'gray',
        needleUpdateSpeed: 1000,
        arcColors: [], arcDelimiters: [],
        rangeLabel: [], needleStartValue: 0
    };
}