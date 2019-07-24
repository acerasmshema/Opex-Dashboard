import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BenchmarkSidePanelComponent } from './benchmark-side-panel.component';

describe('BenchmarkSidePanelComponent', () => {
  let component: BenchmarkSidePanelComponent;
  let fixture: ComponentFixture<BenchmarkSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BenchmarkSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BenchmarkSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
