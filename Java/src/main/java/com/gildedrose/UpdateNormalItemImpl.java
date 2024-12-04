package com.gildedrose;

import static java.lang.Math.max;

public class UpdateNormalItemImpl implements UpdateItemStrategy {

    final static int MAX_QUALITY = 50;
    final static int MIN_QUALITY = 0;

    @Override
    public boolean isResponsibleForItem(String itemName) {
        return true;
    }

    @Override
    public void updateItemAtEndOfDay(Item item) {
        item.quality = max(item.quality - decreasedQualityDelta(item.sellIn), 0);
        item.sellIn--;
    }

    private static int decreasedQualityDelta(int sellIn) {
        if (sellIn < 0) {
           return 2;
        }

        return 1;
    }
}
