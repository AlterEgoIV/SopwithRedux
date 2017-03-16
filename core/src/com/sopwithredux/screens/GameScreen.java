package com.sopwithredux.screens;

import com.badlogic.gdx.Screen;
import com.sopwithredux.SopwithRedux;

/**
 * Created by Carl on 16/03/2017.
 */
public abstract class GameScreen implements Screen
{
    protected SopwithRedux game;

    protected GameScreen(SopwithRedux game)
    {
        this.game = game;
    }
}
