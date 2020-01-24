import { ReceiverInfoView } from './views/receiverInfoView';
import { ParcelType } from './parcelType';
import { ParcelStatesView } from './views/parcelStatesView';
import { Address } from './address';

export class SendersParcel {
    deliveryAddress: Address;
    expectedCourierArrivalDate: string;
    id?: number;
    moveable: boolean;
    paid: boolean;
    parcelFee: number;
    parcelPrice: number;
    parcelStatesView: ParcelStatesView[];
    parcelType: ParcelType;
    priority: boolean;
    receiverInfoView: ReceiverInfoView;
    toReturn: boolean;
}