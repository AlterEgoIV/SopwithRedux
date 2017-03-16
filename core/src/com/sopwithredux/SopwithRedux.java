package com.sopwithredux;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sopwithredux.screens.PlayScreen;
import com.sopwithredux.screens.ScreenName;

import java.util.HashMap;
import java.util.Map;

public class SopwithRedux extends Game
{
	private Map<ScreenName, Screen> screens;
	private AssetManager assetManager;
	private World world;
	private SpriteBatch batch;

	@Override
	public void create()
	{
		screens = new HashMap<ScreenName, Screen>();
		createScreens();
		setScreen(screens.get(ScreenName.PLAYSCREEN));

		assetManager = new AssetManager();
		loadAssets();

		world = new World(assetManager);
		batch = new SpriteBatch();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.update();
		world.handleCollisions();

		batch.begin();
		world.render(batch);
		batch.end();
	}

	@Override
	public void dispose()
	{
		assetManager.dispose();
		batch.dispose();
	}

	private void createScreens()
	{
		screens.put(ScreenName.PLAYSCREEN, new PlayScreen(this));
	}

	private void loadAssets()
	{
		assetManager.load("background.png", Texture.class);
		assetManager.load("plane1.png", Texture.class);
		assetManager.load("plane2.png", Texture.class);
		assetManager.load("bullet.png", Texture.class);
		assetManager.load("bomb.png", Texture.class);
		assetManager.load("planeflash.png", Texture.class);
		assetManager.load("cloud.png", Texture.class);
		assetManager.load("bombpowerup.png", Texture.class);
		assetManager.load("fuelpowerup.png", Texture.class);
		assetManager.load("heart.png", Texture.class);
		assetManager.load("outpost1.png", Texture.class);
		assetManager.load("outpost2.png", Texture.class);
		assetManager.finishLoading();
	}
}
