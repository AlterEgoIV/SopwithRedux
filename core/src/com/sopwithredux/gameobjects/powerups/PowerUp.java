package com.sopwithredux.gameobjects.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.CollidableObject;
import com.sopwithredux.gameobjects.Plane;

import java.util.Random;

/**
 * Created by Carl on 30/03/2017.
 */
public abstract class PowerUp extends CollidableObject
{
    private Random rand;

    protected PowerUp(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                      double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        rand = new Random();
    }

    @Override
    public void update()
    {
        position.y -= speed * Gdx.graphics.getDeltaTime();

        if(position.y <= Gdx.graphics.getHeight() / 5)
        {
            double randomX = rand.nextInt(Gdx.graphics.getWidth());
            position.x = (float)randomX;
            position.y = Gdx.graphics.getHeight() + dimension.y;
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
