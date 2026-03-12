package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @ParameterizedTest(name = "Un objet normal avec sellIn={0} et quality={1} devrait avoir sellIn={2} et quality={3}")
    @CsvSource({
            "10, 20,  9, 19",  // Avant la date de péremption : -1 SellIn, -1 Quality
            " 0, 20, -1, 18",  // Le jour de la date de péremption : baisse 2x plus vite (-2 Quality)
            "-1, 20, -2, 18",  // Après la date de péremption : baisse 2x plus vite (-2 Quality)
            "10,  0,  9,  0",  // La qualité ne peut jamais être négative (avant péremption)
            "-1,  0, -2,  0"   // La qualité ne peut jamais être négative (après péremption)
    })
    void normalItem_UpdateQuality(int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[]{new Item("foo", initialSellIn, initialQuality)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedSellIn, app.getItems()[0].sellIn, "Le sellIn calculé est incorrect");
        assertEquals(expectedQuality, app.getItems()[0].quality, "La qualité calculée est incorrecte");
    }

    @ParameterizedTest(name = "Un objet conjured avec sellIn={0} et quality={1} devrait avoir sellIn={2} et quality={3}")
    @CsvSource({
            "10, 20,  9, 18",  // Avant la date de péremption : -1 SellIn, -2 Quality
            " 0, 20, -1, 16",  // Le jour de la date de péremption : baisse 2x plus vite (-4 Quality)
            "-1, 20, -2, 16",  // Après la date de péremption : baisse 2x plus vite (-4 Quality)
            "10,  0,  9,  0",  // La qualité ne peut jamais être négative (avant péremption)
            "-1,  0, -2,  0"   // La qualité ne peut jamais être négative (après péremption)
    })
    void conjuredItem_UpdateQuality(int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[]{new Item("Conjured", initialSellIn, initialQuality)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedSellIn, app.getItems()[0].sellIn, "Le sellIn calculé est incorrect");
        assertEquals(expectedQuality, app.getItems()[0].quality, "La qualité calculée est incorrecte");
    }

    @ParameterizedTest(name = "Aged Brie avec sellIn={0} et quality={1} devrait avoir sellIn={2} et quality={3}")
    @CsvSource({
            "10, 20,  9, 21", // Avant péremption : la qualité augmente de 1
            " 0, 20, -1, 22", // Le jour de la péremption : la qualité augmente de 2
            "-1, 20, -2, 22", // Après péremption : la qualité augmente de 2
            "10, 50,  9, 50", // La qualité plafonne à 50 (avant péremption)
            "-1, 49, -2, 50"  // La qualité plafonne à 50 (après péremption, devrait faire +2)
    })
    void agedBrie_UpdateQuality(int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[] { new Item("Aged Brie", initialSellIn, initialQuality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedSellIn, app.getItems()[0].sellIn, "Le sellIn du Aged Brie est incorrect");
        assertEquals(expectedQuality, app.getItems()[0].quality, "La qualité du Aged Brie est incorrecte");
    }

    @ParameterizedTest(name = "Sulfuras avec sellIn={0} et quality={1} devrait rester à sellIn={2} et quality={3}")
    @CsvSource({
            "10, 80, 10, 80", // SellIn positif
            " 0, 80,  0, 80", // SellIn à zéro
            "-1, 80, -1, 80"  // SellIn négatif
    })
    void sulfuras_UpdateQuality(int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", initialSellIn, initialQuality) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedSellIn, app.getItems()[0].sellIn, "Le sellIn de Sulfuras ne doit jamais changer");
        assertEquals(expectedQuality, app.getItems()[0].quality, "La qualité de Sulfuras doit toujours être de 80");
    }

    @ParameterizedTest(name = "Backstage pass avec sellIn={0} et quality={1} devrait avoir sellIn={2} et quality={3}")
    @CsvSource({
            "15, 20, 14, 21", // Plus de 10 jours : la qualité augmente de 1
            "11, 20, 10, 21", // Limite à 11 jours : la qualité augmente de 1
            "10, 20,  9, 22", // 10 jours ou moins : la qualité augmente de 2
            " 6, 20,  5, 22", // Limite à 6 jours : la qualité augmente de 2
            " 5, 20,  4, 23", // 5 jours ou moins : la qualité augmente de 3
            " 1, 20,  0, 23", // Limite à 1 jour : la qualité augmente de 3
            " 0, 20, -1,  0", // Après le concert (0 jour) : la qualité tombe à 0
            "10, 49,  9, 50", // La qualité plafonne à 50 (devrait faire +2)
            " 5, 48,  4, 50"  // La qualité plafonne à 50 (devrait faire +3)
    })
    void backstagePasses_UpdateQuality(int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", initialSellIn, initialQuality)};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(expectedSellIn, app.getItems()[0].sellIn, "Le sellIn calculé est incorrect");
        assertEquals(expectedQuality, app.getItems()[0].quality, "La qualité calculée est incorrecte");
    }

    @Test
    void emptyArray_DoesNothing() {
        Item[] items = new Item[]{};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, app.getItems().length);
    }
}