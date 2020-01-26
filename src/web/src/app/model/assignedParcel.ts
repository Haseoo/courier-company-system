import { Place } from './place';
import { ReceiverInfoView } from './views/receiverInfoView';
import { ParcelType } from './views/magazineView/ParcelType';

export interface AssignedParcel {
    currentState:               string;
    destination:                Place;
    expectedCourierArrivalDate: string;
    id:                         number;
    paid:                       boolean;
    parcelFee:                  number;
    parcelPrice:                number;
    parcelType:                 ParcelType;
    priority:                   boolean;
    receiverInfoView:           ReceiverInfoView;
    source:                     Place;
    toReturn:                   boolean;
}