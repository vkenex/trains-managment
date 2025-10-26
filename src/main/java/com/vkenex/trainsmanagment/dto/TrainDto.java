package com.vkenex.trainsmanagment.dto;

import java.time.LocalDateTime;

public class TrainDto {

    private final Long id;
    private final int number;
    private final String departureStationName;
    private final String destinationStationName;
    private final LocalDateTime timeOfDeparture;
    private final LocalDateTime timeOfArrival;
    private final String travelTime;

    public TrainDto(Long id, int number, String departureStationName, String destinationStationName,
                    LocalDateTime timeOfDeparture, LocalDateTime timeOfArrival, String travelTime) {
        this.id = id;
        this.number = number;
        this.departureStationName = departureStationName;
        this.destinationStationName = destinationStationName;
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.travelTime = travelTime;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public LocalDateTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public String getTravelTime() {
        return travelTime;
    }
}