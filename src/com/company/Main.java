package com.company;

import com.company.SimEntities.AssignmentServer;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.io.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

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




    }
}
