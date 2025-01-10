package com.example.mitfahrerzentrale.app.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {


    /**
     * Formatiert ein ZonedDateTime in einen String mit einem benutzerdefinierten Format.
     *
     * @param zonedDateTime das ZonedDateTime-Objekt
     * @param format das gewünschte Datumsformat
     * @return ein formatierter String
     */
    public static String format(ZonedDateTime zonedDateTime, String format) {
        if (zonedDateTime == null || format == null || format.isEmpty()) {
            throw new IllegalArgumentException("ZonedDateTime oder Format darf nicht null oder leer sein");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return zonedDateTime.format(formatter);
    }


    /**
     * Konvertiert eine Zeit in einer bestimmten Zeitzone und formatiert sie mit einem benutzerdefinierten Format.
     *
     * @param dateTime das LocalDateTime-Objekt
     * @param zoneId die gewünschte Zeitzone
     * @param format das gewünschte Datumsformat
     * @return ein formatierter String
     */
    public static String formatWithZone(LocalDateTime dateTime, String zoneId, String format) {
        if (dateTime == null || zoneId == null || format == null || format.isEmpty()) {
            throw new IllegalArgumentException("DateTime, ZoneId oder Format darf nicht null oder leer sein");
        }
        ZoneId zone = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = dateTime.atZone(zone);
        return format(zonedDateTime, format);
    }

    /**
     * Formatiert ein Instant in ein LocalDateTime und dann in einen String mit einem benutzerdefinierten Format.
     *
     * @param instant das Instant-Objekt
     * @param zoneId die gewünschte Zeitzone
     * @param format das gewünschte Datumsformat
     * @return ein formatierter String
     */
    public static String formatFromInstant(Instant instant, String zoneId, String format) {
        if (instant == null || zoneId == null || format == null || format.isEmpty()) {
            throw new IllegalArgumentException("Instant, ZoneId oder Format darf nicht null oder leer sein");
        }

        // Konvertiere das Instant in ein ZonedDateTime mit der angegebenen Zeitzone
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of(zoneId));
        return format(zonedDateTime, format);
    }
}
