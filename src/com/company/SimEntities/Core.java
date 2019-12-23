package com.company.SimEntities;

import com.company.Event;
import com.company.FundamentalManager;

import java.util.Random;

public class Core extends SimEntity{

    public double serviceRate;
    public Server ownerServer;

    public Work workThatIsGettingService;

    public Core(double serviceRate, Server ownerServer, FundamentalManager fundamentalManager){
        super(fundamentalManager);

        this.serviceRate = serviceRate;
        this.ownerServer = ownerServer;

    }

    public void getWorkFromServer(Work w){

        workThatIsGettingService = w;
        w.servicingCore = this;
        w.timeServiceBegin = systemTime;
        createEvent_FinishWork();

    }

    public void finishWork(){

        workThatIsGettingService.hasFinishedWork = true;
        workThatIsGettingService.timeServiceEnd = systemTime;
        fundamentalManager.workLeftTheSystem(workThatIsGettingService);

        workThatIsGettingService = null;

        Work w = ownerServer.queue.poll();
        
        if(w != null){
            getWorkFromServer(w);
        } else {
            ownerServer.idleCores.add(this);
            ownerServer.workingCores.remove(this);
        }


    }


    public void createEvent_FinishWork(){
        try {

            double invocationTime = systemTime + generateExponentialVariable();

            fundamentalManager.registerEvent(new Event(invocationTime,
                    this.getClass().getMethod("finishWork"),this));

//            System.out.println(invocationTime);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public double generateExponentialVariable(){
        Random r = new Random();
        return -(1/serviceRate)*Math.log(r.nextDouble());
    }

    @Override
    public void tick() {

    }
}
