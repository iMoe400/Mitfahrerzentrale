package com.example.mitfahrerzentrale.geo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NominatimResponseDTO {

    @JsonProperty("place_id")
    private String placeId;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private String osmId;

    private String lat;
    private String lon;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("boundingbox")
    private String[] boundingBox;

    private Address address;

    @Getter
    @Setter
    public static class Address {
        @JsonProperty("house_number")
        private String houseNumber;

        private String road;

        @JsonProperty("city")
        private String city;

        @JsonProperty("town")
        private String town;

        @JsonProperty("village")
        private String village;

        @JsonProperty("hamlet")
        private String hamlet;

        private String state;
        private String county;

        private String postcode;
        private String country;

        // Hilfsmethode für die Stadt oder Gemeinde
        public String getCityOrTown() {
            return city != null ? city : town != null ? town : village != null ? village : hamlet != null ? hamlet : "Keine Stadt verfügbar";
        }
    }
}
