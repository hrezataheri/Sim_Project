package com.company.SimEntities;

import com.company.FundamentalManager;
import com.company.Comparators.WorkComparator;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Server extends SimEntity {

    public int numberOfCores;
    public PriorityQueue<Work> queue;
    public ArrayList<Core> allCores;
    public ArrayList<Core> workingCores;
    public ArrayList<Core> idleCores;





    public Server(String description, FundamentalManager fundamentalManager){
        super(fundamentalManager);

        queue = new PriorityQueue<>(new WorkComparator());
        allCores = new ArrayList<>();
        String[] coreRates = description.split(",");
        numberOfCores = Integer.valueOf(coreRates[0]);

        for (int i = 1 ; i < coreRates.length ; i++){
            Core core = new Core(Double.valueOf(coreRates[i]), this, this.fundamentalManager);
            allCores.add(core);
        }

        workingCores = new ArrayList<>();
        idleCores = new ArrayList<>();

        for (int i = 0 ; i < allCores.size() ; i++){
            idleCores.add(allCores.get(i));
        }

    }

    public Server(){
        queue = new PriorityQueue<>(new WorkComparator());
    }

    public void insertWorkIntoQueue(Work w){
        queue.add(w);
        w.servicingServer = this;
        w.timeEnteredServerQueue = systemTime;

        if (!idleCores.isEmpty()){
            Core c = idleCores.remove(idleCores.size()-1);
            workingCores.add(c);
            c.getWorkFromServer(queue.poll());
        }
    }


}
