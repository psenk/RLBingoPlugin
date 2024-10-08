package com.bingo.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

public class TeamsPanel extends JPanel
{
	@Getter
	private final List<String> teamNames;

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


	private final JPanel teamsListPanel;
	private JButton expandButton;
	private boolean isCollapsed;

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

	public TeamsPanel()
	{
		this.setLayout(new BorderLayout());
		this.teamNames = new ArrayList<>();
		this.isCollapsed = false;

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BorderLayout());
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);

		JLabel headerLabel = new JLabel("Teams");
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
				toggleTeamListVisibility();
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
					expandButton.setIcon(ARROW_DOWN_ICON);
				}
			}
		});
		expandButton.setVisible(false);
		headerButtons.add(expandButton);

		JButton addTeamButton = new JButton(ADD_ICON);
		addTeamButton.setBorder(BorderFactory.createEmptyBorder());
		addTeamButton.setContentAreaFilled(false);
		addTeamButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				addTeamButton.setIcon(ADD_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				addTeamButton.setIcon(ADD_ICON);
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				addNewTeam();
			}
		});
		headerButtons.add(addTeamButton);

		headerPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				toggleTeamListVisibility();
			}
		});
		headerPanel.add(headerButtons, BorderLayout.EAST);
		this.add(headerPanel, BorderLayout.NORTH);

		teamsListPanel = new JPanel();
		teamsListPanel.setLayout(new BoxLayout(teamsListPanel, BoxLayout.Y_AXIS));

		JScrollPane teamsScrollPane = new JScrollPane(teamsListPanel);
		teamsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		teamsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		teamsListPanel.setPreferredSize(isCollapsed ? new Dimension(0, 0) : null);
		teamsScrollPane.setVisible(!isCollapsed);
		this.add(teamsScrollPane, BorderLayout.CENTER);
	}

	private void addNewTeam()
	{
		String teamName = JOptionPane.showInputDialog(this, "Enter Team Name:", "Add Team", JOptionPane.PLAIN_MESSAGE);

		if (teamName != null && !teamName.trim().isEmpty())
		{
			if (teamNames.contains(teamName))
			{
				JOptionPane.showMessageDialog(this, "This team already exists.", "Duplicate Tea,", JOptionPane.ERROR_MESSAGE);
				return;
			}
			teamNames.add(teamName);
			refreshTeamList();
		}
	}

	private void refreshTeamList()
	{
		teamsListPanel.removeAll();

		for (String teamName : teamNames)
		{
			JPanel teamPanel = createTeamPanel(teamName);
			teamsListPanel.add(teamPanel);
		}
		teamsListPanel.revalidate();
		teamsListPanel.repaint();
	}

	private JPanel createTeamPanel(String teamName)
	{
		JPanel teamPanel = new JPanel();
		teamPanel.setLayout(new BorderLayout());
		teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		teamPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JLabel teamNameLabel = new JLabel(teamName);
		teamNameLabel.setForeground(ColorScheme.TEXT_COLOR);
		teamPanel.add(teamNameLabel, BorderLayout.WEST);

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
				editTeam(teamName);
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
				deleteTeam(teamName);
			}
		});
		panelButtons.add(deleteButton);

		teamPanel.add(panelButtons, BorderLayout.EAST);
		return teamPanel;
	}

	private void deleteTeam(String teamName)
	{
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + teamName + "?", "Delete Team", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION)
		{
			teamNames.remove(teamName);
			refreshTeamList();
		}
	}

	private void toggleTeamListVisibility()
	{
		expandButton.setVisible(!teamNames.isEmpty());

		isCollapsed = !isCollapsed;
		if (isCollapsed)
		{
			this.expandButton.setIcon(ARROW_UP_ICON);
		}
		else
		{
			this.expandButton.setIcon(ARROW_DOWN_ICON);
		}
		teamsListPanel.setVisible(!isCollapsed);
		this.revalidate();
	}

	private void editTeam(String teamName)
	{
		String newName = JOptionPane.showInputDialog(this, "Edit Team Name:", teamName);
		if (newName != null && !newName.trim().isEmpty())
		{
			if (teamNames.contains(newName) && !newName.equals(teamName))
			{
				JOptionPane.showMessageDialog(this, "This team name already exists.", "Duplicate Team", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int index = teamNames.indexOf(teamName);
			if (index != -1)
			{
				teamNames.set(index, newName);
				refreshTeamList();
			}
			else if (newName.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Team name cannot be empty.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}