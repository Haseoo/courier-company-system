import { Address } from './Address';
import { Logistician } from './Logistician';
import { Parcel } from './Parcel';

export interface MagazineView {
    active: boolean;
    address: Address;
    id: number;
    logisticians: Logistician[];
    parcels: Parcel[];
}
