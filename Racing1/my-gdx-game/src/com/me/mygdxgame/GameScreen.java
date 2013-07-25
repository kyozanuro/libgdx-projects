package com.me.mygdxgame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen
{
	
	public static final int NUM_HORIZONTAL_CELLS = 10;
	public static final int NUM_VERTICAL_CELLS = 20;
	public static final int CELL_SIZE = 28;
	public static final int CELL_OFFSET = 2;
	public static int[][] Array = new int[NUM_VERTICAL_CELLS][];
	public static Random GlobalRandom = new Random();
	
	private Texture emptycellTexture;
	private Texture filledcellTexture;
	private Texture blackLineTexture;
	private Texture blackRecTexture;
	
	
	private BitmapFont arialFont;
	private BitmapFont whiteArialFont40;
	
	public static int Width;
	public static int Height;

	private int delayCount;

	
	private DigitNumber scoreDigitNumber;
	private DigitNumber speedDigitNumber;
	private DigitNumber levelDigitNumber;
	
	private MyKeyDownHandler myKeyDownHandler;
	
	private boolean isPausingGame;
	private boolean isGameOver;
	private boolean pauseEffectOn;
	private int nextBrickX;
	private int nextBrickY;
	
	private Lane leftLane;
	private Lane rightLane;
	private Timer timer;
	private PlayerCar player;
	private CarSpawner spawner;
	public static int TravelCount;//count step of player's car traveling
	public GameScreen(int width, int height)
	{
		
		
		Width = width;
		Height = height;
	
		Player.CurrentSpeedDelay = ( - 5 * (Player.CurrentSpeed + 1) + 55 );
		emptycellTexture = new Texture(Gdx.files.internal("data/emptycell.png"));
		emptycellTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	/*	blackDigitFont = new BitmapFont(Gdx.files.internal("data/BlackDigit.fnt"),
				Gdx.files.internal("data/BlackDigit.png"), true);*/
		
		
		filledcellTexture = new Texture(
				Gdx.files.internal("data/filledcell.png"));
		filledcellTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		blackLineTexture = new Texture(
				Gdx.files.internal("data/verticalline.png"));
		blackLineTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		
		blackRecTexture = new Texture(
				Gdx.files.internal("data/blackrec.png"));
		blackRecTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		nextBrickX = (NUM_HORIZONTAL_CELLS +1) * (CELL_SIZE + CELL_OFFSET);
		nextBrickY = 6 * (CELL_SIZE + CELL_OFFSET);
		
		arialFont = new BitmapFont(Gdx.files.internal("data/BlackArial30.fnt"), Gdx.files.internal("data/BlackArial30.png"), true);
		whiteArialFont40 = new BitmapFont(Gdx.files.internal("data/WhiteArial40.fnt"), Gdx.files.internal("data/WhiteArial40.png"), true);
		
		scoreDigitNumber = new DigitNumber(6, 320, 70);
		speedDigitNumber = new DigitNumber(2, nextBrickX + 25, nextBrickY + 4 *(CELL_OFFSET + CELL_SIZE) + 60, false);
		levelDigitNumber = new DigitNumber(2, nextBrickX + 25, nextBrickY + 4 *(CELL_OFFSET + CELL_SIZE) + 110, false);
		
		
		for (int i = 0; i < NUM_VERTICAL_CELLS; i++)
		{
			Array[i] = new int[NUM_HORIZONTAL_CELLS];
			// 0: empty
			// 1: filled
		}
		leftLane = new Lane(DIRECTION.BOT, new Cell(0,0), false);
		rightLane = new Lane(DIRECTION.BOT, new Cell(NUM_HORIZONTAL_CELLS - 1,0), false);
		spawner = new CarSpawner();
		player = new PlayerCar(this.spawner);
		
		myKeyDownHandler = new MyKeyDownHandler();
		//use for player's car to turn left, turn right
		myKeyDownHandler.KeyDown = new IKeyDownHandler()
		{
			
			@Override
			public void KeyDownHandler(int keycode)
			{
				if (!isGameOver && !isPausingGame)
				{
					if (keycode == Keys.LEFT)
					{
						isGameOver = !player.MoveLeft();
					}
					if (keycode == Keys.RIGHT)
					{
						isGameOver = !player.MoveRight();
					}
				}
			}
		};
		
		//use for player's car to fire a bullet:
		myKeyDownHandler.KeyDownOneTime = new IKeyDownHandler()
		{
			
			@Override
			public void KeyDownHandler(int keycode)
			{
				if (!isGameOver)
				{	
					if (keycode == Keys.SPACE)
					{
						//fire
						player.Fire();
					}
				}
				
				if (keycode == Keys.ESCAPE)
				{
					isPausingGame = !isPausingGame;
							
				}
			}
		};
		
		
		timer = new Timer(0.5f, true);
		currentSpeedToTimeInterval(Player.CurrentSpeed);
		timer.Tick = new ITimerTick()
		{
			
			@Override
			public void TickHandler(Timer sender)
			{
				TravelCount++;
				
				Player.CurrentScore+=1;
				scoreDigitNumber.ChangeNumberTo(Player.CurrentScore);
				
				if (TravelCount % 50 == 0)
				{
					player.IncreaseBullet();
				}
				if (Player.CurrentScore >= Player.ChangeSpeedAtScore)
				{
					Player.CurrentSpeed++;
					speedDigitNumber.ChangeNumberTo(Player.CurrentSpeed);
					currentSpeedToTimeInterval(Player.CurrentSpeed);
					Player.ChangeSpeedAtScore*=2;
				}
					
					
				leftLane.Update();
				rightLane.Update();
				isGameOver = !spawner.UpdateCars();
				
				
				if (TravelCount% 10 == 0)
				{
					spawner.UpdateSpawn();
				}
			}
		};
	}
	
	//relationship between speed and frame rate limit
	public void currentSpeedToTimeInterval(int speed)
	{
		//0 speed -> 0.5 seconds
		//10 speed -> 0.05 seconds
		float interval = speed * (-0.045f) + 0.5f;
		timer.SetTimeInterval(interval);
	}
	
	public void Update()
	{
		if (this.isGameOver)
		{
			return;
		}
		this.delayCount += 1;
		if (this.delayCount < 0)
		{
			this.delayCount = 0;
		}
		if (this.isPausingGame)
		{
			return;
		}
		myKeyDownHandler.Update();
		timer.Update();
		
				
				
		
		
	
		if (this.delayCount % 6 == 0)
		{
			player.UpdateBullets();
		}
		
		/*if (this.delayCount % 4 == 0)
		{
			leftLane.Update();
			rightLane.Update();
		}*/
		
		/*if (this.delayCount % 3 !=0)
		{
			leftLane.Update();
			rightLane.Update();
		}*/
	}
	public void Draw(SpriteBatch batch)
	{
		
		
		
		// Draw main screen
		for (int i = 0; i < NUM_VERTICAL_CELLS; i++)
		{
			for (int j = 0; j < NUM_HORIZONTAL_CELLS; j++)
			{
				if (Array[i][j] == 0)
				{
					batch.setColor(1, 1, 1, 0.7f);
					batch.draw(emptycellTexture, j * (CELL_SIZE + CELL_OFFSET),
							i  * (CELL_SIZE + CELL_OFFSET), CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				} else
				{
					// filled
					batch.setColor(1, 1, 1, 1);
					batch.draw(filledcellTexture,
							j * (CELL_SIZE + CELL_OFFSET), i
									* (CELL_SIZE + CELL_OFFSET), CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				}

			}
		}

		//draw vertical line
		for (int i=0;i<NUM_VERTICAL_CELLS;i++)
			batch.draw(blackLineTexture, NUM_HORIZONTAL_CELLS *(CELL_SIZE + CELL_OFFSET), i * (CELL_SIZE + CELL_OFFSET), 4,CELL_SIZE + CELL_OFFSET , 0, 0, 4, 30, false, false);
		
		
		// Draw right menu
		
		//int[][] next_array = NextBrick.GetArray();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (player.ArrayBullet[i][j] == 0)
				{
					batch.setColor(1, 1, 1, 0.7f);
					batch.draw(emptycellTexture, j * (CELL_SIZE + CELL_OFFSET) + nextBrickX,
							 i*(CELL_SIZE + CELL_OFFSET) + nextBrickY, CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				}else
				{
					// filled
					batch.setColor(1, 1, 1, 1);
					batch.draw(filledcellTexture,
							j * (CELL_SIZE + CELL_OFFSET) + nextBrickX, 
									i*(CELL_SIZE + CELL_OFFSET) + nextBrickY, CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				}
			}
		}
		
		
		//draw score text background
		scoreDigitNumber.Draw(batch);
		speedDigitNumber.Draw(batch);
		levelDigitNumber.Draw(batch);
		
		//arialFont.setColor(Color.BLACK);
		
		arialFont.draw(batch, "Score", Width - 120, 30);
		
		arialFont.draw(batch, "NEXT", nextBrickX + 15, nextBrickY - 30);
		arialFont.draw(batch, "SPEED", nextBrickX + 15, nextBrickY + 4 *(CELL_SIZE + CELL_OFFSET)+5);
		arialFont.draw(batch, "LEVEL", nextBrickX + 15, 460);
		
		
		//draw PAUSE
		if (this.isPausingGame)
		{
			if (this.delayCount%50 == 0)
			{
				pauseEffectOn = !pauseEffectOn;
			}
			if (pauseEffectOn)
			{
				batch.setColor(1, 1, 1, 1);
			}else
			{
				batch.setColor(1, 1, 1, 0.15f);
			}
		}else
		{
			batch.setColor(1, 1, 1, 0.15f);
		}
		batch.draw(blackRecTexture, nextBrickX, Height - 50, 120, 31, 0, 0, 128, 32, false, false);
		
		whiteArialFont40.setColor(156/255.0f, 178/255.0f, 165/255.0f, 1);
		
		whiteArialFont40.draw(batch, "PAUSE", nextBrickX+3, Height - 48);
	
		
		
	}

	
}
