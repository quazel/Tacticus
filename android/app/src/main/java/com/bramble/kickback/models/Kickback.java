package com.bramble.kickback.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Period;

public class Kickback {

    private DateTime start;
    private DateTime stop;
    // temp placeholder variable for actual location
    private String location;

    public Kickback(DateTime start, DateTime stop, String location) {
        this.start = start;
        this.stop = stop;
        this.location = location;
    }

    public Kickback(Kickback kickback) {
        this.start = kickback.start;
        this.stop = kickback.stop;
        this.location = kickback.location;
    }

    public DateTime getStart() {
        return new Kickback(this).start;
    }

    public DateTime getStop () {
        return new Kickback(this).stop;
    }

    public String getLocation() {
        return new Kickback(this).location;
    }

    public boolean overlaps(Kickback kickback) {
        DateTime now = DateTime.now();
        Period period = new Period(now, kickback.getStop());
        return period.getHours() > 1;
    }


    public String getTimeRange() {
        String startMinutes = Integer.toString(this.getStart().getMinuteOfHour());
        String stopMinutes = Integer.toString(this.getStop().getMinuteOfHour());

        if (startMinutes.equals("0")) {
            startMinutes = "00";
        }
        if (stopMinutes.equals("0")) {
            stopMinutes = "00";
        }

        String toReturn = "";
        int startHour = this.getStart().getHourOfDay();
        int stopHour = this.getStop().getHourOfDay();

        int hour = this.getStart().get(DateTimeFieldType.hourOfHalfday());
        if (hour == 0) {
            hour += 1;
        }
        toReturn += hour + ":" + startMinutes;

        if (startHour < 12 || startHour == 24) {
            toReturn+= "am - ";
        }
        else {
            toReturn +=  "pm - ";
        }

        hour = this.getStop().get(DateTimeFieldType.hourOfHalfday());
        if (hour == 0) {
            hour += 1;
        }
        toReturn += hour + ":" + stopMinutes;

        if (stopHour < 12 || startHour == 24) {
            toReturn += "am";
        }
        else {
            toReturn += "pm";
        }

        return toReturn;
    }
    
}
