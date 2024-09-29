package com.bingo;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
	name = "Bingo Plugin"
)
public class BingoPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BingoConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
		this.isStarted = true;
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
		this.isStarted = false;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
    BingoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BingoConfig.class);
	}

	// my code starts here

	public boolean isStarted;
}
