package com.example.mitfahrerzentrale.geo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NominatimResponseDTO {

    private String placeId;
    private String osmType;
    private String osmId;
    private String lat;
    private String lon;
    private String displayName;

    @JsonProperty("boundingbox")
    private String[] boundingBox;

    private Address address;


    @Getter
    @Setter
    public static class Address {
        private String road;
        private String city;
        private String state;
        private String postcode;
        private String country;
    }
}
