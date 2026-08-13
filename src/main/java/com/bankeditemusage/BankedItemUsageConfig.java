package com.bankeditemusage;

import com.bankeditemusage.classes.BankedItemList;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("bankeditemusage")
public interface BankedItemUsageConfig extends Config
{
    @ConfigItem(
            keyName = "recent",
            name = "Recent Items",
            description = "The number of days since last use that an item is deemed \"recent\".",
            position = 1
    )
    default int recent() {
        return 30;
    }
    @ConfigItem(
            keyName = "recentColor",
            name = "Recent Items Color",
            description = "The color border for recent items.",
            position = 2
    )
    default Color recentColor() {
        return new Color(0, 255, 0);
    }

    @ConfigItem(
            keyName = "uncommon",
            name = "Uncommon Items",
            description = "The number of days since last use that an item is deemed \"uncommon\".",
            position = 3
    )
    default int uncommon() {
        return 180;
    }
    @ConfigItem(
            keyName = "uncommonColor",
            name = "Uncommon Items Color",
            description = "The color border for uncommonly used items.",
            position = 4
    )
    default Color uncommonColor() {
        return new Color(255, 255, 0);
    }

    @ConfigItem(
            keyName = "oldColor",
            name = "Old Items Color",
            description = "The color border for old items.",
            position = 5
    )
    default Color oldColor() {
        return new Color(255, 0, 0);
    }

    BankedItemList itemList = new BankedItemList();
}
