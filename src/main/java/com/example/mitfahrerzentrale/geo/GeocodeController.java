package com.example.mitfahrerzentrale.geo;

import com.example.mitfahrerzentrale.geo.dto.NominatimResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/geocode")
public class GeocodeController {

    private final WebClient webClient;

    public GeocodeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<NominatimResponseDTO>> searchLocation(@RequestParam String query) {
        // Sende API-Anfrage und mappe direkt auf Liste von DTOs
        List<NominatimResponseDTO> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .queryParam("addressdetails", "1")
                        .queryParam("limit", "5")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<NominatimResponseDTO>>() {})
                .block(); // Synchroner Aufruf

        // Prüfe, ob Ergebnisse vorhanden sind
        if (response == null || response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    public NominatimResponseDTO searchSingleLocation(String query) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .queryParam("addressdetails", "1")
                        .queryParam("limit", "1") // Begrenze auf ein Ergebnis
                        .build())
                .retrieve()
                .bodyToMono(NominatimResponseDTO[].class) // API gibt ein Array zurück
                .blockOptional() // Konvertiere zu Optional, falls kein Ergebnis zurückkommt
                .map(results -> results.length > 0 ? results[0] : null) // Nimm das erste Ergebnis, falls vorhanden
                .orElseThrow(() -> new RuntimeException("No results found for query: " + query));
    }
}
