package edu.hackathon.foodhunter;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    /**location of the event*/
    private String location;

    /**Event*/
    private String event;

    /**This is format and use internal to sort event based on date of the event*/
    protected Date dateFormat;


    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

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

    protected long convertTime(String date){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date dateUnix = dateFormat.parse(date);
            return dateUnix.getTime()/1000;
        }
        catch (ParseException e) {
            //the origin of time to push it to the bottom
            //1/1/1970
            return 0;
        }
    }

    /**
     * Parse user input to set date. If the date is invalid, push the date to the bottom of the list
     * @param date
     * @return
     */
    protected boolean setDateFormat(String date) {
        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.dateFormat = dateformat.parse(date);
            return true;
        }
        catch (ParseException e) {
            //the origin of time to push it to the bottom
            //1/1/1970
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

    /**
     * Compare the method based on date
     * @param event
     * @return
     */
    @Override
    public int compareTo(Event event) {
        return this.dateFormat.compareTo(event.dateFormat);
    }

    /**
     * This method add attribute into a map with key is the string and Object is Events
     * @return
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("food", this.food);
        result.put("location", this.location);
        result.put("date", this.date);
        result.put("time", this.time);
        result.put("event", this.event);

        return result;

    }

}
