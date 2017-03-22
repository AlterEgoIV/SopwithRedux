package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

import java.awt.*;

/**
 * Created by Carl on 08/03/2017.
 */
public class Bullet extends Projectile
{
    public Bullet(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                  double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        initialise(position, dimension, speed, angle, isFlippedX, isFlippedY);
    }

    public void initialise(Vector2 position, Vector2 dimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.isFlippedX = isFlippedX;
        this.isFlippedY = isFlippedY;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.scl((float)speed);
    }

    @Override
    public void update()
    {
        direction.nor();
        direction.scl((float)speed * Gdx.graphics.getDeltaTime());
        position.add(direction);

        if(position.x > Gdx.graphics.getWidth() || position.x < 0 ||
          position.y > Gdx.graphics.getHeight() || position.y < 0)
        {
            world.remove(this);
        }

        updateHitBox();
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {
        if(collidableObject instanceof Plane)
        {
            world.remove(this);
        }
    }

    @Override
    public void resolvePlaneCollision(Plane plane)
    {

    }

    @Override
    public void resolveBulletCollision(Bullet bullet)
    {
        world.remove(this);
    }
}
