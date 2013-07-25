package com.me.mygdxgame;
 
public class ComputerCar extends RaceCar
{ 
	
	public ComputerCar(int x, int y)
	{
		this.position = new Cell(x, y);
		this.Update();
	
	}
	
	public boolean MoveDown()
	{
		boolean isOK = true;
		this.Clear();
		this.position.Y+=1;
		if (this.position.Y > GameScreen.NUM_VERTICAL_CELLS - 1)
		{
			this.ShouldRemove = true;
		}
		isOK = !this.CheckCollision();
		this.Update();
		return isOK;
	}
	
}
