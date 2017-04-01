package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.*;
import com.sopwithredux.Event;
import com.sopwithredux.gameobjects.powerups.BombPowerUp;
import com.sopwithredux.gameobjects.powerups.FuelPowerUp;
import com.sopwithredux.gameobjects.projectiles.Bullet;

import java.awt.*;

/**
 * Created by Carl on 08/03/2017.
 */
public class Plane extends CollidableObject implements InputHandler
{
    private int up, down, left, right, fire, dropBomb,
                bulletCoolDownTime, bulletTimeToCool, bombCoolDownTime, bombTimeToCool, fuelDecreaseTime, timeToDecreaseFuel,
                lives, outposts, bombs, maxBombs, fuel, maxFuel, damageTaken, maxDamage;
    private double maxSpeed;
    private boolean isPlayer1, falling;

    public Plane(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed,
                 double angle, boolean isFlippedX, boolean isFlippedY, int up, int down, int left, int right, int fire, int dropBomb,
                 boolean isPlayer1)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.fire = fire;
        this.dropBomb = dropBomb;
        this.isPlayer1 = isPlayer1;
        bulletCoolDownTime = 60 / 4;
        bombCoolDownTime = 60;
        bulletTimeToCool = 0;
        bombTimeToCool = 0;
        fuelDecreaseTime = 30;
        timeToDecreaseFuel = 30;
        lives = 5;
        outposts = 5;
        maxBombs = 5;
        bombs = 5;
        maxFuel = 100;
        fuel = 100;
        damageTaken = 0;
        maxDamage = 5;
        maxSpeed = this.speed;
        falling = false;
    }

    @Override
    public void update()
    {
        if(falling)
        {
            position.y -= speed * Gdx.graphics.getDeltaTime();
        }
        else
        {
            handleInput();
        }

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));

        if(position.y <= Gdx.graphics.getHeight() / 5)
        {
            --lives;
            sendEvent(this, Event.PLANE_LOST_LIFE);
            position.y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 5;
            fuel = maxFuel;
            sendEvent(this, Event.MAX_FUEL_RESTORED);
        }

        updateHitBox();

        if(bulletTimeToCool > 0) --bulletTimeToCool;
        if(bombTimeToCool > 0) --bombTimeToCool;

        if(timeToDecreaseFuel > 0)
        {
            --timeToDecreaseFuel;

            if(timeToDecreaseFuel == 0)
            {
                timeToDecreaseFuel = fuelDecreaseTime;

                if(fuel > 0)
                {
                    --fuel;
                    falling = false;
                    sendEvent(this, Event.PLANE_LOST_FUEL);
                }
                else
                {
                    falling = true;
                }
            }
        }
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
            if(!isFlippedY) isFlippedY = true;
        }

        if(Gdx.input.isKeyPressed(right))
        {
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
        if(bombs > 0)
        {
            --bombs;

            sendEvent(this, Event.PLANE_LOST_BOMB);

            Vector2 pos = position.cpy();
            Vector2 dim = new Vector2(dimension.x / 3, dimension.y / 3);

            pos.y -= (dimension.y / 2 + dim.y) - 1;

            world.addBomb(pos, dim, maxSpeed * 1.1, angle, isFlippedX, isFlippedY);
        }
    }

    public void takeDamage()
    {
        ++damageTaken;

        if(damageTaken == maxDamage)
        {
            --lives;
            damageTaken = 0;

            sendEvent(this, Event.PLANE_LOST_LIFE);
        }
    }

    public void addFuel(FuelPowerUp fuelPowerUp)
    {
        if(!(fuel >= maxFuel))
        {
            world.remove(fuelPowerUp);
        }

        fuel += 10;
        if(fuel > maxFuel) fuel = maxFuel;
        sendEvent(this, Event.FUEL_RESTORED);
    }

    public void addBomb(BombPowerUp bombPowerUp)
    {
        if(!(bombs >= maxBombs))
        {
            world.remove(bombPowerUp);
        }

        ++bombs;
        if(bombs > maxBombs) bombs = maxBombs;
        sendEvent(this, Event.BOMBS_INCREASED);
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

    public int getLives()
    {
        return lives;
    }

    public int getOutposts()
    {
        return outposts;
    }

    public int getBombs()
    {
        return bombs;
    }

    public int getFuel()
    {
        return fuel;
    }

    public boolean isPlayer1()
    {
        return isPlayer1;
    }
}
