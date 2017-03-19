package com.sopwithredux.utilities;

import com.sopwithredux.gameobjects.GameObject;

/**
 * Created by Carl on 17/03/2017.
 */
public class Pair
{
    private GameObject first, second;

    public Pair(GameObject first, GameObject second)
    {
        this.first = first;
        this.second = second;
    }

    public GameObject getFirst()
    {
        return first;
    }

    public GameObject getSecond()
    {
        return second;
    }

    public void set(GameObject first, GameObject second)
    {
        this.first = first;
        this.second = second;
    }
}
