import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment-failed',
  templateUrl: './payment-failed.component.html',
  styleUrls: ['./payment-failed.component.css']
})
export class PaymentFailedComponent implements OnInit {

  count = 5;
  loading = true;
  interval: any;
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
        this.router.navigate(['/client']);
      }
    }, 1000);
  }

}
