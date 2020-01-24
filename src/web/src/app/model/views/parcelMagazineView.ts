import { ReceiverContactData } from '../receiverContactData';
import { ParcelType } from '../parcelType';
import { Address } from '../address';
import { Sender } from '../sender';
export class ParcelMagazineView {
    currentState: string;
    dateMoved: boolean;
    deliveryAddress: Address;
    effectivePrice: number;
    expectedDeliveryTime: string;
    id?: number;
    paid: boolean;
    parcelFee: number;
    parcelType: ParcelType;
    priority: boolean;
    receiverContactData: ReceiverContactData;
    sender: Sender;
    senderAddress: Address;
}