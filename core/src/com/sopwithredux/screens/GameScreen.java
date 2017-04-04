package com.sopwithredux.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sopwithredux.Event;
import com.sopwithredux.Observer;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.Subject;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 16/03/2017.
 */
public abstract class GameScreen implements Screen, Observer
{
    protected SopwithRedux game;
    protected SpriteBatch batch;
    protected Texture background;
    protected List<UIObject> uiObjects;

    protected GameScreen(SopwithRedux game)
    {
        this.game = game;
        batch = new SpriteBatch();
        uiObjects = new ArrayList<UIObject>();
    }

    @Override
    public void handleEvent(Subject subject, Event event)
    {
        switch(event)
        {
            case GOTO_MAIN_MENU_SCREEN:
            {
                game.setScreen(game.screens.get(ScreenName.MAIN_MENU_SCREEN));
                break;
            }

            case GOTO_PLAY_SCREEN:
            {
                game.setScreen(game.screens.get(ScreenName.PLAY_SCREEN));
                break;
            }

            case GOTO_CONTROL_SCREEN:
            {
                game.setScreen(game.screens.get(ScreenName.CONTROLS_SCREEN));
                break;
            }

            case GOTO_GAME_OVER_SCREEN:
            {
                game.setScreen(game.screens.get(ScreenName.GAME_OVER_SCREEN));
                break;
            }
        }
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        background.dispose();
    }
}
