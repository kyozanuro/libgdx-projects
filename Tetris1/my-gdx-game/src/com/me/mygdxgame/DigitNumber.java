package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//include number changing effect, electronic digit number
public class DigitNumber
{
	private int numDigits;
	private BitmapFontCache fontCache;
	private BitmapFont font;
	private String cacheText;
	private String text;
	private int x;
	private int y;

	// changing number effect:
	private boolean isChangingNumber;
	private int currentNumber;
	private int newNumber;
	private int changeValue;
	private boolean turnZeroNumberOn = true;

	public DigitNumber(int num_digits, int x, int y)
	{
		init(num_digits, x, y);
	}
	public DigitNumber(int num_digits, int x, int y, boolean turn_zero_on)
	{
		this.turnZeroNumberOn = turn_zero_on;
		init(num_digits, x, y);
	}
	private void init(int num_digits, int x, int y)
	{
		numDigits = num_digits;
		font = new BitmapFont(Gdx.files.internal("data/BlackDigit45.fnt"),
				Gdx.files.internal("data/BlackDigit45.png"), true);
		//font.setColor(Color.BLACK);
		fontCache = new BitmapFontCache(font);

		for (int i = 0; i < num_digits; i++)
		{
			cacheText += "8";
		}
		this.x = x;
		this.y = y;
		fontCache.setText(cacheText, x, y);
		this.SetNumber(0);
	}
	public void TurnZeroNumberOnOff(boolean on)
	{
		this.turnZeroNumberOn = on;
		
	}
	public void ChangeNumberTo(int new_number)
	{

		if (new_number == this.currentNumber)
			return;

		isChangingNumber = true;
		newNumber = new_number;
		this.changeValue = (newNumber - this.currentNumber) / 50 + 1;//plus one to prevent zero value
	
	}

	public void SetNumber(int number)
	{
		// 100 -> 000100 (6 digits)
		// 1234 -> 001234
		// this.currentNumber = number;
		String snumber = number + "";
		int num_digits = snumber.length();
		int numZeros = this.numDigits - num_digits;
		String new_text = "";
		for (int i = 0; i < numZeros; i++)
		{
			if (this.turnZeroNumberOn)
			{
				new_text += "0";
			}else
			{
				new_text += " ";
			}
		}
		new_text += snumber;
		this.text = new_text;

	}

	public void Draw(SpriteBatch batch)
	{
		// update:
		//System.out.println("currentNumber = " + this.currentNumber);

		if (this.isChangingNumber)
		{
			if ((this.changeValue > 0 && this.currentNumber >= this.newNumber)
					|| (this.changeValue < 0 && this.currentNumber <= this.newNumber))
			{
				System.out.println("end changing");
				this.isChangingNumber = false;
				this.currentNumber = this.newNumber;
				this.SetNumber(this.currentNumber);
			}
		}
		if (this.isChangingNumber)// mean it need changing
		{
			// System.out.println("Changing" + this.currentNumber);
			this.currentNumber += this.changeValue;
			this.SetNumber(this.currentNumber);
		}

		// draw the text background effect
		fontCache.setColor(1, 1, 1, 0.20f);
		fontCache.draw(batch);

		// draw text

		font.draw(batch, text, this.x, this.y);
	}
}
