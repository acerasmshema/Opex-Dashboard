import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBenchmarkComponent } from './search-benchmark.component';

describe('SearchBenchmarkComponent', () => {
  let component: SearchBenchmarkComponent;
  let fixture: ComponentFixture<SearchBenchmarkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchBenchmarkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchBenchmarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
