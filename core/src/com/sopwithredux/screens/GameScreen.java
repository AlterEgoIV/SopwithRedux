package com.sopwithredux.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sopwithredux.SopwithRedux;

/**
 * Created by Carl on 16/03/2017.
 */
public abstract class GameScreen implements Screen
{
    protected SopwithRedux game;
    protected SpriteBatch batch;
    protected Texture background;

    protected GameScreen(SopwithRedux game)
    {
        this.game = game;
        batch = new SpriteBatch();
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }
}
