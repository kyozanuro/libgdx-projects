package com.me.mygdxgame.client;

import com.me.mygdxgame.GameScreen;
import com.me.mygdxgame.MyGdxGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication
{
	@Override
	public GwtApplicationConfiguration getConfig()
	{

		int width = (GameScreen.NUM_HORIZONTAL_CELLS + 6)
				* (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		int height = (GameScreen.NUM_VERTICAL_CELLS - 2)
				* (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(
				width, height);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener()
	{
		return new MyGdxGame();
	}
}