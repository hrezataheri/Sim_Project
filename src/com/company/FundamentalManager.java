package com.company;

import com.company.Comparators.EventComparator;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class FundamentalManager {

    public long numberOfFinishedWorks;
//    public double timeStep = 0;
    public double time = 0;
    public ArrayList<SimEntity> simEntities;
    public PriorityQueue<Event> events;
    public ArrayList<Work> worksThatHaveLeftTheSystem;



    public FundamentalManager(){
        simEntities = new ArrayList<>();
        events = new PriorityQueue<>(new EventComparator());
        worksThatHaveLeftTheSystem = new ArrayList<>();
    }

    public void increaseTime(){
        Event e = events.poll();
        if (e != null){
            time = e.invocationTime;
            simEntities.forEach((k) -> k.setTime());
            e.invokeEvent();
        }

    }

    public void workLeftTheSystem(Work work){
        worksThatHaveLeftTheSystem.add(work);
        numberOfFinishedWorks++;
    }

    public void registerEvent(Event event){
        events.add(event);
    }
}
