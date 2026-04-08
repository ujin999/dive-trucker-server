package com.trucker.application.order.util;

public class WaitingTimeData {
    private static final int[] waitingTimes =
            { 0, 930, 885, 810, 795, 780, 750, 720, 690, 735, 900, 1080, 1350, 1560, 1590, 1470, 1470, 1530, 1650, 1740, 1620, 1500, 1110, 1050, 990 };

    public static int getWaitingTime(int time) {
        if (time < 1 || time >= waitingTimes.length) {
            throw new IllegalArgumentException("time은 1~24 사이여야 합니다.");
        }
        return waitingTimes[time];
    }
}
