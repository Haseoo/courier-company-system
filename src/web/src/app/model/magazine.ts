import { Address } from './address';
import { Logistician } from './logistician';
import { ParcelMagazineView } from './views/parcelMagazineView';

export class Magazine {
  id?: number;
  address: Address;
  active?: boolean;
  logisticans?: Logistician;
  parcels?: ParcelMagazineView;

  public constructor(init?: Partial<Magazine>) {
    Object.assign(this, init);
}
}
