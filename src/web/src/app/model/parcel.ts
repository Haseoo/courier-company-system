import { ParcelType } from './parcelType';
import { ReceiverInfoView } from './views/receiverInfoView';
export class Parcel {
    expectedCourierArrivalDate: string;
    id?: number;
    paid: boolean;
    parcelFee: number;
    parcelPrice: number;
    parcelType: ParcelType;
    priority: boolean;
    receiverInfoView: ReceiverInfoView;
    toReturn: boolean;

}
