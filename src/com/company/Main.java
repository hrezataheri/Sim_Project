package com.company;

import com.company.SimEntities.AssignmentServer;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
//        String[] s = new String[]{"3,1,1,1","2,4,4"};
//
//        FundamentalManager f = new FundamentalManager();
//        AssignmentServer a = new AssignmentServer(1,1,1,s,f);
//        try {
//            f.registerEvent(new Event(0,a.getClass().getMethod("generateExponentialVariable", Work.class),a,new Work(1,1,a,f)));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        f.increaseTime();
//        f.increaseTime();
//        f.increaseTime();

//        FundamentalManager f = new FundamentalManager(new ArrayList<SimEntity>());
//        PriorityQueue<Server> servers = new PriorityQueue<>(new ServerComparator());
//
//        Server s1 = new Server("3,1,1,1", f);
//        Server s2 = new Server("2,1,1", f);
//        Server s3 = new Server("5,1,1,1,2,3", f);
//        Server s4 = new Server("1,3", f);
//
//        servers.add(s1);
//        servers.add(s2);
//        servers.add(s3);
//        servers.add(s4);
//
////        System.out.println(servers.peek().equals(s4));
//
//        s4.queue.add(new Work(1,2,f));
//        s4.queue.add(new Work(1,2,f));
//        s1.queue.add(new Work(1,2,f));
//        s3.queue.add(new Work(1,2,f));
//
//        servers.add(new Server());
//        servers.poll();
//
////        System.out.println(servers.peek().equals(s2));
//
//        Iterator<Server> si = servers.iterator();
//        for (int i = 0; i < servers.size(); i++) {
//            Server q = si.next();
//            if (q.queue.size() == 3)
//                System.out.println("khar");
//        }



        String[] s = new String[]{"3,1,1,1","2,4,4"};


        FundamentalManager fundamentalManager = new FundamentalManager();
        AssignmentServer assignmentServer = new AssignmentServer(2,2, 2,s,fundamentalManager);


        while (fundamentalManager.numberOfFinishedWorks < 50000000) {
            fundamentalManager.increaseTime();
//            System.out.println(fundamentalManager.time);
        }


    }
}
