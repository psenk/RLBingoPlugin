package com.bingo;

import com.bingo.io.Token;
import com.bingo.panels.ActiveBingoPanel;
import com.bingo.panels.BingoScapePluginPanel;
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
import com.google.gson.Gson;

// TODO:

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

	@Inject
	private ConfigManager configManager;

	private BingoScapePluginPanel bingoScapePluginPanel;
	private MainBingoPanel mainBingoPanel;
	private ActiveBingoPanel activeBingoPanel;
	private CreateBingoPanel createBingoPanel;
	private ModifyBingoPanel modifyBingoPanel;
	private NavigationButton navigationButton;

	private final Gson gson = new Gson();

	@Override
	protected void startUp() throws Exception
	{
		//log.info("Example started!");
		destroyToken();
		this.bingoScapePluginPanel = new BingoScapePluginPanel(this);
		this.mainBingoPanel = new MainBingoPanel(this);
		this.activeBingoPanel = new ActiveBingoPanel(this);
		this.createBingoPanel = new CreateBingoPanel(this);
		this.modifyBingoPanel = new ModifyBingoPanel(this);

		bingoScapePluginPanel.addPanel(mainBingoPanel);
		bingoScapePluginPanel.addPanel(activeBingoPanel);
		bingoScapePluginPanel.addPanel(createBingoPanel);
		bingoScapePluginPanel.addPanel(modifyBingoPanel);

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
		setActiveConfigPanel(BingoConfig.Panel.MAIN);
		navigationButton = NavigationButton.builder()
			.tooltip("BingoScape")
			.icon(icon)
			.priority(100)
			.panel(bingoScapePluginPanel)
			.build();
		clientToolbar.addNavigation(navigationButton);
		bingoScapePluginPanel.setVisible(true);
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

		bingoScapePluginPanel.revalidate();
		bingoScapePluginPanel.repaint();
	}

	public void panelSelector(BingoConfig.Panel p)
	{
		switch (p)
		{
			case MAIN:
				this.onMainPage = true;
				setActivePanel(mainBingoPanel);
				setActiveConfigPanel(BingoConfig.Panel.MAIN);
				break;
			case ACTIVE:
				this.onMainPage = false;
				setActivePanel(activeBingoPanel);
				setActiveConfigPanel(BingoConfig.Panel.ACTIVE);
				break;
			case CREATE:
				this.onMainPage = false;
				setActivePanel(createBingoPanel);
				setActiveConfigPanel(BingoConfig.Panel.CREATE);
				break;
			case MODIFY:
				this.onMainPage = false;
				setActivePanel(modifyBingoPanel);
				setActiveConfigPanel(BingoConfig.Panel.MODIFY);
				break;
		}
		bingoScapePluginPanel.updateHomeButton(this.onMainPage);
	}

	@Override
	protected void shutDown() throws Exception
	{
		//log.info("Example stopped!");
		// TODO: do i need to log out of something here?
		destroyToken();
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

	public void setActiveConfigPanel(BingoConfig.Panel p)
	{
		configManager.setConfiguration("bingo", "activePanel", p);
	}

	public void setActiveToken(Token t)
	{
		String tokenJson = gson.toJson(t);
		configManager.setConfiguration("bingo", "activeToken", tokenJson);
	}

	public Token getActiveToken()
	{
		String configValue = configManager.getConfiguration("bingo", "activeToken");
		if (configValue == null)
		{
			return null;
		}
		return gson.fromJson(configValue, Token.class);
	}

	public void destroyToken()
	{
		if (getActiveToken().getId() != 0)
		{
			Token blankToken = new Token(0);
			String blankTokenJson = gson.toJson(blankToken);
			configManager.setConfiguration("bingo", "activeToken", blankTokenJson);
		}
		System.out.println("TOKEN DESTROYED");
	}
}
