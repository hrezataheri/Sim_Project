package com.company.SimEntities;

import com.company.FundamentalManager;

public class SimEntity {

    public double systemTime;
    public FundamentalManager fundamentalManager;

    public SimEntity(FundamentalManager fundamentalManager){
        this.fundamentalManager = fundamentalManager;
        this.fundamentalManager.simEntities.add(this);
    }

    public SimEntity(){

    }


    public void setTime(){
        this.systemTime = fundamentalManager.time;
    }

}
