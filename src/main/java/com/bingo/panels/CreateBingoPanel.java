package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import com.bingo.io.LogIn;
import com.bingo.io.Token;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class CreateBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.CREATE;

	private final FlatTextField bingoName;
	private final FlatTextField bingoDescription;
	private final JSpinner bingoDuration;

	@Getter
	private List<String> bingoTeams = new ArrayList<>();

	@Getter
	private Map<Integer, BingoBoard> bingoBoards = new HashMap<>();

	private final BingoScapePlugin plugin;

	public CreateBingoPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel basicInfoPanel = new JPanel();
		basicInfoPanel.setLayout(new BoxLayout(basicInfoPanel, BoxLayout.Y_AXIS));

		JLabel bingoNameLabel = new JLabel("Bingo Name:");
		bingoNameLabel.setAlignmentX(LEFT_ALIGNMENT);
		basicInfoPanel.add(bingoNameLabel);

		bingoName = new FlatTextField();
		bingoName.setPreferredSize(new Dimension(200, 20));
		bingoName.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		bingoName.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		basicInfoPanel.add(bingoName);
		basicInfoPanel.add(Box.createVerticalStrut(10));

		JLabel bingoDescriptionLabel = new JLabel("Bingo Description:");
		bingoDescriptionLabel.setAlignmentX(LEFT_ALIGNMENT);
		basicInfoPanel.add(bingoDescriptionLabel);

		bingoDescription = new FlatTextField();
		bingoDescription.setPreferredSize(new Dimension(200, 20));
		bingoDescription.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		bingoDescription.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		basicInfoPanel.add(bingoDescription);
		basicInfoPanel.add(Box.createVerticalStrut(10));

		JLabel bingoDurationLabel = new JLabel("Bingo Duration (in days):");
		bingoDurationLabel.setAlignmentX(LEFT_ALIGNMENT);
		basicInfoPanel.add(bingoDurationLabel);

		bingoDuration = new JSpinner();
		bingoDuration.setPreferredSize(new Dimension(100, 20));
		bingoDuration.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		bingoDuration.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		basicInfoPanel.add(bingoDuration);

		this.add(basicInfoPanel);
		this.add(Box.createVerticalStrut(10));

		TeamsPanel teamsPanel = new TeamsPanel();
		this.add(teamsPanel);
		this.add(Box.createVerticalStrut(10));

		BoardsPanel boardsPanel = new BoardsPanel();
		this.add(boardsPanel);
		this.add(Box.createVerticalStrut(10));

		JLabel setTilesButton = new JLabel("Select Tasks");
		setTilesButton.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		setTilesButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setTilesButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					bingoTeams = teamsPanel.getTeamNames();
					for (BingoBoard b : boardsPanel.boards)
					{
						bingoBoards.put(bingoBoards.size() + 1, b);
					}
					BingoGame game = new BingoGame(getBingoName(), getBingoDescription(), getBingoDuration(), getBingoTeams(), getBingoBoards());
					plugin.createNewBingo(game);
					setTilesButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				setTilesButton.setBackground(ColorScheme.DARK_GRAY_COLOR);
				setTilesButton.setBorder(BorderFactory.createLineBorder(ColorScheme.MEDIUM_GRAY_COLOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				setTilesButton.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
				setTilesButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});

		JPanel setTilesPanel = new JPanel();
		setTilesPanel.add(setTilesButton);
		this.add(setTilesButton, BorderLayout.SOUTH);
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
}
