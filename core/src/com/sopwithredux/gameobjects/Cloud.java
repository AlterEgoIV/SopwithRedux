package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 16/03/2017.
 */
public class Cloud extends GameObject
{
    public Cloud(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension,
                    double speed, double angle, boolean isFlippedX, boolean isFlippedY)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle, isFlippedX, isFlippedY);
    }

    @Override
    public void update()
    {
        position.x -= speed * Gdx.graphics.getDeltaTime();

        if(position.x < -dimension.x)
        {
            position.x = Gdx.graphics.getWidth() + dimension.x / 2.0f;
        }
    }
}
