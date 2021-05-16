import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FRequestsComponent } from './f-requests.component';

describe('FRequestsComponent', () => {
  let component: FRequestsComponent;
  let fixture: ComponentFixture<FRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
