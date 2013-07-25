package com.me.mygdxgame;

public abstract class RaceCar extends RetroObject
{
	
	public RaceCar()
	{
		int[][] a =
			{
			{ 9, 1, 9 },
			{ 1, 1, 1 },
			{ 9, 1, 9 },
			{ 1, 9, 1 }//9 is invisible (like 0), but use for collision detection

			};
		this.array = a;
	}

	

}
