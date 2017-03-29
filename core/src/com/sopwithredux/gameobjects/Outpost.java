package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 24/03/2017.
 */
public class Outpost extends CollidableObject
{
    private Vector2 startingPosition;
    private boolean player1Outpost;

    public Outpost(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                      double speed, double angle, boolean isFlippedX, boolean isFlippedY, boolean player1Outpost)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        this.player1Outpost = player1Outpost;
        startingPosition = position;
    }

    @Override
    public void update()
    {
        position.x -= speed * Gdx.graphics.getDeltaTime();

        if(position.x < -dimension.x)
        {
            position = startingPosition;
        }

        updateHitBox();
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {
        if(player1Outpost)
        {
            //collidableObject.++outpostsDestroyed;
        }
        else
        {
            //++outpostsDestroyed;
        }

        //world.remove(this);
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
