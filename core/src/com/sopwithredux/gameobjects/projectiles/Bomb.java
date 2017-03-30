package com.sopwithredux.gameobjects.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.CollidableObject;
import com.sopwithredux.gameobjects.Plane;

/**
 * Created by Carl on 29/03/2017.
 */
public class Bomb extends CollidableObject
{
    private double targetAngle;

    public Bomb(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        rotationSpeed = 1.0;

        if(isFlippedY)
        {
            targetAngle = 270.0;
        }
        else
        {
            targetAngle = -90;
        }

    }

    @Override
    public void update()
    {
        position.y -= speed * Gdx.graphics.getDeltaTime();

        if(angle <= targetAngle)
        {
            angle += rotationSpeed;
        }
        else if(angle >= targetAngle)
        {
            angle -= rotationSpeed;
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
    public void resolveBulletCollision(Bullet bullet)
    {

    }
}
