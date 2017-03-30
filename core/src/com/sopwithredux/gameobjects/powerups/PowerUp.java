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
    private boolean rotateRight;
    private double amplitude, rotationValue;

    protected PowerUp(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                      double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        rotationSpeed = .05;
        rotateRight = true;
        amplitude = 15;
        rotationValue = angle;
    }

    @Override
    public void update()
    {
        position.y -= speed * Gdx.graphics.getDeltaTime();

        if(rotateRight)
        {
            rotationValue += rotationSpeed;

            if(angle >= 15.0) rotateRight = false;
        }
        else
        {
            rotationValue -= rotationSpeed;

            if(angle <= -15.0) rotateRight = true;
        }

        angle = Math.cos(rotationValue) * amplitude;

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
