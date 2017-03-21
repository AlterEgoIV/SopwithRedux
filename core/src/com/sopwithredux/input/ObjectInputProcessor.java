package com.sopwithredux.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 21/03/2017.
 */
public class ObjectInputProcessor implements InputProcessor
{
    private List<InputHandler> inputHandlers;

    public ObjectInputProcessor()
    {
        inputHandlers = new ArrayList<InputHandler>();
    }

    public void add(InputHandler inputHandler)
    {
        if(inputHandlers.contains(inputHandler))
        {
            throw new AssertionError("ObjectInputProcessor already contains InputHandler object");
        }

        inputHandlers.add(inputHandler);
    }

    public void remove(InputHandler inputHandler)
    {
        if(!inputHandlers.contains(inputHandler))
        {
            throw new AssertionError("Attempting to remove non existent InputHandler object");
        }

        inputHandlers.remove(inputHandler);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        for(InputHandler inputHandler : inputHandlers)
        {
            inputHandler.handleKeyPress(keycode);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        for(InputHandler inputHandler : inputHandlers)
        {
            inputHandler.handleKeyRelease(keycode);
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
