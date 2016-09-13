package edu.hackathon.foodhunter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.hackathon.foodhunter.tool.Dummy;

/**
 * Main activity class
 */
public class EventList extends AppCompatActivity implements AddWindowFragment.OnFragmentInteractionListener{


    /**List of event*/
    protected ArrayList<Event> eventList;

    /*Sorted list*/
    protected ArrayList<Event> sortedList;

    /**ViewGroup for maintaining the list of view*/
    protected RecyclerView eventLayout;

    /**Adapter to bind AdapterView to list of events*/
    protected EventListAdapter eventAdapter;

    /**Menu used the app*/
    protected Menu mainMenu;

    /*Database*/
    private DatabaseReference mDatabase;
    DatabaseReference refDatabase;

    protected Event createdEvent;

    /*Debugging TAG. Use in log*/
    private static final String TAG = Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Init layout and listener*/
        this.initLayout();
        this.initListener();

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        refDatabase = mDatabase.child("events");

        // Attach a listener to read the data at our events reference
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();
                //List<Event> events =
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        /*The origin list*/
        this.eventList = new ArrayList<Event>();
        this.sortedList = new ArrayList<Event>();

        /*Setup the adapter view*/
        eventAdapter = new EventListAdapter(this.eventList);
        eventLayout.setAdapter(eventAdapter);

        //TODO: Implement some dummy test in resource
        //ArrayList<Event> downloadedEvent = this.getEventList();
        ArrayList<Event> downloadedEvent = Dummy.dummyList();
        for (Event e:downloadedEvent) {
            //adding the event and notify the changes
            this.addEvent(e);
        }
        //TODO: Implement to pull to update
    }

    @Override
    protected void onStart() {
        super.onStart();
        refDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        // Handle item selection
        //have to use the selection above
        switch (item.getItemId()) {
            case R.id.menu_filter:
                sort();
                //point the list over
                eventList = sortedList;
                break;
            case R.id.top_menu_add:
                updateEvent();
                break;
            case R.id.submenu_share:
                Toast.makeText(this, "The function is not yet completed",
                        Toast.LENGTH_LONG).show();
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
     * This will download the list of the event from the server.
     * The method will parse the input from the server and return the correct list of event
     * @return ArrayList</Event> the list of the event from the firebase server
     */
    protected ArrayList<Event> getEventList() {
        //TODO:Retrieve the list of event from Firebase server
        return new ArrayList<Event>();
    }

    /**
     * Add the event to the list and notify the adapter
     * @param event
     */
    protected void addEvent(Event event) {
        this.sortedList.add(event);
        this.eventList.add(event);
        this.eventAdapter.notifyDataSetChanged();
    }

    /**
     * Sort the list of the event in the sortList
     */
    protected void sort() {
        //should sort main list
        Collections.sort(sortedList);
    }

    /**
     * When the user click "Add"
     * Update the event to the serv
     * er.
     */
    protected void updateEvent () {
        //TODO:Open the popup fragment on top of the old one
        Intent addWindow = new Intent(this.getBaseContext(), AddWindow.class);
        startActivity(addWindow);
    }

    /**
     * Load the display of the main screen
     * This is called when the user pull to load the screen
     */
    protected void loadMainScreen () {
        eventList = getEventList();
        //data will be changed so need to notify adapter
        eventAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddWindowListener(String event) {

    }
}
