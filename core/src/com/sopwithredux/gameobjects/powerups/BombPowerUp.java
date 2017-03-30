package com.sopwithredux.gameobjects.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 30/03/2017.
 */
public class BombPowerUp extends PowerUp
{
    public BombPowerUp(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }
}
