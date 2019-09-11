import { Injectable } from '@angular/core';
import { ConsumptionModel } from '../shared/models/consumption-model';
import { SearchKpiData } from '../shared/models/search-kpi-data';
import { DatePipe } from '@angular/common';
import { StatusService } from '../shared/service/status.service';
import { ApiCallService } from '../shared/service/api/api-call.service';
import { ConsumptionRequest } from '../dashboard/consumption-dashboard/consumption-reqest';
import { ConsumptionGridView } from '../dashboard/consumption-dashboard/consumption-grid-view';
import { API_URL } from 'src/app/shared/constant/API_URLs';
import { CommonMessage } from 'src/app/shared/constant/Common-Message';
import { MessageService } from 'primeng/components/common/messageservice';

@Injectable()
export class BenchmarkService {

    benchmarkKpiUrl = API_URL.apiURLs.BENCHMARK_FILTER_URL;
    private searchKpiData: SearchKpiData;

    constructor(private apiCallService: ApiCallService,
        private statusService: StatusService,
        private datePipe: DatePipe,
    private messageService:MessageService) { }


    public filterCharts(searchKpiData: SearchKpiData) {
        this.searchKpiData = searchKpiData;

        searchKpiData.startDate = this.datePipe.transform(searchKpiData.date[0], 'yyyy-MM-dd');
        searchKpiData.endDate = this.datePipe.transform(searchKpiData.date[1], 'yyyy-MM-dd');

        searchKpiData.kpiTypes.forEach(kpiType => {
            kpiType.kpiList.forEach(kpi => {
                const benchmarkModel = this.createChart(kpi.kpiId, kpi.kpiName + " (" + kpi.unit + ")");
                this.statusService.benchmarkList.push(benchmarkModel);
                searchKpiData.kpiId = kpi.kpiId;
                this.updateChart(searchKpiData);
            });
        });
    }

    createChart(kpiId: number, kpiName: string) {
        let benchmark = new ConsumptionModel();
        benchmark.kpiId = kpiId
        benchmark.kpiName = kpiName;
        benchmark.xAxis = true;
        benchmark.yAxis = true;
        benchmark.showDataLabel = true;
        benchmark.roundEdges = false;
        benchmark.checked = false;
        benchmark.legend = false;
        benchmark.barPadding = 1;
        benchmark.groupPadding = 1;
        benchmark.chartType = "bar";
        benchmark.showXAxisLabel = false;
        benchmark.showYAxisLabel = false;
        benchmark.gradient = "gradient";
        benchmark.xAxisLabel = "";
        benchmark.yAxisLabel = "";
        benchmark.showKpiType = false;
        benchmark.colorScheme = { domain: ['#5e49d1', '#5e49d1', '#5e49d1', '#5e49d1', '#dde034', '#dde034', '#dde034'] };

        return benchmark;
    }

    updateChart(searchKpiData: SearchKpiData) {
        let millIds = [];
        searchKpiData.mills.forEach(mill => {
            millIds.push(mill.millId);
        });

        let benchmarkRequest = new ConsumptionRequest();
        benchmarkRequest.startDate = searchKpiData.startDate;
        benchmarkRequest.endDate = searchKpiData.endDate;
        benchmarkRequest.kpiId = searchKpiData.kpiId;
        benchmarkRequest.millId = millIds;
        benchmarkRequest.frequency = searchKpiData.frequency["code"];

        this.getDataforBenchmart(benchmarkRequest).
            subscribe((response: any) => {
                const benchmark = this.statusService.benchmarkList.find((con) => con.kpiId === benchmarkRequest.kpiId);

                if (response.kpiData.length > 0) {
                    benchmark.data = response.kpiData;
                    let maxValue = 0;
                    benchmark.data.forEach(kpiData => {
                        kpiData.series.forEach(seriesData => {
                            if (seriesData.value > maxValue)
                                maxValue = seriesData.value;
                        });
                    });
                    benchmark.yScaleMax = Math.round(maxValue + (maxValue * 0.2));
                    this.resetDataLabel(benchmark, benchmark.data.length);
                    benchmark.error = false;
                }
                else {
                    benchmark.error = true;
                }

                if (this.statusService.isSpin)
                    this.statusService.spinnerSubject.next(false);
            },
            (error: any) => {
              this.statusService.spinnerSubject.next(false);
              if(error.status=="0"){
              alert(CommonMessage.ERROR.SERVER_ERROR)
              }else{
                this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
            }
          });
    }

    downloadBenchmarkData(kpiId: number, kpiName: string, isDaily: boolean) {
        if (!isDaily) {
            const benchmarkData = this.statusService.benchmarkList.find((con) => con.kpiId === kpiId).data;
            this.download(benchmarkData, kpiName);
        }
        else {
            this.statusService.spinnerSubject.next(true);
            let millIds = [];
            this.searchKpiData.mills.forEach(mill => {
                millIds.push(mill.millId);
            });

            let benchmarkRequest = new ConsumptionRequest();
            benchmarkRequest.startDate = this.searchKpiData.startDate;
            benchmarkRequest.endDate = this.searchKpiData.endDate;
            benchmarkRequest.kpiId = kpiId;
            benchmarkRequest.millId = millIds;
            benchmarkRequest.frequency = "0";

            this.getDataforBenchmart(benchmarkRequest).
                subscribe((response: any) => {
                    const kpiData = response.kpiData;
                    this.download(kpiData, kpiName);
                    this.statusService.spinnerSubject.next(false);
                },
                (error: any) => {
                  this.statusService.spinnerSubject.next(false);
                  if(error.status=="0"){
                  alert(CommonMessage.ERROR.SERVER_ERROR)
                  }else{
                    this.messageService.add({ severity: 'error', summary: '', detail: CommonMessage.ERROR_CODES[error.error.status] });
                }
              });
        }
    }

    download(benchmarkData: any, kpiName: string) {
        let consumptionGridView = new ConsumptionGridView();
        consumptionGridView.show = true;
        consumptionGridView.paginator = true;
        consumptionGridView.scrollable = true;
        consumptionGridView.rows = 10;
        consumptionGridView.title = kpiName;

        consumptionGridView.columnNames.push({ header: "DATE", field: "DATE" });
        benchmarkData[0].series.forEach(processLine => {
            consumptionGridView.columnNames.push({ header: processLine.name, field: processLine.name });
        });

        let gridsData = [];
        benchmarkData.forEach(processDetail => {
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

    refreshBenchmark() {
        if (this.statusService.benchmarkList.length > 0) {
            this.statusService.benchmarkList.forEach(benchmark => {
                if (benchmark.data !== undefined && benchmark.data.length > 0) {
                    benchmark.chartType = "";
                    setTimeout(() => {
                        benchmark.chartType = "bar";
                        this.resetDataLabel(benchmark, benchmark.data.length);
                    }, 50);
                }
            });
        }
    }

    resetDataLabel(benchmark: ConsumptionModel, section: number) {
        setTimeout(() => {
            let barCount: number = 0;
            let benchmarkChart = document.getElementById("benchmark_" + benchmark.kpiId);
            if (benchmarkChart !== undefined && benchmarkChart !== null) {
                const benchmarkData = benchmark.data;
                let processLines = [];
                benchmarkData[0].series.forEach(benchmark => {
                    processLines.push(benchmark.name);
                });

                let nodes = benchmarkChart.getElementsByClassName("textDataLabel");
                for (let index = 0; index < nodes.length; index++) {
                    const labelElement: any = nodes[index];
                    if (section > 5) {
                        let rotation = labelElement.getAttribute('transform');
                        let newRotation = rotation.replace('-45', '270')
                        labelElement.setAttribute('transform', newRotation);
                        labelElement.style.fontSize = '8px';
                    }
                    else {
                        let x = labelElement.getAttribute("x") - 10;
                        labelElement.setAttribute('x', x);
                        labelElement.removeAttribute('transform');
                    }
                    labelElement.style.fontWeight = '600';

                    if (barCount == processLines.length)
                        barCount = 0;

                    labelElement.innerHTML = processLines[barCount];
                    barCount++;
                }
            }
        }, 1500);
    }

    getDataforBenchmart(benchmarkRequest: ConsumptionRequest) {
        return this.apiCallService.callAPIwithData(this.benchmarkKpiUrl, benchmarkRequest);
    }

}