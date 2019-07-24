import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UtilitiesConsumptionDashboardComponent } from './utilities-consumption-dashboard.component';

describe('UtilitiesConsumptionDashboardComponent', () => {
  let component: UtilitiesConsumptionDashboardComponent;
  let fixture: ComponentFixture<UtilitiesConsumptionDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UtilitiesConsumptionDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UtilitiesConsumptionDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
