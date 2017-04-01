package com.sopwithredux;

/**
 * Created by Carl on 01/04/2017.
 */
public interface Observer
{
    void handleEvent(Subject subject, Event event);
}
