package com.bingo.bingo;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;

public class BingoTile extends JPanel
{
	private int tileId;

	@Getter
	@Setter
	private ImageIcon tileIcon;

	@Getter
	@Setter
	private String tileTask;

	private double tileWidth;
	private double tileHeight;

	// default tile
	public BingoTile(int tileId)
	{
		this.tileWidth = 5.0;
		this.tileHeight = 5.0;
		this.tileTask = "";
	}

}
