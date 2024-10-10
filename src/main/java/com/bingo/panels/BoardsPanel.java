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
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.util.ImageUtil;

public class BoardsPanel extends JPanel
{
	// TODO: add total tiles panel
	@Setter
	private BoardListener boardListener;

	@Getter
	public List<BingoBoard> boards;

	private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_ICON_HOVER;
	private static final ImageIcon DELETE_ICON;
	private static final ImageIcon DELETE_ICON_HOVER;
	private static final ImageIcon EDIT_ICON;
	private static final ImageIcon EDIT_ICON_HOVER;

	private final JPanel boardsListPanel;
	private final JPanel boardCustomizationPanel;
	private JButton createButton;
	private boolean isCustomizationPanelVisible;
	private JScrollPane boardsScrollPane;
	private BingoBoard editingBoard;

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
	}

	public BoardsPanel()
	{
		this.setLayout(new BorderLayout());
		this.boards = new ArrayList<>();
		this.isCustomizationPanelVisible = false;

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);

		JLabel headerLabel = new JLabel("Boards");
		headerLabel.setForeground(ColorScheme.TEXT_COLOR);
		headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		headerPanel.add(headerLabel, BorderLayout.WEST);

		JPanel headerButtons = new JPanel(new FlowLayout());

		JButton addBoardButton = new JButton(ADD_ICON);
		addBoardButton.setBorder(BorderFactory.createEmptyBorder());
		addBoardButton.setContentAreaFilled(false);
		addBoardButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					toggleCustomizationPanelVisibility(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				addBoardButton.setIcon(ADD_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				addBoardButton.setIcon(ADD_ICON);
			}
		});
		headerButtons.add(addBoardButton);

		headerPanel.add(headerButtons, BorderLayout.EAST);
		this.add(headerPanel, BorderLayout.NORTH);

		boardsListPanel = new JPanel();
		boardsListPanel.setLayout(new BoxLayout(boardsListPanel, BoxLayout.Y_AXIS));
		boardsListPanel.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);

		boardsScrollPane = new JScrollPane(boardsListPanel);
		boardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		boardsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		boardsScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 800));
		this.add(boardsScrollPane, BorderLayout.CENTER);

		boardCustomizationPanel = createBoardCustomizationPanel();
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
		cancelButton.addActionListener(e -> toggleCustomizationPanelVisibility(false));
		customizationPanel.add(cancelButton);
		customizationPanel.setVisible(false);
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

		if (this.editingBoard != null)
		{
			editingBoard.setBoardName(newBoardName);
			editingBoard.setBoardDescription(newBoardDescription);
			editingBoard.setBoardWidth(newBoardWidth);
			editingBoard.setBoardHeight(newBoardHeight);
		}
		else
		{
			BingoBoard board = new BingoBoard(getBoardName(), getBoardDescription(), getBoardWidth(), getBoardHeight());
			boards.add(board);

			if (boardListener != null)
			{
				boardListener.onBoardAdded(board);
			}
		}

		refreshBoardList();
		resetCustomizationFields();
		toggleCustomizationPanelVisibility(false);
	}

	private void refreshBoardList()
	{
		boardsListPanel.removeAll();
		boardsListPanel.add(boardCustomizationPanel);

		for (BingoBoard board : boards)
		{
			JPanel boardPanel = createBoardPanel(board);
			boardPanel.setForeground(ColorScheme.TEXT_COLOR);
			boardsListPanel.add(boardPanel);
		}

		if (boards.size() >= 5)
		{
			boardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		else
		{
			boardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}

		boardsScrollPane.setVisible(!boards.isEmpty());

		boardsListPanel.revalidate();
		boardsListPanel.repaint();
	}

	private void resetCustomizationFields()
	{
		boardName.setText("");
		boardDescription.setText("");
		boardWidth.setValue(1);
		boardHeight.setValue(1);
		editingBoard = null;
	}

	private JPanel createBoardPanel(BingoBoard board)
	{
		JPanel boardPanel = new JPanel(new BorderLayout());
		boardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		boardPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
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
			public void mouseClicked(MouseEvent e)
			{
				editBoard(board);
			}

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
		});
		panelButtons.add(editButton);

		JButton deleteButton = new JButton(DELETE_ICON);
		deleteButton.setBorder(BorderFactory.createEmptyBorder());
		deleteButton.setContentAreaFilled(false);
		deleteButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				deleteBoard(board);
			}

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
		});
		panelButtons.add(deleteButton);

		boardPanel.add(panelButtons, BorderLayout.EAST);
		if (boardListener != null)
		{
			boardListener.onBoardAdded(board);
		}

		return boardPanel;
	}

	private void deleteBoard(BingoBoard board)
	{
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + boardName + "?", "Delete Board", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION)
		{
			if (boardListener != null) {
				boardListener.onBoardRemoved(board);
			}
			boards.remove(board);
			refreshBoardList();
		}
	}

	private void toggleCustomizationPanelVisibility(boolean visible)
	{
		isCustomizationPanelVisible = visible;
		boardCustomizationPanel.setVisible(isCustomizationPanelVisible);
		if (isCustomizationPanelVisible)
		{
			boardsListPanel.remove(boardCustomizationPanel);
			boardsListPanel.add(boardCustomizationPanel, 0);
		}
		refreshBoardList();
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
		this.editingBoard = board;
		boardName.setText(board.getBoardName());
		boardDescription.setText(board.getBoardDescription());
		boardWidth.setValue(board.getBoardWidth());
		boardHeight.setValue(board.getBoardHeight());

		createButton.setText("Save");

		if (!isCustomizationPanelVisible)
		{
			toggleCustomizationPanelVisibility(true);
		}
	}
}
