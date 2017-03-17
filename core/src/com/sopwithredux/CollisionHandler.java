package com.sopwithredux;

import com.sopwithredux.gameobjects.CollidableObject;
import com.sopwithredux.gameobjects.Plane;
import com.sopwithredux.utilities.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 08/03/2017.
 */
public class CollisionHandler
{
    private List<CollidableObject> collidableObjects;
    //private List<Pair> collidedObjects;
    //private Pair pair;

    public CollisionHandler()
    {
        collidableObjects = new ArrayList<CollidableObject>();
        //collidedObjects = new ArrayList<Pair>();
        //pair = new Pair();
    }

    public void handleCollisions()
    {
        for(CollidableObject collidableObject : collidableObjects)
        {
            for(CollidableObject otherCollidableObject : collidableObjects)
            {
                if(collidableObject.collidesWith(otherCollidableObject))
                {
                    System.out.println("Collision!");

//                    pair.set(collidableObject, otherCollidableObject);
//
//                    if(!collidedObjects.contains(pair))
//                    {
//                        collidedObjects.add(pair);
//                    }

                    collidableObject.resolveCollision(otherCollidableObject);
                    //otherCollidableObject.resolveCollision(collidableObject);
                }
            }
        }

//        for(Pair collidedObjects : collidedObjects)
//        {
//            resolveCollision((CollidableObject)collidedObjects.getFirst(), (CollidableObject)collidedObjects.getSecond());
//        }
//
//        collidedObjects.clear();
    }

//    private void resolveCollision(CollidableObject collidedObject1, CollidableObject collidedObject2)
//    {
//        Rectangle rect = collidedObject1.hitBox.intersection(collidedObject2.hitBox);
//
//        if(collidedObject1 instanceof Plane)
//        {
//            if(collidedObject2 instanceof Plane)
//            {
//                if(collidedObject1.hitBox.x < collidedObject2.hitBox.x)
//                {
//                    collidedObject1.move(-rect.width / 2, 0);
//                    collidedObject2.move(rect.width / 2, 0);
//                }
//                else if(collidedObject1.hitBox.x > collidedObject2.hitBox.x)
//                {
//                    collidedObject1.move(rect.width / 2, 0);
//                    collidedObject2.move(-rect.width / 2, 0);
//                }
//
//                if(collidedObject1.hitBox.y < collidedObject2.hitBox.y)
//                {
//                    collidedObject1.move(0, -rect.height / 2);
//                    collidedObject2.move(0, rect.height / 2);
//                }
//                else if(collidedObject1.hitBox.y > collidedObject2.hitBox.y)
//                {
//                    collidedObject1.move(0, rect.height);
//                    collidedObject2.move(0, -rect.height);
//                }
//            }
//        }
//
//        collidedObject1.updateHitBox();
//        collidedObject2.updateHitBox();
//    }

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
