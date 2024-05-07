package com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;

@AutoRegisterCapability
public interface IAdvancedPickup extends INBTSerializable<CompoundTag>, MenuProvider {

    boolean isAutoPickupEnabled();
    void setAutoPickupEnabled(boolean autoPickupEnabled);
    boolean isPickingUpItems();
    int getPickupTicks();
    ArrayList<ItemNameFilter> getAutoPickupFilters();
    void updateNameFilter(ResourceLocation resourceLocation, ItemFilterType itemFilterType);
    void removeNameFilter(ResourceLocation resourceLocation);
    void setPickingUp();
    void subtractPickupTick();
    void clearNameFiltersOfType(ItemFilterType itemFilterType);

    ItemFilterType getFilterResult(Item item);
    void copyFrom(IAdvancedPickup source);

    boolean isSettingsScreenUpToDate();
    void setSettingsScreenUpToDate(boolean isUpdated);

}
