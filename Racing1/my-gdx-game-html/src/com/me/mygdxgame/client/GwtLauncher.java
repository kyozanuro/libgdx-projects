package com.me.mygdxgame.client;

import com.me.mygdxgame.GameScreen;
import com.me.mygdxgame.MyGdxGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1,1);
		cfg.width = (GameScreen.NUM_HORIZONTAL_CELLS + 6) * (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		cfg.height = (GameScreen.NUM_VERTICAL_CELLS ) * (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new MyGdxGame();
	}
}