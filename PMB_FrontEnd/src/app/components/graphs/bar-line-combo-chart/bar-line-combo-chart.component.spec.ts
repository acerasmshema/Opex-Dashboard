import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BarLineComboChartComponent } from './bar-line-combo-chart.component';

describe('BarLineComboChartComponent', () => {
  let component: BarLineComboChartComponent;
  let fixture: ComponentFixture<BarLineComboChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BarLineComboChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BarLineComboChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
