package com.me.mygdxgame;

public class BrickType3 extends Brick {
	private static int[][] a = { 
								{ 0, 0, 0, 0 }, 
								{ 1, 1, 1, 0 },
								{ 0, 1, 0, 0 },
								{ 0, 0, 0, 0 }
								};


	private static int[][] b = { 
								{ 0, 1, 0, 0 }, 
								{ 1, 1, 0, 0 },
								{ 0, 1, 0, 0 },
								{ 0, 0, 0, 0 }
								};
	
	private static int[][] c = { 
								{ 0, 1, 0, 0 }, 
								{ 1, 1, 1, 0 },
								{ 0, 0, 0, 0 },
								{ 0, 0, 0, 0 }
								};
	
	private static int[][] d = { 
								{ 0, 1, 0, 0 }, 
								{ 0, 1, 1, 0 },
								{ 0, 1, 0, 0 },
								{ 0, 0, 0, 0 }
								};
	
	public BrickType3() 
	{
		super(4);
		this.array[0] = a;
		this.array[1] = b;
		this.array[2] = c;
		this.array[3] = d;
		
	}
	public Brick Clone()
	{
		return new BrickType3();
	}
	
}
