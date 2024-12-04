package com.gildedrose;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    final static int MAX_QUALITY = 50;
    final static int MIN_QUALITY = 0;

    @ParameterizedTest
    @MethodSource("normalItems")
    void updateQuality_when_updateQualityOfNormalItems_then_decreaseQuality(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality - 1, newItems[0].quality);
    }

    @Test
    void updateQuality_when_updateQualityOfNormalItemsWithMinQuality_then_stayAtMinQuality() {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = new Item("+5 Dexterity Vest", 10, MIN_QUALITY);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(MIN_QUALITY, newItems[0].quality);
    }

    @ParameterizedTest
    @MethodSource("wellAgedItems")
    void updateQuality_when_updateQualityOfWellAgedItems_then_increaseQuality(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality + 1, newItems[0].quality);
    }

    @Test
    void updateQuality_when_updateQualityOfWellAgedItemsWithMaximumQuality_then_stayAtMaximux() {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = new Item("Aged Brie", 2, MAX_QUALITY);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(MAX_QUALITY, newItems[0].quality);
    }

    @Test
    void updateQuality_when_updateQualityOfBackstagePassSellInGreater10_then_IncreaseQualityby1() {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality + 1, newItems[0].quality);
    }

    @Test
    void updateQuality_when_updateQualityOfBackstagePassSellInBetween10And5_then_IncreaseQualityby2() {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality + 2, newItems[0].quality);
    }

    @Test
    void updateQuality_when_updateQualityOfBackstagePassSellInBetween5And0_then_IncreaseQualityby3() {
        // arrange
        Item[] originalItems = new Item[2];
        originalItems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        originalItems[1] = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        for (int i = 0; i < originalItems.length; i++) {
            assertEquals(originalItems[i].quality + 3, newItems[i].quality);
        }
    }

    @Test
    void updateQuality_when_updateQualityOfBackstagePassSellIn0_then_QualityEquals0() {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(0, newItems[0].quality);
    }

    @ParameterizedTest
    @MethodSource("sulfuras")
    void updateQuality_when_updateQualityOfSulfuras_then_nothingChanges(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality, newItems[0].quality);
    }

    @ParameterizedTest
    @MethodSource("spoiledNormalItems")
    void updateQuality_when_SellInIsLess0_then_qualityDecreasesDoubles(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality - 2, newItems[0].quality);
    }

    @ParameterizedTest
    @MethodSource("spoiledWellAgedItems")
    void updateQuality_when_SellInIsLess0OnWellAgedItems_then_qualityIncreaseDoubles(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].quality + 2, newItems[0].quality);
    }

    @ParameterizedTest
    @MethodSource("allItemsExceptSulfuras")
    void updateQuality_when_updateQualities_then_SellInReducedBy1(Item originalItem) {
        // arrange
        Item[] originalItems = new Item[1];
        originalItems[0] = originalItem;

        Item[] newItems = deepCloneItems(originalItems);

        GildedRose testee = new GildedRose(newItems);

        // act
        testee.updateQuality();

        // assert
        assertEquals(originalItems[0].sellIn - 1, newItems[0].sellIn);
    }

    public static Item[] deepCloneItems(Item[] original) {
        Item[] newItems = new Item[original.length];

        for (int i = 0; i < original.length; i++) {
            Item oi = original[i];
            newItems[i] = new Item(oi.name, oi.sellIn, oi.quality);
        }

        return newItems;
    }

    public static Item[] normalItems() {
        return new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Elixir of the Mongoose", 5, 7), //
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};
    }

    public static Item[] spoiledNormalItems() {
        Item[] items = normalItems();

        for (Item item :  items) {
            item.sellIn = -1;
        }

        return items;
    }

    public static Item[] spoiledWellAgedItems() {
        Item[] items = wellAgedItems();

        for (Item item :  items) {
            item.sellIn = -1;
        }

        return items;
    }

    public static Item[] wellAgedItems() {
        return new Item[]{
            new Item("Aged Brie", 2, 0),
        };
    }

    public static Item[] backstagePasses() {
        return new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 40)
        };
    }

    public static Item[] allItemsExceptSulfuras() {
        List<Item[]> itemListArray = Arrays.asList(
            normalItems(),
            wellAgedItems(),
            backstagePasses(),
            spoiledNormalItems(),
            spoiledWellAgedItems()
        );

        // List<Item[]>
        return itemListArray
            // -> Stream<List<Item[]>
            .stream()
            // -> Stream<List<Item>>
            .map(Arrays::asList)
            // -> Stream<Item>
            .flatMap(Collection::stream)
            // -> Item[]
            .toArray(Item[]::new);
    }

    public static Item[] sulfuras() {
        return new Item[]{
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
        };
    }

}
