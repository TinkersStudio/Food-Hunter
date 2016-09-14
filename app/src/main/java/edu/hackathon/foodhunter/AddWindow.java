package edu.hackathon.foodhunter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    /**Button group*/
    protected Button huntButton;
    protected Button cancelButton;

    /**Edit text box*/
    protected EditText foodText;
    protected EditText locationText;
    protected EditText dateText;
    protected EditText timeText;
    protected EditText eventText;

    /**Event that is created*/
    protected Event createdEvent;

    private DatabaseReference mDatabase;

    /*Debugging TAG. Use in log*/
    private static final String TAG = Activity.class.getName();

    protected String e_event;
    protected String e_food;
    protected String e_date;
    protected String e_time;
    protected String e_location;

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
        this.foodText = (EditText)findViewById(R.id.food_edit_text);
        this.eventText = (EditText)findViewById(R.id.event_edit_text);
        this.dateText = (EditText)findViewById(R.id.date_edit_text);
        this.timeText = (EditText)findViewById(R.id.time_edit_text);
        this.locationText = (EditText)findViewById(R.id.location_edit_text);
    }

    /**
     * Init the button listener
     * When the user click on Hunt: Validate the event.
     * If the event is not correct display the error
     */
    public void initListener() {
        //FIXME: Fix the listener and the thread

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Get the value to the variable*/
                AddWindow.this.e_date = AddWindow.this.dateText.getText().toString().trim();
                AddWindow.this.e_event = AddWindow.this.eventText.getText().toString().trim();
                AddWindow.this.e_food = AddWindow.this.foodText.getText().toString().trim();
                AddWindow.this.e_time = AddWindow.this.timeText.getText().toString().trim();
                AddWindow.this.e_location = AddWindow.this.locationText.getText().toString().trim();

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
                    UploadTask uploadTask = new UploadTask();
                    //event should be created at this point already
                    uploadTask.execute(createdEvent);
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

    public boolean validateEvent() {
        if (validateFoodText()
                && validateDateText()
                && validateLocationText()
                && validateTimeText()) {
            createdEvent = new Event(this.e_event,
                    this.e_location,
                    this.e_food,
                    this.e_date,
                    this.e_time);
        }
        return false;
    }

    protected boolean validateFoodText() {
        if (this.e_food.equals("")) {
            return false;
        }
        return true;
    }

    protected boolean validateLocationText() {
        if(this.e_location.equals("")) {
            return false;
        }
        return true;
    }

    protected boolean validateDateText() {
        if(this.e_date.equals("")) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        //Try the format
        try {
            dateFormat.parse(this.e_date.trim());
        }
        catch (ParseException e) {
            return false;
        }

        return true;
    }

    protected boolean validateTimeText() {
        if(this.e_time.equals("")) {
            return false;
        }

        return true;
    }

    /**
     * This class handle uploading joke
     */
    public class UploadTask extends AsyncTask<Event, Void, String> {
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(Event... params) {
            //upload the event in here

            try {
                String key = AddWindow.this.mDatabase.child("events").push().getKey();
                Map<String, Object> eventValues = createdEvent.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/events/"+key, eventValues);


                //send it to the server
                mDatabase.updateChildren(childUpdates);
            }
            catch (Exception e) {
                Log.d(AddWindow.this.TAG, "Error in downloading");
            }
            return "Success";
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AddWindow.this, "Start uploading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPreExecute();
            Toast.makeText(AddWindow.this, "Finish uploading...", Toast.LENGTH_SHORT).show();
        }
    }
}
