import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WoodConsumptionDashboardComponent } from './wood-consumption-dashboard.component';

describe('WoodConsumptionDashboardComponent', () => {
  let component: WoodConsumptionDashboardComponent;
  let fixture: ComponentFixture<WoodConsumptionDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WoodConsumptionDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WoodConsumptionDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
