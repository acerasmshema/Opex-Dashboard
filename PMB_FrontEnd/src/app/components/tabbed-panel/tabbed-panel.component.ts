import { Component, OnInit,AfterViewInit  } from '@angular/core';

@Component({
  selector: 'app-tabbed-panel',
  templateUrl: './tabbed-panel.component.html',
  styleUrls: ['./tabbed-panel.component.scss']
})
export class TabbedPanelComponent implements OnInit {
  disbaleTab:boolean=true;
  constructor() {
    
   }

  ngOnInit() {
    
    setTimeout(() => {
      this.disbaleTab = false; 
  }, 5000);
  }

  

}
