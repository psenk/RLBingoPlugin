package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import javax.swing.JPanel;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class ActiveBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.ACTIVE;

	private final JPanel headerPanel;

	private final AuthPanel authPanel;
	private final JPanel teamPanel;

	private final BingoScapePlugin plugin;

	// TODO: icons?
	// TODO: enums: bosses, actions/tasks

	public ActiveBingoPanel(final BingoScapePlugin plugin, BingoConfig config)
	{
		super(false);
		this.plugin = plugin;

		headerPanel = new JPanel();
		headerPanel.setVisible(true);

		// auth panel
		authPanel = new AuthPanel(config, this);
		headerPanel.add(authPanel);

		// TODO: await?
		// team panel
		teamPanel = new JPanel();
		teamPanel.setVisible(false);

		updatePanelVisibility(config);

		this.add(headerPanel);
	}

	public void updatePanelVisibility(BingoConfig config)
	{
		if (config.activeToken() != null)
		{
			authPanel.setVisible(false);
			teamPanel.setVisible(true);
		}
		else
		{
			authPanel.setVisible(true);
			teamPanel.setVisible(false);
		}
	}
}
