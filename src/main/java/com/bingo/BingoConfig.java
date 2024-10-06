package com.bingo;

import com.bingo.io.Token;
import lombok.Getter;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bingo")
public interface BingoConfig extends Config
{

	@Getter
	enum Panel
	{
		MAIN("", "BingoScape home screen."),
		ACTIVE("View Active Bingo", "View the status of an active bingo event."),
		CREATE("Create New Bingo", "Create a new bingo event."),
		MODIFY("Modify Existing Bingo", "Modify an existing bingo event.");

		private final String title;
		private final String tooltip;

		Panel(String title, String tooltip)
		{
			this.title = title;
			this.tooltip = tooltip;
		}
	}

	@ConfigItem(
		keyName = "box",
		name = "This is a box",
		description = "Default box text"
	)
	default String box()
	{
		return "Box";
	}

	@ConfigItem(
		keyName = "activePanel",
		name = "Active Panel",
		description = "Current active panel.",
		hidden = true
	)
	default Panel activePanel()
	{
		return Panel.MAIN;
	}

	@ConfigItem(
		keyName = "activePanel",
		name = "",
		description = "",
		hidden = true
	)
	void setActivePanel(Panel p);

	@ConfigItem(
		keyName = "sessionToken",
		name = "Session Token",
		description = "Active session token.",
		hidden = true
	)
	default Token activeToken()
	{
		return null;
	}

	@ConfigItem(
		keyName = "sessionToken",
		name = "",
		description = "",
		hidden = true
	)
	void setActiveToken(Token token);

}
