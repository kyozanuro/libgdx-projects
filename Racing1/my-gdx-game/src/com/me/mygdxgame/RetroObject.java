package com.me.mygdxgame;

public abstract class RetroObject
{
	protected int[][] array;
	
	protected Cell position;

	public boolean ShouldRemove;
	
	public boolean CheckCollision()
	{
		for (int i = 0; i < this.array.length; i++)
		{
			for (int j = 0; j < this.array[0].length; j++)
			{
				if (this.array[i][j] == 1 || this.array[i][j] == 9)//dont need this checking
				{
					int a = i + this.position.Y;
					int b = j + this.position.X;
					if (a >=0 && a < GameScreen.Array.length && 
							b >=0 & b < GameScreen.Array[a].length)
						{
							if (GameScreen.Array[a][b] == 1 || GameScreen.Array[a][b] == 9)
							{
								System.out.println("a = "+a +", b = "+b);
								System.out.println("Collision!!!!!!!!");
								return true;// collision occur
							}
						}

				}
			}
		}
		return false;
	}

	public void Clear()
	{
		// clears old cells:
		for (int i = 0; i < this.array.length; i++)
		{
			for (int j = 0; j < this.array[0].length; j++)
			{
				if (this.array[i][j] == 1)
				{
					int a = i + this.position.Y;
					int b = j + this.position.X;
					if (a >=0 && a < GameScreen.Array.length && 
							b >=0 & b < GameScreen.Array[a].length)
						{
							GameScreen.Array[a][b] = 0;
						}
				}
			}
		}
	}

	public void Update()
	{
		
		// update new cells
		for (int i = 0; i < this.array.length; i++)
		{
			for (int j = 0; j < this.array[0].length; j++)
			{
				if (this.array[i][j] == 1)
				{
					int a = i + this.position.Y;
					int b = j + this.position.X;
					if (a >=0 && a < GameScreen.Array.length && 
							b >=0 & b < GameScreen.Array[a].length)
						{
						GameScreen.Array[a][b] = 1;
						}
				}

			}
		}

	}

	/*private int[][] cloneArray(int[][] array)
	{
		int[][] newArray = new int[array.length][];
		for (int i = 0; i < array.length; i++)
		{
			newArray[i] = new int[array[i].length];
			for (int j = 0; j < array[i].length; j++)
			{
				newArray[i][j] = array[i][j];
			}
		}
		return newArray;
	}*/

}
