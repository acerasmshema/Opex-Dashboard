import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterDataSidePanelComponent } from './master-data-side-panel.component';

describe('MasterDataSidePanelComponent', () => {
  let component: MasterDataSidePanelComponent;
  let fixture: ComponentFixture<MasterDataSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MasterDataSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterDataSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
