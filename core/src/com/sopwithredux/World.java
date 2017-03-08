package com.sopwithredux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 08/03/2017.
 */
public class World
{
    private List<GameObject> activeGameObjects, inactiveGameObjects, activeGameObjectsToAdd, activeGameObjectsToRemove;
    private CollisionHandler collisionHandler;

    public World()
    {
        activeGameObjects = new ArrayList<GameObject>();
        inactiveGameObjects = new ArrayList<GameObject>();
        activeGameObjectsToAdd = new ArrayList<GameObject>();
        activeGameObjectsToRemove = new ArrayList<GameObject>();
        collisionHandler = new CollisionHandler();

        activeGameObjects.add(new Ship(this, new Vector2(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2),
          new Vector2(50, 50), 5.0, 0.0, 5.0, Color.RED,
          Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.M));

        activeGameObjects.add(new Ship(this, new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2),
          new Vector2(50, 50), 5.0, 0.0, 5.0, Color.CYAN,
          Input.Keys.W, Input.Keys.A, Input.Keys.D, Input.Keys.E));

        for(GameObject gameObject : activeGameObjects)
        {
            if(gameObject instanceof CollidableObject)
            {
                collisionHandler.add((CollidableObject)gameObject);
            }
        }
    }

    void update()
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

    void handleCollisions()
    {
        collisionHandler.handleCollisions();
    }

    void render(SpriteBatch batch)
    {
        for(GameObject gameObject : activeGameObjects)
        {
            gameObject.render(batch);
        }
    }

    public void add(GameObject gameObject)
    {
        activeGameObjectsToAdd.add(gameObject);

        if(gameObject instanceof CollidableObject)
        {
            collisionHandler.add((CollidableObject)gameObject);
        }
    }

    public void addBullet(Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        for(GameObject gameObject : inactiveGameObjects)
        {
            if(gameObject instanceof Bullet)
            {
                ((Bullet)gameObject).initialise(position, dimension, speed, angle, colour);
                activeGameObjectsToAdd.add(gameObject);
                inactiveGameObjects.remove(gameObject);
                return;
            }
        }

        activeGameObjectsToAdd.add(new Bullet(this, position, dimension, speed, angle, colour));
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
