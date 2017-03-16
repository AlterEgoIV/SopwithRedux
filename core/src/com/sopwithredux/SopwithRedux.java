package com.sopwithredux;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sopwithredux.screens.MainMenuScreen;
import com.sopwithredux.screens.PlayScreen;
import com.sopwithredux.screens.ScreenName;

import java.util.HashMap;
import java.util.Map;

public class SopwithRedux extends Game
{
	public Map<ScreenName, Screen> screens;
	private AssetManager assetManager;

	@Override
	public void create()
	{
		// Load assets first
		assetManager = new AssetManager();
		loadAssets();

		screens = new HashMap<ScreenName, Screen>();
		createScreens();
		setScreen(screens.get(ScreenName.PLAYSCREEN));
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose()
	{
		assetManager.dispose();
	}

	private void createScreens()
	{
		screens.put(ScreenName.MAINMENU_SCREEN, new MainMenuScreen(this, assetManager));
		screens.put(ScreenName.PLAYSCREEN, new PlayScreen(this, assetManager));
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
