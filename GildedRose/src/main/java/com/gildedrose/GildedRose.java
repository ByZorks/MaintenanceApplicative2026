package com.gildedrose;

class GildedRose {
    private final Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case "Aged Brie" -> updateQualityAgedBrie(item);
                case "Backstage passes to a TAFKAL80ETC concert" -> updateQualityBackstagePasses(item);
                case "Sulfuras, Hand of Ragnaros" -> { /* Ne change rien */ }
                case "Conjured" -> updateQualityConjured(item);
                default -> updateQualityNormal(item);
            }
        }
    }

    private void updateQualityAgedBrie(Item item) {
        if (item.sellIn > 0) {
            increaseQuality(item, 1);
        } else {
            increaseQuality(item, 2);
        }

        item.sellIn--;
    }

    private void updateQualityBackstagePasses(Item item) {
        if (item.sellIn >= 11) {
            increaseQuality(item, 1);
        } else if (item.sellIn >= 6) {
            increaseQuality(item, 2);
        } else if (item.sellIn >= 1) {
            increaseQuality(item, 3);
        } else {
            item.quality = 0;
        }

        item.sellIn--;
    }

    private void updateQualityNormal(Item item) {
        if (item.sellIn <= 0) {
            decreaseQuality(item, 2);
        } else {
            decreaseQuality(item, 1);
        }

        item.sellIn--;
    }

    private void updateQualityConjured(Item item) {
        if (item.sellIn <= 0) {
            decreaseQuality(item, 4);
        } else {
            decreaseQuality(item, 2);
        }

        item.sellIn--;
    }

    private void increaseQuality(Item item, int quality) {
        item.quality = Math.clamp((long) item.quality + quality, 0, 50);
    }

    private void decreaseQuality(Item item, int quality) {
        item.quality = Math.clamp((long) item.quality - quality, 0, 50);
    }

    public Item[] getItems() {
        return items;
    }
}
