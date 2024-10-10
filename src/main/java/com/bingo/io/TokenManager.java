package com.bingo.io;

import com.google.gson.Gson;
import javax.inject.Inject;
import net.runelite.client.config.ConfigManager;

public class TokenManager
{
	@Inject
	private final ConfigManager configManager;

	private final Gson gson = new Gson();

	@Inject
	public TokenManager(ConfigManager configManager)
	{
		this.configManager = configManager;
	}

	public void setActiveToken(Token t)
	{
		String tokenJson = gson.toJson(t);
		System.out.println("WE DID IT!!");
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

	public boolean isValidToken(Token t)
	{
		return t != null && t.getId() != 0;
	}

	public void destroyToken()
	{
		if (getActiveToken().getId() != 0 || getActiveToken() != null)
		{
			Token blankToken = new Token(0);
			String blankTokenJson = gson.toJson(blankToken);
			configManager.setConfiguration("bingo", "activeToken", blankTokenJson);
		}
	}
}
