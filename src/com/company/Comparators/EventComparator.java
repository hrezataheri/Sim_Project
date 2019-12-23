package com.company.Comparators;

import com.company.Event;

import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
       if (o1.invocationTime > o2.invocationTime)
           return 1;
       return -1;
    }
}
