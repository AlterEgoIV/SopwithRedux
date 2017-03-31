package com.sopwithredux.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.sopwithredux.SopwithRedux;
import com.sopwithredux.World;

/**
 * Created by Carl on 16/03/2017.
 */
public class PlayScreen extends GameScreen
{
    private World world;
    //GlyphLayout glyphLayout;
    //BitmapFont bitmapFont;
    //Pixmap pixmap;
    //Texture texture;

    public PlayScreen(SopwithRedux game, AssetManager assetManager)
    {
        super(game);
        world = new World(assetManager);
//        bitmapFont = new BitmapFont();
//        bitmapFont.setFixedWidthGlyphs("hello");
//        bitmapFont.getData().scale(3);
//        bitmapFont.setColor(Color.BLACK);
//        glyphLayout = new GlyphLayout(bitmapFont, "Hello");
//        //glyphLayout.width = 20;
//        //glyphLayout.height = 20;
//        //bitmapFont.getData().setScale(glyphLayout.width, glyphLayout.height);
//        //bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        //bitmapFont.getData().scale(Gdx.graphics.getWidth() / 500);
//
//        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.BLACK);
//        pixmap.drawLine(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
//        pixmap.drawLine(0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
//
//        texture = new Texture(pixmap);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        world.update();
        world.handleCollisions();

        batch.begin();
        world.render(batch);
//        bitmapFont.draw(batch, glyphLayout, (Gdx.graphics.getWidth() / 2) - (glyphLayout.width / 2),
//                                            (Gdx.graphics.getHeight() / 2) + (glyphLayout.height / 2));
//        batch.draw(texture, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }
}
