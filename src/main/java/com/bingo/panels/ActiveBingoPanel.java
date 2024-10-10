package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.io.Token;
import com.bingo.io.TokenManager;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class ActiveBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.ACTIVE;
	protected final BingoScapePlugin plugin;
	private final TokenManager tokenManager;

	private final AuthPanel authPanel;
	private final JPanel teamPanel;

	// TODO: icons?
	// TODO: enums: bosses, actions/tasks

	public ActiveBingoPanel(final BingoScapePlugin plugin, TokenManager tokenManager)
	{
		super(false);
		this.plugin = plugin;
		this.tokenManager = tokenManager;
		this.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		authPanel = new AuthPanel(plugin, BingoConfig.Panel.ACTIVE);
		authPanel.unhideAdminPanel(false);
		headerPanel.add(authPanel);

		// TODO: await?
		teamPanel = new JPanel();
		teamPanel.setVisible(false);
		headerPanel.add(teamPanel);

		this.add(headerPanel, BorderLayout.NORTH);
		this.updatePanelVisibility();
	}

	public void updatePanelVisibility()
	{
		Token t = tokenManager.getActiveToken();
		if (tokenManager.isValidToken(t))
		{
			authPanel.setVisible(false);
			teamPanel.setVisible(true);
			return;
		}
		authPanel.setVisible(true);
		teamPanel.setVisible(false);
	}

	public void handleSuccessfulLogin()
	{

		authPanel.setVisible(false);
		teamPanel.setVisible(true);
		this.revalidate();
		this.repaint();
	}
}
