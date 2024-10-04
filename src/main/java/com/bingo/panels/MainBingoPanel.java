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
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class MainBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.MAIN;

	private final JPanel buttonsPanel;
	private final JScrollPane contentWrapperPane;

	private final JPanel activeBingoButton;
	private final JPanel createBingoButton;
	private final JPanel modifyBingoButton;

	private final BingoScapePlugin plugin;

	public MainBingoPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;
		this.buttonsPanel = new JPanel(new BorderLayout());

		this.activeBingoButton = buttonBuilder(BingoConfig.Panel.ACTIVE);
		this.createBingoButton = buttonBuilder(BingoConfig.Panel.CREATE);
		this.modifyBingoButton = buttonBuilder(BingoConfig.Panel.MODIFY);

		buttonsPanel.add(activeBingoButton, BorderLayout.NORTH);
		buttonsPanel.add(createBingoButton, BorderLayout.CENTER);
		buttonsPanel.add(modifyBingoButton, BorderLayout.SOUTH);

		// add all in here
		final JPanel contentPanel = new JPanel();
		final BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.add(buttonsPanel);

		final JPanel contentWrapper = new JPanel(new BorderLayout());
		contentWrapper.add(contentPanel, BorderLayout.NORTH);
		this.contentWrapperPane = new JScrollPane(contentWrapper);
		contentWrapperPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		add(contentWrapperPane, BorderLayout.CENTER);
	}

	private JPanel buttonBuilder(BingoConfig.Panel panel)
	{
		JPanel button = new JPanel();
		button.add(new JLabel(panel.getTitle()));
		button.setVisible(true);
		button.setToolTipText(panel.getTooltip());
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

	// TODO: build the plugin panel


	// NOTES:
	// - use static initializers
	// Java Swift!
	// Add titles and buttons to all Panels
}
