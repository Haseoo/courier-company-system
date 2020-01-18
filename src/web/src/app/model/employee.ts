import { EmployeeType } from './enums/employeeType';
export class Employee{
    employeeType: EmployeeType;
    id?: number;
    name: string;
    pesel: string;
    phoneNumber: string;
    surname: string;
}