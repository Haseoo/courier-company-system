import { Place } from '../place';
import { StateType } from '../enums/stateType';
import { ParcelType } from '../parcelType';
import { ReceiverInfoView } from './receiverInfoView';
export class ParcelCourierView{
    currentState: StateType;
    destination: Place;
    expectedCourierArrivalDate: string;
    id?: number;
    paid: boolean;
    parcelFee: number;
    parcelPrice: number;
    parcelType: ParcelType;
    priority: boolean;
    receiverInfoView: ReceiverInfoView;
    source: Place;
    toReturn: boolean;
}