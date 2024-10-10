package com.bingo.panels;

import com.bingo.BingoScapePlugin;
import com.bingo.bingo.BingoGame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.runelite.client.config.ConfigGroup;

@ConfigGroup("bingo")
public class ConfirmPanel extends JPanel
{
	private BingoGame game;

	public ConfirmPanel(BingoGame game)
	{
		this.game = game;
	}
}
