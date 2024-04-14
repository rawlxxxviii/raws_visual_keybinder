package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import net.minecraft.nbt.CompoundTag;

public class AdvancedPickupCapability {

    private static final int MAX_TICK_COUNT = 6;

    private boolean autoPickupEnabled = false;
    private int pickupTicks = 0;

    public boolean isPickingUpItems() {
        return pickupTicks > 0;
    }

    public int getPickupTicks() {
        return pickupTicks;
    }

    public void addPickupTick() {
        this.pickupTicks = Math.min(this.pickupTicks + 2, MAX_TICK_COUNT);
    }
    public void subtractPickupTick() {
        this.pickupTicks = Math.max(this.pickupTicks - 1, 0);
    }

    public boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }

    public void setAutoPickupEnabled(boolean autoPickupEnabled) {
        this.autoPickupEnabled = autoPickupEnabled;
    }

    public void copyFrom(AdvancedPickupCapability source) {
        this.autoPickupEnabled = source.autoPickupEnabled;
        this.pickupTicks = source.pickupTicks;
    }

    public  void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("autoPickupEnabled", autoPickupEnabled);
        nbt.putInt("pickupTicks", pickupTicks);
    }

    public  void loadNBTData(CompoundTag nbt){
        this.autoPickupEnabled = nbt.getBoolean("autoPickupEnabled");
        this.pickupTicks = nbt.getInt("pickupTicks");
    }

}
