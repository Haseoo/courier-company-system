import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { takeUntil } from 'rxjs/internal/operators/takeUntil';
import { interval } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';

@Component({
  selector: 'app-payment-success',
  templateUrl: './payment-success.component.html',
  styleUrls: ['./payment-success.component.css']
})
export class PaymentSuccessComponent implements OnInit {

  count = 5;
  interval: any;
  loading = true;
  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
    this.startTimer(); 
  }

  startTimer() {
    this.interval = setInterval(() => {
      if (this.count > 0) {
        this.count--;
      } else {
        clearInterval(this.interval);
        this.router.navigate(['/client']);
      }
    }, 1000);
  }
}
