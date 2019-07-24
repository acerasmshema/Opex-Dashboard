import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBenchmarkSetComponent } from './search-benchmark-set.component';

describe('SearchBenchmarkSetComponent', () => {
  let component: SearchBenchmarkSetComponent;
  let fixture: ComponentFixture<SearchBenchmarkSetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchBenchmarkSetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchBenchmarkSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
