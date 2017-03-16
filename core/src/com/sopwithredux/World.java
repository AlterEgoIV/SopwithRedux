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
            if(gameObject instanceof CollidableObject)
            {
                collisionHandler.add((CollidableObject)gameObject);
            }
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
        activeGameObjects.add(new Cloud(this, assetManager.get("cloud.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2),
          new Vector2(Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 20),
          new Vector2(256, 128),
          100.0, 0.0, false, false));
    }

    private void createPlanes()
    {
        activeGameObjects.add(new Plane(this, assetManager.get("plane1.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 20),
          new Vector2(512, 256),
          200.0, false, false,
          Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, Input.Keys.E));

        activeGameObjects.add(new Plane(this, assetManager.get("plane2.png", Texture.class),
          new Vector2(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2),
          new Vector2(Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 20),
          new Vector2(512, 256),
          200.0, true, false,
          Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.SHIFT_RIGHT));
    }

    public void add(GameObject gameObject)
    {
        activeGameObjectsToAdd.add(gameObject);

        if(gameObject instanceof CollidableObject)
        {
            collisionHandler.add((CollidableObject)gameObject);
        }
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
                return;
            }
        }

        activeGameObjectsToAdd.add(new Bullet(this, assetManager.get("bullet.png", Texture.class),
          position, dimension, new Vector2(32, 16), speed, angle, isFlippedX, isFlippedY));
    }

    public void remove(GameObject gameObject)
    {
        if(!activeGameObjects.contains(gameObject))
        {
            throw new AssertionError("Attempting to remove non-active GameObject");
        }

        inactiveGameObjects.add(gameObject);
        activeGameObjectsToRemove.add(gameObject);

        if(gameObject instanceof CollidableObject)
        {
            collisionHandler.remove((CollidableObject)gameObject);
        }
    }
}
