package com.company;

import com.company.Comparators.EventComparator;
import com.company.Miscellaneous.StatisticalValue;
import com.company.Miscellaneous.StatisticalValue_ServerQueueLength;
import com.company.Miscellaneous.Work_Type;
import com.company.SimEntities.AssignmentServer;
import com.company.SimEntities.Server;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class FundamentalManager {

//    public double timeStep = 0;
    public double time = 0;
    public ArrayList<SimEntity> simEntities;
    public PriorityQueue<Event> events;
    public ArrayList<Work> worksThatHaveLeftTheSystem;

    public AssignmentServer assignmentServer;

    StatisticalValue timeSpentInSystem = new StatisticalValue();
    StatisticalValue timeSpentInSystem_TYPE_ONE = new StatisticalValue();
    StatisticalValue timeSpentInSystem_TYPE_TWO = new StatisticalValue();

    StatisticalValue timeSpentInQueue = new StatisticalValue();
    StatisticalValue timeSpentInQueue_TYPE_ONE = new StatisticalValue();
    StatisticalValue timeSpentInQueue_TYPE_TWO = new StatisticalValue();

    StatisticalValue numberOfExpiredWorks = new StatisticalValue();
    StatisticalValue numberOfExpiredWorks_TYPE_ONE = new StatisticalValue();
    StatisticalValue numberOfExpiredWorks_TYPE_TWO = new StatisticalValue();

    StatisticalValue_ServerQueueLength[] serversQueueLength;


    public FundamentalManager(){
        simEntities = new ArrayList<>();
        events = new PriorityQueue<>(new EventComparator());
        worksThatHaveLeftTheSystem = new ArrayList<>();
    }

    public void increaseTime(){
        Event e = events.poll();
        if (e != null){
            time = e.invocationTime;
//            System.out.println(time);
            simEntities.forEach((k) -> k.setTime());
            e.invokeEvent();
        }

    }

    public void workLeftTheSystem(Work work){
        worksThatHaveLeftTheSystem.add(work);

        double timeSpentInSystem = time - work.timeEnteredAssignmentServerQueue;


    }

    public void registerEvent(Event event){
        events.add(event);
    }

    public void setAssignmentServer(AssignmentServer assignmentServer){
        this.assignmentServer = assignmentServer;
        serversQueueLength = new StatisticalValue_ServerQueueLength[assignmentServer.servers.size()];
        Iterator<Server> serverIterator = assignmentServer.servers.iterator();
        for (int i = 0; i < serversQueueLength.length; i++) {
            Server s = serverIterator.next();
            serversQueueLength[i].setServer(s);
        }
    }
}
