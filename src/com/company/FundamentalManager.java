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
    StatisticalValue assignmentServerQueueLength = new StatisticalValue();


    public FundamentalManager(double workArrivalRate, double deadLineExpectedValue, double assignmentRate, String[] eachServerDescription){
        simEntities = new ArrayList<>();
        events = new PriorityQueue<>(new EventComparator());
        worksThatHaveLeftTheSystem = new ArrayList<>();
        assignmentServer = new AssignmentServer(workArrivalRate,deadLineExpectedValue, assignmentRate,eachServerDescription,this);



        serversQueueLength = new StatisticalValue_ServerQueueLength[assignmentServer.servers.size()];
        Iterator<Server> serverIterator = assignmentServer.servers.iterator();
        for (int i = 0; i < serversQueueLength.length; i++) {
            serversQueueLength[i] = new StatisticalValue_ServerQueueLength(serverIterator.next());
        }
    }

    public void increaseTime(){
        Event e = events.poll();
        if (e != null){
            time = e.invocationTime;
//            System.out.println(timeSpentInQueue_TYPE_ONE.valueAccuracy);
            simEntities.forEach((k) -> k.setTime());
            e.invokeEvent();
        }

    }

    public void workLeftTheSystem(Work work){

        worksThatHaveLeftTheSystem.add(work);

        if (worksThatHaveLeftTheSystem.size() < 5000)
            return;

        double timeSpentInSystem = time - work.timeEnteredAssignmentServerQueue;
        this.timeSpentInSystem.update(timeSpentInSystem);
        if (work.workType == Work_Type.TYPE_ONE)
            this.timeSpentInSystem_TYPE_ONE.update(timeSpentInSystem);
        else
            this.timeSpentInSystem_TYPE_TWO.update(timeSpentInSystem);



        double timeSpentInQueue = (work.timeServiceBegin > 0) ? (work.timeServiceBegin - work.timeEnteredAssignmentServerQueue)
                : (time - work.timeEnteredAssignmentServerQueue);
        this.timeSpentInQueue.update(timeSpentInQueue);
        if(work.workType == Work_Type.TYPE_ONE)
            this.timeSpentInQueue_TYPE_ONE.update(timeSpentInQueue);
        else
            this.timeSpentInQueue_TYPE_TWO.update(timeSpentInQueue);


        if (!work.hasFinishedWork) {
            numberOfExpiredWorks.update(1);
            if(work.workType == Work_Type.TYPE_ONE)
                numberOfExpiredWorks_TYPE_ONE.update(1);
            else
                numberOfExpiredWorks_TYPE_TWO.update(1);
        }

        for (int i = 0; i < serversQueueLength.length; i++) {
            serversQueueLength[i].update(-1);
        }

        assignmentServerQueueLength.update(assignmentServer.queue.size());



    }

    public void registerEvent(Event event){
        events.add(event);
    }

    public int checkAccuracyOfAllVariables(){
        if(!timeSpentInSystem.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInSystem.valueAccuracy);
            return -1;}
        if(!timeSpentInSystem_TYPE_ONE.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInQueue_TYPE_ONE.valueAccuracy);
            return -1;}
        if(!timeSpentInSystem_TYPE_TWO.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInSystem_TYPE_TWO.valueAccuracy);
            return -1;}
        if(!timeSpentInQueue.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInQueue.valueAccuracy);
            return -1;}
        if(!timeSpentInQueue_TYPE_ONE.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInQueue_TYPE_ONE.valueAccuracy);
            return -1;}
        if(!timeSpentInQueue_TYPE_TWO.hasMetDesiredAccuracy)
            {
                //system.out.println(timeSpentInQueue_TYPE_TWO.valueAccuracy);
            return -1;}
        for (int i = 0; i < serversQueueLength.length; i++) {
            if (!serversQueueLength[i].hasMetDesiredAccuracy)
                {
                    //system.out.println(serversQueueLength[i].valueAccuracy);
                return -1;}
        }

        if(!assignmentServerQueueLength.hasMetDesiredAccuracy)
            {
                //system.out.println(assignmentServerQueueLength.valueAccuracy);
            return -1;}

        return worksThatHaveLeftTheSystem.size();

    }

}
