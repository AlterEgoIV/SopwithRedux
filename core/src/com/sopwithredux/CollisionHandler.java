package com.sopwithredux;

import com.sopwithredux.gameobjects.*;
import com.sopwithredux.gameobjects.powerups.BombPowerUp;
import com.sopwithredux.gameobjects.powerups.FuelPowerUp;
import com.sopwithredux.gameobjects.powerups.PowerUp;
import com.sopwithredux.gameobjects.projectiles.Bomb;
import com.sopwithredux.gameobjects.projectiles.Bullet;
import com.sopwithredux.utilities.Pair;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 08/03/2017.
 */
public class CollisionHandler
{
    private World world;
    private List<CollidableObject> collidableObjects;
    private List<Pair> collidedObjects;

    public CollisionHandler(World world)
    {
        this.world = world;
        collidableObjects = new ArrayList<CollidableObject>();
        collidedObjects = new ArrayList<Pair>();
    }

    public void handleCollisions()
    {
        detectCollisions();

        for(Pair pair : collidedObjects)
        {
            resolveCollision((CollidableObject)pair.getFirst(), (CollidableObject)pair.getSecond());
        }

        collidedObjects.clear();
    }

    private void detectCollisions()
    {
        for(int i = 0; i < collidableObjects.size() - 1; ++i)
        {
            for(int j = i + 1; j < collidableObjects.size(); ++j)
            {
                if(collidableObjects.get(i).collidesWith(collidableObjects.get(j)))
                {
                    //System.out.println("Collision!");

                    collidedObjects.add(new Pair(collidableObjects.get(i), collidableObjects.get(j)));
                }
            }
        }
    }

    private void resolveCollision(CollidableObject collidedObject1, CollidableObject collidedObject2)
    {
        Rectangle rect = collidedObject1.hitBox.intersection(collidedObject2.hitBox);

        if(collidedObject1 instanceof Plane)
        {
            if(collidedObject2 instanceof Plane)
            {
                if(collidedObject1.hitBox.x < collidedObject2.hitBox.x)
                {
                    collidedObject1.move(-rect.width / 2, 0);
                    collidedObject2.move(rect.width / 2, 0);
                }
                else if(collidedObject1.hitBox.x > collidedObject2.hitBox.x)
                {
                    collidedObject1.move(rect.width / 2, 0);
                    collidedObject2.move(-rect.width / 2, 0);
                }

                if(collidedObject1.hitBox.y < collidedObject2.hitBox.y)
                {
                    collidedObject1.move(0, -rect.height / 2);
                    collidedObject2.move(0, rect.height / 2);
                }
                else if(collidedObject1.hitBox.y > collidedObject2.hitBox.y)
                {
                    collidedObject1.move(0, rect.height);
                    collidedObject2.move(0, -rect.height);
                }

                collidedObject1.updateHitBox();
                collidedObject2.updateHitBox();
            }
            else if(collidedObject2 instanceof Bullet)
            {
                world.remove(collidedObject2);
                ((Plane)collidedObject1).takeDamage();
            }
            else if(collidedObject2 instanceof Bomb)
            {
                world.remove(collidedObject2);
            }
            else if(collidedObject2 instanceof PowerUp)
            {
                if(collidedObject2 instanceof FuelPowerUp)
                {
                    ((Plane)collidedObject1).addFuel((FuelPowerUp)collidedObject2);
                }

                if(collidedObject2 instanceof BombPowerUp)
                {
                    ((Plane)collidedObject1).addBomb((BombPowerUp)collidedObject2);
                }
            }
        }
        else if(collidedObject1 instanceof Bullet)
        {
            if(collidedObject2 instanceof Plane)
            {
                world.remove(collidedObject1);
                ((Plane)collidedObject2).takeDamage();
            }
        }
        else if(collidedObject1 instanceof Bomb)
        {
            if(collidedObject2 instanceof Outpost)
            {
                System.out.println("Called1");
                ((Bomb)collidedObject1).destroyOutpost((Outpost)collidedObject2);
            }
        }
        else if(collidedObject1 instanceof Outpost)
        {
            if(collidedObject2 instanceof Bomb)
            {
                System.out.println("Called2");
                ((Bomb)collidedObject2).destroyOutpost((Outpost)collidedObject1);
            }
        }
        else if(collidedObject1 instanceof PowerUp)
        {
            if(collidedObject2 instanceof Plane)
            {
                world.remove(collidedObject1);
            }
        }
    }

    public void add(CollidableObject collidableObject)
    {
        collidableObjects.add(collidableObject);
    }

    public void remove(CollidableObject collidableObject)
    {
        if(collidableObjects.contains(collidableObject))
        {
            collidableObjects.remove(collidableObject);
        }
    }
}
