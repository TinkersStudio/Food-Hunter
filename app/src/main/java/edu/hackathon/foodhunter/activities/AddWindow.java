package edu.hackathon.foodhunter.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.hackathon.foodhunter.model.Event;
import edu.hackathon.foodhunter.R;
import es.dmoral.toasty.Toasty;

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

    /**Reference of the data firebase*/
    private DatabaseReference mDatabase;

    /*Debugging TAG. Use in log*/
    private static final String TAG = Activity.class.getName();

    /**String value for the event*/
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
        //no event at the moment
        createdEvent = null;
        //Inflate all of the layout
        initLayout();
        //add the listener
        initListener();
    }

    /**
     * Inflate the layout and initialize all of the component
     */
    public void initLayout() {
        setContentView(R.layout.fragment_layout);
        //button
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
        huntButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /** Get the value to the variable*/

                AddWindow.this.e_date = AddWindow.this.dateText.getText().toString().trim();
                AddWindow.this.e_event = AddWindow.this.eventText.getText().toString().trim();
                AddWindow.this.e_food = AddWindow.this.foodText.getText().toString().trim();
                AddWindow.this.e_time = AddWindow.this.timeText.getText().toString().trim();
                AddWindow.this.e_location = AddWindow.this.locationText.getText().toString().trim();

                if (!validateTimeText()) {
                    Toasty.error(getApplicationContext(), "Missing time", Toast.LENGTH_SHORT, true).show();
                }
                else if(!validateLocationText()) {
                    Toasty.error(getApplicationContext(), "Missing location", Toast.LENGTH_SHORT, true).show();
                }
                else if(!validateDateText()) {
                    Toasty.error(getApplicationContext(),  "Missing date or wrong format. " +
                            "Correct format: MM/dd/yyyy", Toast.LENGTH_SHORT, true).show();
                }
                else if(!validateFoodText()) {
                    Toasty.error(getApplicationContext(), "Missing food", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    //TODO Update a SnackBar to display completion
                    createdEvent = new Event(e_event, e_location, e_food, e_date, e_time);
                    UploadTask uploadTask = new UploadTask();
                    //event should be created at this point already
                    try {
                        //run a parallel task to upload
                        String status = uploadTask.execute(createdEvent).get(2000, TimeUnit.MICROSECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    AddWindow.super.onBackPressed();
                }
            }
        });

        //just close the fragment
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWindow.super.onBackPressed();
            }
        });


        foodText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        if (foodText.getText() != null)
                        {
                            e_food = foodText.getText().toString().trim();

                        }
						/* Hide keyboard when done */
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(foodText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });

        locationText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        if (locationText.getText() != null)
                        {
                            e_location = locationText.getText().toString().trim();

                        }
						/* Hide keyboard when done */
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(locationText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });

        eventText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        if (eventText.getText() != null)
                        {
                            e_event = eventText.getText().toString().trim();

                        }
						/* Hide keyboard when done */
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(eventText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });

        dateText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        if (dateText.getText() != null)
                        {
                            e_date = dateText.getText().toString().trim();

                        }
						/* Hide keyboard when done */
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(dateText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });

        timeText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                        if (timeText.getText() != null)
                        {
                            e_time = timeText.getText().toString().trim();
                        }
						/* Hide keyboard when done */
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(timeText.getWindowToken(), 0);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    protected boolean validateFoodText() {
        if (this.e_food.isEmpty()) {
            return false;
        }
        return true;
    }

    protected boolean validateLocationText() {
        if(this.e_location.isEmpty()) {
            return false;
        }
        return true;
    }

    protected boolean validateDateText() {
        if(this.e_date.isEmpty()) {
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
        if(this.e_time.isEmpty()) {
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
                String key = AddWindow.this.mDatabase.child("evTextViewents").push().getKey();
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
            Log.d(AddWindow.this.TAG, "Uploading the data");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPreExecute();
            Log.d(AddWindow.this.TAG, "Complete updating the database");
        }
    }
}
