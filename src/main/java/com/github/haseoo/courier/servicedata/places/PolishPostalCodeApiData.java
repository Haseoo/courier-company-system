package com.github.haseoo.courier.servicedata.places;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;


@Value
public class PolishPostalCodeApiData {

    @Value
    public static class Numeracja {
        private String form;
        private String to;
        private String parity;

        public Numeracja(@JsonProperty(value = "od") String form,
                         @JsonProperty(value = "do") String to,
                         @JsonProperty(value = "parzystosc") String parity) {
            this.form = form;
            this.to = to;
            this.parity = parity;
        }
    }

    public PolishPostalCodeApiData(@JsonProperty(value = "kod") String postalCode,
                                   @JsonProperty(value = "miejscowosc") String locality,
                                   @JsonProperty(value = "ulica") String street,
                                   @JsonProperty(value = "gmina") String commune,
                                   @JsonProperty(value = "powiat") String county,
                                   @JsonProperty(value = "wojewodztwo") String voivodeship,
                                   @JsonProperty(value = "dzielnica") String district,
                                   @JsonProperty(value = "numeracja") List<Numeracja> numeration) {
        this.postalCode = postalCode;
        this.locality = locality;
        this.street = street;
        this.commune = commune;
        this.county = county;
        this.voivodeship = voivodeship;
        this.district = district;
        this.numeration = numeration;
    }

    private String postalCode;
    private String locality;
    private String street;
    private String commune;
    private String county;
    private String voivodeship;
    private String district;
    private List<Numeracja> numeration;
}
