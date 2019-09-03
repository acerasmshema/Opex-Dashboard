import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KappaNumberPredictionComponent } from './kappa-number-prediction.component';

describe('KappaNumberPredictionComponent', () => {
  let component: KappaNumberPredictionComponent;
  let fixture: ComponentFixture<KappaNumberPredictionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KappaNumberPredictionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KappaNumberPredictionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
