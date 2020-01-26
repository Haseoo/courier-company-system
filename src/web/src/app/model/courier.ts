import { Parcel } from './parcel';
import { AssignedParcel } from './assignedParcel';

export interface Courier {
    assignedParcels: AssignedParcel[];
    id:              number;
    name:            string;
    pesel:           string;
    phoneNumber:     string;
    surname:         string;
}

