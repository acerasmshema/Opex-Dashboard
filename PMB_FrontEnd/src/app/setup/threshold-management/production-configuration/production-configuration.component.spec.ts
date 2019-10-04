import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionConfigurationComponent } from './production-configuration.component';

describe('ProductionConfigurationComponent', () => {
  let component: ProductionConfigurationComponent;
  let fixture: ComponentFixture<ProductionConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductionConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
