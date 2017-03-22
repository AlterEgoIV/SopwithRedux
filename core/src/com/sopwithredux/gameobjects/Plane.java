package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.input.InputHandler;
import com.sopwithredux.World;

/**
 * Created by Carl on 08/03/2017.
 */
public class Plane extends CollidableObject implements InputHandler
{
    private int up, down, left, right, fire, coolDownTime, timeToCool;
    private double maxSpeed;
    boolean movingUp, movingDown, movingLeft, movingRight, firingBullet;

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
        movingUp = false;
        movingDown = false;
        movingLeft = false;
        movingRight = false;
        firingBullet = false;
    }

    @Override
    public void update()
    {
        //handleInput();

        if(movingUp)
        {
            position.y += speed * Gdx.graphics.getDeltaTime();
            //angle = 90.0;
        }

        if(movingDown)
        {
            position.y -= speed * Gdx.graphics.getDeltaTime();
            //angle = 270.0;
        }

        if(movingLeft)
        {
            position.x -= speed * Gdx.graphics.getDeltaTime();
            angle = 180.0;
            if(!isFlippedX) isFlippedX = true;
        }

        if(movingRight)
        {
            position.x += speed * Gdx.graphics.getDeltaTime();
            angle = 0.0;
            if(isFlippedX) isFlippedX = false;
        }

        if(firingBullet && timeToCool == 0)
        {
            timeToCool = coolDownTime;
            fireBullet();
        }

        //direction.x = (float)Math.cos(Math.toRadians(angle));
        //direction.y = (float)Math.sin(Math.toRadians(angle));

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

    @Override
    public void handleKeyPress(int keyCode)
    {
        if(keyCode == up)
        {
            //direction.x = (float)Math.cos(Math.toRadians(90.0));
            //direction.y = (float)Math.sin(Math.toRadians(90.0));
            movingUp = true;
        }

        if(keyCode == down)
        {
            //direction.x = (float)Math.cos(Math.toRadians(270.0));
            //direction.y = (float)Math.sin(Math.toRadians(270.0));
            movingDown = true;
        }

        if(keyCode == left)
        {
            //direction.x = (float)Math.cos(Math.toRadians(180.0));
            //direction.y = (float)Math.sin(Math.toRadians(180.0));
            movingLeft = true;
        }

        if(keyCode == right)
        {
            //direction.x = (float)Math.cos(Math.toRadians(0.0));
            //direction.y = (float)Math.sin(Math.toRadians(0.0));
            movingRight = true;
        }

        //direction.x = 0.0f;
        //direction.y = 0.0f;

        if(keyCode == fire)
        {
            firingBullet = true;
        }
    }

    @Override
    public void handleKeyRelease(int keyCode)
    {
        if(keyCode == up)
        {
            //direction.x = (float)Math.cos(Math.toRadians(90.0));
            //direction.y = (float)Math.sin(Math.toRadians(90.0));
            movingUp = false;
        }

        if(keyCode == down)
        {
            //direction.x = (float)Math.cos(Math.toRadians(270.0));
            //direction.y = (float)Math.sin(Math.toRadians(270.0));
            movingDown = false;
        }

        if(keyCode == left)
        {
            //direction.x = (float)Math.cos(Math.toRadians(180.0));
            //direction.y = (float)Math.sin(Math.toRadians(180.0));
            movingLeft = false;
        }

        if(keyCode == right)
        {
            //direction.x = (float)Math.cos(Math.toRadians(0.0));
            //direction.y = (float)Math.sin(Math.toRadians(0.0));
            movingRight = false;
        }

        if(keyCode == fire)
        {
            firingBullet = false;
        }
    }

    private void fireBullet()
    {
        Vector2 pos = position.cpy();

        if(isFlippedX)
        {
            pos.add(-dimension.x / 2 + 1, 0);
        }
        else
        {
            pos.add(dimension.x / 2 + 1, 0);
        }

        world.addBullet(pos, new Vector2(20.0f, 10.0f), maxSpeed * 3, angle, isFlippedX, isFlippedY);
    }

//    @Override
//    public void resolveCollision(CollidableObject collidableObject)
//    {
//        Rectangle rect = hitBox.intersection(collidableObject.hitBox);
//
//        if(collidableObject instanceof Plane)
//        {
//            if(hitBox.x < collidableObject.hitBox.x)
//            {
//                position.x -= rect.width;
//            }
//            else if(hitBox.x > collidableObject.hitBox.x)
//            {
//                position.x += rect.width;
//            }
//
//            if(hitBox.y < collidableObject.hitBox.y)
//            {
//                position.y -= rect.height;
//            }
//            else if(hitBox.y > collidableObject.hitBox.y)
//            {
//                position.y += rect.height;
//            }
//        }
//
//        //updateHitBox();
//    }
}
