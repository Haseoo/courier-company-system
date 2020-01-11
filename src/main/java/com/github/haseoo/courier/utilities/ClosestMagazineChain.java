package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.models.AddressModel;
import com.github.haseoo.courier.models.MagazineModel;
import com.github.haseoo.courier.servicedata.places.AddressData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public abstract class ClosestMagazineChain {
    private ClosestMagazineChain nextSteep;

    public abstract List<MagazineModel> filterList(List<MagazineModel> magazineList, AddressData address);

    public static class FilterByStreet extends ClosestMagazineChain {
        private FilterByStreet(ClosestMagazineChain nextSteep) {
            super(nextSteep);
        }

        @Override
        public List<MagazineModel> filterList(List<MagazineModel> magazineList, AddressData address) {
            return magazineList.stream()
                    .filter(magazine -> magazine.getAddress().getStreet().equals(address.getStreet()))
                    .collect(Collectors.toList());
        }
    }

    public static class FilterByCity extends ClosestMagazineChain {

        private FilterByCity(ClosestMagazineChain nextSteep) {
            super(nextSteep);
        }

        @Override
        public List<MagazineModel> filterList(List<MagazineModel> magazineList, AddressData address) {
            return magazineList.stream()
                    .filter(magazine -> magazine.getAddress().getCity().equals(address.getCity()))
                    .collect(Collectors.toList());
        }
    }

    public static class FilterByPostalCode extends ClosestMagazineChain {
        private FilterByPostalCode(ClosestMagazineChain nextSteep) {
            super(nextSteep);
        }

        @Override
        public List<MagazineModel> filterList(List<MagazineModel> magazineList, AddressData address) {
            return magazineList.stream()
                    .filter(magazine -> magazine.getAddress().getPostalCode().equals(address.getPostalCode()))
                    .collect(Collectors.toList());
        }
    }

    public static ClosestMagazineChain prepareChain(boolean isPostalCodeInCity) {
        if (isPostalCodeInCity) {
            return new FilterByCity(new FilterByPostalCode(new FilterByStreet(null)));
        } else {
            return new FilterByPostalCode(null);
        }
    }
}
