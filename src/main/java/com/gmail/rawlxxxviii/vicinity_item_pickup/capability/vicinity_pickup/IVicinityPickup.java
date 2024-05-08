package com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.TickEvent;

@AutoRegisterCapability
public interface IVicinityPickup extends INBTSerializable<CompoundTag>, MenuProvider {

    float getReach();
    void copyFrom(IVicinityPickup source);
    void onPlayerTick(TickEvent.PlayerTickEvent event);

}
