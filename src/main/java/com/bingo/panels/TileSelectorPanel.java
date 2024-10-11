package com.bingo.panels;

import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

@ConfigGroup("bingo")
public class TileSelectorPanel extends JPanel
{
	private final BingoGame activeGame;

	private static final ImageIcon ARROW_DOWN_ICON;
	private static final ImageIcon ARROW_DOWN_ICON_HOVER;
	private static final ImageIcon ARROW_UP_ICON;
	private static final ImageIcon ARROW_UP_ICON_HOVER;

	private final Map<BingoBoard, JPanel> boardPanels = new HashMap<>();
	private final Map<BingoBoard, Boolean> panelStates;

	static
	{
		final BufferedImage arrowDownIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_DOWN_ICON = new ImageIcon(arrowDownIcon);
		ARROW_DOWN_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowDownIcon, -100));

		final BufferedImage arrowUpIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_UP_ICON = new ImageIcon(ImageUtil.flipImage(arrowDownIcon, false, true));
		ARROW_UP_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowUpIcon, -100));
	}

	public TileSelectorPanel(BingoGame activeGame)
	{
		super(false);
		this.activeGame = activeGame;
		this.panelStates = new HashMap<>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		if (this.activeGame.getBingoBoards() != null)
		{
			displayBoards();
		}
	}

	public void displayBoards()
	{
		for (BingoBoard board : this.activeGame.getBingoBoards().values())
		{
			panelStates.putIfAbsent(board, true);
			JPanel headerPanel = createBoardHeaderPanel(board);
			this.add(headerPanel);
		}
		this.revalidate();
		this.repaint();
	}

	private JPanel createBoardHeaderPanel(BingoBoard board)
	{
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());

		JLabel boardTitle = new JLabel(board.getBoardName());
		headerPanel.add(boardTitle, BorderLayout.WEST);

		JButton expandButton = new JButton(ARROW_DOWN_ICON);
		expandButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		expandButton.setContentAreaFilled(false);
		expandButton.addMouseListener(new MouseAdapter()
		{
			private boolean isCollapsed = true;

			@Override
			public void mouseClicked(MouseEvent e)
			{
				isCollapsed = !isCollapsed;
				panelStates.put(board, isCollapsed);
				if (isCollapsed)
				{
					expandButton.setIcon(ARROW_DOWN_ICON);
					removeBoardPanel(board);
				}
				else
				{
					expandButton.setIcon(ARROW_UP_ICON);
					addBoardPanel(board);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (isCollapsed)
				{
					expandButton.setIcon(ARROW_DOWN_ICON_HOVER);
				}
				else
				{
					expandButton.setIcon(ARROW_UP_ICON_HOVER);
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (isCollapsed)
				{
					expandButton.setIcon(ARROW_DOWN_ICON);
				}
				else
				{
					expandButton.setIcon(ARROW_UP_ICON);
				}
			}
		});
		headerPanel.add(expandButton, BorderLayout.EAST);
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);
		return headerPanel;
	}

	private void addBoardPanel(BingoBoard board)
	{
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
		int count = 1;
		for (int i = 0; i < board.getBoardHeight(); i++)
		{
			JPanel rowPanel = new JPanel();
			for (int j = 0; j < board.getBoardWidth(); j++)
			{
				JButton tileButton = new JButton(String.valueOf(count));
				tileButton.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
				tileButton.setContentAreaFilled(false);
				rowPanel.add(tileButton);
				count++;
			}
			boardPanel.add(rowPanel);
		}
		this.add(boardPanel);
		boardPanels.put(board, boardPanel);
	}

	private void removeBoardPanel(BingoBoard board)
	{
		JPanel boardPanel = boardPanels.remove(board);
		if (boardPanel != null)
		{
			this.remove(boardPanel);
			this.revalidate();
			this.repaint();
		}
	}
}
