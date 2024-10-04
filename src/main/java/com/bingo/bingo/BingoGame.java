package com.bingo.bingo;

import java.util.Map;

public class BingoGame
{
	public int bingoID;
	public String bingoTitle;
	public String bingoDescription;
	public Map<Integer, BingoBoard> bingoBoards;

	public BingoGame(String title, String description) {
		bingoTitle = title;
		bingoDescription = description;
	}

	public void addBingoBoard(BingoBoard board)
	{
		bingoBoards.put(bingoBoards.size(), board);
	}

	public void removeBingoBoard(int id)
	{
		bingoBoards.remove(id);
	}
}
