package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class MainBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.MAIN;

	private final BingoScapePlugin plugin;

	public MainBingoPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;

		JPanel activeBingoButton = buttonBuilder(BingoConfig.Panel.ACTIVE);
		JPanel createBingoButton = buttonBuilder(BingoConfig.Panel.CREATE);
		JPanel modifyBingoButton = buttonBuilder(BingoConfig.Panel.MODIFY);

		final JPanel contentPanel = new JPanel();
		final BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);

		JPanel buttonsPanel = new JPanel(new BorderLayout(0, 10));
		buttonsPanel.add(activeBingoButton, BorderLayout.NORTH);
		buttonsPanel.add(createBingoButton, BorderLayout.CENTER);
		buttonsPanel.add(modifyBingoButton, BorderLayout.SOUTH);
		contentPanel.add(buttonsPanel);

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		add(contentPanel, BorderLayout.CENTER);
	}

	private JPanel buttonBuilder(BingoConfig.Panel panel)
	{
		JPanel button = new JPanel();
		button.add(new JLabel(panel.title));
		button.setVisible(true);
		button.setToolTipText(panel.tooltip);
		button.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		button.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					button.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					plugin.panelSelector(panel);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				button.setBackground(ColorScheme.DARK_GRAY_COLOR);
				button.setBorder(BorderFactory.createLineBorder(ColorScheme.MEDIUM_GRAY_COLOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				button.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
				button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});
		return button;
	}
}
