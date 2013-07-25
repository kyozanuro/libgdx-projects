package com.me.mygdxgame;

import java.util.ArrayList;

public class Bullet extends RetroObject
{
	
	public Bullet(int x, int y)
	{
		this.position = new Cell(x, y);
		int[][] a = 
			{
				{1}
			};
		this.array = a;
	}
	
	
	public ComputerCar BulletCheckCollision(ArrayList<ComputerCar> computers)
	{
		for (ComputerCar car : computers)
		{
			
			if (this.position.X <= car.position.X + car.array[0].length && this.position.X >= car.position.X)
			{
				if (this.position.Y <= car.position.Y + car.array.length && this.position.Y >=car.position.Y)
				{
					return car;
				}
			}
		}
		return null;//no collision
	}
	
	public void GoUp()//true if it hit a computer car
	{
		this.Clear();
		this.position.Y --;
		this.Update();
		if (this.position.Y < 0)
		{
			this.ShouldRemove = true;
		}
			
	}
}
