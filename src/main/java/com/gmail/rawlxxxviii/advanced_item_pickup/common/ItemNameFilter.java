package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import com.gmail.rawlxxxviii.advanced_item_pickup.util.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemNameFilter {

    private ResourceLocation resourceLocation;
    private ItemFilterType type;

    public boolean isMatch(Item item){
        return ItemUtils.getResourceLocation(item).equals(resourceLocation) ;
    }

    public ItemNameFilter(ResourceLocation resourceLocation, ItemFilterType type) {
        this.resourceLocation = resourceLocation;
        this.type = type;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public ItemFilterType getType() {
        return type;
    }

    public void setType(ItemFilterType type) {
        this.type = type;
    }

    public CompoundTag serializeNBT(){

        var tag = new CompoundTag();
        tag.putString("name", this.getResourceLocation().toString());
        tag.putInt("type", this.getType().ordinal());

        return tag;
    }

    public ItemNameFilter(CompoundTag nbt){
        this.setResourceLocation(new ResourceLocation(nbt.getString("name")));
        this.setType( ItemFilterType.fromInteger(nbt.getInt("type")));
    }

}
