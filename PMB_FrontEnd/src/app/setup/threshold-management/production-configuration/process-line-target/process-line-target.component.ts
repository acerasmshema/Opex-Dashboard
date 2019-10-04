import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-process-line-target',
  templateUrl: './process-line-target.component.html',
  styleUrls: ['./process-line-target.component.scss']
})
export class ProcessLineTargetComponent implements OnInit {

  public canvasWidth = 220
  public needleValue = 80
  public bottomLabel = '2126'
  public options = {
      hasNeedle: true,
      needleColor: 'black',
      needleUpdateSpeed: 1000,
      arcColors:  ["red", "yellow", "green"],      
      arcDelimiters: [80, 90],
      rangeLabel: ['0', '2500'],
      needleStartValue: 50,
  }
  
  constructor() { }

  ngOnInit() {
  }

}
