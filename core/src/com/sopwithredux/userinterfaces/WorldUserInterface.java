package com.sopwithredux.userinterfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.uiobjects.Icon;
import com.sopwithredux.gameobjects.uiobjects.Text;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

/**
 * Created by Carl on 31/03/2017.
 */
public class WorldUserInterface extends UserInterface
{
    public WorldUserInterface(World world, AssetManager assetManager)
    {
        String[] iconNames = {"heart.png", "outpost1.png", "fuelpowerup.png", "bombpowerup.png"};
        float width = Gdx.graphics.getWidth() / 30.0f, height = Gdx.graphics.getWidth() / 30.0f;
        float sourceWidth = 16.0f, sourceHeight = 16.0f;
        float x = Gdx.graphics.getWidth() / 40.0f, y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 16);
        float xSpacing = width * 1.5f, ySpacing = height / 1.8f;

        uiObjects.add(new Text(world,
          new Vector2(Gdx.graphics.getWidth() / 40.0f, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 150)),
          0.0, 0.0, 1f, 1f,
          false, false, "Player 1"));

        for(int i = 0; i < 4; ++i)
        {
            uiObjects.add(new Icon(world, assetManager.get(iconNames[i], Texture.class),
              new Vector2(x, y),
              new Vector2(width, height),
              new Vector2(sourceWidth, sourceHeight),
              0.0, 0.0, false, false));

            uiObjects.add(new Text(world, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
              false, false, "1"));

            if(i == 0)
            {
                sourceWidth = 256.0f;
                sourceHeight = 128.0f;
            }

            x += xSpacing;
        }

        iconNames[1] = "outpost2.png";
        x = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 40.0f);
        sourceWidth = 16.0f;
        sourceHeight = 16.0f;

        uiObjects.add(new Text(world,
          new Vector2(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 40.0f), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 150)),
          0.0, 0.0, 1f, 1f,
          false, false, "Player 2"));

        for(int i = 0; i < 4; ++i)
        {
            uiObjects.add(new Icon(world, assetManager.get(iconNames[i], Texture.class),
              new Vector2(x, y),
              new Vector2(width, height),
              new Vector2(sourceWidth, sourceHeight),
              0.0, 0.0, false, false));

            uiObjects.add(new Text(world, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
              false, false, "1"));

            if(i == 0)
            {
                sourceWidth = 256.0f;
                sourceHeight = 128.0f;
            }

            x -= xSpacing;
        }
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        for(UIObject uiObject : uiObjects)
        {
            uiObject.render(batch);
        }
    }
}
