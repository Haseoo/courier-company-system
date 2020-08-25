import { Address } from './Address';
import { Logistician } from './Logistician';
import { ParcelType } from './ParcelType';
import { Sender } from './Sender';
export interface Parcel {
    currentState: string;
    dateMoved: boolean;
    deliveryAddress: Address;
    effectivePrice: number;
    expectedDeliveryTime: string;
    id: number;
    paid: boolean;
    parcelFee: number;
    parcelType: ParcelType;
    priority: boolean;
    receiverContactData: Logistician;
    sender: Sender;
    senderAddress: Address;
}
