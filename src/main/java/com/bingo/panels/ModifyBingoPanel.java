package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.bingo.BingoGame;
import com.bingo.io.TokenManager;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import lombok.Setter;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

@ConfigGroup("bingo")
public class ModifyBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.MODIFY;
	private final BingoScapePlugin plugin;
	private final TokenManager tokenManager;

	private static final ImageIcon ARROW_DOWN_ICON;
	private static final ImageIcon ARROW_DOWN_ICON_HOVER;
	private static final ImageIcon ARROW_UP_ICON;
	private static final ImageIcon ARROW_UP_ICON_HOVER;

	private final AuthPanel authPanel;
	private final JPanel contentPanel;

	@Setter
	private BingoGame bingoGame = new BingoGame(0);

	static
	{
		final BufferedImage arrowDownIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_DOWN_ICON = new ImageIcon(arrowDownIcon);
		ARROW_DOWN_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowDownIcon, -100));

		final BufferedImage arrowUpIcon = ImageUtil.loadImageResource(MainBingoPanel.class, "/com/bingo/arrow_icon.png");
		ARROW_UP_ICON = new ImageIcon(ImageUtil.flipImage(arrowDownIcon, false, true));
		ARROW_UP_ICON_HOVER = new ImageIcon(ImageUtil.luminanceOffset(arrowUpIcon, -100));
	}

	public ModifyBingoPanel(final BingoScapePlugin plugin, final TokenManager tokenManager)
	{
		super(false);
		this.plugin = plugin;
		this.tokenManager = tokenManager;
		this.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		authPanel = new AuthPanel(plugin, BingoConfig.Panel.MODIFY);
		authPanel.unhideAdminPanel(true);
		headerPanel.add(authPanel);
		this.add(headerPanel, BorderLayout.NORTH);

		contentPanel = new JPanel();
		contentPanel.setVisible(false);
		this.add(contentPanel, BorderLayout.CENTER);
	}

	public void handleSuccessfulLogin() {
		authPanel.setVisible(false);
		contentPanel.setVisible(true);
		this.revalidate();
		this.repaint();
	}
}
