package com.bingo.panels;

import com.bingo.bingo.BingoBoard;
import com.bingo.bingo.BingoGame;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.util.ImageUtil;

public class BoardsPanel extends JPanel
{
	// TODO: add total tiles panel
	private BingoGame activeGame;

	private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_ICON_HOVER;
	private static final ImageIcon DELETE_ICON;
	private static final ImageIcon DELETE_ICON_HOVER;
	private static final ImageIcon EDIT_ICON;
	private static final ImageIcon EDIT_ICON_HOVER;

	private JDialog customizationDialog;
	private final JPanel boardsListPanel;
	private final JScrollPane boardsScrollPane;

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

	public BoardsPanel(BingoGame activeGame)
	{
		this.activeGame = activeGame;
		setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);
		headerPanel.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));

		JLabel headerLabel = new JLabel("Boards");
		headerLabel.setForeground(ColorScheme.TEXT_COLOR);
		headerLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
		headerPanel.add(headerLabel, BorderLayout.WEST);

		JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton addBoardButton = new JButton(ADD_ICON);
		addBoardButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		addBoardButton.setContentAreaFilled(false);
		addBoardButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					showCustomizationDialog(null);
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
		add(headerPanel, BorderLayout.NORTH);

		boardsListPanel = new JPanel();
		boardsListPanel.setLayout(new BoxLayout(boardsListPanel, BoxLayout.Y_AXIS));
		boardsListPanel.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);

		boardsScrollPane = new JScrollPane(boardsListPanel);
		boardsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		boardsScrollPane.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		add(boardsScrollPane, BorderLayout.CENTER);
	}

	private void showCustomizationDialog(BingoBoard board)
	{
		customizationDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Board Customization", Dialog.ModalityType.APPLICATION_MODAL);
		customizationDialog.setLayout(new GridLayout(5, 2, 10, 10));
		customizationDialog.setSize(new Dimension(225, 200));
		customizationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		customizationDialog.setLocationRelativeTo(boardsListPanel);
		customizationDialog.setResizable(false);

		boardName = new FlatTextField();
		boardName.requestFocusInWindow();
		boardName.setBackground(ColorScheme.CONTROL_COLOR);
		boardDescription = new FlatTextField();
		boardDescription.setBackground(ColorScheme.CONTROL_COLOR);
		boardWidth = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
		boardHeight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

		customizationDialog.add(new JLabel("Name:"));
		customizationDialog.add(boardName);
		customizationDialog.add(new JLabel("Description:"));
		customizationDialog.add(boardDescription);
		customizationDialog.add(new JLabel("Width (in tiles):"));
		customizationDialog.add(boardWidth);
		customizationDialog.add(new JLabel("Height (in tiles):"));
		customizationDialog.add(boardHeight);

		if (board != null)
		{
			boardName.setText(board.getBoardName());
			boardDescription.setText(board.getBoardDescription());
			boardWidth.setValue(board.getBoardWidth());
			boardHeight.setValue(board.getBoardHeight());
		}

		JButton saveButton = new JButton(board == null ? "Create" : "Save");
		saveButton.addActionListener(e -> createOrUpdateBoard(board));
		customizationDialog.add(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> customizationDialog.dispose());
		customizationDialog.add(cancelButton);
		customizationDialog.setVisible(true);
	}

	private void createOrUpdateBoard(BingoBoard board)
	{
		String newBoardName = getBoardName();
		String newBoardDescription = getBoardDescription();
		int newBoardWidth = getBoardWidth();
		int newBoardHeight = getBoardHeight();

		if (newBoardName.isEmpty() || newBoardDescription.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (board != null)
		{
			board.setBoardName(newBoardName);
			board.setBoardDescription(newBoardDescription);
			board.setBoardWidth(newBoardWidth);
			board.setBoardHeight(newBoardHeight);
		}
		else
		{
			BingoBoard newBoard = new BingoBoard(newBoardName, newBoardDescription, newBoardWidth, newBoardHeight);
			activeGame.addBingoBoard(newBoard);
		}

		refreshBoardList();
		customizationDialog.dispose();
	}

	private void refreshBoardList()
	{
		boardsListPanel.removeAll();
		boardsScrollPane.getVerticalScrollBar().setValue(0);

		for (BingoBoard board : activeGame.getBingoBoards().values())
		{
			JPanel boardPanel = createBoardPanel(board);
			boardPanel.setForeground(ColorScheme.TEXT_COLOR);
			boardsListPanel.add(boardPanel);
		}

		boardsScrollPane.setVerticalScrollBarPolicy(boardsListPanel.getPreferredSize().height > boardsScrollPane.getViewport().getHeight() ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		boardsScrollPane.setVisible(!activeGame.getBingoBoards().isEmpty());

		boardsListPanel.revalidate();
		boardsListPanel.repaint();
	}

	private JPanel createBoardPanel(BingoBoard board)
	{
		JPanel boardPanel = new JPanel(new BorderLayout());
		boardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		boardPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		boardPanel.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
		boardPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JLabel boardNameLabel = new JLabel(board.getBoardName());
		boardNameLabel.setForeground(ColorScheme.TEXT_COLOR);
		boardPanel.add(boardNameLabel, BorderLayout.WEST);

		JPanel panelButtons = new JPanel(new FlowLayout());

		JButton editButton = new JButton(EDIT_ICON);
		editButton.setBorder(new EmptyBorder(0, 0, 0, 0));
		editButton.setContentAreaFilled(false);
		editButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				showCustomizationDialog(board);
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
		deleteButton.setBorder(new EmptyBorder(0, 0, 0, 0));
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

		return boardPanel;
	}

	private void deleteBoard(BingoBoard board)
	{
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + board.getBoardName() + "?", "Delete Board", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION)
		{
			activeGame.removeBingoBoard(board);
			refreshBoardList();
		}
	}

	private String getBoardName()
	{
		return boardName.getText().trim();
	}

	private String getBoardDescription()
	{
		return boardDescription.getText().trim();
	}

	private int getBoardWidth()
	{
		return (int) boardWidth.getValue();
	}

	private int getBoardHeight()
	{
		return (int) boardHeight.getValue();
	}
}
