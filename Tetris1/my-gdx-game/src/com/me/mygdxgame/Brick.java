package com.me.mygdxgame;

public abstract class Brick
{
	protected int[][][] array;
	protected int numForm;// number of form types
	protected int currentForm;// current form
	
	
	public int X;
	public int Y;
	public boolean IsCheckingScore;//check score effect
	int countScoreMultiply = 0;
	public Brick(int num_form)
	{
		this.X = 4;
		this.numForm = num_form;
		this.currentForm = GameScreen.GlobalRandom.nextInt(num_form);
		array = new int[num_form][][];

	}

	public boolean Init()
	{
		int[][] tmp = createTmpArrayFrom(GameScreen.Array);
		boolean invalid = updateNewCells(tmp);
		if (!invalid)
		{
			// valid -> update GameScreen array
			GameScreen.Array = tmp;
		}
		return !invalid;
	}

	public Brick Clone()
	{
		return null;
	}

	public void Rotate()
	{
		// rorate brick

		int[][] tmp = createTmpArrayFrom(GameScreen.Array);

		clearOldCell(tmp);

		int oldForm = this.currentForm;
		if (this.currentForm < this.numForm - 1)
		{
			this.currentForm++;
		} else
		{
			this.currentForm = 0;
		}

		if (updateNewCells(tmp))
		{
			// invalid
			this.currentForm = oldForm;

			// return false;//cannot go down
		} else
		{
			// valid -> update GameScreen array
			GameScreen.Array = tmp;
			// return true;//can go down
		}
	}

	public boolean Go(int direction)
	{
		// brick goes down

		int[][] tmp = createTmpArrayFrom(GameScreen.Array);

		clearOldCell(tmp);
		if (direction == 0)
		{
			this.X--;
		} else if (direction == 1)
		{
			this.X++;
		} else if (direction == 2)
		{
			this.Y++;
		}

		if (updateNewCells(tmp))
		{
			// invalid
			if (direction == 0)
			{
				this.X++;
			} else if (direction == 1)
			{
				this.X--;
			} else if (direction == 2)
			{
				this.Y--;
			}

			return false;// cannot go down
		} else
		{
			// valid -> update GameScreen array
			GameScreen.Array = tmp;
			return true;// can go down
		}

	}

	public boolean CheckScore()
	{
		//int count = 0;
		for (int i = 3; i >= 0; i--)
		{
			if (this.Y + i >= GameScreen.NUM_VERTICAL_CELLS)
				continue;
			int j;
			for (j = 0; j < GameScreen.NUM_HORIZONTAL_CELLS; j++)
			{

				if (GameScreen.Array[this.Y + i][j] == 0)
				{
					break;
				}
			}
			if (j == GameScreen.NUM_HORIZONTAL_CELLS)
			{
				// increase score
				Player.CurrentScore += Player.ScoreEachLine * (++countScoreMultiply);
				
				//change game speed here
				if (Player.CurrentSpeed < 14)
				{
					if (Player.CurrentScore >= Player.ChangeSpeedAtScore)
					{
						Player.ChangeSpeedAtScore*= 2;
						Player.CurrentSpeed++;
						Player.CurrentSpeedDelay = ( - 5 * (Player.CurrentSpeed + 1) + 55 );
					}
				}
				// update array

				for (int y = this.Y + i; y > 0; y--)
				{
					GameScreen.Array[y] = GameScreen.Array[y - 1];
				}
				//i++; //increase i here because the lines were changed(down) -> for multiple lines checking
				
				//for single line checking effect
				//found one line then exit
				System.out.println("Increase score, return");
				return true;
				
			}

		}
		//here mean not found any line satisfy the condition
		//stop effect
		IsCheckingScore = false;
		countScoreMultiply = 0;//reset
		System.out.println("Stop check score");
		return false;

	}
	
	

	public int[][] GetArray()
	{
		return this.array[currentForm];
	}

	/*
	 * private void processUpdate() { int[][] tmp = GameScreen.Array.clone();
	 * clearOldCell(tmp); if (!updateNewCells(tmp)) { // valid -> update
	 * GameScreen array
	 * 
	 * GameScreen.Array = tmp; } }
	 */

	protected int[][] createTmpArrayFrom(int[][] array)
	{
		int[][] tmp = new int[array.length][];
		for (int i = 0; i < tmp.length; i++)
		{
			tmp[i] = new int[array[i].length];
			for (int j = 0; j < array[i].length; j++)
			{
				tmp[i][j] = array[i][j];
			}
		}
		return tmp;
	}

	private void clearOldCell(int[][] tmp)
	{
		// clear old cells:
		int[][] brickArray = this.GetArray();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{

				if (brickArray[i][j] == 1)
					tmp[this.Y + i][this.X + j] = 0;
			}
		}
	}

	protected boolean updateNewCells(int[][] tmp)
	{
		// update new cells into array:
		boolean invalid = false;
		int[][] brickArray = this.GetArray();

		// check to find a invalid cell
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (brickArray[i][j] == 0)
					continue;

				// only process at filled cell (1)
				if (this.Y + i >= GameScreen.NUM_VERTICAL_CELLS
						|| this.X + j >= GameScreen.NUM_HORIZONTAL_CELLS
						|| this.Y + i < 0 || this.X + j < 0)
				{
					if (brickArray[i][j] == 1)
					{
						invalid = true;
						break;// can not update this cell (invalid cell)
					}
					continue;
				}

				if (tmp[this.Y + i][this.X + j] == 1 && brickArray[i][j] == 1)
				{
					// can not update this cell (conflict with old cell)
					invalid = true;
					break;
				}

			}
		}
		if (!invalid)
		{
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 4; j++)
				{
					if (brickArray[i][j] == 1)
					{
						tmp[this.Y + i][this.X + j] = brickArray[i][j];
					}
				}
			}
		}
		return invalid;
	}

}
