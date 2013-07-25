package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-gdx-game";
		cfg.useGL20 = false;
		cfg.width = (GameScreen.NUM_HORIZONTAL_CELLS + 6) * (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		cfg.height = (GameScreen.NUM_VERTICAL_CELLS - 2) * (GameScreen.CELL_SIZE + GameScreen.CELL_OFFSET);
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
