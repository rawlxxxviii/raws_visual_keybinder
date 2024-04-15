package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import net.minecraft.world.item.ItemStack;

public class ItemnNameFilter {

    private String itemName;
    private ItemFilterType type = ItemFilterType.DISABLED;

    public boolean isMatch(ItemStack itemStack){
        return itemName == null || itemStack.getDescriptionId().equals(itemName);
    }

    public ItemnNameFilter(String itemName, ItemFilterType type) {
        this.itemName = itemName;
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemFilterType getType() {
        return type;
    }

    public void setType(ItemFilterType type) {
        this.type = type;
    }
}
