package com.sopwithredux.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 08/03/2017.
 */
public class Bullet extends Projectile
{
    public Bullet(World world, Texture image, Vector2 position, Vector2 dimension, Vector2 sourceDimension, double speed, double angle)
    {
        super(world, image, position, dimension, sourceDimension, speed, angle);
        initialise(position, dimension, speed, angle);
    }

    public void initialise(Vector2 position, Vector2 dimension, double speed, double angle)
    {
        this.position = position;
        this.dimension = dimension;
        this.speed = speed;
        this.angle = angle;

        direction.x = (float)Math.cos(Math.toRadians(angle));
        direction.y = (float)Math.sin(Math.toRadians(angle));
        direction.scl((float)speed);
        //this.position.add(direction);

        Pixmap pixmap = new Pixmap((int)dimension.x, (int)dimension.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.drawLine(0, 0, (int)dimension.x, 0);

        image = new Texture(pixmap);
        image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // Reduces jagged lines

        pixmap.dispose();
    }

    @Override
    public void update()
    {
        direction.nor();
        direction.scl((float)speed * Gdx.graphics.getDeltaTime());
        position.add(direction);

        if(position.x > Gdx.graphics.getWidth() || position.x < 0 ||
          position.y > Gdx.graphics.getHeight() || position.y < 0)
        {
            world.remove(this);
        }

        updateRectangle();
    }

    @Override
    public void resolveCollision(CollidableObject collidableObject)
    {

    }
}
