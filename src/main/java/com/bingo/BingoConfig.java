package com.bingo;

import com.bingo.io.Token;
import java.awt.Color;
import java.awt.Dimension;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

// https://github.com/KATalyzt36/TutorialRunelitePlugins
// https://github.com/runelite/runelite/wiki/Creating-plugin-config-panels

@ConfigGroup("bingo")
public interface BingoConfig extends Config
{
	// TODO: move this enum somewhere else
	enum Panel
	{
		MAIN("", "BingoScape home screen."),
		ACTIVE("View Active Bingo", "View the status of an active bingo event."),
		CREATE("Create New Bingo", "Create a new bingo event."),
		MODIFY("Modify Existing Bingo", "Modify an existing bingo event.");

		public final String title;
		public final String tooltip;

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
		keyName = "checkbox",
		name = "This is a checkbox",
		description = "Default checkbox text"
	)
	default boolean checkbox()
	{
		return false;
	}

	@ConfigItem(
		keyName = "spinner",
		name = "This is a spinner",
		description = "Default spinner text"
	)
	default int spinner()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "spinnerx2",
		name = "This is two spinners",
		description = "Default spinner text"
	)
	default Dimension spinners()
	{
		return new Dimension(0, 0);
	}

	@ConfigItem(
		keyName = "colorPicker",
		name = "Color Picker",
		description = "Default color picker text"
	)
	default Color colorPicker()
	{
		return Color.BLUE;
	}

	@ConfigItem(
		keyName = "combobox",
		name = "Combo Box",
		description = "Default combo box text"
	)
	default Panel combobox()
	{
		return Panel.MAIN;
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
		keyName = "activeToken",
		name = "Active Token",
		description = "Active session token.",
		hidden = true
	)
	default Token activeToken()
	{
		return new Token(0);
	}
}
