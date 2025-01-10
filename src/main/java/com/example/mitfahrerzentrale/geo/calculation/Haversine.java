package com.example.mitfahrerzentrale.geo.calculation;


public class Haversine {

    private static final double EARTH_RADIUS_KM = 6371.0; // Radius der Erde in Kilometern

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Konvertiere Grad in Bogenmaß
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Unterschiede berechnen
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine-Formel
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Entfernung berechnen
        return EARTH_RADIUS_KM * c;
    }

}
