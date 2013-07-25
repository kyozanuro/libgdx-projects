package com.me.mygdxgame;

public class Cell
{
	public int X;
	public int Y;
	public Cell()
	{
	}
	public Cell(int x, int y)
	{
		X = x;
		Y = y;
	}
}

enum DIRECTION {
	LEFT, TOP, RIGHT, BOT
};