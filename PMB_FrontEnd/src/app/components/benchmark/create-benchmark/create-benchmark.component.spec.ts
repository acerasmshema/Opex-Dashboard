import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBenchmarkComponent } from './create-benchmark.component';

describe('CreateBenchmarkComponent', () => {
  let component: CreateBenchmarkComponent;
  let fixture: ComponentFixture<CreateBenchmarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateBenchmarkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBenchmarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
