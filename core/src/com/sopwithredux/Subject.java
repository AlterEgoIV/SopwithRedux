package com.sopwithredux;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 01/04/2017.
 */
public abstract class Subject
{
    private List<Observer> observers;

    public Subject()
    {
        observers = new ArrayList<Observer>();
    }

    protected void sendEvent(Subject subject, Event event)
    {
        for(Observer observer : observers)
        {
            observer.handleEvent(subject, event);
        }
    }

    public boolean addObserver(Observer newObserver)
    {
        for(Observer observer : observers)
        {
            if(newObserver.equals(observer)) return false;
        }

        return observers.add(newObserver);
    }

    public boolean removeObserver(Observer currentObserver)
    {
        for(Observer observer : observers)
        {
            if(currentObserver.equals(observer)) return observers.remove(observer);
        }

        return false;
    }
}
