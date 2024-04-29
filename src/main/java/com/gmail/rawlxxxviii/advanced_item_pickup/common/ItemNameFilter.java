package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemNameFilter {

    private String itemName;
    private ItemFilterType type;

    public boolean isMatch(ItemStack itemStack){
        return itemName == null || itemStack.getDescriptionId().equals(itemName);
    }

    public ItemNameFilter(String itemName, ItemFilterType type) {
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

    public CompoundTag serializeNBT(){

        var tag = new CompoundTag();
        tag.putString("name", this.getItemName());
        tag.putInt("type", this.getType().ordinal());

        return tag;
    }

    public ItemNameFilter(CompoundTag nbt){
        this.setItemName(nbt.getString("name"));
        this.setType( ItemFilterType.fromInteger(nbt.getInt("type")));
    }

}
