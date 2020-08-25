import { Address } from '../views/magazineView/Address';
import { ReceiverContactData } from '../receiverContactData';

export interface CommandAddData {
    parcelTypeId: number;
    deliveryAddress: Address;
    senderAddress: Address;
    parcelFee: number;
    senderId: number;
    receiverContactData: ReceiverContactData;
    priority: boolean;
}