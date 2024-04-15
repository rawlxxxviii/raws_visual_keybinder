package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemnNameFilter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class AdvancedPickupCapability {

    private static final int MAX_TICK_COUNT = 6;

    private boolean autoPickupEnabled = false;
    public boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }
    public void setAutoPickupEnabled(boolean autoPickupEnabled) {
        this.autoPickupEnabled = autoPickupEnabled;
    }

    private int pickupTicks = 0;
    public boolean isPickingUpItems() {
        return pickupTicks > 0;
    }
    public int getPickupTicks() {
        return pickupTicks;
    }

    private ArrayList<ItemnNameFilter> autoPickupNameFilters;
    public ArrayList<ItemnNameFilter> getAutoPickupFilters() {
        return autoPickupNameFilters;
    }



    public void addPickupTick() {
        this.pickupTicks = Math.min(this.pickupTicks + 2, MAX_TICK_COUNT);
    }
    public void subtractPickupTick() {
        this.pickupTicks = Math.max(this.pickupTicks - 1, 0);
    }



    public AdvancedPickupCapability() {
        autoPickupNameFilters = new ArrayList<ItemnNameFilter>();
        autoPickupNameFilters.add(new ItemnNameFilter("block.minecraft.dirt", ItemFilterType.NEVER));
        autoPickupNameFilters.add(new ItemnNameFilter("item.minecraft.wheat_seeds", ItemFilterType.ALLWAYS));
    }



    public @Nullable ItemFilterType getFilterResult(ItemStack itemStack){

        var itemnNameFilter = autoPickupNameFilters.stream().filter(x-> x.isMatch(itemStack)).findFirst();
        return itemnNameFilter.map(ItemnNameFilter::getType).orElse(null);

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
