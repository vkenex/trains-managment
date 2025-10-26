package com.vkenex.trainsmanagment.entity;

import com.vkenex.trainsmanagment.entity.enums.WagonType;

public class Wagon {

    private long id;
    private int Number;
    private WagonType type;
    private int seatCount;

    private Long trainId;

    public Wagon() {
    }

    public Wagon(long id, int Number, WagonType type, int seatCount, Long trainId) {
        this.id = id;
        this.Number = Number;
        this.type = type;
        this.seatCount = seatCount;
        this.trainId = trainId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        this.Number = number;
    }

    public WagonType getType() {
        return type;
    }

    public void setType(WagonType type) {
        this.type = type;
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
