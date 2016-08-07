package edu.hackathon.foodhunter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kid on 8/6/16.
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

    private Event event;
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

    public EventViewHolder(View itemView) {
        /**call to super*/
        super(itemView);

        /*Initialize the component*/
        this.e_foodText = (TextView) itemView.findViewById(R.id.food_text_view);
        this.e_dayText = (TextView) itemView.findViewById(R.id.day_text_view);
        this.e_locationText = (TextView) itemView.findViewById(R.id.location_text_view);
        this.e_timeText = (TextView) itemView.findViewById(R.id.time_text_view);
        this.e_eventText = (TextView) itemView.findViewById(R.id.event_text_view);
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
