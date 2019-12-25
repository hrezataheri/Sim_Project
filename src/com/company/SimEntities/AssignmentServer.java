package com.company.SimEntities;

import com.company.Event;
import com.company.FundamentalManager;
import com.company.Comparators.ServerComparator;
import com.company.Comparators.WorkComparator;
import com.company.Miscellaneous.StatisticalValue;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignmentServer extends SimEntity {

    public PriorityQueue<Work> queue;

    public double serviceRate;
    public double workArrivalRate;
    public double workDeadlineExpectedValue;

    public PriorityQueue<Server> servers;
    public AtomicInteger atomicInteger;



    public AssignmentServer( double workArrivalRate, double workDeadlineExpectedValue,double serviceRate, String[] descriptionsList, FundamentalManager fundamentalManager) {
        super(fundamentalManager);

        this.workArrivalRate = workArrivalRate;
        this.serviceRate = serviceRate;
        this.workDeadlineExpectedValue = workDeadlineExpectedValue;
        queue = new PriorityQueue<>(new WorkComparator());
        atomicInteger = new AtomicInteger();

        servers = new PriorityQueue<>(new ServerComparator());

        for (int i = 0; i < descriptionsList.length; i++) {
            servers.add(new Server(descriptionsList[i], this.fundamentalManager));
        }


        createEvent_InsertWorkIntoQueue(0);
    }

    public void insertWorkIntoQueue() {
        Work w = new Work(workDeadlineExpectedValue, atomicInteger.getAndIncrement(),this ,fundamentalManager);
        if(queue.isEmpty()){
            createEvent_SendWorkFromQueueToServers(w);
        }

        queue.add(w);
        w.timeEnteredAssignmentServerQueue = systemTime;
        createEvent_InsertWorkIntoQueue();


    }




    public void sendWorkFromQueueToServers(Work w) {

        if (w != null && w.isStillInSystem) {

            queue.remove(w);

            refreshServerList();
            selectServerWithMinimumQueueLength().insertWorkIntoQueue(w);;

            Work q = queue.peek();
            createEvent_SendWorkFromQueueToServers(q);

        }

    }

    public int generatePoissonVariable() {
        double c = 1.0/serviceRate;
        int x = 0;
        double sum = 0;
        Random r = new Random();

        while(sum < c){
            sum += -Math.log(r.nextDouble());
            x++;
        }

        return x-1;
    }

    public double generateExponentialVariable(){
        Random r = new Random();
        return -(1.0/workArrivalRate)*Math.log(r.nextDouble());
    }

//    public double generateExponentialVariable(Work work){
//        System.out.println(work.workType);
//        return 0;
//    }

    public Server selectServerWithMinimumQueueLength(){

        Server serverWithMinimumQueueLength = servers.peek();
        ArrayList<Server> similarServers = new ArrayList<>();
        Iterator<Server> serverIterator = servers.iterator();
        for (int i = 0; i < servers.size(); i++) {
            Server q = serverIterator.next();
            if (q.queue.size() == serverWithMinimumQueueLength.queue.size())
                similarServers.add(q);
        }

        Random r = new Random();
        int randomInteger = r.nextInt(similarServers.size());
        return similarServers.get(randomInteger);

    }

    public void refreshServerList(){
        servers.add(new Server());
        servers.poll();
    }

    public void createEvent_SendWorkFromQueueToServers(Work work){
        if (work != null) {
            try {

                double invocationTime = systemTime + (double) generatePoissonVariable();

                fundamentalManager.registerEvent(new Event(invocationTime,
                        this.getClass().getMethod("sendWorkFromQueueToServers",Work.class), this, work));

                work.timeAssignmentServiceBegin = systemTime;

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }


    public void createEvent_InsertWorkIntoQueue(){
        try {

            double invocationTime = systemTime + generateExponentialVariable();

            fundamentalManager.registerEvent(new Event(invocationTime,
                    this.getClass().getMethod("insertWorkIntoQueue"), this));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void createEvent_InsertWorkIntoQueue(double time){
        try {

//            double invocationTime = systemTime + generateExponentialVariable();

            fundamentalManager.registerEvent(new Event(time,
                    this.getClass().getMethod("insertWorkIntoQueue"), this));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void tick() {

    }
}
