package com.me.mygdxgame;

import java.util.ArrayList;

public class PlayerCar extends RaceCar
{
	private int pos = 0;
	private int numBullets = 1;
	private ArrayList<Bullet> listOfBullets;
	private final int MAX_BULLETS = 16;
	private CarSpawner spawner;
	public int[][] ArrayBullet; 
	
	
	public PlayerCar(CarSpawner spawner)
	{
		this.position = new Cell(2, GameScreen.NUM_VERTICAL_CELLS - 4);
		this.spawner = spawner;
		listOfBullets = new ArrayList<Bullet>();
		this.Update();
		reallocateArrayBullet();
		
		
	}
	private void reallocateArrayBullet()
	{
		ArrayBullet = new int[4][];
		int count = this.numBullets;
		for (int i=0;i<ArrayBullet.length;i++)
		{
			ArrayBullet[i] = new int[4];
			for (int j=0;j<4;j++)
			{
				if (count > 0)
				{
					ArrayBullet[i][j] = 1;
					count --;
				}
			}
		}
	}
	public void IncreaseBullet()
	{
		if (this.numBullets < MAX_BULLETS)
			this.numBullets++;
		
		reallocateArrayBullet();
	}
	
	public void Fire()
	{
		if (this.numBullets > 0)
		{
			this.numBullets--;
			listOfBullets.add(new Bullet(this.position.X + 1, this.position.Y - 1));
			reallocateArrayBullet();
		}
		
	}
	public void UpdateBullets()
	{
		ArrayList<Bullet> removeList = new ArrayList<Bullet>();
		for(Bullet bullet : this.listOfBullets)
		{
			if (bullet.ShouldRemove)
				removeList.add(bullet);
			
			bullet.GoUp();
			ComputerCar computer = bullet.BulletCheckCollision(this.spawner.ListOfCars);
			if (computer != null)
			{
				//collision occur
				bullet.ShouldRemove = true;
				Player.CurrentScore += Player.ScoreEachCar * 2;
				this.spawner.RemoveCar(computer);
			}
		}
		for(Bullet bullet: removeList)
		{
			bullet.Clear();
			this.listOfBullets.remove(bullet);
		}
	}
	public boolean MoveLeft()
	{
		boolean isOK = true;
		if (this.pos != 0)
		{
			this.pos = 0;
			this.Clear();
			this.position.X -= 3;
			isOK = !this.CheckCollision();
			this.Update();
		}
		return isOK;//mean OK, no problem
	}
	public boolean MoveRight()
	{
		boolean isOK = true;
		if (this.pos != 1)
		{
			this.pos = 1;
			this.Clear();
			this.position.X += 3;
			isOK = !this.CheckCollision();
			this.Update();
		
		}
		return isOK;//ok
	}
}
