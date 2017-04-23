package com.sopwithredux.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.Event;
import com.sopwithredux.Observer;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.Subject;
import com.sopwithredux.gameobjects.uiobjects.Button;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 16/03/2017.
 */
public class MainMenuScreen extends GameScreen
{
    public MainMenuScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game);

        background = assetManager.get("startmenu.png");
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
    protected void createUserInterface(AssetManager assetManager)
    {
        float yOffset = (Gdx.graphics.getHeight() / 20) * 2;
        float x = Gdx.graphics.getWidth() / 2, y = Gdx.graphics.getHeight() / 2;

        Button button = new Button(assetManager.get("startbutton.png", Texture.class),
          assetManager.get("startselect.png", Texture.class),
          new Vector2(x, y),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 20),
          new Vector2(480, 108),
          0.0, 0.0, false, false, Event.GOTO_PLAY_SCREEN);

        //button.init();
        uiObjects.add(button);

        y = (Gdx.graphics.getHeight() / 2) - yOffset;

        button = new Button(assetManager.get("controlbutton.png", Texture.class),
          assetManager.get("controlselect.png", Texture.class),
          new Vector2(x, y),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 20),
          new Vector2(480, 108),
          0.0, 0.0, false, false, Event.GOTO_CONTROL_SCREEN);

        //button.init();
        uiObjects.add(button);
    }
}
