package com.aaslan.employeemanagement.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    private static Clock clock = Clock.systemDefaultZone();

    private static Clock getClock() {
        return clock;
    }

    public static Date now() {
        return Date.from(LocalDateTime.now(getClock()).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minus(ChronoUnit chronoUnit, int var) {
        Instant instant = LocalDateTime.now(getClock()).atZone(ZoneId.systemDefault()).minus(var, chronoUnit).toInstant();
        return Date.from(instant);
    }

    public static Date plus(ChronoUnit chronoUnit, int var) {
        Instant instant = LocalDateTime.now(getClock()).atZone(ZoneId.systemDefault()).plus(var, chronoUnit).toInstant();
        return Date.from(instant);
    }

    public static long difference(Date start) {
        return now().getTime() - start.getTime();
    }
}
