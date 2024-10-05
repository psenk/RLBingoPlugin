package com.bingo.bingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class BingoGame
{
	public int bingoID;

	@Getter
	@Setter
	public String bingoTitle;

	@Getter
	@Setter
	public String bingoDescription;

	public Map<Integer, BingoBoard> bingoBoards;
	public List<String> teams;

	public BingoGame(String title, String description) {
		bingoTitle = title;
		bingoDescription = description;
		bingoBoards = new HashMap<Integer, BingoBoard>();
		teams = new ArrayList<String>();
	}

	public void addBingoBoard(BingoBoard board)
	{
		bingoBoards.put(bingoBoards.size(), board);
	}

	public void removeBingoBoard(int id)
	{
		bingoBoards.remove(id);
	}

	public BingoBoard getBingoBoard(int id)
	{
		return bingoBoards.get(id);
	}

	public void addTeam(String team)
	{
		teams.add(team);
	}

	public void removeTeam(String team)
	{
		teams.remove(team);
	}

	public BingoGame getTestBingo()
	{
		BingoGame testBingo = new BingoGame("Test Bingo", "Test Bingo Description");
		testBingo.addBingoBoard(new BingoBoard(1, "Test Board", 5, 5));
		testBingo.addTeam("Test Team");
		return testBingo;
	}
}
