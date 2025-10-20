package com.vkenex.trainsmanagment.entity;

import com.vkenex.trainsmanagment.entity.enums.WagonType;

public class Wagon {

    private Long id;
    private int wagonNumber;
    private WagonType wagonType;
    private int seatCount;

    private Long trainId;

    public Wagon() {
    }

    public Wagon(Long id, int wagonNumber, WagonType wagonType, int seatCount, Long trainId) {
        this.id = id;
        this.wagonNumber = wagonNumber;
        this.wagonType = wagonType;
        this.seatCount = seatCount;
        this.trainId = trainId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(int wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public WagonType getWagonType() {
        return wagonType;
    }

    public void setWagonType(WagonType wagonType) {
        this.wagonType = wagonType;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }
}
