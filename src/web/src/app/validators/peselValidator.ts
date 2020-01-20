import { AbstractControl, FormControl, ValidatorFn } from '@angular/forms';

export class PeselValidator {
  static validatePesel(control: AbstractControl): { [key: string]: any } {
    if (!/^[0-9]{11}$/.test(control.value)) {
      return { pesel: false };
    }
    const weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
    let sum = 0;
    const digits = `${control.value}`.split('').map(digit => parseInt(digit, 10));
    const controlNumber = digits[10];
    for (let i = 0; i < weight.length; i++) {
      sum += digits[i] * weight[i];
    }
    if ((10 - (sum % 10)) === controlNumber) {
      return null;
    }
    return { pesel: false };
  }
}
