package com.me.mygdxgame;

public class BrickType8 extends Brick {
	//this type of brick is appear rarely than the others
	private static int[][] a = { 
								{ 0, 0, 0, 0 }, 
								{ 0, 1, 0, 0 },
								{ 0, 0, 0, 0 },
								{ 0, 0, 0, 0 }
								};
	public BrickType8() {
		super(1);
		this.array[0] = a;
	}
	
	public Brick Clone()
	{
		return new BrickType8();
	}
}
