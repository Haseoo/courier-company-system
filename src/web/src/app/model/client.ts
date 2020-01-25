import { SendParcel } from './views/magazineView/SendParcel';

export interface Client {
    active:       boolean;
    clientType:   string;
    emailAddress: string;
    id:           number;
    phoneNumber:  string;
    sendParcels:  SendParcel[];
  }