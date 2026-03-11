package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void item_Constructor() {
        Item item = new Item("name",0,0);
        assertEquals("name", item.name);
        assertEquals(0, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void item_ToString() {
        Item item = new Item("foo", 10, 20);
        assertEquals("foo, 10, 20", item.toString());    }
}
