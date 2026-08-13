package com.bankeditemusage.classes;

import com.bankeditemusage.BankedItemUsageConfig;
import com.bankeditemusage.BankedItemUsagePlugin;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.*;

public class BankedItemOverlay extends WidgetItemOverlay {
    private final BankedItemUsageConfig config;

    @Inject
    BankedItemOverlay(BankedItemUsageConfig config) {
        this.config = config;

        showOnBank();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem) {
        BankedItem item = BankedItemUsageConfig.itemList.get(itemId);
        if (item == null) return;

        switch(item.getItemAge(config)) {
            case RECENT:
                graphics.setColor(config.recentColor());
                break;
            case UNCOMMON:
                graphics.setColor(config.uncommonColor());
                break;
            default:
                graphics.setColor(config.oldColor());
        }

        var bounds = widgetItem.getCanvasBounds();
        graphics.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
