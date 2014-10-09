package com.junipertech.kickback.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Period;

public class Kickback {

    private DateTime start;
    private DateTime stop;

    public Kickback(DateTime start, DateTime stop) {
        this.start = start;
        this.stop = stop;
    }

    public Kickback(Kickback kickback) {
        this.start = kickback.start;
        this.stop = kickback.stop;
    }

    public DateTime getStart() {
        return new Kickback(this).start;
    }

    public DateTime getStop () {
        return new Kickback(this).stop;
    }

    public boolean overlaps(Kickback kickback) {
        DateTime now = DateTime.now();
        Period period = new Period(now, kickback.getStop());
        return period.getHours() > 1;
    }

    public String toString() {
        if (this.getStart().get(DateTimeFieldType.hourOfDay()) <= 12) {
            if (this.getStop().get(DateTimeFieldType.hourOfDay()) < 12 ||
                    this.getStop().get(DateTimeFieldType.hourOfDay()) == 24) {
                return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                        this.getStart().get(DateTimeFieldType.minuteOfHour()) + "am" +
                        " - " +
                        this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                        this.getStop().get(DateTimeFieldType.minuteOfHour())+"am";
            }
            return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    this.getStart().get(DateTimeFieldType.minuteOfHour()) + "am" +
                    " - " +
                    this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    this.getStop().get(DateTimeFieldType.minuteOfHour())+"pm";
        }
        else {

            return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                   this.getStart().get(DateTimeFieldType.minuteOfHour()) + "pm" +
                   " - " +
                   this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                   this.getStop().get(DateTimeFieldType.minuteOfHour())+"pm";

        }
    }
}
