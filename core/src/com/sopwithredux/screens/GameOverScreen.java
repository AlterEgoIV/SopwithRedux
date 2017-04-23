package com.sopwithredux.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.gameobjects.uiobjects.Text;

/**
 * Created by Carl on 04/04/2017.
 */
public class GameOverScreen extends GameScreen
{
    public GameOverScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game, assetManager);
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

    }

    public void setText(String text)
    {
        uiObjects.clear();
        uiObjects.add(new Text(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
          0.0, 0.0, 1f, 1f,
          false, false, text));
    }

    public void setBackground(String image)
    {
        background = assetManager.get(image);
    }
}
