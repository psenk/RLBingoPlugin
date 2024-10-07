package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import javax.inject.Inject;
import javax.swing.JPanel;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.PluginPanel;

@ConfigGroup("bingo")
public class ActiveBingoPanel extends PluginPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.ACTIVE;

	@Inject
	private ConfigManager configManager;

	private final AuthPanel authPanel;
	private final JPanel teamPanel;

	protected final BingoScapePlugin plugin;

	// TODO: icons?
	// TODO: enums: bosses, actions/tasks

	public ActiveBingoPanel(final BingoScapePlugin plugin)
	{
		super(false);
		this.plugin = plugin;

		JPanel headerPanel = new JPanel();
		headerPanel.setVisible(true);

		// auth panel
		authPanel = new AuthPanel(this);
		headerPanel.add(authPanel);

		// TODO: await?
		// team panel
		teamPanel = new JPanel();
		teamPanel.setVisible(false);

		this.add(headerPanel);
		this.updatePanelVisibility();
	}

	public void updatePanelVisibility()
	{
		if (plugin.getActiveToken() != null) // TODO: isValidToken()?
		{
			authPanel.setVisible(false);
			teamPanel.setVisible(true);
			return;
		}
		authPanel.setVisible(true);
		teamPanel.setVisible(false);
	}
}
