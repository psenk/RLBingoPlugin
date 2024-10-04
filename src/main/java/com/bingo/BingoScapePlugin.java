package com.bingo;

import com.bingo.panels.ActiveBingoPanel;
import com.bingo.panels.ContainerPanel;
import com.bingo.panels.CreateBingoPanel;
import com.bingo.panels.MainBingoPanel;
import com.bingo.panels.ModifyBingoPanel;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.Getter;
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
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "BingoScape"
)
public class BingoScapePlugin extends Plugin
{
	@Getter
	private boolean onMainPage;

	@Inject
	private Client client;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private BingoConfig config;

	private ContainerPanel containerPanel;
	private MainBingoPanel mainBingoPanel;
	private ActiveBingoPanel activeBingoPanel;
	private CreateBingoPanel createBingoPanel;
	private ModifyBingoPanel modifyBingoPanel;

	private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		//log.info("Example started!");
		this.containerPanel = new ContainerPanel(this);
		this.mainBingoPanel = new MainBingoPanel(this);
		this.activeBingoPanel = new ActiveBingoPanel(this);
		this.createBingoPanel = new CreateBingoPanel(this);
		this.modifyBingoPanel = new ModifyBingoPanel(this);

		containerPanel.add(mainBingoPanel);
		containerPanel.add(activeBingoPanel);
		containerPanel.add(createBingoPanel);
		containerPanel.add(modifyBingoPanel);

		mainBingoPanel.setVisible(true);
		this.onMainPage = true;
		activeBingoPanel.setVisible(false);
		createBingoPanel.setVisible(false);
		modifyBingoPanel.setVisible(false);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/com/bingo/icon.png");
		if (icon == null)
		{
			log.warn("plugin icon is null");
			return;
		}
		config.setActivePanel(BingoConfig.Panel.MAIN);
		navigationButton = NavigationButton.builder()
			.tooltip("BingoScape")
			.icon(icon)
			.priority(100)
			.panel(containerPanel)
			.build();
		clientToolbar.addNavigation(navigationButton);
		containerPanel.setVisible(true);
	}

	protected void setActivePanel(PluginPanel panel)
	{
		if (panel == null)
		{
			log.warn("setActivePanel: panel is null");
			return;
		}

		mainBingoPanel.setVisible(false);
		activeBingoPanel.setVisible(false);
		createBingoPanel.setVisible(false);
		modifyBingoPanel.setVisible(false);
		panel.setVisible(true);

		containerPanel.revalidate();
		containerPanel.repaint();
	}

	public void panelSelector(BingoConfig.Panel p)
	{
		switch (p)
		{
			case MAIN:
				this.onMainPage = true;
				setActivePanel(mainBingoPanel);
				config.setActivePanel(BingoConfig.Panel.MAIN);
				break;
			case ACTIVE:
				this.onMainPage = false;
				setActivePanel(activeBingoPanel);
				config.setActivePanel(BingoConfig.Panel.ACTIVE);
				break;
			case CREATE:
				this.onMainPage = false;
				setActivePanel(createBingoPanel);
				config.setActivePanel(BingoConfig.Panel.CREATE);
				break;
			case MODIFY:
				this.onMainPage = false;
				setActivePanel(modifyBingoPanel);
				config.setActivePanel(BingoConfig.Panel.MODIFY);
				break;
		}
		containerPanel.updateHomeButton(this.onMainPage);
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
		clientToolbar.removeNavigation(navigationButton);
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
