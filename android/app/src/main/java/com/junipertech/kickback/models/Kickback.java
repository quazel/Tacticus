package com.junipertech.kickback.models;

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

    /*
    public String getTimeRange() {

        String startMinutes = Integer.getTimeRange(this.getStart().getMinuteOfHour());
        String stopMinutes = Integer.getTimeRange(this.getStop().getMinuteOfHour());

        if(startMinutes.equals("0")) {
            startMinutes = "00";
        }
        if(stopMinutes.equals("0")) {
            stopMinutes = "00";
        }

        if (this.getStart().getHourOfDay() < 12 ||
            this.getStart().getHourOfDay() == 24)
        {
            if (this.getStop().getHourOfDay() < 12 ||
                this.getStop().getHourOfDay() == 24)
            {
                return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                        startMinutes + "am" +
                        " - " +
                        this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                        stopMinutes+"am";
            }
            return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    startMinutes + "am" +
                    " - " +
                    this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    stopMinutes+"pm";
        }
        else
        {
            if (this.getStart().getHourOfDay() == 12)
            {
                return "12:" +
                        startMinutes + "pm" +
                        " - " +
                        this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                        stopMinutes + "pm";
            }
            return this.getStart().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    startMinutes + "pm" +
                    " - " +
                    this.getStop().get(DateTimeFieldType.hourOfHalfday()) + ":" +
                    stopMinutes+"pm";

        }
    }*/
}
