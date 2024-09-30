package com.bingo;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bingo")
public interface BingoConfig extends Config {
    @ConfigItem(
            keyName = "box",
            name = "This is a box",
            description = "Default box text"
    )
    default String box() {
        return "Box";
    }
}
