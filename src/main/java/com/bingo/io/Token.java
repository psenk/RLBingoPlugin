package com.bingo.io;

import java.util.Random;
import lombok.Getter;

@Getter
public class Token
{
	private Integer id = 0;
	private String type;

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

	public Token(String type)
	{
		this.id = 0; // null token
		this.type = type;
	}

	public Token(Integer id, String type)
	{
		this.id = id;
		this.type = type;
	}
}
