package com.company.Comparators;

import com.company.SimEntities.Server;

import java.util.Comparator;

public class ServerComparator implements Comparator<Server> {
    @Override
    public int compare(Server o1, Server o2) {
        if (o1.queue.size() > o2.queue.size())
            return 1;
        else
            return -1;
    }
}
