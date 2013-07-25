package com.me.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGdxGame implements ApplicationListener
{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private GameScreen screen;

	@Override
	public void create()
	{
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.setToOrtho(true);
		batch = new SpriteBatch();
		screen = new GameScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void dispose()
	{
		batch.dispose();

	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(156/255.0f, 178/255.0f, 165/255.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		screen.Update();
		batch.begin();
		screen.Draw(batch);
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
}
