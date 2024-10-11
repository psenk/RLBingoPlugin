package com.bingo.bingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BingoGame
{
	private int bingoID;

	@Setter
	private String bingoTitle;

	@Setter
	private String bingoDescription;

	@Setter
	private int bingoDuration;

	@Setter
	private List<String> bingoTeams;

	private Map<Integer, BingoBoard> bingoBoards;

	public BingoGame()
	{
		this.bingoTitle = "";
		this.bingoDescription = "";
		this.bingoDuration = 0;
		this.bingoTeams = new ArrayList<>();
		this.bingoBoards = new HashMap<>();
	}

	public BingoGame(int id)
	{
		this.bingoID = id;
	}

	public void addBingoBoard(BingoBoard board)
	{
		this.bingoBoards.put(bingoBoards.size() + 1, board);
	}

	public void removeBingoBoard(BingoBoard board)
	{
		for (int i = 0; i < this.bingoBoards.size(); i++)
		{
			if (this.bingoBoards.get(i).equals(board))
			{
				this.bingoBoards.remove(i);
			}
		}

	}

	public BingoBoard getBingoBoard(int id)
	{
		return this.bingoBoards.get(id);
	}

	public void addTeam(String team)
	{
		this.bingoTeams.add(team);
	}

	public void removeTeam(String team)
	{
		this.bingoTeams.remove(team);
	}
}
