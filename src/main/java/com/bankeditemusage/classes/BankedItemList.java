package com.bankeditemusage.classes;

import java.util.*;

public class BankedItemList extends Vector<BankedItem> {
    @Override
    public BankedItem get(int id) {
        for (BankedItem item : this) {
            if (item.id == id) return item;
        }
        return null;
    }
}
