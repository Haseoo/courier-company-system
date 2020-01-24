import { SendParcel } from './SendParcel';
export interface Sender {
    active: boolean;
    clientType: string;
    emailAddress: string;
    id: number;
    phoneNumber: string;
    sendParcels: SendParcel[];
}
