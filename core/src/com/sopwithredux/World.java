package com.sopwithredux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.gameobjects.*;

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
    private CollisionHandler collisionHandler;
    private Texture background;

    public World(AssetManager assetManager)
    {
        this.assetManager = assetManager;
        activeGameObjects = new ArrayList<GameObject>();
        inactiveGameObjects = new ArrayList<GameObject>();
        activeGameObjectsToAdd = new ArrayList<GameObject>();
        activeGameObjectsToRemove = new ArrayList<GameObject>();
        collisionHandler = new CollisionHandler();
        background = assetManager.get("background.png", Texture.class);

        createGameObjects();

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
    }

    private void createGameObjects()
    {
        createClouds();
        createPlanes();
    }

    private void createClouds()
    {
        Random rand = new Random();
        double randomWidth, randomHeight, randomX, randomY, randomSpeed;

        for(int i = 0; i < 30; ++i)
        {
            randomWidth = rand.nextInt(120) + 100;
            randomHeight = rand.nextInt(60) + 50;
            randomX = rand.nextInt(Gdx.graphics.getWidth());
            randomY = rand.nextInt(Gdx.graphics.getHeight()) + Gdx.graphics.getHeight() / 4.0f;
            randomSpeed = rand.nextInt(200) + 100;

            activeGameObjects.add(new Cloud(this, assetManager.get("cloud.png", Texture.class),
              new Vector2((float)randomX, (float)randomY),
              new Vector2((float)randomWidth, (float)randomHeight),
              new Vector2(256.0f, 128.0f),
              randomSpeed, 0.0, false, false));
        }
    }

    private void createPlanes()
    {
        activeGameObjects.add(new Plane(this, assetManager.get("plane1.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 3.0f, Gdx.graphics.getHeight() / 2.0f),
          new Vector2(Gdx.graphics.getWidth() / 10.0f, Gdx.graphics.getWidth() / 20.0f),
          new Vector2(512.0f, 256.0f),
          200.0, 0.0, false, false,
          Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.E));

        activeGameObjects.add(new Plane(this, assetManager.get("plane2.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 3.0f, Gdx.graphics.getHeight() / 2.0f),
          /*new Vector2(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 3.0f), Gdx.graphics.getHeight() / 2.0f),*/
          new Vector2(Gdx.graphics.getWidth() / 10.0f, Gdx.graphics.getWidth() / 20.0f),
          new Vector2(512.0f, 256.0f),
          200.0, 180.0, true, false,
          Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.SHIFT_RIGHT));
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
        for(GameObject gameObject : inactiveGameObjects)
        {
            if(gameObject instanceof Bullet)
            {
                ((Bullet)gameObject).initialise(position, dimension, speed, angle, isFlippedX, isFlippedY);
                activeGameObjectsToAdd.add(gameObject);
                inactiveGameObjects.remove(gameObject);
                collisionHandler.add((CollidableObject)gameObject);
                return;
            }
        }

        Bullet bullet = new Bullet(this, assetManager.get("bullet.png", Texture.class),
          position, dimension, new Vector2(32, 16), speed, angle, isFlippedX, isFlippedY);

        activeGameObjectsToAdd.add(bullet);

        // This is okay so long as there are no Bullets in inactiveGameObjects before Bullets are created here
        collisionHandler.add(bullet);
    }

    public void remove(GameObject gameObject)
    {
        if(!activeGameObjects.contains(gameObject))
        {
            throw new AssertionError("Attempting to remove non active GameObject");
        }

        inactiveGameObjects.add(gameObject);
        activeGameObjectsToRemove.add(gameObject);

        // Okay, CollisionHandler doesn't do anything until all objects have updated
        if(gameObject instanceof CollidableObject) collisionHandler.remove((CollidableObject)gameObject);
    }
}
