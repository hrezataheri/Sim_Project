package com.company;

import com.company.SimEntities.AssignmentServer;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.io.*;
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



        try {
            BufferedReader inputReader;
            inputReader = new BufferedReader(new FileReader("C:\\Users\\Reza Taheri\\IdeaProjects\\Sim_Project\\src\\com\\company\\SystemDescription.txt"));
            int serverNumber;
            int workArrivalRate;
            int deadLineExpectedValue;
            int assignmentRate;
            String[] str = inputReader.readLine().split(",");
            serverNumber = Integer.valueOf(str[0]);
            workArrivalRate = Integer.valueOf(str[1]);
            deadLineExpectedValue = Integer.valueOf(str[2]);
            assignmentRate = Integer.valueOf(str[3]);

            String[] eachServerDescription = new String[serverNumber];
            for (int i = 0; i < eachServerDescription.length; i++) {
                eachServerDescription[i] = inputReader.readLine();
//                System.out.println(eachServerDescription[i]);
            }

            FundamentalManager fundamentalManager = new FundamentalManager();
            AssignmentServer assignmentServer = new AssignmentServer(workArrivalRate,deadLineExpectedValue, assignmentRate,eachServerDescription,fundamentalManager);


            while (fundamentalManager.worksThatHaveLeftTheSystem.size() < 50000000) {
                fundamentalManager.increaseTime();
//            System.out.println(fundamentalManager.time);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        String[] s = new String[]{"3,1,1,1","2,4,4"};
//
//



    }
}
