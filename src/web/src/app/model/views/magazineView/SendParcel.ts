import { Address } from './Address';
import { Logistician } from './Logistician';
import { ParcelType } from './ParcelType';
import { ParcelStatesView } from './ParcelStatesView';
export interface SendParcel {
    deliveryAddress: Address;
    expectedCourierArrivalDate: string;
    id: number;
    moveable: boolean;
    paid: boolean;
    parcelFee: number;
    parcelPrice: number;
    parcelStatesView: ParcelStatesView[];
    parcelType: ParcelType;
    priority: boolean;
    receiverInfoView: Logistician;
    toReturn: boolean;
}
