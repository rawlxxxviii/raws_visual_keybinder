package com.gmail.rawlxxxviii.advanced_item_pickup.capability.vicinity_pickup;

import com.gmail.rawlxxxviii.advanced_item_pickup.menu.VicinityPickupMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class VicinityPickupImplementation implements IVicinityPickup, MenuProvider {


    private float reach = 50F;


    public VicinityPickupImplementation() {
    }


    public void copyFrom(IVicinityPickup source) {
        this.reach = source.getReach();
    }


    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putFloat("reach", this.getReach());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.reach = nbt.getFloat("reach");
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Vicinity pickup");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new VicinityPickupMenu(id, inventory);
    }

    @Override
    public float getReach() {
        return reach;
    }
}
