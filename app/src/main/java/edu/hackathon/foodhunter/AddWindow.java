package edu.hackathon.foodhunter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by kid on 8/9/16.
 */
public class AddWindow extends Activity{

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

    /**
     * Init the button listener
     * When the user click on Hunt: Validate the event. If the event is not correct display the error
     *
     */
    public void initListener() {
        //TODO: Fix the listener and te thread
        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        if(!validateEvent()) {
                            if (!validateTimeText()) {
                                Toast.makeText(getApplicationContext(), "Missing time",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(!validateLocationText()) {
                                Toast.makeText(getApplicationContext(), "Missing location",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(!validateDateText()) {
                                Toast.makeText(getApplicationContext(), "Missing date or wrong format. " +
                                                "Correct format: MM/dd/yyyy",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(!validateFoodText()) {
                                Toast.makeText(getApplicationContext(), "Missing food",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Add event",
                                    Toast.LENGTH_SHORT).show();
                            //something
                            //sendEvent(createdEvent);
                            //TODO: Use snack bar to display something

                            //finish();
                        }
                    }
                }).start();
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
        String key = this.mDatabase.child("events").push().getKey();
        Map<String, Object> eventValues = createdEvent.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/"+key, eventValues);


        //send it to the server
        mDatabase.updateChildren(childUpdates);
    }

    public boolean validateEvent() {
        if (validateFoodText()
                && validateDateText()
                && validateLocationText()
                && validateTimeText()) {
            createdEvent = new Event(this.eventText.getText().toString(),
                    this.locationText.getText().toString(),
                    this.foodText.getText().toString(),
                    this.dateText.getText().toString(),
                    this.timeText.getText().toString());
        }
        return false;
    }

    protected boolean validateFoodText() {
        if (this.foodText.getText().toString().trim().equals("")) {
            return false;
        }
        return true;
    }

    protected boolean validateLocationText() {
        if(this.locationText.getText().toString().trim().equals("")) {
            return false;
        }
        return true;
    }

    protected boolean validateDateText() {
        //TODO: Check the format of the date input

        if(this.dateText.getText().toString().trim().equals("")) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        //Try the format
        try {
            dateFormat.parse(this.dateText.getText().toString().trim());
        }
        catch (ParseException e) {
            return false;
        }

        return true;
    }

    protected boolean validateTimeText() {
        if(this.timeText.getText().toString().trim().equals("")) {
            return false;
        }

        return true;
    }

}
