package com.sopwithredux.input;

/**
 * Created by Carl on 08/03/2017.
 */
public interface InputHandler
{
    void handleInput();
    void handleKeyPress(int keyCode);
    void handleKeyRelease(int keyCode);
}
