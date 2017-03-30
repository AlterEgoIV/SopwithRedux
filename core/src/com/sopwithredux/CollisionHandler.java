package com.sopwithredux;

import com.sopwithredux.gameobjects.*;
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
                    System.out.println("Collision!");

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
                //collidedObject1.moveBack((float)rect.getWidth(), (float)rect.getHeight());
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

            if(collidedObject2 instanceof com.sopwithredux.gameobjects.projectiles.Bullet)
            {
                world.remove(collidedObject2);
                //collidedObject1.resolveBulletCollision((Bullet)collidedObject2);
            }
            else if(collidedObject2 instanceof com.sopwithredux.gameobjects.projectiles.Bomb)
            {
                world.remove(collidedObject2);
            }
        }
        else if(collidedObject1 instanceof com.sopwithredux.gameobjects.projectiles.Bomb)
        {
            if(collidedObject2 instanceof Outpost)
            {
                world.remove(collidedObject1);
                world.remove(collidedObject2);
            }
        }
        else if(collidedObject1 instanceof Outpost)
        {
            if(collidedObject2 instanceof com.sopwithredux.gameobjects.projectiles.Bomb)
            {
                world.remove(collidedObject1);
                world.remove(collidedObject2);
            }
        }

//        if(collidedObject1 instanceof Bullet)
//        {
//            if(collidedObject2 instanceof Plane)
//            {
//                collidedObject1.resolvePlaneCollision((Plane)collidedObject2);
//            }
//        }
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
