/*
 * Ticket model
 * Developers: k.d.panov@gmail.com
 */
package org.core.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String flightNumber;
    private String link;
    private String originAirport;
    private String destinationAirport;
    private OffsetDateTime departureAt;
    private String airline;
    private String destination;
    private OffsetDateTime returnAt;
    private String origin;
    private int price;
    private int returnTransfers;
    private int duration; // Время в пути
    private int durationTo; // Время в пути до
    private int durationBack; // Время в пути обратно
    private int transfers;
    private long forRequest;
    public Ticket(String flightNumber, String link, String originAirport, String destinationAirport,
                  OffsetDateTime departureAt, String airline, String destination,
                  OffsetDateTime returnAt, String origin, int price,
                  int returnTransfers, int duration, int durationTo,
                  int durationBack, int transfers, long forRequest) {
        this.flightNumber = flightNumber;
        this.link = link;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.departureAt = departureAt;
        this.airline = airline;
        this.destination = destination;
        this.returnAt = returnAt;
        this.origin = origin;
        this.price = price;
        this.returnTransfers = returnTransfers;
        this.duration = duration;
        this.durationTo = durationTo;
        this.durationBack = durationBack;
        this.transfers = transfers;
        this.forRequest = forRequest;
    }

    public long getForRequest() {
        return forRequest;
    }

    public void setForRequest(long forRequest) {
        this.forRequest = forRequest;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public OffsetDateTime getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(OffsetDateTime departureAt) {
        this.departureAt = departureAt;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public OffsetDateTime getReturnAt() {
        return returnAt;
    }

    public void setReturnAt(OffsetDateTime returnAt) {
        this.returnAt = returnAt;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReturnTransfers() {
        return returnTransfers;
    }

    public void setReturnTransfers(int returnTransfers) {
        this.returnTransfers = returnTransfers;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDurationTo() {
        return durationTo;
    }

    public void setDurationTo(int durationTo) {
        this.durationTo = durationTo;
    }

    public int getDurationBack() {
        return durationBack;
    }

    public void setDurationBack(int durationBack) {
        this.durationBack = durationBack;
    }

    public int getTransfers() {
        return transfers;
    }

    public void setTransfers(int transfers) {
        this.transfers = transfers;
    }

    // Метод для обработки массива JSON
    public static List<Ticket> fromJsonArray(JSONArray jsonArray, long forRequest) {
        List<Ticket> tickets = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Ticket ticket = new Ticket(
                    jsonObject.getString("flight_number"),
                    jsonObject.getString("link"),
                    jsonObject.getString("origin_airport"),
                    jsonObject.getString("destination_airport"),
                    OffsetDateTime.parse(jsonObject.getString("departure_at"), formatter),
                    jsonObject.getString("airline"),
                    jsonObject.getString("destination"),
                    OffsetDateTime.parse(jsonObject.getString("return_at"), formatter),
                    jsonObject.getString("origin"),
                    jsonObject.getInt("price"),
                    jsonObject.getInt("return_transfers"),
                    jsonObject.getInt("duration"),
                    jsonObject.getInt("duration_to"),
                    jsonObject.getInt("duration_back"),
                    jsonObject.getInt("transfers"),
                    forRequest
            );
            tickets.add(ticket);
        }
        return tickets;
    }
}
