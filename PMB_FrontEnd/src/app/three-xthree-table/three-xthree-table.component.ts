import { Component, Input, OnInit } from '@angular/core';

import { ChemicalConsumptionService } from '../services/ChemicalConsumption/chemical-consumption.service';

@Component({
  selector: 'three-xthree-table',
  templateUrl: './three-xthree-table.component.html',
  styleUrls: ['./three-xthree-table.component.scss']
})
export class ThreeXThreeTableComponent implements OnInit {
  
  gridData: any[] = [];
  @Input() kpiCategoryId: string;
  @Input() header: string;

  constructor(private chemicalConsumptionService: ChemicalConsumptionService) { }

  ngOnInit() {
    this.getDataForGrid();
  }

  public getDataForGrid() {
    const requestData = {
      kpiCategoryId: this.kpiCategoryId
    };

    this.chemicalConsumptionService.getDataForGrid(requestData).subscribe((data: any) => {
      data.map(ob => {
        ob.series.map(sro => {
          sro['color'] = this.parseAndGetColor(sro);
        })
      });
      console.log(data)
      this.gridData = data;
    });
  }
  
  public parseAndGetColor(sro) {
    let ar = sro.target.split(",");
    let prev = 0;
    if(sro.value == NaN){
      return "black"
    }
    for(let vl of ar) {
      let ix = vl.split(":");
      if(sro.value >= prev && sro.value <= parseInt(ix[1].trim())) {
        return ix[0].trim()
      } else {
        prev = parseInt(ix[1].trim())
      }
    }
    return "black";
  }
}
