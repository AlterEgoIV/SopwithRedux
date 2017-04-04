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
public class MainMenuScreen extends GameScreen implements Observer
{
    private List<UIObject> uiObjects;

    public MainMenuScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game);

        background = assetManager.get("startmenu.png");
        uiObjects = new ArrayList<UIObject>();

        uiObjects.add(new Button(assetManager.get("startbutton.png", Texture.class),
          assetManager.get("startselect.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 20),
          new Vector2(480, 108),
          0.0, 0.0, false, false, Event.GOTO_PLAY_SCREEN));

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
    public void render(float delta)
    {
        for(UIObject uiObject : uiObjects)
        {
            uiObject.update();
        }

        batch.begin();
        batch.draw(background, 0, 0);

        for(UIObject uiObject : uiObjects)
        {
            uiObject.render(batch);
        }
        batch.end();
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
    public void handleEvent(Subject subject, Event event)
    {
        switch(event)
        {
            case GOTO_PLAY_SCREEN:
            {
                System.out.println("Going to play");
                game.setScreen(game.screens.get(ScreenName.PLAY_SCREEN));
                break;
            }
        }
    }
}
