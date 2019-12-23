package com.company.Miscellaneous;

import com.company.SimEntities.Server;

public class StatisticalValue_ServerQueueLength extends StatisticalValue {

    public Server server;

    @Override
    public void updateSums(double newRecord) {
        valueRecordNumber++;
        valueSum += server.queue.size();
        valueSum_Squared += server.queue.size() * server.queue.size();

    }

    public void setServer(Server server){
        this.server = new Server();
    }
}
