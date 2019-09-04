import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsumptionDashboardComponent } from './consumption-dashboard.component';

describe('ConsumptionDashboardComponent', () => {
  let component: ConsumptionDashboardComponent;
  let fixture: ComponentFixture<ConsumptionDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ConsumptionDashboardComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsumptionDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
