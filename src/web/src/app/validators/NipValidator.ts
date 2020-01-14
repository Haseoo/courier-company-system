import { AbstractControl, FormControl } from '@angular/forms';

export class NipValidator {
  static validateNip(fc: FormControl) {
    // if (!/^[0-9]{10}$/.test(fc.value)) {
    //   return { validateNip: true };
    // }
    // const times = [6, 5, 7, 2, 3, 4, 5, 6, 7];
    // const digits = `${fc.value}`.split('').map(digit => parseInt(digit, 10));
    // digits[]
    // const dig11 = digits.splice(-1)[0];
    // const sumcon = digits
    //   .reduce((previousValue, currentValue, index) => previousValue + currentValue * times[index % 4]) % 10;
    // return null;
  }

}
