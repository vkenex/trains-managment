package com.vkenex.trainsmanagment.entity.enums;

public enum WagonType {
    COUPE(40),
    SECOND_CLASS(60),
    COMMON(90);

    private final int seatCount;

    WagonType(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getSeatCount() {
        return seatCount;
    }
}