package com.sopwithredux;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.sopwithredux.screens.*;

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
		setScreen(screens.get(ScreenName.MAIN_MENU_SCREEN));
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
		assetManager.load("startmenu.png", Texture.class);
		assetManager.load("controls.png", Texture.class);
		assetManager.load("startbutton.png", Texture.class);
		assetManager.load("startselect.png", Texture.class);
		assetManager.load("controlbutton.png", Texture.class);
		assetManager.load("controlselect.png", Texture.class);
		assetManager.load("exitbutton.png", Texture.class);
		assetManager.load("exitselect.png", Texture.class);
		assetManager.load("gameoverp1win.png", Texture.class);
		assetManager.load("gameoverp2win.png", Texture.class);
		assetManager.finishLoading();
	}

	private void createScreens()
	{
		screens.put(ScreenName.MAIN_MENU_SCREEN, new MainMenuScreen(this, assetManager));
		screens.put(ScreenName.PLAY_SCREEN, new PlayScreen(this, assetManager));
		screens.put(ScreenName.CONTROLS_SCREEN, new ControlsScreen(this, assetManager));
		screens.put(ScreenName.GAME_OVER_SCREEN, new GameOverScreen(this, assetManager));
	}
}
