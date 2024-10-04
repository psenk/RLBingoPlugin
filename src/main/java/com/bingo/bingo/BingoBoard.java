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
	private String boardTitle;

	private List<BingoTile> tiles;
	private int length;
	private int width;

	public BingoBoard(int boardNumber, String boardTitle, int length, int width)
	{
		this.boardNumber = boardNumber;
		this.boardTitle = boardTitle;
		this.length = length;
		this.width = width;

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
