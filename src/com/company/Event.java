package com.company;

import com.company.SimEntities.AssignmentServer;
import com.company.SimEntities.SimEntity;
import com.company.SimEntities.Work;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Event {

    public double invocationTime;
    public Method method;
    public SimEntity invoker;
    public SimEntity arg;


    public Event(double invocationTime, Method method, SimEntity invoker,SimEntity arg) {


        this.invocationTime = invocationTime;
        this.method = method;
        this.invoker = invoker;
        this.arg = arg;

//        System.out.println(invocationTime);


    }

    public Event(double invocationTime, Method method, SimEntity invoker) {

        this.invocationTime = invocationTime;
        this.method = method;
        this.invoker = invoker;
        this.arg = null;

//        System.out.println(invocationTime);
    }

    public void invokeEvent()  {

        if (invoker != null){

            try {

                if (arg != null) {
                    method.invoke(invoker, arg);
                }
                else
                    method.invoke(invoker);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
