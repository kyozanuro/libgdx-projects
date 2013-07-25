package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class MyKeyDownHandler
{
	public IKeyDownHandler KeyDown;//key down continuously
	public IKeyDownHandler KeyDownOneTime;//key down only one time
	MyInputProcessor inputProcessor;
	final int MAX_SPEED_KEY_DOWN = 30;
	final int MIN_SPEED_KEY_DOWN = 2;
	int delayCount;
	int delaySpeedKeyDown = MAX_SPEED_KEY_DOWN;
	public MyKeyDownHandler()
	{
		inputProcessor = new MyInputProcessor();
		inputProcessor.KeyDownOneTime = new IKeyDownOneTime()
		{
			
			@Override
			public void KeyDownOneTimeHandler()
			{
				delaySpeedKeyDown = MAX_SPEED_KEY_DOWN;
				delayCount = 0;
				if (KeyDown!=null)
				{
					KeyDown.KeyDownHandler(inputProcessor.CurrentKeyDownCode);
				}
				
				
				if (KeyDownOneTime!=null)
				{
					KeyDownOneTime.KeyDownHandler(inputProcessor.CurrentKeyDownCode);
				}
			}
		};
	
		Gdx.input.setInputProcessor(inputProcessor);
	}
	public void Update()
	{
		this.delayCount++;
		if (this.delayCount < 0)
		{
			this.delayCount = 0;
		}
		
		if (inputProcessor.IsKeyDown)
		{
			this.delaySpeedKeyDown--;
			if (this.delaySpeedKeyDown < MIN_SPEED_KEY_DOWN)
			{
				this.delaySpeedKeyDown = MIN_SPEED_KEY_DOWN;
			}
			if (this.delayCount % this.delaySpeedKeyDown == 0)
			{
				if (this.KeyDown!=null)
				{
					this.KeyDown.KeyDownHandler(inputProcessor.CurrentKeyDownCode);
				}
			}
		}
	}
	
	
}
interface IKeyDownHandler
{
	void KeyDownHandler(int keycode);
}
