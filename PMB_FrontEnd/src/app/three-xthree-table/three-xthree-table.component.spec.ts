import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreeXThreeTableComponent } from './three-xthree-table.component';

describe('ThreeXThreeTableComponent', () => {
  let component: ThreeXThreeTableComponent;
  let fixture: ComponentFixture<ThreeXThreeTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ThreeXThreeTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ThreeXThreeTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
