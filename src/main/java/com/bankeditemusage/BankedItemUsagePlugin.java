package com.bankeditemusage;

import com.bankeditemusage.classes.BankedItem;
import com.bankeditemusage.classes.BankedItemList;
import com.bankeditemusage.classes.BankedItemOverlay;
import com.google.inject.Provides;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.swing.text.Highlighter;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.SpriteID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemMapping;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.game.npcoverlay.HighlightedNpc;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;

import static com.bankeditemusage.BankedItemUsageConfig.itemList;

@Slf4j
@PluginDescriptor(
	name = "Banked Item Usage"
)
public class BankedItemUsagePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BankedItemUsageConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private BankedItemOverlay overlay;

	@Provides
	BankedItemUsageConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BankedItemUsageConfig.class);
	}



	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged e) {
		if (e.getContainerId() != InventoryID.BANK) return; //Ignore updates to anything except the bank

		//1. Populate the item list
		populateItemList(e.getItemContainer().getItems());

		//3. Update item sprites
	}

	private void populateItemList(Item[] bankItems) {
		for(var item : itemList) {
			if (Arrays.stream(bankItems).noneMatch(i -> i.getId() == item.id)) {
				item.timeUpdated = LocalDate.now();
				item.quantity = 0;
			}
		}

		for (Item item : bankItems) { //NOTE: if new quantity is 0, the item is omitted from this list
			BankedItem bankedItem = itemList.get(item.getId());

			//Add new item
			if (bankedItem == null) {
				itemList.add(new BankedItem(item));
				continue;
			}

			//Update changed item
			if (bankedItem.quantity != item.getQuantity()) {
				bankedItem.timeUpdated = LocalDate.now();
				bankedItem.quantity = item.getQuantity();

				log.debug("Updated item {} - {}", bankedItem.id, bankedItem.quantity);
			}
		}
	}
}