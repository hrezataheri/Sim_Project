package com.company.SimEntities;

import com.company.Event;
import com.company.FundamentalManager;
import com.company.Miscellaneous.Work_Type;

import java.util.Random;

public class Work extends SimEntity{

    public Work_Type workType;

    public double deadline;
    public double timeEnteredAssignmentServerQueue;
    public double timeAssignmentServiceBegin;
    public double timeEnteredServerQueue;
    public double timeServiceBegin;
    public double timeServiceEnd;

    public long number;

    public boolean isStillInSystem;
    public boolean hasFinishedWork;

    public AssignmentServer assignmentServer;
    public Server servicingServer;
    public Core servicingCore;



    public Work(double deadlineExpectedValue, long number,AssignmentServer assignmentServer, FundamentalManager fundamentalManager){

        super(fundamentalManager);

        Random r = new Random();
        this.workType = (r.nextDouble() < 0.1) ? Work_Type.TYPE_ONE : Work_Type.TYPE_TWO;

        this.deadline = fundamentalManager.time - deadlineExpectedValue*Math.log(r.nextDouble());

        this.timeEnteredAssignmentServerQueue = -1;
        this.timeAssignmentServiceBegin = -1;
        this.timeEnteredServerQueue = -1;
        this.timeServiceBegin = -1;
        this.timeServiceEnd = -1;

        this.number = number;
        this.isStillInSystem = true;
        this.hasFinishedWork = false;
        this.assignmentServer = assignmentServer;

        createEvent_LeaveSystemBeforeBeginningService();


    }

    public void leaveSystemBeforeBeginningService(){


        if (servicingCore != null) {
            return;
        }
        else {



            if (servicingServer != null)
                servicingServer.queue.remove(this);
            else {
                assignmentServer.queue.remove(this);
                Work q = assignmentServer.queue.peek();
                assignmentServer.createEvent_SendWorkFromQueueToServers(q);

            }

            this.isStillInSystem = false;
            fundamentalManager.workLeftTheSystem(this);

        }


    }

    public void createEvent_LeaveSystemBeforeBeginningService(){
        try {
            fundamentalManager.registerEvent(new Event(deadline, this.getClass().getMethod("leaveSystemBeforeBeginningService"), this));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
