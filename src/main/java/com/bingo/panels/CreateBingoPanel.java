package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class CreateBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.CREATE;
	private final BingoScapePlugin plugin;

	private final JPanel mainPanel;
	private final CardLayout cardLayout;
	private final BasicInfoPanel basicInfoPanel;
	private final TileSelectorPanel tileSelectorPanel;
	private final ConfirmPanel confirmPanel;

	@Getter
	private List<String> bingoTeams = new ArrayList<>();
	@Getter
	private Map<Integer, BingoBoard> bingoBoards = new HashMap<>();
	private BingoGame activeGame;

	public CreateBingoPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;
		this.activeGame = new BingoGame();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		JButton basicInfoButton = new JButton("Info");
		JButton taskSelectorButton = new JButton("Tasks");
		JButton confirmButton = new JButton("Final");

		basicInfoButton.setPreferredSize(new Dimension(65, 20));
		taskSelectorButton.setPreferredSize(new Dimension(65, 20));
		confirmButton.setPreferredSize(new Dimension(65, 20));

		buttonPanel.add(basicInfoButton);
		buttonPanel.add(taskSelectorButton);
		buttonPanel.add(confirmButton);

		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(0, 10)));

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		mainPanel.setPreferredSize(new Dimension(225, 0));
		this.add(mainPanel);

		basicInfoPanel = new BasicInfoPanel(activeGame);
		tileSelectorPanel = new TileSelectorPanel(activeGame);
		confirmPanel = new ConfirmPanel(activeGame);

		mainPanel.add(basicInfoPanel, "BasicInfoPanel");
		mainPanel.add(tileSelectorPanel, "TileSelectorPanel");
		mainPanel.add(confirmPanel, "ConfirmPanel");

		cardLayout.show(mainPanel, "BasicInfoPanel");

		basicInfoButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				cardLayout.show(mainPanel, "BasicInfoPanel");
			}
		});

		taskSelectorButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				cardLayout.show(mainPanel, "TileSelectorPanel");
			}
		});

		confirmButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				cardLayout.show(mainPanel, "ConfirmPanel");
			}
		});

		JButton generateButton = new JButton("Generate Bingo");
		generateButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{

			}

			@Override
			public void mouseEntered(MouseEvent e)
			{

			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				generateBingo();
			}
		});

		JPanel generateButtonPanel = new JPanel(new BorderLayout());
		generateButtonPanel.add(generateButton, BorderLayout.SOUTH);

		this.add(generateButtonPanel);
	}

	private void generateBingo()
	{
		activeGame.setBingoTitle(basicInfoPanel.getBingoName());
		activeGame.setBingoDescription(basicInfoPanel.getBingoDescription());
		activeGame.setBingoDuration(basicInfoPanel.getBingoDuration());
		activeGame.setBingoTeams(basicInfoPanel.getBingoTeams());

		for (BingoBoard board : basicInfoPanel.getBingoBoards())
		{
			activeGame.addBingoBoard(board);
		}
	}
}
