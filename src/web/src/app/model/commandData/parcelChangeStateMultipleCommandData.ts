export class ParcelChangeStateMultipleCommandData {
        parcelsIds = Array<number>();

        addToList(id: number) {
                this.parcelsIds.push(id);
        }

}