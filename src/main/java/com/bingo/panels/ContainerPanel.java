package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

public class ContainerPanel extends PluginPanel
{
	private static final String TITLE;
	private static final ImageIcon GITHUB_ICON;
	private static final ImageIcon GITHUB_ICON_HOVER;
	private static final ImageIcon HOME_ICON;
	private static final ImageIcon HOME_ICON_HOVER;
	private final JPanel headerPanel;
	private final JPanel headerButtons;
	private final JLabel titleLabel;
	private final JLabel githubButton;
	private final JLabel homeButton;
	String GITHUB_LINK = "https://github.com/psenk/RLBingoPlugin";

	private final BingoScapePlugin plugin;

	static
	{
		final BufferedImage githubIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/github_icon.png");
		GITHUB_ICON = new ImageIcon(githubIcon);
		GITHUB_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(githubIcon, -100));

		final BufferedImage homeIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/home_icon.png");
		HOME_ICON = new ImageIcon(homeIcon);
		HOME_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(homeIcon, -100));

		TITLE = "BingoScape";
	}

	public ContainerPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;
		this.headerPanel = new JPanel(new BorderLayout());

		this.titleLabel = new JLabel(TITLE);
		titleLabel.setForeground(Color.WHITE);

		this.githubButton = new JLabel(GITHUB_ICON);
		githubButton.setVisible(true);
		githubButton.setToolTipText(GITHUB_LINK);
		githubButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					LinkBrowser.browse(GITHUB_LINK);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				githubButton.setIcon(GITHUB_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				githubButton.setIcon(GITHUB_ICON);
			}
		});

		this.homeButton = new JLabel(HOME_ICON);
		homeButton.setVisible(false); // because main is default
		homeButton.setToolTipText("Return to main screen.");
		homeButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					plugin.panelSelector(BingoConfig.Panel.MAIN);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				homeButton.setIcon(HOME_ICON_HOVER);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				homeButton.setIcon(HOME_ICON);
			}
		});

		this.headerButtons = new JPanel();
		headerButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 5));
		headerButtons.add(homeButton);
		headerButtons.add(githubButton);

		final JPanel headerTopRow = new JPanel(new BorderLayout());
		headerTopRow.add(titleLabel, BorderLayout.WEST);
		headerTopRow.add(headerButtons, BorderLayout.EAST);

		headerPanel.add(headerTopRow, BorderLayout.NORTH);
		this.add(headerPanel, BorderLayout.NORTH);
		this.revalidate();
		this.repaint();
	}

	public void updateHomeButton(boolean onMainPage)
	{
		homeButton.setVisible(!onMainPage);
	}
}
