package com.sopwithredux.gameobjects.uiobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;
import com.sopwithredux.gameobjects.GameObject;

/**
 * Created by Carl on 31/03/2017.
 */
public abstract class UIObject extends GameObject
{
    protected UIObject(Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }

    protected UIObject(World world, Vector2 position, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, position, speed, angle, isFlippedX, isFlippedY);
    }

    protected UIObject(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }
}
