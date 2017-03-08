package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
    private double rotationSpeed;
    private int forward, rotateLeft, rotateRight, fire, coolDownTime, timeToCool;

    public Plane(World world, Vector2 position, Vector2 dimension, double speed, double angle, double rotationSpeed, Color colour,
                 int forward, int rotateLeft, int rotateRight, int fire)
    {
        super(world, position, dimension, speed, angle, colour);
        this.rotationSpeed = rotationSpeed;
        this.forward = forward;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.fire = fire;
        coolDownTime = 60 / 4;
        timeToCool = 0;

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(colour);
        pixmap.drawLine(0, 0, (int)dimension.x, (int)dimension.y / 2); // Outside rotateLeft
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x, (int)dimension.y / 2); // Outside rotateRight
        pixmap.drawLine(0, 0, (int)dimension.x / 2, (int)dimension.y / 2); // Inside rotateLeft
        pixmap.drawLine(0, (int)dimension.y, (int)dimension.x / 2, (int)dimension.y / 2); // Inside rotateRight

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
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
        if(Gdx.input.isKeyPressed(forward))
        {
            direction.scl((float)speed);
            position.add(direction);
        }

        if(Gdx.input.isKeyPressed(rotateLeft))
        {
            angle += rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(rotateRight))
        {
            angle -= rotationSpeed;
        }

        if(Gdx.input.isKeyPressed(fire) && timeToCool == 0)
        {
            timeToCool = coolDownTime;
            fireBullet();
        }
    }

    private void fireBullet()
    {
        world.addBullet(new Vector2(position.x, position.y), new Vector2(10, 1), speed * 2, angle, colour);
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
