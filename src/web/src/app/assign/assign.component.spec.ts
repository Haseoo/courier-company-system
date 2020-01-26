import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignComponent } from './assign.component';

describe('AsignComponent', () => {
  let component: AsignComponent;
  let fixture: ComponentFixture<AsignComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AsignComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AsignComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
