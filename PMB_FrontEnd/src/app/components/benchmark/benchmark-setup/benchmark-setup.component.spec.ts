import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchmarkSetupComponent } from './benchmark-setup.component';

describe('BenchmarkSetupComponent', () => {
  let component: BenchmarkSetupComponent;
  let fixture: ComponentFixture<BenchmarkSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BenchmarkSetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BenchmarkSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
