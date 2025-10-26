package com.vkenex.trainsmanagment.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class Train {

    private long id;
    private int number;

    private long departureStationId;
    private long arrivalStationId;

    private LocalDateTime timeOfDeparture;
    private LocalDateTime timeOfArrival;


    public Train() {}

    public Train(long id, int number, long departureStationId, long arrivalStationId,
                 LocalDateTime timeOfDeparture,
                 LocalDateTime timeOfArrival) {
        this.id = id;
        this.number = number;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
    }

    public String getTravelTime() {
        return getFormattedTravelTime(Duration.between(timeOfDeparture, timeOfArrival));
    }

    private String getFormattedTravelTime(Duration duration) {

        if (duration == null || duration.isZero() || duration.isNegative()) {
            return "N/A";
        }

        StringBuilder sb = new StringBuilder();

        long days = duration.toDays();
        if (days > 0) {
            sb.append(days).append("д ");
        }

        long hours = duration.toHours();
        if (hours > 0) {
            sb.append(hours).append("ч ");
        }

        long minutes = duration.toMinutes();
        if (minutes > 0) {
            sb.append(minutes).append("м");
        }

        if (sb.length() == 0) {
            return "менее 1м";
        }

        return sb.toString().trim();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(Long departureStationId) {
        this.departureStationId = departureStationId;
    }

    public Long getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(Long arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public LocalDateTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(LocalDateTime timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(LocalDateTime timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }
}
