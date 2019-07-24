import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabbedPanelComponent } from './tabbed-panel.component';

describe('TabbedPanelComponent', () => {
  let component: TabbedPanelComponent;
  let fixture: ComponentFixture<TabbedPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabbedPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabbedPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
