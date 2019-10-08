import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnualConfigurationComponent } from './annual-configuration.component';

describe('AnnualConfigurationComponent', () => {
  let component: AnnualConfigurationComponent;
  let fixture: ComponentFixture<AnnualConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnnualConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnualConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
