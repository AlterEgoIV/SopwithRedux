package com.sopwithredux.gameobjects.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.CollidableObject;

/**
 * Created by Carl on 08/03/2017.
 */
public abstract class Projectile extends CollidableObject
{
    protected Projectile(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                         double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }

//    @Override
//    public void resolveCollision(CollidableObject collidableObject)
//    {
//
//    }
}
