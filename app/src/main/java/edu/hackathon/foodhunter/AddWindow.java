package edu.hackathon.foodhunter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * Created by kid on 8/9/16.
 */
public class AddWindow extends Activity {

    protected Button huntButton;
    protected Button cancelButton;

    protected TextView foodText;
    protected TextView locationText;
    protected TextView dateText;
    protected TextView timeText;
    protected TextView eventText;

    protected Event createdEvent;

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        //get the database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        createdEvent = null;
        initLayout();
        initListener();
    }


    public void initLayout() {
        setContentView(R.layout.fragment_layout);
        this.huntButton = (Button) findViewById(R.id.hunt_button);
        this.cancelButton = (Button) findViewById(R.id.cancel_button);
        //text
        this.foodText = (TextView) findViewById(R.id.food_edit_text);
        this.eventText = (TextView) findViewById(R.id.event_edit_text);
        this.dateText = (TextView) findViewById(R.id.date_edit_text);
        this.timeText = (TextView) findViewById(R.id.time_edit_text);
        this.locationText = (TextView) findViewById(R.id.location_edit_text);
    }

    public void initListener() {
        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEvent()) {
                    //the event is not valid. print something
                }
                else {
                    sendEvent(createdEvent);
                    //display something
                    finish();
                }
            }
        });

        //just close the fragment
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sendEvent(Event event) {
        //get the field from the list
        //mDatabase.child("")

        //send it to the server
    }

    public boolean validateEvent() {
        //The field is not filled
        //TODO: Validate is not correct
        if (this.timeText.getText().equals("") || this.foodText.getText().equals("")
                ||this.dateText.getText().equals("") || this.locationText.equals("")) {
            return false;
        }
        //getText().toString()
        /**
        createdEvent = new Event(this.eventText.getText(), this.locationText.getText(),
                this.foodText.getText(), this.dateText.getText(),
                this.timeText.getText());
        */
        return true;
    }
}
