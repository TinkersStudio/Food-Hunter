package edu.hackathon.foodhunter.tool;

import java.util.ArrayList;

import edu.hackathon.foodhunter.Event;

/**
 * Created by kid on 8/8/16.
 * This class is used for several debugging tool. It is not important
 * and not included in the final product
 */
public class Dummy {
    public Dummy() {
        //null
    }

    public static ArrayList<Event> dummyList() {
        ArrayList<Event> listOfItem = new ArrayList<Event>();
        Event event1 = new Event("Club", "R11", "Pizza", "07/05/2016","Noon");
        Event event2 = new Event("Club", "R11", "Burrito", "07/06/2016","Noon");
        Event event3 = new Event("Club", "R11", "Pizza", "07/09/2016","Noon");
        listOfItem.add(event1);
        listOfItem.add(event2);
        listOfItem.add(event3);
        return listOfItem;
    }
}
