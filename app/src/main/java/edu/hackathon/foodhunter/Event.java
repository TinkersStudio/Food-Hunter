package edu.hackathon.foodhunter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kid on 8/6/16.
 */
public class Event implements Comparable<Event>
{
    /**Type of food*/
    private String food;

    /**Date and time*/
    private String date;
    private String time;

    /**Event*/
    private String event;

    /**This is format and use internal to sort event based on date of the event*/
    private Date dateFormat;

    /**To order the list based on date created*/
    private Date dateCreated;

    /**location of the event*/
    private String location;
    public Event( String event, String location, String food, String date, String time) {
        this.food = food;
        this.location = location;
        this.date = date;
        this.time = time;
        this.event = event;
        if (!setDateFormat(this.date)) {
            this.food = "ERROR";
            this.location = "404";
        }

    }

    protected boolean setDateFormat(String date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dateFormat = dateformat.parse(date);
            return true;
        }
        catch (ParseException e) {
            //the origin of time to push it to the bottom
            this.dateFormat.setTime(0);
            return false;
        }
    }
    public void setFood (String food) {
        this.food = food;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //hell yeah!!
    public String getFood () {
        return this.food;
    }

    public String getEvent() {
        return this.event;
    }

    public String getTime() {
        return this.time;
    }

    public String getDate() {
        return this.date;
    }

    public String getLocation() {
        return this.location;
    }

    @Override
    public boolean equals(Object obj) {
        Event event1 = (Event)obj;
        return (event1.getDate().equals(this.date) && event1.getEvent().equals(this.event)
                && event1.getFood().equals(this.food) && event1.getTime().equals(this.time)&&
                event1.getLocation().equals(this.location));
    }

    @Override
    public int compareTo(Event event) {
        return 0;
    }
}
