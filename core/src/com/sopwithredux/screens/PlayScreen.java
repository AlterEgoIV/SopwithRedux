package com.sopwithredux.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.World;

/**
 * Created by Carl on 16/03/2017.
 */
public class PlayScreen extends GameScreen
{
    private World world;

    public PlayScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game);
        world = new World(assetManager);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        world.update();
        world.handleCollisions();

        batch.begin();
        world.render(batch);
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
}