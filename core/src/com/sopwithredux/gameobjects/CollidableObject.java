package com.sopwithredux.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

import java.awt.Rectangle;

/**
 * Created by Carl on 08/03/2017.
 */
public abstract class CollidableObject extends GameObject
{
    public Rectangle rectangle;
    private Texture rectangleImage;

    protected CollidableObject(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed)
    {
        super(world, image, position, dimension, sourceDimension, speed);
        rectangle = new Rectangle((int)position.x - (int)dimension.x / 2, (int)position.y - (int)dimension.y / 2,
          (int)dimension.x, (int)dimension.y);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.drawRectangle(0, 0, (int)dimension.x, (int)dimension.y);

        rectangleImage = new Texture(pixmap);
    }

    protected CollidableObject(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle);
        rectangle = new Rectangle((int)position.x - (int)dimension.x / 2, (int)position.y - (int)dimension.y / 2,
          (int)dimension.x, (int)dimension.y);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.drawRectangle(0, 0, (int)dimension.x, (int)dimension.y);

        rectangleImage = new Texture(pixmap);
    }

    protected void updateRectangle()
    {
        rectangle.setLocation((int)position.x - (int)dimension.x / 2, (int)position.y - (int)dimension.y / 2);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        //batch.draw(image, position.x - dimension.x / 2, position.y - dimension.y / 2, dimension.x, dimension.y);
        batch.draw(image,
          position.x - dimension.x / 2, position.y - dimension.y / 2,
          position.x - dimension.x / 2, position.y - dimension.y / 2, // Origin is center point for rotation
          dimension.x, dimension.y,
          1f, 1f,
          0,
          0, 0, (int)sourceDimension.x, (int)sourceDimension.y,
          isFlippedX, isFlippedY);
        //batch.draw(rectangleImage, rectangle.x, rectangle.y);
    }

    public boolean collidesWith(CollidableObject collidableObject)
    {
        // Returns true if the collidable object is not itself and there is a rectangle intersection
        return !collidableObject.equals(this) && rectangle.intersects(collidableObject.rectangle);
    }

    public abstract void resolveCollision(CollidableObject collidableObject);
}
