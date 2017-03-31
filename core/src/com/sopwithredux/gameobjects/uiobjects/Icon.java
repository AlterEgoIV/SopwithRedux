package com.sopwithredux.gameobjects.uiobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 31/03/2017.
 */
public class Icon extends UIObject
{
    public Icon(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }

    @Override
    public void update()
    {

    }
}
