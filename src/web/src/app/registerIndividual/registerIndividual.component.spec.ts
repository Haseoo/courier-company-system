import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterIndividualComponent } from './registerIndividual.component';

describe('RegisterComponent', () => {
  let component: RegisterIndividualComponent;
  let fixture: ComponentFixture<RegisterIndividualComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterIndividualComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterIndividualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
