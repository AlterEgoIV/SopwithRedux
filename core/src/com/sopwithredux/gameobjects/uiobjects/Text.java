package com.sopwithredux.gameobjects.uiobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sopwithredux.World;

/**
 * Created by Carl on 31/03/2017.
 */
public class Text extends UIObject
{
    private BitmapFont bitmapFont;
    private GlyphLayout glyphLayout;

    public Text(World world, Vector2 position, double speed, double angle, double scaleX, double scaleY,
                boolean isFlippedX, boolean isFlippedY, String text)
    {
        super(world, position, speed, angle, isFlippedX, isFlippedY);

        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout(bitmapFont, text);
        position.x -= glyphLayout.width / 2;
        position.y -= glyphLayout.height / 2;
        bitmapFont.getData().setScale((float)scaleX, (float)scaleY);
    }

    public Text(World world, Vector2 position, double speed, double angle, double scaleX, double scaleY,
                boolean isFlippedX, boolean isFlippedY, int text)
    {
        super(world, position, speed, angle, isFlippedX, isFlippedY);

        String t = Integer.toString(text);
        bitmapFont = new BitmapFont();
        glyphLayout = new GlyphLayout(bitmapFont, t);
        position.x -= glyphLayout.width / 2;
        position.y -= glyphLayout.height / 2;
        bitmapFont.getData().setScale((float)scaleX, (float)scaleY);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        bitmapFont.draw(batch, glyphLayout, position.x, position.y);
    }

    public void setText(String text)
    {
        glyphLayout.setText(bitmapFont, text);
    }

    public void setText(int text)
    {
        String t = Integer.toString(text);
        glyphLayout.setText(bitmapFont, t);
    }
}
