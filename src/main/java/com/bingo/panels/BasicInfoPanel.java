package com.bingo.panels;

import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class BasicInfoPanel extends JPanel
{
	@Inject
	TileSelectorPanel tileSelectorPanel;

	private final BingoGame game;

	FlatTextField bingoName;
	FlatTextField bingoDescription;
	JSpinner bingoDuration;

	public TeamsPanel teamsPanel;
	public BoardsPanel boardsPanel;

	public BasicInfoPanel(BingoGame game)
	{
		this.game = game;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel namePanel = new JPanel(new BorderLayout());
		JLabel nameLabel = new JLabel("Bingo Name:");
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(Box.createVerticalStrut(5));
		this.bingoName = createTextField();
		namePanel.add(bingoName, BorderLayout.SOUTH);
		this.add(namePanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel descriptionPanel = new JPanel(new BorderLayout());
		JLabel descriptionLabel = new JLabel("Bingo Description:");
		descriptionPanel.add(descriptionLabel, BorderLayout.WEST);
		descriptionPanel.add(Box.createVerticalStrut(5));
		this.bingoDescription = createTextField();
		descriptionPanel.add(bingoDescription, BorderLayout.SOUTH);
		this.add(descriptionPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel durationPanel = new JPanel(new BorderLayout());
		JLabel durationLabel = new JLabel("Bingo Duration (in days):");
		durationPanel.add(durationLabel, BorderLayout.WEST);
		durationPanel.add(Box.createVerticalStrut(5));
		this.bingoDuration = new JSpinner();
		this.bingoDuration.setBackground(ColorScheme.MEDIUM_GRAY_COLOR);
		durationPanel.add(bingoDuration, BorderLayout.SOUTH);
		this.add(durationPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));

		teamsPanel = new TeamsPanel();
		this.add(teamsPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));

		boardsPanel = new BoardsPanel();
		boardsPanel.setBoardListener(tileSelectorPanel);
		this.add(boardsPanel);
	}

	private FlatTextField createTextField()
	{
		FlatTextField textField = new FlatTextField();
		textField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		textField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		return textField;
	}

	public String getBingoName()
	{
		return bingoName.getText();
	}

	public String getBingoDescription()
	{
		return bingoDescription.getText();
	}

	public int getBingoDuration()
	{
		return (int) bingoDuration.getValue();
	}

	public List<String> getBingoTeams()
	{
		return teamsPanel.getTeamNames();
	}

	public List<BingoBoard> getBingoBoards()
	{
		return boardsPanel.getBoards();
	}
}
