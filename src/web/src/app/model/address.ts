export class Address {
  buildingNumber: string;
  city: string;
  flatNumber: string;
  postalCode: string;
  street: string;

  public constructor(init?: Partial<Address>) {
    Object.assign(this, init);
  }

}
