package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.InputHandler;
import com.sopwithredux.World;

import java.awt.Rectangle;

/**
 * Created by Carl on 08/03/2017.
 */
public class Plane extends CollidableObject implements InputHandler
{
    private int up, down, left, right, fire, coolDownTime, timeToCool;

    public Plane(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed,
                 int up, int down, int left, int right, int fire)
    {
        super(world, image, position, dimension, sourceDimension, speed);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.fire = fire;
        coolDownTime = 60 / 4;
        timeToCool = 0;
    }

    @Override
    public void update()
    {
        handleInput();

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        updateRectangle();

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
            if(!isFlippedX) isFlippedX = true;
        }

        if(Gdx.input.isKeyPressed(right))
        {
            position.x += speed * Gdx.graphics.getDeltaTime();
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
        Vector2 pos = new Vector2(this.position.x, this.position.y);
        pos.add(dimension.x / 2 + 1, 0);
        world.addBullet(pos, new Vector2(20, 10), speed * 3, angle);
        //world.addBullet(new Vector2(position.x, position.y), new Vector2(10, 1), speed * 3, angle);
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {
        Rectangle rect = rectangle.intersection(collidableObject.rectangle);

        if(collidableObject instanceof Plane)
        {
            if(rectangle.x < collidableObject.rectangle.x)
            {
                position.x -= rect.width;
            }
            else if(rectangle.x > collidableObject.rectangle.x)
            {
                position.x += rect.width;
            }

            if(rectangle.y < collidableObject.rectangle.y)
            {
                position.y -= rect.height;
            }
            else if(rectangle.y > collidableObject.rectangle.y)
            {
                position.y += rect.height;
            }
        }
    }
}
