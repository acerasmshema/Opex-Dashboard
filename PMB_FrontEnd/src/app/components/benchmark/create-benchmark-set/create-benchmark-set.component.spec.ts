import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBenchmarkSetComponent } from './create-benchmark-set.component';

describe('CreateBenchmarkSetComponent', () => {
  let component: CreateBenchmarkSetComponent;
  let fixture: ComponentFixture<CreateBenchmarkSetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateBenchmarkSetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBenchmarkSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
