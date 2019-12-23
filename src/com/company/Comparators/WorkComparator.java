package com.company.Comparators;

import com.company.SimEntities.Work;
import com.company.Miscellaneous.Work_Type;

import java.util.Comparator;

public class WorkComparator implements Comparator<Work> {
    @Override
    public int compare(Work o1, Work o2) {
        if (o1.workType == o2.workType)
            if (o1.number > o2.number)
                return 1;
            else
                return -1;
        else if (o1.workType == Work_Type.TYPE_ONE)
            return 1;
        else return -1;

    }
}
