import { ClientType } from './enums/clientType';
import { SendersParcel } from './sendersParcel';

export class Sender{
    active: boolean;
    clientType: string;
    emailAddress: string;
    id?: number;
    phoneNumber: string;
    sendParcels: SendersParcel[];
}