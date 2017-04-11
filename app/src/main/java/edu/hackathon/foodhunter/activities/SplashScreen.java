package edu.hackathon.foodhunter.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import edu.hackathon.foodhunter.R;
import edu.hackathon.foodhunter.activities.MainActivity;

/**
 * Created by kid on 9/18/16.
 */
public class SplashScreen extends Activity {
    //5 secs
    public static int TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for a duration
                    sleep(TIME_OUT);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}

