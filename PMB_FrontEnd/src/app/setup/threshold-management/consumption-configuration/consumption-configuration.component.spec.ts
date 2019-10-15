import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsumptionConfigurationComponent } from './consumption-configuration.component';

describe('ConsumptionConfigurationComponent', () => {
  let component: ConsumptionConfigurationComponent;
  let fixture: ComponentFixture<ConsumptionConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsumptionConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsumptionConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
