package com.sopwithredux;

import com.sopwithredux.gameobjects.CollidableObject;
import com.sopwithredux.gameobjects.Plane;
import com.sopwithredux.utilities.Pair;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 08/03/2017.
 */
public class CollisionHandler
{
    private List<CollidableObject> collidableObjects;
    private List<Pair> collidedObjects;

    public CollisionHandler()
    {
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
        for(CollidableObject collidableObject : collidableObjects)
        {
            for(CollidableObject otherCollidableObject : collidableObjects)
            {
                if(collidableObject.collidesWith(otherCollidableObject))
                {
                    System.out.println("Collision!");

                    boolean addPair = true;

                    // This loop ensures no duplicate pairs are added
                    for(Pair pair : collidedObjects)
                    {
                        if((pair.getFirst() == collidableObject && pair.getSecond() == otherCollidableObject) ||
                           (pair.getFirst() == otherCollidableObject && pair.getSecond() == collidableObject))
                        {
                            addPair = false;
                            break;
                        }
                    }

                    if(addPair) collidedObjects.add(new Pair(collidableObject, otherCollidableObject));
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
            }
        }

        collidedObject1.updateHitBox();
        collidedObject2.updateHitBox();
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
