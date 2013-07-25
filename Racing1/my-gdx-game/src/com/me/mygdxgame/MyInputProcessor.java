package com.me.mygdxgame;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor implements InputProcessor
{
	IKeyDownOneTime KeyDownOneTime;
	
	public boolean IsKeyDown = false;
	public int CurrentKeyDownCode;
	@Override
	public boolean keyDown(int keycode)
	{
		IsKeyDown = true;
		CurrentKeyDownCode = keycode;
		if (KeyDownOneTime!=null)
		{
			KeyDownOneTime.KeyDownOneTimeHandler();
		}
			
		System.out.println("keycode = "+keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (keycode == this.CurrentKeyDownCode)
		{
			//mean up the same key
			IsKeyDown = false;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

}

interface IKeyDownOneTime
{
	public void KeyDownOneTimeHandler();
}
