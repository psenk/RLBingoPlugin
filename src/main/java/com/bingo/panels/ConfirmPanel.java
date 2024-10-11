package com.bingo.panels;

import com.bingo.bingo.BingoGame;
import javax.swing.JPanel;
import net.runelite.client.config.ConfigGroup;

@ConfigGroup("bingo")
public class ConfirmPanel extends JPanel
{
	private BingoGame activeGame;

	public ConfirmPanel(BingoGame activeGame)
	{
		this.activeGame = activeGame;
	}
}
