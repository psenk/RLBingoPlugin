package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.LineBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class BasicInfoPanel extends JPanel
{
	private final BingoGame activeGame;

	FlatTextField bingoName;
	FlatTextField bingoDescription;
	JSpinner bingoDuration;

	// TODO: why are these public
	public TeamsPanel teamsPanel;
	public BoardsPanel boardsPanel;

	public BasicInfoPanel(BingoGame activeGame)
	{
		this.activeGame = activeGame;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel infoPanel = createInfoPanel();
		add(infoPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));

		this.teamsPanel = new TeamsPanel(activeGame);
		teamsPanel.setPreferredSize(new Dimension(225, 300));
		add(teamsPanel, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(0, 10)));

		this.boardsPanel = new BoardsPanel(activeGame);
		boardsPanel.setPreferredSize(new Dimension(225, 300));
		add(boardsPanel, BorderLayout.SOUTH);
	}

	private JPanel createInfoPanel()
	{
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.add(new JLabel("Bingo Name:"), BorderLayout.WEST);
		namePanel.add(Box.createVerticalStrut(5));
		this.bingoName = createTextField();
		namePanel.add(bingoName, BorderLayout.SOUTH);
		infoPanel.add(namePanel);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel descriptionPanel = new JPanel(new BorderLayout());
		descriptionPanel.add(new JLabel("Bingo Description:"), BorderLayout.WEST);
		descriptionPanel.add(Box.createVerticalStrut(5));
		this.bingoDescription = createTextField();
		descriptionPanel.add(bingoDescription, BorderLayout.SOUTH);
		infoPanel.add(descriptionPanel);
		infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel durationPanel = new JPanel(new BorderLayout());
		durationPanel.add(new JLabel("Bingo Duration (in days):"), BorderLayout.WEST);
		durationPanel.add(Box.createVerticalStrut(5));
		this.bingoDuration = new JSpinner();
		bingoDuration.setBackground(ColorScheme.MEDIUM_GRAY_COLOR);
		durationPanel.add(bingoDuration, BorderLayout.SOUTH);
		infoPanel.add(durationPanel);

		return infoPanel;
	}

	private FlatTextField createTextField()
	{
		FlatTextField textField = new FlatTextField();
		textField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		textField.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
		return textField;
	}
}
