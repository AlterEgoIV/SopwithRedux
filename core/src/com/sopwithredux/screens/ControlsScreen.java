package com.sopwithredux.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.Event;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.gameobjects.uiobjects.Button;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;

/**
 * Created by Carl on 04/04/2017.
 */
public class ControlsScreen extends GameScreen
{
    public ControlsScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game);

        background = assetManager.get("controls.png");
        createUserInterface(assetManager);

        for(UIObject uiObject : uiObjects)
        {
            uiObject.addObserver(this);
        }
    }

    @Override
    public void show()
    {

    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    protected void createUserInterface(AssetManager assetManager)
    {
        uiObjects.add(new Button(assetManager.get("startbutton.png", Texture.class),
          assetManager.get("startselect.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 20),
          new Vector2(480, 108),
          0.0, 0.0, false, false, Event.GOTO_MAIN_MENU_SCREEN));
    }
}
