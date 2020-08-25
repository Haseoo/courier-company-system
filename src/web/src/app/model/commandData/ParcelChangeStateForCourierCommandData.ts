export class ParcelChangeStateForCourierCommandData {
    newState: string;
    courierId?: number;
    wasPaid?: boolean;

    constructor(newState: string, courierId: number, wasPaid: boolean) {
        this.newState = newState;
        this.courierId = courierId;
        this.wasPaid = wasPaid;
    }
}