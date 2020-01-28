export class ParcelPickupCommandData {
    parcelsIds: number;
    wasPaid:	boolean;

    constructor(id: number, paid:boolean){
        this.parcelsIds = id;
        this.wasPaid = paid;
    }
}