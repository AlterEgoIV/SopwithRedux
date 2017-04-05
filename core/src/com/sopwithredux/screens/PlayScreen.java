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

        background = assetManager.get("background.png");
        world = new World(assetManager, background);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        if(!world.isFinished())
        {
            world.update();
            world.handleCollisions();

            batch.begin();
            world.render(batch);
            batch.end();
        }
        else
        {
            if(world.player1Won)
            {
                ((GameOverScreen)game.screens.get(ScreenName.GAME_OVER_SCREEN)).setText("Player 1 wins!");
            }
            else
            {
                ((GameOverScreen)game.screens.get(ScreenName.GAME_OVER_SCREEN)).setText("Player 2 wins!");
            }

            game.setScreen(game.screens.get(ScreenName.GAME_OVER_SCREEN));
        }
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
}
