package edu.hackathon.foodhunter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Main activity class
 */
public class EventList extends AppCompatActivity {


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

    /*Debugging TAG. Use in log*/
    private static final String TAG = Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*The origin list*/
        this.eventList = new ArrayList<>();
        this.sortedList = new ArrayList<>();

        /*Setup the adapter view*/
        eventAdapter = new EventListAdapter(this.eventList);
        eventLayout.setAdapter(eventAdapter);

        //TODO: Implement some dummy test in resource
        ArrayList<Event> downloadedEvent = this.getEventList();
        for (Event e:downloadedEvent) {
            //adding the event and notify the changes
            this.addEvent(e);
        }

        //TODO: Implement call back so when the user pull down the list is reloaded
    }

    //TODO: Implement onSaveInstanceState and onRestoreInstanceState

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
     *
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
                //TODO: Implement share
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
     * This will download the list of the event from the server
     * @return
     */
    protected ArrayList<Event> getEventList() {
        //Retrieve the list of event from Firebase server
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
     * Sort the list of the event to display to the screen
     */
    protected void sort() {
        //TODO: Sort the list of events
        //SortedSet set = Collections.synchronizedSortedSet((SortedSet) eventList);
        Collections.sort(sortedList);
        eventAdapter.notifyDataSetChanged();
    }

    /**
     * Update the event to the server
     */
    protected void updateEvent () {
        //Open the popup fragment on top of the old one
    }

    /**
     * Load the display of the main screen
     */
    protected void loadMainScreen () {
        //TODO: Need to implement
        sortedList = getEventList();
    }
}
