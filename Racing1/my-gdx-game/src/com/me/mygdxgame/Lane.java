package com.me.mygdxgame;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Lane
{
	private ArrayList<Barrier> listOfBarriers;
	private DIRECTION flowDirection;
	private boolean useRandom;
	private int numOfCells = 2;
	private int distanceBetweenBarriers = 1;
	private Cell position;
	private boolean vertical;

	public Lane(DIRECTION flow_direction, Cell pos, boolean use_random)
	{
		if (pos.X < 0 || 
			pos.X > GameScreen.NUM_HORIZONTAL_CELLS - 1 ||
			pos.Y < 0 || pos.Y > GameScreen.NUM_VERTICAL_CELLS - 1)
				
		{
			throw new IllegalArgumentException("invalid position to init a lane");
		}
		this.listOfBarriers = new ArrayList<Barrier>();
		this.flowDirection = flow_direction;
		this.position = pos;
		this.useRandom = use_random;
		init();
	}

	private void init()
	{
		// init the list
		int currentPos;// can be x/y depends on the flowDirection
		if (this.flowDirection == DIRECTION.LEFT
				|| this.flowDirection == DIRECTION.RIGHT)
		{
			// Horizontal flow
			currentPos = this.position.X;
			vertical = false;
		} else
		{
			// Vertical flow
			currentPos = this.position.Y;
			vertical = true;

		}
/*
		while (true)
		{
			Barrier barrier = new Barrier();
			listOfBarriers.add(barrier);
			barrier.NumberOfCells = numOfCells;
			barrier.FirstCell = new Cell();
			if (this.flowDirection == DIRECTION.LEFT
					|| this.flowDirection == DIRECTION.RIGHT)
			{

				// Horizontal flow
				barrier.FirstCell.Y = this.position.Y;
				barrier.FirstCell.X = currentPos;
				if (this.flowDirection == DIRECTION.RIGHT)
				{
					// flow from left to right
					if (barrier.FirstCell.X + barrier.NumberOfCells > GameScreen.NUM_HORIZONTAL_CELLS - 1)
					{
						break;
					}
				} else
				{
					// flow from right to left

					if (barrier.FirstCell.X - barrier.NumberOfCells < 0)
					{
						break;
					}
				}

			} else
			{

				// Vertical flow
				barrier.FirstCell.X = this.position.X;
				barrier.FirstCell.Y = currentPos;
				if (this.flowDirection == DIRECTION.BOT)
				{
					// flow from top to bot
					if (barrier.FirstCell.Y + barrier.NumberOfCells > GameScreen.NUM_VERTICAL_CELLS - 1)
					{
						break;
					}
				} else
				{
					// flow from bot to top

					if (barrier.FirstCell.Y - barrier.NumberOfCells < 0)
					{
						break;
					}
				}
			}
			
			currentPos += barrier.NumberOfCells + this.distanceBetweenBarriers;// advance
																				// the
																				// current
																				// pos
		}*/
		
		if (this.vertical)
		{

			while (true)
			{
				Barrier barrier = new Barrier();
				listOfBarriers.add(barrier);
				barrier.NumberOfCells = numOfCells;
				barrier.FirstCell = new Cell();
				
				barrier.FirstCell.X = this.position.X;
				barrier.FirstCell.Y = currentPos;
				if (this.flowDirection == DIRECTION.BOT)
				{
					// flow from top to bot
					if (barrier.FirstCell.Y + barrier.NumberOfCells > GameScreen.NUM_VERTICAL_CELLS - 1)
					{
						break;
					}
					currentPos += barrier.NumberOfCells + this.distanceBetweenBarriers;
				} else
				{
					// flow from bot to top

					if (barrier.FirstCell.Y - barrier.NumberOfCells < 0)
					{
						break;
					}
					currentPos-= barrier.NumberOfCells + this.distanceBetweenBarriers;
				}
			}
		}else
		{
			while (true)
			{
				Barrier barrier = new Barrier();
				listOfBarriers.add(barrier);
				barrier.NumberOfCells = numOfCells;
				barrier.FirstCell = new Cell();
				
				barrier.FirstCell.Y = this.position.Y;
				barrier.FirstCell.X = currentPos;
				if (this.flowDirection == DIRECTION.RIGHT)
				{
					// flow from left to right
					if (barrier.FirstCell.X + barrier.NumberOfCells > GameScreen.NUM_HORIZONTAL_CELLS - 1)
					{
						break;
					}
					currentPos += barrier.NumberOfCells + this.distanceBetweenBarriers;
				} else
				{
					// flow from right to left

					if (barrier.FirstCell.X - barrier.NumberOfCells < 0)
					{
						break;
					}
					currentPos -= barrier.NumberOfCells + this.distanceBetweenBarriers;
				}
			}
		}
				
		
		Update(); //update first time
	}

	/**
	 * This make no sense if use random to generate
	 * 
	 * @param num
	 *            number of cells of each barrier
	 */
	public void SetNumOfCells(int num)
	{
		this.numOfCells = num;
	}

	/**
	 * This makes no sense if use random to generate
	 * 
	 * @param num
	 */
	public void SetDistanceBetweenBarriers(int num)
	{
		this.distanceBetweenBarriers = num;
	}

	public void Update()
	{
		// update mean there must be something change
		// clear olds
		/*if (this.vertical)
		{
			
			for (int i = this.position.Y; i < GameScreen.NUM_VERTICAL_CELLS; i++)
			{
				GameScreen.Array[i][this.position.X] = 0;
			}
		} else
		{
			for (int i = this.position.X; i < GameScreen.NUM_HORIZONTAL_CELLS; i++)
			{
				GameScreen.Array[this.position.X][i] = 0;
			}
		}*/
		//clear old things, check to add new barrier & remove the last barrier(when it comes outside)
		Barrier first = this.listOfBarriers.get(0);
		Barrier newBarrier = null;
		if (this.flowDirection == DIRECTION.BOT)
		{
			//clear old things
			for (int i = this.position.Y; i < GameScreen.NUM_VERTICAL_CELLS; i++)
			{
				GameScreen.Array[i][this.position.X] = 0;
			}
			
			if (first.FirstCell.Y == this.position.Y)
			{
				//add new barrier
				newBarrier = new Barrier();
				newBarrier.FirstCell = new Cell(this.position.X, this.position.Y - this.distanceBetweenBarriers - this.numOfCells);
				newBarrier.NumberOfCells = this.numOfCells;
				this.listOfBarriers.add(0, newBarrier);
			}
			
			//remove:
			if (this.listOfBarriers.get(this.listOfBarriers.size() - 1).FirstCell.Y > 
				GameScreen.NUM_VERTICAL_CELLS - 1)
			{
				this.listOfBarriers.remove(this.listOfBarriers.size() - 1);
			}
			
		}else if (this.flowDirection == DIRECTION.TOP)
		{
			//clear
			for (int i = this.position.Y; i >= 0; i--)
			{
				GameScreen.Array[i][this.position.X] = 0;
			}
			
			if (first.FirstCell.Y == this.position.Y)
			{
				//add new barrier
				newBarrier = new Barrier();
				newBarrier.FirstCell = new Cell(this.position.X, this.position.Y + this.distanceBetweenBarriers + this.numOfCells);
				newBarrier.NumberOfCells = this.numOfCells;
				this.listOfBarriers.add(0, newBarrier);
			}
			//remove:
			if (this.listOfBarriers.get(this.listOfBarriers.size() - 1).FirstCell.Y < 0)
				
			{
				this.listOfBarriers.remove(this.listOfBarriers.size() - 1);
			}
		}else if (this.flowDirection == DIRECTION.RIGHT)
		{
			//clear
			for (int i = this.position.X; i < GameScreen.NUM_HORIZONTAL_CELLS ; i++)
			{
				GameScreen.Array[this.position.Y][i] = 0;
			}
			
			
			if (first.FirstCell.X == this.position.X)
			{
				//add new barrier
				newBarrier = new Barrier();
				newBarrier.FirstCell = new Cell(this.position.X - this.distanceBetweenBarriers - this.numOfCells, this.position.Y);
				newBarrier.NumberOfCells = this.numOfCells;
				this.listOfBarriers.add(0, newBarrier);
			}
			//remove:
			if (this.listOfBarriers.get(this.listOfBarriers.size() - 1).FirstCell.X > GameScreen.NUM_HORIZONTAL_CELLS - 1)
				
			{
				this.listOfBarriers.remove(this.listOfBarriers.size() - 1);
			}
		}else if (this.flowDirection == DIRECTION.LEFT)
		{
			//clear
			for (int i = this.position.X; i >= 0; i--)
			{
				GameScreen.Array[this.position.Y][i] = 0;
			}
			
			if (first.FirstCell.X == this.position.X)
			{
				//add new barrier
				newBarrier = new Barrier();
				newBarrier.FirstCell = new Cell(this.position.X + this.distanceBetweenBarriers + this.numOfCells, this.position.Y);
				newBarrier.NumberOfCells = this.numOfCells;
				this.listOfBarriers.add(0, newBarrier);
			}
			//remove:
			if (this.listOfBarriers.get(this.listOfBarriers.size() - 1).FirstCell.X < 0)
				
			{
				this.listOfBarriers.remove(this.listOfBarriers.size() - 1);
			}
		}
		
		
		
		
		
		for (Barrier barrier : this.listOfBarriers)
		{
			

			int currentPos = 0;
			if (this.flowDirection == DIRECTION.RIGHT)
			{
				barrier.FirstCell.X++;
				for (int i = 0; i < barrier.NumberOfCells; i++)
				{
					currentPos = barrier.FirstCell.X + i;
					if (currentPos < GameScreen.NUM_HORIZONTAL_CELLS
							&& currentPos >= this.position.X)
					{
						GameScreen.Array[barrier.FirstCell.Y][currentPos] = 1;
					}
				}
			} else if (this.flowDirection == DIRECTION.LEFT)
			{
				barrier.FirstCell.X--;
				for (int i = 0; i < barrier.NumberOfCells; i++)
				{
					currentPos = barrier.FirstCell.X - i;
					if (currentPos <= this.position.X
							&& currentPos >= 0)
					{
						GameScreen.Array[barrier.FirstCell.Y][currentPos] = 1;
					}
				}
			} else if (this.flowDirection == DIRECTION.BOT)
			{
				barrier.FirstCell.Y++;
				for (int i = 0; i < barrier.NumberOfCells; i++)
				{
					currentPos = barrier.FirstCell.Y + i;
					if (currentPos < GameScreen.NUM_VERTICAL_CELLS
							&& currentPos >= this.position.Y)
					{
						GameScreen.Array[currentPos][barrier.FirstCell.X] = 1;
					}
				}
			} else if (this.flowDirection == DIRECTION.TOP)
			{
				barrier.FirstCell.Y--;
				for (int i = 0; i < barrier.NumberOfCells; i++)
				{
					currentPos = barrier.FirstCell.Y - i;
					if (currentPos <=this.position.Y
							&& currentPos >= 0)
					{
						GameScreen.Array[currentPos][barrier.FirstCell.X] = 1;
					}
				}

			}
		}
	}
	/*
	 * public void Draw(SpriteBatch batch) { //draw mean change GameScreen.Array
	 * 
	 * }
	 */

}

class Barrier
{
	public Cell FirstCell;
	public int NumberOfCells;
}
