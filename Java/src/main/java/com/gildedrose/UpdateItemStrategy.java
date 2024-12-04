package com.gildedrose;

public interface UpdateItemStrategy {

    boolean isResponsibleForItem(String itemName);

    void updateItemAtEndOfDay(Item item);
}
