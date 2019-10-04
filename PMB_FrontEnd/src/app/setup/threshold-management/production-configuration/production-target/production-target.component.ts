import { Component, OnInit } from '@angular/core';
import { ProductionLine } from 'src/app/dashboard/production-dashboard/production-LINE';

@Component({
  selector: 'app-production-target',
  templateUrl: './production-target.component.html',
  styleUrls: ['./production-target.component.scss']
})
export class ProductionTargetComponent implements OnInit {

  public canvasWidth = 220
  public needleValue = 80
  public bottomLabel = '7500'
  public options = {
      hasNeedle: true,
      needleColor: 'black',
      needleUpdateSpeed: 1000,
      arcColors:  ["red", "yellow", "green"],      
      arcDelimiters: [80, 90],
      rangeLabel: ['0', '9000'],
      needleStartValue: 50,
  }

  constructor() { }

  ngOnInit() {
   
  }

  onSubmit() {
    this.options.rangeLabel[0] = '11';
    this.options.rangeLabel[1] = '80';
  }

}
