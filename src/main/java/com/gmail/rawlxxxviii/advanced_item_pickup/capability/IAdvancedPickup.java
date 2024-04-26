package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
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
    void updateNameFilter(String name, ItemFilterType itemFilterType);
    void removeNameFilter(String name);
    void addPickupTick();
    void subtractPickupTick();

    ItemFilterType getFilterResult(ItemStack itemStack);
    void copyFrom(IAdvancedPickup source);

//    boolean isSettingsScreenUpToDate();
//    void setSettingsScreenUpToDate(boolean isUpdated);

}
