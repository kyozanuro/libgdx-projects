package com.me.mygdxgame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen
{

	public static final int NUM_HORIZONTAL_CELLS = 10;
	public static final int NUM_VERTICAL_CELLS = 22;
	public static final int CELL_SIZE = 28;
	public static final int CELL_OFFSET = 2;
	public static int[][] Array = new int[NUM_VERTICAL_CELLS][];
	private Texture emptycellTexture;
	private Texture filledcellTexture;
	private Texture blackLineTexture;
	private Texture blackRecTexture;
	private BitmapFont arialFont;
	private BitmapFont whiteArialFont40;
	//private BitmapFontCache arialFontCache;
	private int screenWidth;
	private int screenHeight;

	private int delayCount;

	public static Brick CurrentBrick;
	private Brick NextBrick;

	private ArrayList<Brick> listOfNormalBricks;
	private ArrayList<Brick> listOfPreciousBricks;
	public static Random GlobalRandom = new Random();
	
	
	private int nextBrickX;
	private int nextBrickY;
	
	private DigitNumber scoreDigitNumber;
	private DigitNumber speedDigitNumber;
	private DigitNumber levelDigitNumber;
	
	private MyKeyDownHandler myKeyDownHandler;
	
	private boolean isPausingGame;
	private boolean isGameOver;
	private boolean pauseEffectOn;
	public GameScreen(int width, int height)
	{
		
		screenWidth = width;
		screenHeight = height;
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
		//digitNumber.ChangeNumberTo(100);
		
		
		arialFont = new BitmapFont(Gdx.files.internal("data/BlackArial30.fnt"), Gdx.files.internal("data/BlackArial30.png"), true);
		whiteArialFont40 = new BitmapFont(Gdx.files.internal("data/WhiteArial40.fnt"), Gdx.files.internal("data/WhiteArial40.png"), true);
		//arialFontCache = new BitmapFontCache(arialFont);
		
		
		nextBrickX = (NUM_HORIZONTAL_CELLS +1) * (CELL_SIZE + CELL_OFFSET);
		nextBrickY = 6 * (CELL_SIZE + CELL_OFFSET);
		
		scoreDigitNumber = new DigitNumber(6, 320, 70);
		speedDigitNumber = new DigitNumber(2, nextBrickX + 25, nextBrickY + 4 *(CELL_OFFSET + CELL_SIZE) + 60, false);
		levelDigitNumber = new DigitNumber(2, nextBrickX + 25, nextBrickY + 4 *(CELL_OFFSET + CELL_SIZE) + 110, false);
		
		
		for (int i = 0; i < NUM_VERTICAL_CELLS; i++)
		{
			Array[i] = new int[NUM_HORIZONTAL_CELLS];
			// 0: empty
			// 1: filled
		}
		listOfNormalBricks = new ArrayList<Brick>();
		listOfPreciousBricks = new ArrayList<Brick>();
		
		//normal bricks:
		listOfNormalBricks.add(new BrickType1());
		listOfNormalBricks.add(new BrickType2());
		listOfNormalBricks.add(new BrickType3());
		listOfNormalBricks.add(new BrickType4());
		listOfNormalBricks.add(new BrickType5());
		listOfNormalBricks.add(new BrickType6());
		listOfNormalBricks.add(new BrickType7());
		
		//precious bricks:
		listOfPreciousBricks.add(new BrickType8());
		CreateNewBreak();
		/*MyInputProcessor inputProcessor = new MyInputProcessor();
		
		Gdx.input.setInputProcessor(inputProcessor);*/
		
		/*TextBounds a = blackDigitFont.getBounds("0");
		System.out.println(a.width);
		System.out.println(a.height);*/
		
		myKeyDownHandler = new MyKeyDownHandler();
		myKeyDownHandler.KeyDown = new IKeyDownHandler()
		{
			
			@Override
			public void KeyDownHandler(int keycode)
			{
				if (!isPausingGame)
				{
					if (keycode == Keys.LEFT && !CurrentBrick.IsCheckingScore)
					{
						CurrentBrick.Go(0);
					}else if (keycode == Keys.RIGHT && !CurrentBrick.IsCheckingScore)
					{
						CurrentBrick.Go(1);
					}
					if (keycode == Keys.DOWN)
					{
						if (!CurrentBrick.Go(2) && !CurrentBrick.IsCheckingScore)//prevent player press down while checking score
							{
							/*CurrentBrick.CheckScore();
							digitNumber.ChangeNumberTo(Player.CurrentScore);*/
							CurrentBrick.IsCheckingScore = true;
							delayCount = Player.CurrentSpeedDelay;// modify delayCount so it will check score immediately!
							
							//CreateNewBreak();
						}
					}
				}
				if (keycode == Keys.SPACE)
				{
					isPausingGame = !isPausingGame;
				}
				
			}
		};
		
		myKeyDownHandler.KeyDownOneTime = new IKeyDownHandler()
		{
			
			@Override
			public void KeyDownHandler(int keycode)
			{
				if (keycode == Keys.UP && !CurrentBrick.IsCheckingScore)
				{
					CurrentBrick.Rotate();
				}
			}
		};
	}

	public void Dispose()
	{
		emptycellTexture.dispose();
		filledcellTexture.dispose();
	}

	private void CreateNewBreak()
	{
		int ran_num = 0;

		if (CurrentBrick == null)
		{
			ran_num = GlobalRandom.nextInt(listOfNormalBricks.size());
			CurrentBrick = listOfNormalBricks.get(3).Clone();

			ran_num = GlobalRandom.nextInt(listOfNormalBricks.size());
			NextBrick = listOfNormalBricks.get(3).Clone();

		} else
		{
			CurrentBrick = NextBrick;
			if (!CurrentBrick.Init())
			{
				// Game Over!!
				System.out.println("Game Over");
			} else
			{
				if (GlobalRandom.nextDouble()<0.05)//the rate of appearance of BrickType8 is 5%
				{
					ran_num = GlobalRandom.nextInt(listOfPreciousBricks.size());
					NextBrick = listOfPreciousBricks.get(ran_num).Clone();
				}else
				{
					ran_num = GlobalRandom.nextInt(listOfNormalBricks.size());//except the BrickType8
					NextBrick = listOfNormalBricks.get(3).Clone();
				}
				
			}
		}

	}

	public static int SpeedKeyDown = 20;
	public void Update()
	{
		delayCount++;
		if (delayCount < 0)
		{
			//ATTENTION!!
			//If delayCount reached maximum value of integer
			//it will jump to negative number, so it will break our gameloop
			System.out.println("reached max int");
			//reset delayCount to 0 to prevent
			delayCount = 0;
		}
		if (this.isPausingGame)
		{
			return;
		}
		
		this.myKeyDownHandler.Update();
		
		
		
		
		if (delayCount %  Player.CurrentSpeedDelay== 0)
		{
			
			
			if (!CurrentBrick.IsCheckingScore)
			{
				if (!CurrentBrick.Go(2))
				{
					/*CurrentBrick.CheckScore();
					digitNumber.ChangeNumberTo(Player.CurrentScore);*/
					CurrentBrick.IsCheckingScore = true;
					//CreateNewBreak();
					// CurrentBrick.Go(2);
				}
			}
			
			if (CurrentBrick.IsCheckingScore)
			{
				
				System.out.println("check score");
				if (!CurrentBrick.CheckScore())
				{
					//end of effect
					CreateNewBreak();
					//update score to digital text
					
					speedDigitNumber.ChangeNumberTo(Player.CurrentSpeed);
				}
				scoreDigitNumber.ChangeNumberTo(Player.CurrentScore);
			}
		}

	}

	public void Draw(SpriteBatch batch)
	{
		// Draw main screen
		for (int i = 2; i < NUM_VERTICAL_CELLS; i++)
		{
			for (int j = 0; j < NUM_HORIZONTAL_CELLS; j++)
			{
				if (Array[i][j] == 0)
				{
					batch.setColor(1, 1, 1, 0.7f);
					batch.draw(emptycellTexture, j * (CELL_SIZE + CELL_OFFSET),
							(i - 2) * (CELL_SIZE + CELL_OFFSET), CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				} else
				{
					// filled
					batch.setColor(1, 1, 1, 1);
					batch.draw(filledcellTexture,
							j * (CELL_SIZE + CELL_OFFSET), (i - 2)
									* (CELL_SIZE + CELL_OFFSET), CELL_SIZE,
							CELL_SIZE, 0, 0, CELL_SIZE, CELL_SIZE, false, false);
				}

			}
		}

		//draw vertical line
		for (int i=0;i<NUM_VERTICAL_CELLS;i++)
			batch.draw(blackLineTexture, NUM_HORIZONTAL_CELLS *(CELL_SIZE + CELL_OFFSET), i * (CELL_SIZE + CELL_OFFSET), 4,CELL_SIZE + CELL_OFFSET , 0, 0, 4, 30, false, false);
		
		
		// Draw right menu
		
		int[][] next_array = NextBrick.GetArray();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (next_array[i][j] == 0)
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
		
		arialFont.draw(batch, "Score", screenWidth - 120, 30);
		
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
		batch.draw(blackRecTexture, nextBrickX, screenHeight - 50, 120, 31, 0, 0, 128, 32, false, false);
		
		whiteArialFont40.setColor(156/255.0f, 178/255.0f, 165/255.0f, 1);
		
		whiteArialFont40.draw(batch, "PAUSE", nextBrickX+3, screenHeight - 48);
	/*	blackDigitFont.setColor(1, 1, 1, 0.15f);
		blackDigitFont.draw(batch, "000000", 0, 70);
		
		//draw score text
		blackDigitFont.setColor(1, 1, 1, 1);
		//blackDigitFont.draw(batch, "100", 295, 70);
		blackDigitFont.draw(batch, "333121", 300, 70);*/
		
		
	}

}
