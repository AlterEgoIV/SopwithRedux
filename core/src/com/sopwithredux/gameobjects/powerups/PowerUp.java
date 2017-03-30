package com.sopwithredux.gameobjects.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.CollidableObject;
import com.sopwithredux.gameobjects.Plane;

/**
 * Created by Carl on 30/03/2017.
 */
public abstract class PowerUp extends CollidableObject
{
    protected PowerUp(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                      double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }

    @Override
    public void update()
    {
        position.y -= speed * Gdx.graphics.getDeltaTime();

        if(position.y <= Gdx.graphics.getHeight() / 5)
        {
            world.remove(this);
        }

        updateHitBox();
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {

    }

    @Override
    public void resolvePlaneCollision(Plane plane)
    {

    }

    @Override
    public void resolveBulletCollision(com.sopwithredux.gameobjects.projectiles.Bullet bullet)
    {

    }
}
