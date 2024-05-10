package com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup;

import com.gmail.rawlxxxviii.vicinity_item_pickup.menu.VicinityPickupMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.TickEvent;

public class VicinityPickupImplementation implements IVicinityPickup, MenuProvider {


    private double reach = 2.3;
    private double verticalOffset = 0.5;
    private double verticalExtraReachTop = 0.5;
    private boolean disableVanillaPickup = false;

    public VicinityPickupImplementation() {
    }


    public void copyFrom(IVicinityPickup source) {
        this.reach = source.getReach();
        this.verticalOffset = source.getVerticalOffset();
        this.verticalExtraReachTop = source.getVerticalExtraReachTop();
        this.disableVanillaPickup = source.getDisableVanillaPickup();
    }

    @Override
    public void setReach(double value) {
        this.reach = value;
    }

    @Override
    public void setVerticalOffset(double value) {
        this.verticalOffset = value;
    }

    @Override
    public void setVerticalExtraReachTop(double value) {
        this.verticalExtraReachTop = value;
    }

    @Override
    public void setDisableVanillaPickup(boolean value) {
        this.disableVanillaPickup = value;
    }


    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putDouble("reach", this.getReach());
        tag.putDouble("verticalOffset", this.getVerticalOffset());
        tag.putDouble("verticalExtraReachTop", this.getVerticalExtraReachTop());
        tag.putBoolean("disableVanillaPickup", this.getDisableVanillaPickup());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.reach = nbt.getDouble("reach");
        this.verticalOffset = nbt.getDouble("verticalOffset");
        this.verticalExtraReachTop = nbt.getDouble("verticalExtraReachTop");
        this.disableVanillaPickup = nbt.getBoolean("disableVanillaPickup");
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
    public double getReach() {
        return reach;
    }

    @Override
    public double getVerticalOffset() {
        return verticalOffset;
    }

    @Override
    public double getVerticalExtraReachTop() {
        return verticalExtraReachTop;
    }

    @Override
    public boolean getDisableVanillaPickup() {
        return disableVanillaPickup;
    }
}
