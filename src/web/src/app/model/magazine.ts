import { Address } from './address';
import { Logistician } from './logistician';
import { ParcelMagazineView } from './views/parcelMagazineView';

export class Magazine {
  id?: number;
  address: Address;
  active?: boolean;
  logisticians?: Logistician[];
  parcels?: ParcelMagazineView[];

  public constructor() {
    
  }
 
}
