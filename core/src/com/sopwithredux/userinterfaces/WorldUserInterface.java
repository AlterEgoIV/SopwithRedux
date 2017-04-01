package com.sopwithredux.userinterfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.Event;
import com.sopwithredux.Subject;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.Outpost;
import com.sopwithredux.gameobjects.Plane;
import com.sopwithredux.gameobjects.projectiles.Bomb;
import com.sopwithredux.gameobjects.uiobjects.Icon;
import com.sopwithredux.gameobjects.uiobjects.Text;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 31/03/2017.
 */
public class WorldUserInterface extends UserInterface
{
    private List<Text> player1UiTextValues, player2UiTextValues;
    //private Integer lives, outposts, fuel, bombs;
    //private int uiValue;
    private Integer[] player1UiValues, player2UiValues;

    public WorldUserInterface(World world, AssetManager assetManager)
    {
        player1UiTextValues = new ArrayList<Text>();
        player2UiTextValues = new ArrayList<Text>();
        //lives = 5;
        //outposts = 5;
        //bombs = 5;
        //fuel = 100;
        //uiValue = 0;

        //player1UiValues = new Integer[]{lives, outposts, fuel, bombs};
        player1UiValues = new Integer[]{0, 0, 0, 0};
        player2UiValues = new Integer[]{0, 0, 0, 0};

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

            Text text = new Text(world, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
              false, false, player1UiValues[i]);
            uiObjects.add(text);
            player1UiTextValues.add(text);

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

            Text text = new Text(world, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
              false, false, player2UiValues[i]);
            uiObjects.add(text);
            player2UiTextValues.add(text);

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
        for(UIObject uiObject : uiObjects)
        {
            uiObject.update();
        }
    }

    @Override
    public void render(SpriteBatch batch)
    {
        for(UIObject uiObject : uiObjects)
        {
            uiObject.render(batch);
        }
    }

    @Override
    public void handleEvent(Subject subject, Event event)
    {
        switch(event)
        {
            case PLANE_LOST_LIFE:
            {
                Plane plane = (Plane)subject;

                if(plane.isPlayer1())
                {
                    --player1UiValues[0];
                    player1UiTextValues.get(0).setText(player1UiValues[0]);
                }
                else
                {
                    --player2UiValues[0];
                    player2UiTextValues.get(0).setText(player2UiValues[0]);
                }

                break;
            }

            case BOMB_DESTROYED_OUTPOST:
            {
                Outpost outpost = (Outpost)subject;

                System.out.println("Called");
                if(outpost.isPlayer1Outpost())
                {
                    --player1UiValues[1];
                    player1UiTextValues.get(1).setText(player1UiValues[1]);
                }
                else
                {
                    --player2UiValues[1];
                    player2UiTextValues.get(1).setText(player2UiValues[1]);
                }
//                if(plane.isPlayer1())
//                {
//                    --player1UiValues[1];
//                    player1UiTextValues.get(1).setText(player1UiValues[1]);
//                }
//                else
//                {
//                    --player2UiValues[1];
//                    player2UiTextValues.get(1).setText(player2UiValues[1]);
//                }

                break;
            }

            case PLANE_LOST_BOMB:
            {
                Plane plane = (Plane)subject;

                if(plane.isPlayer1())
                {
                    if(player1UiValues[3] > 0)
                    {
                        --player1UiValues[3];
                        player1UiTextValues.get(3).setText(player1UiValues[3]);
                    }
                }
                else
                {
                    if(player2UiValues[3] > 0)
                    {
                        --player2UiValues[3];
                        player2UiTextValues.get(3).setText(player2UiValues[3]);
                    }
                }

                break;
            }

            case PLANE_LOST_FUEL:
            {
                Plane plane = (Plane)subject;

                if(plane.isPlayer1())
                {
                    --player1UiValues[2];
                    player1UiTextValues.get(2).setText(player1UiValues[2]);
                }
                else
                {
                    --player2UiValues[2];
                    player2UiTextValues.get(2).setText(player2UiValues[2]);
                }

                break;
            }

            case MAX_FUEL_RESTORED:
            {
                Plane plane = (Plane)subject;

                if(plane.isPlayer1())
                {
                    player1UiValues[2] = 100;
                    player1UiTextValues.get(2).setText(player1UiValues[2]);
                }
                else
                {
                    player2UiValues[2] = 100;
                    player2UiTextValues.get(2).setText(player2UiValues[2]);
                }

                break;
            }
        }
    }

    public void setPlayer1UiValues(int lives, int outposts, int fuel, int bombs)
    {
        //player1UiValues = new Integer[]{lives, outposts, fuel, bombs};
        player1UiValues[0] = lives;
        player1UiValues[1] = outposts;
        player1UiValues[2] = fuel;
        player1UiValues[3] = bombs;

        for(int i = 0; i < player1UiTextValues.size(); ++i)
        {
            player1UiTextValues.get(i).setText(player1UiValues[i]);
        }
    }

    public void setPlayer2UiValues(int lives, int outposts, int fuel, int bombs)
    {
        //player1UiValues = new Integer[]{lives, outposts, fuel, bombs};
        player2UiValues[0] = lives;
        player2UiValues[1] = outposts;
        player2UiValues[2] = fuel;
        player2UiValues[3] = bombs;

        for(int i = 0; i < player2UiTextValues.size(); ++i)
        {
            player2UiTextValues.get(i).setText(player2UiValues[i]);
        }
    }

//    public void setLives(int lives)
//    {
//        this.lives = lives;
//    }
//
//    public void setOutposts(int outposts)
//    {
//        this.outposts = outposts;
//    }
//
//    public void setFuel(int fuel)
//    {
//        this.fuel = fuel;
//    }
//
//    public void setBombs(int bombs)
//    {
//        this.bombs = bombs;
//    }
}
