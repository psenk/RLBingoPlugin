package com.bingo.io;

import java.util.Random;
import lombok.Getter;

public class Token
{
	@Getter
	private Integer id = null;

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
