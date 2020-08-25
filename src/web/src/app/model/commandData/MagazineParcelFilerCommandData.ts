import { MagazineFilter } from './../enums/magazineFilter';
export class MagazineParcelFilerCommandData {
    type: MagazineFilter;

    constructor(type: MagazineFilter) {
        this.type = type;
    }
}