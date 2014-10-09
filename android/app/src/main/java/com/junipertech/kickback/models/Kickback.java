package com.junipertech.kickback.models;

import org.joda.time.DateTime;
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

}
