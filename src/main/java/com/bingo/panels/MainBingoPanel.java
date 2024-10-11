package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.io.TokenManager;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class MainBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.MAIN;
	private final BingoScapePlugin plugin;
	private final TokenManager tokenManager;

	public MainBingoPanel(final BingoScapePlugin plugin, TokenManager tokenManager)
	{
		super(false);
		this.plugin = plugin;
		this.tokenManager = tokenManager;
		setVisible(true);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		JPanel activeBingoButton = buttonBuilder(BingoConfig.Panel.ACTIVE);
		JPanel createBingoButton = buttonBuilder(BingoConfig.Panel.CREATE);
		JPanel modifyBingoButton = buttonBuilder(BingoConfig.Panel.MODIFY);

		buttonsPanel.add(activeBingoButton);
		buttonsPanel.add(Box.createVerticalStrut(10));
		buttonsPanel.add(createBingoButton);
		buttonsPanel.add(Box.createVerticalStrut(10));
		buttonsPanel.add(modifyBingoButton);

		add(buttonsPanel, BorderLayout.NORTH);
		this.revalidate();
		this.repaint();
	}

	private JPanel buttonBuilder(BingoConfig.Panel panel)
	{
		JPanel button = new JPanel();
		button.add(new JLabel(panel.title));
		button.setVisible(true);
		button.setToolTipText(panel.tooltip);
		button.setBackground(ColorScheme.CONTROL_COLOR);
		button.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
		button.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					if (tokenManager.getActiveToken() == null)
					{
						plugin.panelSelector(BingoConfig.Panel.AUTH);
					}
					else
					{
						plugin.panelSelector(panel);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				button.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
				button.setBorder(new LineBorder(ColorScheme.MEDIUM_GRAY_COLOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				button.setBackground(ColorScheme.CONTROL_COLOR);
				button.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
			}
		});
		return button;
	}
}
