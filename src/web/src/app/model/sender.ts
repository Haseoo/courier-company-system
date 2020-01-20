import { SendersParcel } from './sendersParcel';
import { ClientType } from './enums/clientType';

export class Sender{
    active: boolean;
    clientType: ClientType;
    emailAddress: string;
    id?: number;
    phoneNumber: string;
    sendParcels: SendersParcel;
}