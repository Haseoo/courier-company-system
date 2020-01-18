import { ReceiverContactData } from '../receiverContactData';
import { ParcelType } from '../parcelType';
import { Address } from '../address';
import { StateType } from '../enums/stateType';
import { Sender } from '../sender';
export class ParcelMagazineView {
    currentState: StateType;
    dataMoved: boolean;
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
}