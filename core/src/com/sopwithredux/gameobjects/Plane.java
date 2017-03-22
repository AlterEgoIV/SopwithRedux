package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.InputHandler;
import com.sopwithredux.World;

import java.awt.*;

/**
 * Created by Carl on 08/03/2017.
 */
public class Plane extends CollidableObject implements InputHandler
{
    private int up, down, left, right, fire, coolDownTime, timeToCool;
    private double maxSpeed;

    public Plane(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed,
                 double angle, boolean isFlippedX, boolean isFlippedY, int up, int down, int left, int right, int fire)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.fire = fire;
        coolDownTime = 60 / 4;
        timeToCool = 0;
        maxSpeed = this.speed;
    }

    @Override
    public void update()
    {
        handleInput();

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        updateHitBox();

        if(timeToCool > 0) timeToCool--;
    }

    @Override
    public void handleInput()
    {
        if(Gdx.input.isKeyPressed(up))
        {
            position.y += speed * Gdx.graphics.getDeltaTime();
        }

        if(Gdx.input.isKeyPressed(down))
        {
            position.y -= speed * Gdx.graphics.getDeltaTime();
        }

        if(Gdx.input.isKeyPressed(left))
        {
            position.x -= speed * Gdx.graphics.getDeltaTime();
            angle = 180.0;
            if(!isFlippedX) isFlippedX = true;
        }

        if(Gdx.input.isKeyPressed(right))
        {
            position.x += speed * Gdx.graphics.getDeltaTime();
            angle = 0.0;
            if(isFlippedX) isFlippedX = false;
        }

        if(Gdx.input.isKeyPressed(fire) && timeToCool == 0)
        {
            timeToCool = coolDownTime;
            fireBullet();
        }
    }

    private void fireBullet()
    {
        Vector2 pos = position.cpy();
        Vector2 dim = new Vector2(20.0f, 10.0f);

        if(isFlippedX)
        {
            pos.add(((-dimension.x / 2) - dim.x / 2) - 1, 0);
        }
        else
        {
            pos.add(((dimension.x / 2) + dim.x / 2) + 1, 0);
        }

        world.addBullet(pos, dim, maxSpeed * 3, angle, isFlippedX, isFlippedY);
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {
        Rectangle rect = hitBox.intersection(collidableObject.hitBox);

        if(collidableObject instanceof Plane)
        {
            if(hitBox.x < collidableObject.hitBox.x)
            {
                position.x -= rect.getWidth();
            }
            else if(hitBox.x > collidableObject.hitBox.x)
            {
                position.x += rect.getWidth();
            }

            if(hitBox.y < collidableObject.hitBox.y)
            {
                position.y -= rect.getHeight();
            }
            else if(hitBox.y > collidableObject.hitBox.y)
            {
                position.y += rect.getHeight();
            }

            updateHitBox();
            collidableObject.updateHitBox();
        }

//        if(collidableObject instanceof Bullet)
//        {
//            world.remove(collidableObject);
//        }
    }

    @Override
    public void resolvePlaneCollision(Plane plane)
    {

    }

    @Override
    public void resolveBulletCollision(Bullet bullet)
    {
        world.remove(bullet);
    }
}
