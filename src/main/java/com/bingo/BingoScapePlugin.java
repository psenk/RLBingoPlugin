package com.bingo;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "BingoScape"
)
public class BingoScapePlugin extends Plugin
{
	public boolean isStarted;

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private BingoConfig config;

	private MainPanel mainPanel;
	private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		//log.info("Example started!");
		this.isStarted = true;

		mainPanel = new MainPanel();
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
		navigationButton = NavigationButton.builder()
			.tooltip("BingoScape")
			.icon(icon)
			.priority(100)
			.panel(mainPanel)
			.build();
		clientToolbar.addNavigation(navigationButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
		clientToolbar.removeNavigation(navigationButton);
		this.isStarted = false;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.box(), null);
		}
	}

	@Provides
	BingoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BingoConfig.class);
	}
}
