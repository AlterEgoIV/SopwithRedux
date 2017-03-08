package com.sopwithredux;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Carl on 08/03/2017.
 */
public abstract class GameObject
{
    protected World world;
    protected Vector2 position, dimension, direction;
    protected double speed, angle;
    protected Texture image;
    protected Color colour;

    protected GameObject(World world, Vector2 position, Vector2 dimension, double speed, double angle, Color colour)
    {
        this.world = world;
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;
        this.colour = colour;
        direction = new Vector2(0, 0);
    }

    public abstract void update();

    public void render(SpriteBatch batch)
    {
        batch.draw(image,
          position.x - dimension.x / 2, position.y - dimension.y / 2,
          dimension.x / 2, dimension.y / 2, // Origin is center point for rotation
          dimension.x, dimension.y,
          1f, 1f,
          (float)angle,
          0, 0, (int)dimension.x, (int)dimension.y,
          false, false);
    }
}