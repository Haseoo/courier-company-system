import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogisticianComponent } from './logistician.component';

describe('LogisticianComponent', () => {
  let component: LogisticianComponent;
  let fixture: ComponentFixture<LogisticianComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogisticianComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogisticianComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
