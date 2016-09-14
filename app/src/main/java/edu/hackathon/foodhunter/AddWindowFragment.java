package edu.hackathon.foodhunter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.hackathon.foodhunter.AddWindow;

/**
 * Created by Owner on 9/12/2016.
 * This class is used when in case of needing moving the AddWindow Activity to Fragment type
 * Since I couldn't figure this out, I decided to use Activity approach instead. It is slower
 * but work without any problem.
 * THIS CLASS IS NOT WORKING - REFER TO ADDWINDOW FOR A WORKING VERSION
 */
public class AddWindowFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    protected Button huntButton;
    protected Button cancelButton;

    protected TextView foodText;
    protected TextView locationText;
    protected TextView dateText;
    protected TextView timeText;
    protected TextView eventText;

    public AddWindowFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        /**Init the button and element*/
        this.huntButton = (Button) view.findViewById(R.id.hunt_button);
        this.cancelButton = (Button) view.findViewById(R.id.cancel_button);
        //text
        this.foodText = (TextView) view.findViewById(R.id.food_edit_text);
        this.eventText = (TextView) view.findViewById(R.id.event_edit_text);
        this.dateText = (TextView) view.findViewById(R.id.date_edit_text);
        this.timeText = (TextView) view.findViewById(R.id.time_edit_text);
        this.locationText = (TextView) view.findViewById(R.id.location_edit_text);

        huntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!validateEvent()) {
                    if (!validateTimeText()) {
                        Toast.makeText(getActivity(), "Missing time",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!validateLocationText()) {
                        Toast.makeText(getActivity(), "Missing location",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!validateDateText()) {
                        Toast.makeText(getActivity(), "Missing date or wrong format. " +
                                        "Correct format: MM/dd/yyyy",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!validateFoodText()) {
                        Toast.makeText(getActivity(), "Missing food",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Add event",
                            Toast.LENGTH_SHORT).show();
                    //something
                    //sendEvent(createdEvent);
                }
                //finish();
                getActivity().getFragmentManager().popBackStack();
            }

        });



        //just close the fragment
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void AddWindowListener(String event);
    }

    public boolean validateEvent() {
        if (validateFoodText()
                && validateDateText()
                && validateLocationText()
                && validateTimeText()) {
            /**
            createdEvent = new Event(this.eventText.getText().toString(),
                    this.locationText.getText().toString(),
                    this.foodText.getText().toString(),
                    this.dateText.getText().toString(),
                    this.timeText.getText().toString());
             */
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
