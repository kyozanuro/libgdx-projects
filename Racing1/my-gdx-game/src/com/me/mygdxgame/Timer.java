package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;

public class Timer
{
	public ITimerTick Tick;
	private float timeCumulation;
	private float timeInterval;
	private boolean isLoop;
	private boolean isStopped;
	public Timer(float time_interval, boolean loop)
	{
		timeInterval = time_interval;
		isLoop = loop;
	}
	public void Stop()
	{
		this.isStopped = true;
	}
	public void SetTimeInterval(float interval)
	{
		this.timeInterval = interval;
	}
	public void Update()
	{
		if (this.isStopped)
		{
			return;
		}
		this.timeCumulation += Gdx.graphics.getDeltaTime();
		if (this.timeCumulation >= this.timeInterval)
		{
			this.timeCumulation = this.timeInterval - this.timeInterval;
			if (Tick != null)
			{
				Tick.TickHandler(this);
			}
			if (!this.isLoop)
			{
				this.isStopped = true;
			}
		}
	}
}

interface ITimerTick
{
	public void TickHandler(Timer sender);
}
