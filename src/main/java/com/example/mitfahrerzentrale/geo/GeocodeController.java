package com.example.mitfahrerzentrale.geo;

import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/geocode")
public class GeocodeController {

    private final WebClient webClient;

    public GeocodeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org").build();
    }


    public List<NominatimResponseDTO> searchLocation(String query) {
        try {
            List<NominatimResponseDTO> response = new ArrayList<>();
            // Bereinige den Query-String
            query = query.trim();
            if (query.isEmpty()) {
                return response;
            }

            // API-Anfrage an Nominatim senden
            String finalQuery = query;
            response = webClient.get().uri(uriBuilder -> uriBuilder.path("/search").queryParam("q", finalQuery).queryParam("format", "json").queryParam("addressdetails", "1").queryParam("limit", "100").build()).retrieve().bodyToMono(new ParameterizedTypeReference<List<NominatimResponseDTO>>() {
            }).block(); // Synchroner Aufruf

            // Debugging: Rohe API-Antwort prüfen
            if (response != null) {
                System.out.println("RAW API RESPONSE: ");
                response.forEach(result -> {
                    System.out.println("Display Name: " + result.getDisplayName());
                    if (result.getAddress() != null) {
                        System.out.println("City: " + result.getAddress().getCity());
                        System.out.println("State: " + result.getAddress().getState());
                    } else {
                        System.out.println("No address details available.");
                    }
                });
            } else {
                System.out.println("Keine Ergebnisse von der API erhalten.");
            }

            return response;

        } catch (Exception e) {
            // Fehlerbehandlung und Logging
            System.err.println("Fehler bei der API-Anfrage: " + e.getMessage());
            throw new RuntimeException("Fehler bei der Geolocation-Abfrage.");
        }
    }
}
