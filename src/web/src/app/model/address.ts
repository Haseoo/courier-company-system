export class Address {
  city: string;
  street: string;
  postalCode: string;
  buildingNumber: string;
  flatNumber: string;

    public constructor(init?: Partial<Address>) {
      Object.assign(this, init);
  }

}
