package com.example.mitfahrerzentrale.geo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllResponsesWrapper {

    List<NominatimResponseDTO> nominatimResponseDTOS;

}
