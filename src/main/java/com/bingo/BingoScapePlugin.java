package com.bingo;

import com.bingo.io.TokenManager;
import com.bingo.panels.ActiveBingoPanel;
import com.bingo.panels.BingoScapePluginPanel;
import com.bingo.panels.CreateBingoPanel;
import com.bingo.panels.MainBingoPanel;
import com.bingo.panels.ModifyBingoPanel;
import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
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

// TODO:

@Slf4j
@PluginDescriptor(
	name = "BingoScape"
)
public class BingoScapePlugin extends Plugin
{
	@Getter
	private boolean onMainPage = true; // we start on the main page

	@Inject
	private Client client;
	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private BingoConfig config;
	@Inject
	private ConfigManager configManager;
	private TokenManager tokenManager;

	private BingoScapePluginPanel bingoScapePluginPanel;
	private MainBingoPanel mainBingoPanel;
	@Getter
	private ActiveBingoPanel activeBingoPanel;
	private CreateBingoPanel createBingoPanel;
	@Getter
	private ModifyBingoPanel modifyBingoPanel;
	private NavigationButton navigationButton;

	private Map<BingoConfig.Panel, PluginPanel> panelMap = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		//log.info("Example started!");
		tokenManager = new TokenManager(configManager);
		tokenManager.destroyToken();

		initializePanels();
		setupNavigationButton();
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
		// TODO: do i need to log out of something here?
		tokenManager.destroyToken();
		clientToolbar.removeNavigation(navigationButton);
	}

	private void initializePanels()
	{
		this.bingoScapePluginPanel = new BingoScapePluginPanel(this);

		this.mainBingoPanel = new MainBingoPanel(this, tokenManager);
		this.activeBingoPanel = new ActiveBingoPanel(this, tokenManager);
		this.createBingoPanel = new CreateBingoPanel(this);
		this.modifyBingoPanel = new ModifyBingoPanel(this, tokenManager);

		panelMap.put(BingoConfig.Panel.MAIN, mainBingoPanel);
		panelMap.put(BingoConfig.Panel.ACTIVE, activeBingoPanel);
		panelMap.put(BingoConfig.Panel.CREATE, createBingoPanel);
		panelMap.put(BingoConfig.Panel.MODIFY, modifyBingoPanel);

		bingoScapePluginPanel.addPanel(mainBingoPanel);
		bingoScapePluginPanel.addPanel(activeBingoPanel);
		bingoScapePluginPanel.addPanel(createBingoPanel);
		bingoScapePluginPanel.addPanel(modifyBingoPanel);

		showPanel(BingoConfig.Panel.MAIN);
	}

	private void showPanel(BingoConfig.Panel panelKey)
	{
		panelMap.values().forEach(panel -> panel.setVisible(false));

		PluginPanel selectedPanel = panelMap.get(panelKey);
		if (selectedPanel != null)
		{
			selectedPanel.setVisible(true);
		}

		bingoScapePluginPanel.repaint();
		bingoScapePluginPanel.revalidate();
	}

	private void setupNavigationButton()
	{
		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/com/bingo/plugin_icon.png");
		if (icon == null)
		{
			return;
		}
		setActiveConfigPanel(BingoConfig.Panel.MAIN);
		navigationButton = NavigationButton.builder()
			.tooltip("BingoScape")
			.icon(icon)
			.priority(100)
			.panel(bingoScapePluginPanel)
			.build();
		clientToolbar.addNavigation(navigationButton);
	}

	public void panelSelector(BingoConfig.Panel p)
	{
		onMainPage = (p == BingoConfig.Panel.MAIN);
		showPanel(p);
		setActiveConfigPanel(p);
		bingoScapePluginPanel.updateHomeButton(onMainPage);
	}

	public void setActiveConfigPanel(BingoConfig.Panel p)
	{
		configManager.setConfiguration("bingo", "activePanel", p);
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
