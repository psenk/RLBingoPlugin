package com.bingo.panels;

import com.bingo.bingo.BingoBoard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.util.ImageUtil;

public class BoardsPanel extends JPanel
{
	// TODO: add total tiles panel

	public List<BingoBoard> boards;

	private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_ICON_HOVER;
	private static final ImageIcon DELETE_ICON;
	private static final ImageIcon DELETE_ICON_HOVER;
	private static final ImageIcon EDIT_ICON;
	private static final ImageIcon EDIT_ICON_HOVER;
	private static final ImageIcon ARROW_DOWN_ICON;
	private static final ImageIcon ARROW_DOWN_ICON_HOVER;
	private static final ImageIcon ARROW_UP_ICON;
	private static final ImageIcon ARROW_UP_ICON_HOVER;

	private final JPanel boardsListPanel;
	private final JPanel boardCustomizationPanel;
	private JButton createButton;
	private JButton expandButton;
	private boolean isCollapsed;
	private boolean isCustomizationPanelVisible;
	private BingoBoard edittingBoard;

	private FlatTextField boardName;
	private FlatTextField boardDescription;
	private JSpinner boardWidth;
	private JSpinner boardHeight;

	static
	{
		final BufferedImage addIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/add_icon.png");
		ADD_ICON = new ImageIcon(addIcon);
		ADD_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(addIcon, -100));

		final BufferedImage deleteIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/delete_icon.png");
		DELETE_ICON = new ImageIcon(deleteIcon);
		DELETE_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(deleteIcon, -100));

		final BufferedImage editIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/edit_icon.png");
		EDIT_ICON = new ImageIcon(editIcon);
		EDIT_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(editIcon, -100));

		final BufferedImage arrowDownIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_DOWN_ICON = new ImageIcon(arrowDownIcon);
		ARROW_DOWN_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowDownIcon, -100));

		final BufferedImage arrowUpIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_UP_ICON = new ImageIcon(ImageUtil.flipImage(arrowDownIcon, false, true));
		ARROW_UP_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowUpIcon, -100));
	}

	public BoardsPanel()
	{
		this.setLayout(new BorderLayout());
		this.boards = new ArrayList<>();
		this.isCustomizationPanelVisible = false;
		this.isCollapsed = false;

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);

		JLabel headerLabel = new JLabel("Boards");
		headerLabel.setForeground(ColorScheme.TEXT_COLOR);
		headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		headerPanel.add(headerLabel, BorderLayout.WEST);

		JPanel headerButtons = new JPanel(new FlowLayout());

		expandButton = new JButton(ARROW_DOWN_ICON);
		expandButton.setBorder(BorderFactory.createEmptyBorder());
		expandButton.setContentAreaFilled(false);
		expandButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				toggleBoardListVisibility();
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
		expandButton.setVisible(false);
		headerButtons.add(expandButton);

		JButton addButton = new JButton(ADD_ICON);
		addButton.setBorder(BorderFactory.createEmptyBorder());
		addButton.setContentAreaFilled(false);
		addButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					toggleCustomizationPanelVisibility();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				addButton.setIcon(ADD_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				addButton.setIcon(ADD_ICON);
			}
		});
		headerButtons.add(addButton);

		headerPanel.add(headerButtons, BorderLayout.EAST);
		this.add(headerPanel, BorderLayout.NORTH);

		boardsListPanel = new JPanel();
		boardsListPanel.setLayout(new BoxLayout(boardsListPanel, BoxLayout.Y_AXIS));

		JScrollPane boardsScrollPane = new JScrollPane(boardsListPanel);
		boardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		boardsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(boardsScrollPane, BorderLayout.CENTER);

		boardCustomizationPanel = createBoardCustomizationPanel();
		boardCustomizationPanel.setVisible(false);
		boardsListPanel.add(boardCustomizationPanel);
	}

	private JPanel createBoardCustomizationPanel()
	{
		JPanel customizationPanel = new JPanel(new GridLayout(5, 2, 10, 10));

		customizationPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
		customizationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		boardName = new FlatTextField();
		boardDescription = new FlatTextField();
		boardWidth = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		boardHeight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

		customizationPanel.add(new JLabel("Name:"));
		customizationPanel.add(boardName);
		customizationPanel.add(new JLabel("Description:"));
		customizationPanel.add(boardDescription);
		customizationPanel.add(new JLabel("Width (in tiles):"));
		customizationPanel.add(boardWidth);
		customizationPanel.add(new JLabel("Height (in tiles):"));
		customizationPanel.add(boardHeight);

		createButton = new JButton("Create");
		createButton.addActionListener(e -> createOrUpdateBoard());
		customizationPanel.add(createButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> toggleCustomizationPanelVisibility());
		customizationPanel.add(cancelButton);

		return customizationPanel;
	}

	private void createOrUpdateBoard()
	{
		String newBoardName = this.boardName.getText().trim();
		String newBoardDescription = this.boardDescription.getText().trim();
		int newBoardWidth = (int) boardWidth.getValue();
		int newBoardHeight = (int) boardHeight.getValue();

		if (newBoardName.isEmpty() || newBoardDescription.isEmpty() || newBoardWidth <= 0 || newBoardHeight <= 0)
		{
			JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (this.edittingBoard != null)
		{
			edittingBoard.setBoardName(newBoardName);
			edittingBoard.setBoardDescription(newBoardDescription);
			edittingBoard.setBoardWidth(newBoardWidth);
			edittingBoard.setBoardHeight(newBoardHeight);
		}
		else
		{
			boards.add(new BingoBoard(getBoardName(), getBoardDescription(), getBoardWidth(), getBoardHeight()));
		}
		refreshBoardList();
		resetCustomizationFields();
		toggleCustomizationPanelVisibility();
	}

	private void refreshBoardList()
	{
		boardsListPanel.removeAll();
		boardsListPanel.add(boardCustomizationPanel);

		for (BingoBoard board : boards)
		{
			JPanel boardPanel = createBoardPanel(board);
			boardsListPanel.add(boardPanel);
		}

		boardsListPanel.revalidate();
		boardsListPanel.repaint();
	}

	private void resetCustomizationFields()
	{
		boardName.setText("");
		boardDescription.setText("");
		boardWidth.setValue(1);
		boardHeight.setValue(1);
		edittingBoard = null;
	}

	private JPanel createBoardPanel(BingoBoard board)
	{
		JPanel boardPanel = new JPanel(new BorderLayout());
		boardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		boardPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JLabel boardNameLabel = new JLabel(board.getBoardName());
		boardNameLabel.setForeground(ColorScheme.TEXT_COLOR);
		boardPanel.add(boardNameLabel, BorderLayout.WEST);

		JPanel panelButtons = new JPanel(new FlowLayout());

		JButton editButton = new JButton(EDIT_ICON);
		editButton.setBorder(BorderFactory.createEmptyBorder());
		editButton.setContentAreaFilled(false);
		editButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				editButton.setIcon(EDIT_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				editButton.setIcon(EDIT_ICON);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				editBoard(board);
			}
		});
		panelButtons.add(editButton);

		JButton deleteButton = new JButton(DELETE_ICON);
		deleteButton.setBorder(BorderFactory.createEmptyBorder());
		deleteButton.setContentAreaFilled(false);
		deleteButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				deleteButton.setIcon(DELETE_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				deleteButton.setIcon(DELETE_ICON);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				deleteBoard(board);
			}
		});
		panelButtons.add(deleteButton);
		boardPanel.add(panelButtons, BorderLayout.EAST);

		return boardPanel;
	}

	private void deleteBoard(BingoBoard board)
	{
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + boardName + "?", "Delete Board", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION)
		{
			boards.remove(board);
			refreshBoardList();
		}
	}

	private void toggleCustomizationPanelVisibility()
	{
		isCustomizationPanelVisible = !isCustomizationPanelVisible;
		boardCustomizationPanel.setVisible(isCustomizationPanelVisible);
		this.revalidate();
		this.repaint();
	}

	private void toggleBoardListVisibility()
	{

		expandButton.setVisible(!boards.isEmpty());

		isCollapsed = !isCollapsed;
		if (isCollapsed)
		{
			this.expandButton.setIcon(ARROW_UP_ICON);
		}
		else
		{
			this.expandButton.setIcon(ARROW_DOWN_ICON);
		}
		this.revalidate();
		this.repaint();
	}

	private String getBoardName()
	{
		return boardName.getText();
	}

	private String getBoardDescription()
	{
		return boardDescription.getText();
	}

	private int getBoardWidth()
	{
		return (int) boardWidth.getValue();
	}

	private int getBoardHeight()
	{
		return (int) boardHeight.getValue();
	}

	private void editBoard(BingoBoard board)
	{
		edittingBoard = board;
		boardName.setText(board.getBoardName());
		boardDescription.setText(board.getBoardDescription());
		boardWidth.setValue(board.getBoardWidth());
		boardHeight.setValue(board.getBoardHeight());

		createButton.setText("Save");

		if (!isCustomizationPanelVisible)
		{
			toggleCustomizationPanelVisibility();
		}
	}
}
