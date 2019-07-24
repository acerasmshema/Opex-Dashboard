import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConditionalMonitoringComponent } from './conditional-monitoring.component';

describe('ConditionalMonitoringComponent', () => {
  let component: ConditionalMonitoringComponent;
  let fixture: ComponentFixture<ConditionalMonitoringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConditionalMonitoringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConditionalMonitoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
