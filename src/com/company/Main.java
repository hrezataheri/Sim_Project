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
            inputReader = new BufferedReader(new FileReader("SystemDescription.txt"));
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

            inputReader.close();

            FundamentalManager fundamentalManager = new FundamentalManager(workArrivalRate,deadLineExpectedValue, assignmentRate,eachServerDescription);


            int numberOfWorksRequired = -1;
            while ((fundamentalManager.worksThatHaveLeftTheSystem.size() < 50000000) && (numberOfWorksRequired < 0)) {
                numberOfWorksRequired = fundamentalManager.checkAccuracyOfAllVariables();
                fundamentalManager.increaseTime();
//            System.out.println(fundamentalManager.time);
            }

            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("Results.txt"));
            outputWriter.write("Average Time Spent In The System :" + fundamentalManager.timeSpentInSystem.valueMean);
            outputWriter.newLine();
            outputWriter.write("Average Time Spent In The System _ Type One :" + fundamentalManager.timeSpentInSystem_TYPE_ONE.valueMean);
            outputWriter.newLine();
            outputWriter.write("Average Time Spent In The System _ Type Two :" + fundamentalManager.timeSpentInSystem_TYPE_TWO.valueMean);
            outputWriter.newLine();
            outputWriter.write("Average Time Spent In The Queue :" + fundamentalManager.timeSpentInQueue.valueMean);
            outputWriter.newLine();
            outputWriter.write("Average Time Spent In The Queue _ Type One :" + fundamentalManager.timeSpentInQueue_TYPE_ONE.valueMean);
            outputWriter.newLine();
            outputWriter.write("Average Time Spent In The Queue _ Type Two :" + fundamentalManager.timeSpentInQueue_TYPE_TWO.valueMean);
            outputWriter.newLine();
            outputWriter.write("Total Number Of Works That Left The System :" + fundamentalManager.worksThatHaveLeftTheSystem.size());
            outputWriter.newLine();
            outputWriter.write("Total Number Of Works That Entered The System :" + fundamentalManager.assignmentServer.atomicInteger.get());
            outputWriter.newLine();
            outputWriter.write("Number Of Expired Works :" + fundamentalManager.numberOfExpiredWorks.valueSum);
            outputWriter.newLine();
            outputWriter.write("Number Of Expired Works _ Type One :" + fundamentalManager.numberOfExpiredWorks_TYPE_ONE.valueSum);
            outputWriter.newLine();
            outputWriter.write("Number Of Expired Works _ Type Two :" + fundamentalManager.numberOfExpiredWorks_TYPE_TWO.valueSum);
            outputWriter.newLine();
            for (int i = 0; i < fundamentalManager.serversQueueLength.length; i++) {
                outputWriter.write("Average Queue Length For Server " + i + " : " + fundamentalManager.serversQueueLength[i].valueMean);
                outputWriter.newLine();
            }
            outputWriter.write("Average Queue Length For Assignment Server : " + fundamentalManager.assignmentServerQueueLength.valueMean);
            outputWriter.newLine();

            outputWriter.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
