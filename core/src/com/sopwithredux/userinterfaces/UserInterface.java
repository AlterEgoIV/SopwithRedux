package com.sopwithredux.userinterfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sopwithredux.Observer;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 31/03/2017.
 */
public abstract class UserInterface implements Observer
{
    protected List<UIObject> uiObjects;

    public UserInterface()
    {
        uiObjects = new ArrayList<UIObject>();
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
