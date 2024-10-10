package com.bingo.bingo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class BingoGame
{
	@Getter
	public int bingoID;

	@Getter
	@Setter
	public String bingoTitle;

	@Getter
	@Setter
	public String bingoDescription;

	@Getter
	@Setter
	public int bingoDuration;

	@Setter
	@Getter
	public List<String> bingoTeams;

	@Getter
	public Map<Integer, BingoBoard> bingoBoards;

	public BingoGame()
	{

	}

	public BingoGame(int id)
	{
		this.bingoID = id;
	}

	public BingoGame(String title, String description, int duration, List<String> teams, Map<Integer, BingoBoard> boards)
	{
		this.bingoTitle = title;
		this.bingoDescription = description;
		this.bingoDuration = duration;
		this.bingoTeams = teams;
		this.bingoBoards = boards;
	}

	public void addBingoBoard(BingoBoard board)
	{
		this.bingoBoards.put(bingoBoards.size() + 1, board);
	}

	public void removeBingoBoard(int id)
	{
		this.bingoBoards.remove(id);
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
