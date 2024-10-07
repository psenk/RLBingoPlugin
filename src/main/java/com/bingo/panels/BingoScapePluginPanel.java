package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

public class BingoScapePluginPanel extends PluginPanel
{
	private static final String TITLE;
	private static final ImageIcon GITHUB_ICON;
	private static final ImageIcon GITHUB_ICON_HOVER;
	private static final ImageIcon HOME_ICON;
	private static final ImageIcon HOME_ICON_HOVER;

	private final JLabel githubButton;
	private final JLabel homeButton;
	String GITHUB_LINK = "https://github.com/psenk/RLBingoPlugin";

	private final JPanel contentPanel;

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

	public BingoScapePluginPanel(final BingoScapePlugin plugin)
	{
		super(false);

		this.setLayout(new BorderLayout());
		JPanel headerPanel = new JPanel(new BorderLayout());
		this.contentPanel = new JPanel();
		contentPanel.setVisible(true);

		JLabel titleLabel = new JLabel(TITLE);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBorder(new EmptyBorder(10, 10, 5, 0));
		headerPanel.add(Box.createHorizontalStrut(10));
		headerPanel.add(titleLabel, BorderLayout.WEST);

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


		JPanel headerButtons = new JPanel();
		headerButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		headerButtons.add(homeButton);
		headerButtons.add(githubButton);

		headerPanel.add(headerButtons, BorderLayout.EAST);

		this.add(headerPanel, BorderLayout.NORTH);
		this.add(contentPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public void updateHomeButton(boolean onMainPage)
	{
		homeButton.setVisible(!onMainPage);
	}

	public <T extends PluginPanel> void addPanel(PluginPanel p)
	{
		this.contentPanel.add(p, BorderLayout.CENTER);
	}
}
