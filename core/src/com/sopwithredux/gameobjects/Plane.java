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
    private int up, down, left, right, fire, dropBomb, bulletCoolDownTime, bulletTimeToCool, bombCoolDownTime, bombTimeToCool;
    private double maxSpeed;

    public Plane(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed,
                 double angle, boolean isFlippedX, boolean isFlippedY, int up, int down, int left, int right, int fire, int dropBomb)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.fire = fire;
        this.dropBomb = dropBomb;
        bulletCoolDownTime = 60 / 4;
        bombCoolDownTime = 60;
        bulletTimeToCool = 0;
        bombTimeToCool = 0;
        maxSpeed = this.speed;
    }

    @Override
    public void update()
    {
        //speed = 0.0;

        handleInput();

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        updateHitBox();

        if(bulletTimeToCool > 0) bulletTimeToCool--;
        if(bombTimeToCool > 0) bombTimeToCool--;
    }

    @Override
    public void handleInput()
    {
        if(Gdx.input.isKeyPressed(up))
        {
            //speed = maxSpeed;
            position.y += speed * Gdx.graphics.getDeltaTime();
        }

        if(Gdx.input.isKeyPressed(down))
        {
            //speed = maxSpeed;
            position.y -= speed * Gdx.graphics.getDeltaTime();
        }

        if(Gdx.input.isKeyPressed(left))
        {
            //speed = maxSpeed;
            position.x -= speed * Gdx.graphics.getDeltaTime();
            angle = 180.0;
            if(!isFlippedY) isFlippedY = true;
        }

        if(Gdx.input.isKeyPressed(right))
        {
            //speed = maxSpeed;
            position.x += speed * Gdx.graphics.getDeltaTime();
            angle = 0.0;
            if(isFlippedY) isFlippedY = false;
        }

        if(Gdx.input.isKeyPressed(fire) && bulletTimeToCool == 0)
        {
            bulletTimeToCool = bulletCoolDownTime;
            fireBullet();
        }

        if(Gdx.input.isKeyPressed(dropBomb) && bombTimeToCool == 0)
        {
            bombTimeToCool = bombCoolDownTime;
            dropBomb();
        }
    }

    private void fireBullet()
    {
        Vector2 pos = position.cpy();
        Vector2 dim = new Vector2(dimension.x / 7, dimension.y / 7);

        if(isFlippedY)
        {
            pos.add(((-dimension.x / 2) - dim.x / 2) - 1, 0);
        }
        else
        {
            pos.add(((dimension.x / 2) + dim.x / 2) + 1, 0);
        }

        world.addBullet(pos, dim, maxSpeed * 3, angle, isFlippedX, isFlippedY);
    }

    private void dropBomb()
    {
        Vector2 pos = position.cpy();
        Vector2 dim = new Vector2(dimension.x / 3, dimension.y / 3);

        pos.y -= (dimension.y / 2 + dim.y) - 1;

        world.addBomb(pos, dim, maxSpeed * 1.1, angle, isFlippedX, isFlippedY);
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
