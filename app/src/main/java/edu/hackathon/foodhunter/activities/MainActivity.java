package edu.hackathon.foodhunter.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.hackathon.foodhunter.model.Event;
import edu.hackathon.foodhunter.adapter.EventListAdapter;
import edu.hackathon.foodhunter.R;
import es.dmoral.toasty.Toasty;

/**
 * Main activity class
 */
public class MainActivity extends AppCompatActivity {
    /**List of event*/
    protected ArrayList<Event> eventList;

    /**ViewGroup for maintaining the list of view*/
    protected RecyclerView eventLayout;

    /**Adapter to bind AdapterView to list of events*/
    protected EventListAdapter eventAdapter;

    /**Menu used the app*/
    protected Menu mainMenu;

    /**Reference to the root of the app's database*/
    DatabaseReference m_rootRef;

    DatabaseReference m_eventRef;

    /*Debugging TAG. Use in log*/
    private static final String TAG = Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Init layout and listener*/
        this.initLayout();
        this.initListener();

        /*The origin list*/
        this.eventList = new ArrayList<Event>();

         /*Setup the adapter view*/
        eventAdapter = new EventListAdapter(this.eventList);
        eventLayout.setAdapter(eventAdapter);

        //Event break at firebase ref
        m_rootRef = FirebaseDatabase.getInstance().getReference();

        //reference to nodes
        m_eventRef = m_rootRef.child("events");
        //event listener

        m_eventRef.orderByChild("date").limitToLast(100).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                MainActivity.this.addEvent(event);
                Log.v(TAG, "Add new value");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                Log.v(TAG, "Modify the value");
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                Log.v(TAG, "Removed event");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //IGNORE for now
            }

            @Override
            public void onCancelled(DatabaseError  firebaseError) {
                Log.v(TAG, "Cancel due to error");
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }
    /**
     * Called onPause()
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        /* Save all UI stuff */
        super.onSaveInstanceState(outState);
        //save the value in here
    }

    /**
     * Is called after onStart()
     *
     * @param savedInstanceState
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        //Can restore the value in here
    }

    /**
     * Initialize all of the layout
     */
    protected void initLayout()
    {
        setContentView(R.layout.main_activity);
        this.eventLayout = (RecyclerView) findViewById(R.id.eventRecyclerViewGroup);
        /**Layout manager*/
        //because rotate is disable => always true
        this.eventLayout.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Initialize all listener
     */
    protected void initListener() {

    }

    /**
     * Create the top menu level
     * Init all of the component of the top menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.mainMenu = menu;
        return true;
    }

    /**
     * Option select (top menu)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //have to use the selection above
        switch (item.getItemId()) {
            case R.id.submenu_refresh:
                Toasty.success(getApplicationContext(), "Updated the list", Toast.LENGTH_SHORT, true).show();
                break;

            case R.id.top_menu_add:
                updateEvent();
                break;
            case R.id.submenu_share:
                Toasty.error(getApplicationContext(), "None support feature", Toast.LENGTH_SHORT, true).show();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        //data will be changed so need to notify adapter
        eventAdapter.notifyDataSetChanged();
        return true;
    }

    /**
     * Add the event to the list and notify the adapter
     * @param event
     */
    protected void addEvent(Event event) {
        this.eventList.add(0,event);
        this.eventAdapter.notifyDataSetChanged();
    }

    /**
     * When the user click "Add"
     * Update the event to the server window is open.
     */
    protected void updateEvent () {
        Intent addWindow = new Intent(this.getBaseContext(), AddWindow.class);
        startActivity(addWindow);
    }
}
