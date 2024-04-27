package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.AdvancedPickupSettingsMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class AdvancedPickupImplementation implements IAdvancedPickup, MenuProvider {

    private static final int MAX_TICK_COUNT = 6;

    private boolean settingsScreenUpToDate = false;

    private boolean autoPickupEnabled = false;
    private int pickupTicks = 0;
    private ArrayList<ItemNameFilter> autoPickupNameFilters = new ArrayList<ItemNameFilter>();

    public boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }
    public void setAutoPickupEnabled(boolean autoPickupEnabled) {
        this.autoPickupEnabled = autoPickupEnabled;
    }

    public boolean isPickingUpItems() {
        return pickupTicks > 0;
    }
    public int getPickupTicks() {
        return pickupTicks;
    }

    public ArrayList<ItemNameFilter> getAutoPickupFilters() {
        return autoPickupNameFilters;
    }

    public void updateNameFilter(String name, ItemFilterType itemFilterType){
        var itemNameFilter = autoPickupNameFilters.stream().filter(x-> x.getItemName().equals(name)).findFirst();
        if(itemNameFilter.isEmpty()){
           autoPickupNameFilters.add(new ItemNameFilter(name,itemFilterType));
        }else{
            itemNameFilter.get().setType(itemFilterType);
        }
    }
    public void removeNameFilter(String name){
        autoPickupNameFilters.removeIf(x-> x.getItemName().equals(name));
    }


    public void addPickupTick() {
        this.pickupTicks = Math.min(this.pickupTicks + 2, MAX_TICK_COUNT);
    }
    public void subtractPickupTick() {
        this.pickupTicks = Math.max(this.pickupTicks - 1, 0);
    }



    public AdvancedPickupImplementation() {
        autoPickupNameFilters = new ArrayList<ItemNameFilter>();
        autoPickupNameFilters.add(new ItemNameFilter("block.minecraft.dirt", ItemFilterType.NEVER));
        autoPickupNameFilters.add(new ItemNameFilter("item.minecraft.wheat_seeds", ItemFilterType.ALWAYS));
    }



    public @Nullable ItemFilterType getFilterResult(ItemStack itemStack){

        var itemNameFilter = autoPickupNameFilters.stream().filter(x-> x.isMatch(itemStack)).findFirst();
        return itemNameFilter.map(ItemNameFilter::getType).orElse(null);

    }



    public void copyFrom(IAdvancedPickup source) {
        this.autoPickupEnabled = source.isAutoPickupEnabled();
        this.pickupTicks = source.getPickupTicks();
        this.autoPickupNameFilters = source.getAutoPickupFilters();
    }

    @Override
    public boolean isSettingsScreenUpToDate() {
        return settingsScreenUpToDate;
    }

    @Override
    public void setSettingsScreenUpToDate(boolean isUpdated) {
        settingsScreenUpToDate = isUpdated;
    }

//
//    @Override
//    public boolean isSettingsScreenUpToDate() {
//        return settingsScreenUpToDate;
//    }
//
//    @Override
//    public void setSettingsScreenUpToDate(boolean isUpdated) {
//        settingsScreenUpToDate = isUpdated;
//    }


    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putBoolean("autoPickupEnabled", autoPickupEnabled);
        tag.putInt("pickupTicks", pickupTicks);

        ListTag filterList = new ListTag();
        getAutoPickupFilters().forEach(item->{
            filterList.add(item.serializeNBT());
        });
        tag.put("filterList", filterList);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.autoPickupEnabled = nbt.getBoolean("autoPickupEnabled");
        this.pickupTicks = nbt.getInt("pickupTicks");

        var filterList = (ListTag) nbt.get("filterList");

        this.autoPickupNameFilters =  new ArrayList<ItemNameFilter>();

        if(filterList == null){
            return;
        }

        filterList.forEach( item ->{
            this.autoPickupNameFilters.add( new ItemNameFilter((CompoundTag) item));
        });

    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Advanced pickup");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new AdvancedPickupSettingsMenu(id, inventory);
    }
}
