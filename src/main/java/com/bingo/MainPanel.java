package com.bingo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

@ConfigGroup("bingo")
public class MainPanel extends PluginPanel
{

	private static String TITLE;
	private static ImageIcon GITHUB_ICON;
	private static ImageIcon GITHUB_HOVER_ICON;
	private final JPanel headerPanel;
	private final JScrollPane contentWrapperPane;
	private final JLabel titleLabel;
	private final JLabel githubButton;
	private final BingoScapePlugin plugin;

	String GITHUB_LINK = "https://github.com/psenk/RLBingoPlugin";

	// static initializer
	static
	{
		final BufferedImage githubIcon = ImageUtil.loadImageResource(MainPanel.class, "/com/bingo/github_icon.png");
		GITHUB_ICON = new ImageIcon(githubIcon);
		GITHUB_HOVER_ICON = new ImageIcon(ImageUtil.luminanceOffset(githubIcon, -100));
		TITLE = "BingoScape";
	}

	public MainPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;
		this.headerPanel = new JPanel(new BorderLayout());

		this.titleLabel = new JLabel();
		titleLabel.setText(TITLE);
		titleLabel.setForeground(Color.WHITE);

		this.githubButton = new JLabel(GITHUB_ICON);
		githubButton.setVisible(true);
		githubButton.setToolTipText("https://github.com/psenk/RLBingoPlugin");
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
				githubButton.setIcon(GITHUB_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				githubButton.setIcon(GITHUB_ICON);
			}
		});

		final JPanel headerTopRow = new JPanel(new BorderLayout());
		headerTopRow.add(titleLabel, BorderLayout.WEST);
		headerTopRow.add(githubButton, BorderLayout.EAST);

		headerPanel.add(headerTopRow, BorderLayout.NORTH);

		// add all in here
		final JPanel contentPanel = new JPanel();
		final BoxLayout contentLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(contentLayout);
		contentPanel.add(headerPanel);

		final JPanel contentWrapper = new JPanel(new BorderLayout());
		contentWrapper.add(contentPanel, BorderLayout.NORTH);
		this.contentWrapperPane = new JScrollPane(contentWrapper);
		contentWrapperPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));
		add(contentWrapperPane, BorderLayout.CENTER);
	}

	// TODO: build the plugin panel
	// https://github.com/runelite/runelite/blob/1cb4e6e08e87782a8700964f3462db5a48dd0fa2/runelite-client/src/main/java/net/runelite/client/plugins/xptracker/XpPanel.java#L138
	// at top: github link
	// page of buttons leading to different pages:
	// - create a bingo
	// - open a bingo
	// - modify a bingo
}
