import { ParcelType } from '..';

import { ReceiverInfoView } from './receiverInfoView';

import { ParcelStatesView } from './magazineView';
import { Address } from '../address';


export interface ParcelClientView {
    id: number;
    parcelType: ParcelType;
    receiverInfoView: ReceiverInfoView;
    expectedCourierArrivalDate: string;
    parcelPrice: number;
    parcelFee: number;
    paid: boolean;
    priority: boolean;
    toReturn: boolean;
    parcelStatesView: ParcelStatesView[];
    deliveryAddress: Address;
    moveable: boolean;
    sender: string;
    senderAddress: Address;
}
