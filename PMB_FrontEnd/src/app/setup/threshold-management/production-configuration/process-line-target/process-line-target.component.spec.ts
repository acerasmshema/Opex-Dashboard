import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessLineTargetComponent } from './process-line-target.component';

describe('ProcessLineTargetComponent', () => {
  let component: ProcessLineTargetComponent;
  let fixture: ComponentFixture<ProcessLineTargetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessLineTargetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessLineTargetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
