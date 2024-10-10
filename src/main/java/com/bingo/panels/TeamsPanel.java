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
import javax.swing.SwingUtilities;
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

	private final JPanel teamsListPanel;
	private final JScrollPane teamsScrollPane;

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

	public TeamsPanel()
	{
		this.setLayout(new BorderLayout());
		this.teamNames = new ArrayList<>();

		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(ColorScheme.CONTROL_COLOR);

		JLabel headerLabel = new JLabel("Teams");
		headerLabel.setForeground(ColorScheme.TEXT_COLOR);
		headerLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		headerPanel.add(headerLabel, BorderLayout.WEST);

		JPanel headerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton addTeamButton = new JButton(ADD_ICON);
		addTeamButton.setBorder(BorderFactory.createEmptyBorder());
		addTeamButton.setContentAreaFilled(false);
		addTeamButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					addNewTeam();
				}
			}

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
		});
		headerButtons.add(addTeamButton);

		headerPanel.add(headerButtons, BorderLayout.EAST);
		this.add(headerPanel, BorderLayout.NORTH);

		teamsListPanel = new JPanel();
		teamsListPanel.setLayout(new BoxLayout(teamsListPanel, BoxLayout.Y_AXIS));
		teamsListPanel.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		teamsListPanel.setPreferredSize(new Dimension(225, 800));

		teamsScrollPane = new JScrollPane(teamsListPanel);
		teamsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		teamsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
			teamPanel.setForeground(ColorScheme.TEXT_COLOR);
			teamsListPanel.add(teamPanel);
		}

		if (teamNames.size() >= 5)
		{
			teamsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		}
		else
		{
			teamsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		teamsScrollPane.setVisible(!teamNames.isEmpty());

		teamsListPanel.revalidate();
		teamsListPanel.repaint();
	}

	private JPanel createTeamPanel(String teamName)
	{
		JPanel teamPanel = new JPanel(new BorderLayout());
		teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		teamPanel.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
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
			teamNames.remove(teamName);
			teamNames.add(newName);
			refreshTeamList();
		}
	}
}