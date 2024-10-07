package com.bingo.io;

import java.util.Random;
import lombok.Getter;

@Getter
public class Token
{
	private Integer id = 0;

	public Token()
	{
		Random rand = new Random();
		int val = rand.nextInt(10);
		if (val == 5)
		{
			return;
		}
		else
		{
			this.id = val;
		}
	}

	public Token(Integer id)
	{
		this.id = id;
	}
}
