import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChemicalConsumptionDashboardComponent } from './chemical-consumption-dashboard.component';

describe('ChemicalConsumptionDashboardComponent', () => {
  let component: ChemicalConsumptionDashboardComponent;
  let fixture: ComponentFixture<ChemicalConsumptionDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChemicalConsumptionDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChemicalConsumptionDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
