package com.me.mygdxgame;

import java.util.ArrayList;

public class CarSpawner
{
	public ArrayList<ComputerCar> ListOfCars;
	private enum SpawnType {SINGLE_LEFT, SINGLE_RIGHT, LEFT_RIGHT, RIGHT_LEFT};
	//private enum SpawnType {SINGLE_LEFT, SINGLE_RIGHT, DOUBLE_LEFT, DOUBLE_RIGHT, LEFT_RIGHT, RIGHT_LEFT};
	private float spawnRatio = 0.7f;
	private float hardSpawnRatio = 0.05f;
	public CarSpawner()
	{
		ListOfCars = new ArrayList<ComputerCar>();
	}
	
	public void RemoveCar(ComputerCar computer)
	{
		computer.Clear();
		this.ListOfCars.remove(computer);
	}
	private void Spawn(SpawnType type)
	{
		switch (type)
		{
		case SINGLE_LEFT:
			ListOfCars.add(new ComputerCar(2, -4));
			 break;
		case SINGLE_RIGHT:
			ListOfCars.add(new ComputerCar(5, -4));
			break;
		/*case DOUBLE_LEFT:
			ListOfCars.add(new ComputerCar(2, -9));
			ListOfCars.add(new ComputerCar(2, -4));
			break;
		case DOUBLE_RIGHT:
			ListOfCars.add(new ComputerCar(5, -9));
			ListOfCars.add(new ComputerCar(5, -4));
			break;*/
			
		case LEFT_RIGHT:
			ListOfCars.add(new ComputerCar(2, -4));
			ListOfCars.add(new ComputerCar(5, -9));
			break;
			
		case RIGHT_LEFT:
			ListOfCars.add(new ComputerCar(2, -9));
			ListOfCars.add(new ComputerCar(5, -4));
			break;
		default:
			break;
		}
	}
	public void UpdateSpawn()
	{
		float a = GameScreen.GlobalRandom.nextFloat();
		 System.out.println(a);
		if (a < spawnRatio)
		{
			SpawnType randomType = SpawnType.SINGLE_LEFT;
			if (a < hardSpawnRatio && GameScreen.TravelCount > 500)
			{
				// LEFT_RIGHT or RIGHT_LEFT are hard
				
				randomType = SpawnType.values()[GameScreen.GlobalRandom.nextInt(2) + 2];
				System.out.println("hard wave" + randomType);
			} else
			{
				randomType = SpawnType.values()[GameScreen.GlobalRandom.nextInt(SpawnType.values().length- 2)];
			}
			System.out.println("SpawnType = " + randomType);
			this.Spawn(randomType);
		}
	}
	public boolean UpdateCars()
	{
		ArrayList<ComputerCar> removeList = new ArrayList<ComputerCar>();
		for (ComputerCar car:this.ListOfCars)
		{
			if (car.ShouldRemove)
			{
				Player.CurrentScore+=Player.ScoreEachCar;
				removeList.add(car);
			}
			if (!car.MoveDown())
				return false;
		}
		for (ComputerCar car:removeList)
		{
			this.ListOfCars.remove(car);
		}
		
		return true;
		
		
		
	}
}
