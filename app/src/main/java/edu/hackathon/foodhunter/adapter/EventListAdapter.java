package edu.hackathon.foodhunter.adapter;

/**
 * Created by kid on 8/7/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import edu.hackathon.foodhunter.R;
import edu.hackathon.foodhunter.model.Event;

/**Bind EventViewHolder and the data*/
public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder>{

    /**The data to display*/
    private List<Event> eventList;


    /**
     * Parameterized constructor that takes in the application Context in which
     * it is being used and the Collection of Joke objects to which it is bound.
     * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
     *
     * @param eventList
     *            The Collection of Event objects to which this JokeListAdapter
     *            is bound.
     */
    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }


    public int getCount() {
        return this.eventList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public Object getItem(int position) {
        return this.eventList.get(position);
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary  calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        // set the view's size, margins, paddings and layout parameters
        EventViewHolder viewHolder = new EventViewHolder(v);
        return viewHolder;
    }

    /**Get the type of layout for the recycle view*/
    @Override
    public int getItemViewType(int position) {
        return R.layout.event_recyle_view;
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.setEvent(this.eventList.get(position));
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.getCount();
    }
}
