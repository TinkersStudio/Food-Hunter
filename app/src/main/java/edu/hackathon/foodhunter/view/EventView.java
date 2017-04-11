package edu.hackathon.foodhunter.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.hackathon.foodhunter.R;
import edu.hackathon.foodhunter.model.Event;

/**
 * Created by kid on 8/6/16.
 */

/** This is used with linear layout. Current version is recycle layout*/
public class EventView extends LinearLayout {

    /**The event*/
    private Event event;

    /**
     * Basic mapping of the block
     * 1    2
     * 3    4
     * 5
     */
    /**1. Type of food for the even*/
    private TextView e_foodText;
    /**2. Day of the event*/
    private TextView e_dayText;
    /**3. Location of the event*/
    private TextView e_locationText;
    /**4. Time of the event*/
    private TextView e_timeText;
    /**5. Event type*/
    private TextView e_eventText ;

    /**
     * Constructor for the recycle view
     * @param context app
     * @param event the event that is created
     */
    public EventView(Context context, Event event) {
        super(context);

        /* rootView */
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.event_recyle_view, this, true);

        /*Initialize the component*/
        this.e_foodText = (TextView) findViewById(R.id.food_text_view);
        this.e_dayText = (TextView) findViewById(R.id.day_text_view);
        this.e_locationText = (TextView) findViewById(R.id.location_text_view);
        this.e_timeText = (TextView) findViewById(R.id.time_text_view);
        this.e_eventText = (TextView) findViewById(R.id.event_text_view);

        this.setEvent(event);
        requestLayout();
    }

    /**
     * Mutator to change the event diplay
     * @param event
     */
    public void  setEvent(Event event) {
        this.event = event;

        this.e_foodText.setText(event.getFood());
        this.e_dayText.setText(event.getDate());
        this.e_locationText.setText(event.getLocation());
        this.e_timeText.setText(event.getTime());
        this.e_eventText.setText(event.getEvent());
    }
}
