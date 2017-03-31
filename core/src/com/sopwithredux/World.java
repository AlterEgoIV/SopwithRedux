package com.sopwithredux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.gameobjects.*;
import com.sopwithredux.gameobjects.powerups.BombPowerUp;
import com.sopwithredux.gameobjects.powerups.FuelPowerUp;
import com.sopwithredux.gameobjects.powerups.PowerUp;
import com.sopwithredux.gameobjects.uiobjects.Icon;
import com.sopwithredux.gameobjects.uiobjects.Text;
import com.sopwithredux.gameobjects.uiobjects.UIObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Carl on 08/03/2017.
 */
public class World
{
    private AssetManager assetManager;
    private List<GameObject> activeGameObjects, inactiveGameObjects, activeGameObjectsToAdd, activeGameObjectsToRemove;
    private List<UIObject> uiObjects;
    private CollisionHandler collisionHandler;
    private Texture background, heart, outpost1, outpost2, bombPowerUp, fuelPowerUp;
    private Random rand;
    int spawnTime, timeLeftToSpawn;

    public World(AssetManager assetManager)
    {
        this.assetManager = assetManager;
        activeGameObjects = new ArrayList<GameObject>();
        inactiveGameObjects = new ArrayList<GameObject>();
        activeGameObjectsToAdd = new ArrayList<GameObject>();
        activeGameObjectsToRemove = new ArrayList<GameObject>();
        uiObjects = new ArrayList<UIObject>();
        collisionHandler = new CollisionHandler(this);
        background = assetManager.get("background.png");
        heart = assetManager.get("heart.png");
        outpost1 = assetManager.get("outpost1.png");
        outpost2 = assetManager.get("outpost2.png");
        bombPowerUp = assetManager.get("bombpowerup.png");
        fuelPowerUp = assetManager.get("fuelpowerup.png");
        rand = new Random();
        spawnTime = 300;
        timeLeftToSpawn = 0;

        createGameObjects();
        createUI();

        for(GameObject gameObject : activeGameObjects)
        {
            if(gameObject instanceof CollidableObject) collisionHandler.add((CollidableObject)gameObject);
        }
    }

    public void update()
    {
        for(GameObject gameObject : activeGameObjects)
        {
            gameObject.update();
        }

        if(!activeGameObjectsToRemove.isEmpty())
        {
            activeGameObjects.removeAll(activeGameObjectsToRemove);
            activeGameObjectsToRemove.clear();
        }

        if(!activeGameObjectsToAdd.isEmpty())
        {
            activeGameObjects.addAll(activeGameObjectsToAdd);
            activeGameObjectsToAdd.clear();
        }

        spawnPowerUps();

        if(timeLeftToSpawn > 0) timeLeftToSpawn--;
    }

    public void handleCollisions()
    {
        collisionHandler.handleCollisions();
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for(GameObject gameObject : activeGameObjects)
        {
            gameObject.render(batch);
        }

        for(UIObject uiObject : uiObjects)
        {
            uiObject.render(batch);
        }
    }

    private void createGameObjects()
    {
        createClouds();
        createOutposts();
        createPlanes();
    }

    private void createClouds()
    {
        double randomWidth, randomHeight, randomX, randomY, randomSpeed;

        for(int i = 0; i < 20; ++i)
        {
            randomWidth = rand.nextInt(120) + 100;
            randomHeight = rand.nextInt(60) + 50;
            randomX = rand.nextInt(Gdx.graphics.getWidth());
            randomY = rand.nextInt(Gdx.graphics.getHeight()) + Gdx.graphics.getHeight() / 3.0f;
            randomSpeed = rand.nextInt(200) + 100;

            activeGameObjects.add(new Cloud(this, assetManager.get("cloud.png", Texture.class),
              new Vector2((float)randomX, (float)randomY),
              new Vector2((float)randomWidth, (float)randomHeight),
              new Vector2(256.0f, 128.0f),
              randomSpeed, 0.0, false, false));
        }
    }

    private void createOutposts()
    {
        double randomX;

        for(int i = 0; i < 5; ++i)
        {
            randomX = rand.nextInt(Gdx.graphics.getWidth() * 4) + Gdx.graphics.getWidth();

            activeGameObjects.add(new Outpost(this, assetManager.get("outpost1.png", Texture.class),
              new Vector2((float)randomX, Gdx.graphics.getHeight() / 8),
              new Vector2(Gdx.graphics.getWidth() / 20.0f, Gdx.graphics.getWidth() / 20.0f),
              new Vector2(256.0f, 128.0f),
              200.0, 0.0, false, false, true));

            randomX = rand.nextInt(Gdx.graphics.getWidth() * 4) + Gdx.graphics.getWidth();

            activeGameObjects.add(new Outpost(this, assetManager.get("outpost2.png", Texture.class),
              new Vector2((float)randomX, Gdx.graphics.getHeight() / 8),
              new Vector2(Gdx.graphics.getWidth() / 20.0f, Gdx.graphics.getWidth() / 20.0f),
              new Vector2(256.0f, 128.0f),
              200.0, 0.0, false, false, false));
        }
    }

    private void createPlanes()
    {
        activeGameObjects.add(new Plane(this, assetManager.get("plane1.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 3.0f, Gdx.graphics.getHeight() / 2.0f),
          new Vector2(Gdx.graphics.getWidth() / 10.0f, Gdx.graphics.getWidth() / 20.0f),
          new Vector2(512.0f, 256.0f),
          200.0, 0.0, false, false,
          Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.E, Input.Keys.F));

        activeGameObjects.add(new Plane(this, assetManager.get("plane2.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 3.0f, Gdx.graphics.getHeight() / 2.0f),
          /*new Vector2(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 3.0f), Gdx.graphics.getHeight() / 2.0f),*/
          new Vector2(Gdx.graphics.getWidth() / 10.0f, Gdx.graphics.getWidth() / 20.0f),
          new Vector2(512.0f, 256.0f),
          200.0, 180.0, false, true,
          Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.SHIFT_RIGHT, Input.Keys.ENTER));
    }

    private void createUI()
    {
        //Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 30, 1, Pixmap.Format.RGBA8888);
        String[] iconNames = {"heart.png", "outpost1.png", "fuelpowerup.png", "bombpowerup.png"};
        float width = Gdx.graphics.getWidth() / 30.0f, height = Gdx.graphics.getWidth() / 30.0f;
        float sourceWidth = 16.0f, sourceHeight = 16.0f;
        float x = Gdx.graphics.getWidth() / 40.0f, y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 16);
        float xSpacing = width * 1.5f, ySpacing = height / 1.8f;

        uiObjects.add(new Text(this,
          new Vector2(Gdx.graphics.getWidth() / 40.0f, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 150)),
          0.0, 0.0, 1f, 1f,
          false, false, "Player 1"));

        for(int i = 0; i < 4; ++i)
        {
            uiObjects.add(new Icon(this, assetManager.get(iconNames[i], Texture.class),
              new Vector2(x, y),
              new Vector2(width, height),
              new Vector2(sourceWidth, sourceHeight),
              0.0, 0.0, false, false));

            uiObjects.add(new Text(this, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
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

        uiObjects.add(new Text(this,
          new Vector2(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 40.0f), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 150)),
          0.0, 0.0, 1f, 1f,
          false, false, "Player 2"));

        for(int i = 0; i < 4; ++i)
        {
            uiObjects.add(new Icon(this, assetManager.get(iconNames[i], Texture.class),
              new Vector2(x, y),
              new Vector2(width, height),
              new Vector2(sourceWidth, sourceHeight),
              0.0, 0.0, false, false));

            uiObjects.add(new Text(this, new Vector2(x, y - ySpacing), 0.0, 0.0, 1.5, 1.5,
              false, false, "1"));

            if(i == 0)
            {
                sourceWidth = 256.0f;
                sourceHeight = 128.0f;
            }

            x -= xSpacing;
        }

//        uiObjects.add(new Icon(this, assetManager.get("heart.png", Texture.class),
//          new Vector2(x, y),
//          new Vector2(width, height),
//          new Vector2(16.0f, 16.0f),
//          0.0, 0.0, false, false));
//
//        x += xSpacing;
//
//        uiObjects.add(new Icon(this, assetManager.get("outpost1.png", Texture.class),
//          new Vector2(x, y),
//          new Vector2(width, height),
//          new Vector2(256.0f, 128.0f),
//          0.0, 0.0, false, false));
//
//        x += xSpacing;
//
//        uiObjects.add(new Icon(this, assetManager.get("fuelpowerup.png", Texture.class),
//          new Vector2(x, y),
//          new Vector2(width, height),
//          new Vector2(256.0f, 128.0f),
//          0.0, 0.0, false, false));
//
//        x += xSpacing;
//
//        uiObjects.add(new Icon(this, assetManager.get("bombpowerup.png", Texture.class),
//          new Vector2(x, y),
//          new Vector2(width, height),
//          new Vector2(256.0f, 128.0f),
//          0.0, 0.0, false, false));
    }

    private void spawnPowerUps()
    {
        PowerUp powerUp;

        if(timeLeftToSpawn == 0)
        {
            timeLeftToSpawn = spawnTime;

            int randomNumber = rand.nextInt(2);
            double randomX = rand.nextInt(Gdx.graphics.getWidth());

            if(randomNumber == 1)
            {
                powerUp = new BombPowerUp(this, assetManager.get("bombpowerup.png", Texture.class),
                  new Vector2((float)randomX, Gdx.graphics.getHeight() + (Gdx.graphics.getWidth() / 20.0f) / 2),
                  new Vector2(Gdx.graphics.getWidth() / 20.0f, Gdx.graphics.getWidth() / 20.0f),
                  new Vector2(256.0f, 128.0f),
                  50.0, 0.0, false, false);
            }
            else
            {
                powerUp = new FuelPowerUp(this, assetManager.get("fuelpowerup.png", Texture.class),
                  new Vector2((float)randomX, Gdx.graphics.getHeight() + (Gdx.graphics.getWidth() / 20.0f) / 2),
                  new Vector2(Gdx.graphics.getWidth() / 20.0f, Gdx.graphics.getWidth() / 20.0f),
                  new Vector2(256.0f, 128.0f),
                  50.0, 0.0, false, false);
            }

            activeGameObjects.add(powerUp);
            collisionHandler.add(powerUp);
        }
    }

    public void add(GameObject gameObject)
    {
        if(activeGameObjects.contains(gameObject))
        {
            throw new AssertionError("Attempting to add already existing gameObject to activeGameObjects");
        }

        activeGameObjectsToAdd.add(gameObject);
        if(gameObject instanceof CollidableObject) collisionHandler.add((CollidableObject)gameObject);
    }

    public void addBullet(Vector2 position, Vector2 dimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        com.sopwithredux.gameobjects.projectiles.Bullet bullet = new com.sopwithredux.gameobjects.projectiles.Bullet(this, assetManager.get("bullet.png", Texture.class),
          position, dimension, new Vector2(32, 16), speed, angle, isFlippedX, isFlippedY);

        activeGameObjectsToAdd.add(bullet);

        // This is okay so long as there are no Bullets in inactiveGameObjects before Bullets are created here
        collisionHandler.add(bullet);
    }

    public void addBomb(Vector2 position, Vector2 dimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        com.sopwithredux.gameobjects.projectiles.Bomb bomb = new com.sopwithredux.gameobjects.projectiles.Bomb(this, assetManager.get("bomb.png", Texture.class),
          position, dimension, new Vector2(128, 64), speed, angle, isFlippedX, isFlippedY);

        activeGameObjectsToAdd.add(bomb);
        collisionHandler.add(bomb);
    }

    public void remove(GameObject gameObject)
    {
        if(!activeGameObjects.contains(gameObject))
        {
            throw new AssertionError("Attempting to remove non active GameObject");
        }

        //inactiveGameObjects.add(gameObject);
        activeGameObjectsToRemove.add(gameObject);

        // Okay, CollisionHandler doesn't do anything until all objects have updated
        if(gameObject instanceof CollidableObject) collisionHandler.remove((CollidableObject)gameObject);
    }
}
