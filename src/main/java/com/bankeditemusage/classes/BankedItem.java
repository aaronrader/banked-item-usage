package com.bankeditemusage.classes;

import com.bankeditemusage.BankedItemUsageConfig;
import net.runelite.api.Item;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class BankedItem {
    public static enum itemAgeEnum { OLD, UNCOMMON, RECENT };

    public final int id;
    public int quantity;
    public LocalDate timeUpdated;

    public BankedItem(Item item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.timeUpdated = LocalDate.EPOCH;
    }

    public itemAgeEnum getItemAge(BankedItemUsageConfig config) {
        if (this.timeUpdated.isBefore(LocalDate.now().minusDays(config.uncommon()))) {
            return itemAgeEnum.OLD;
        }
        else if (this.timeUpdated.isBefore(LocalDate.now().minusDays(config.recent()))) {
            return itemAgeEnum.UNCOMMON;
        }
        else {
            return itemAgeEnum.RECENT;
        }
    }
}
