package com.gmail.rawlxxxviii.advanced_item_pickup.capability.vicinity_pickup;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.IAdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

@AutoRegisterCapability
public interface IVicinityPickup extends INBTSerializable<CompoundTag>, MenuProvider {

    float getReach();
    void copyFrom(IVicinityPickup source);

}
