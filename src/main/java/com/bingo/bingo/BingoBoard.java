package com.bingo.bingo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

public class BingoBoard extends JPanel
{
	@Getter
	@Setter
	private int boardNumber;

	@Getter
	@Setter
	private String boardName;

	@Getter
	@Setter
	private String boardDescription;

	@Getter
	@Setter
	private int boardWidth;

	@Getter
	@Setter
	private int boardHeight;

	@Getter
	private List<BingoTile> tiles;

	public BingoBoard(String boardName, String boardDescription, int boardWidth, int boardHeight)
	{
		this.boardName = boardName;
		this.boardDescription = boardDescription;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;

		this.tiles = new ArrayList<>();
	}

	public BingoBoard(int boardNumber, String boardDescription, int width, int length)
	{
		this.boardNumber = boardNumber;
		this.boardDescription = boardDescription;
		this.boardWidth = width;
		this.boardHeight = length;

		this.tiles = new ArrayList<>();
	}

	public void addTileToBoard(BingoTile tile)
	{
		this.tiles.add(tile);
	}

	public void removeTileFromBoard(BingoTile tile)
	{
		this.tiles.remove(tile);
	}
}
