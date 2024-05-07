package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import com.gmail.rawlxxxviii.advanced_item_pickup.menu.VicinityPickupMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class VicinityContainer implements Container {

    private List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
    private VicinityPickupMenu parentMenu;

    public VicinityContainer(VicinityPickupMenu parentMenu) {
        this.parentMenu = parentMenu;
    }


    public void setItemEntities(List<ItemEntity> itemEntities){
        this.itemEntities = itemEntities;
    }

    public List<ItemEntity> getItemEntities(){
        return itemEntities;
    }

    @Override
    public int getContainerSize() {
        return itemEntities.size();
    }


    @Override
    public boolean isEmpty() {

        for(var x : this.itemEntities) {
            if (!x.getItem().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {

        index += parentMenu.getScrollRowPos() * VicinityPickupMenu.COLUMN_COUNT;

        if(index >= itemEntities.size()){
            return ItemStack.EMPTY;
        }

        var itemEntity = itemEntities.get(index);
        return itemEntity == null ? ItemStack.EMPTY : itemEntity.getItem();

    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        index += parentMenu.getScrollRowPos() * VicinityPickupMenu.COLUMN_COUNT;

        if(index >= itemEntities.size()){
            return ItemStack.EMPTY;
        }
        var itemEntity = itemEntities.get(index);
        if(amount == 0 || itemEntity == null || itemEntity.getItem().isEmpty()){
            return ItemStack.EMPTY;
        }

        var result = itemEntity.getItem().split(amount);

        if(!result.isEmpty()){
            this.setChanged();
        }

        return  result;
    }

    @Override
    public void clearContent() {
        itemEntities.clear();
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return new ItemStack( ForgeRegistries.ITEMS.getValue( new ResourceLocation("minecraft:dirt") ),10);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {

    }

    @Override
    public int getMaxStackSize() {
        return Container.super.getMaxStackSize();
    }

    @Override
    public void setChanged() {
        this.parentMenu.containerChanged(this);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    @Override
    public void startOpen(Player p_18955_) {
        Container.super.startOpen(p_18955_);
    }

    @Override
    public void stopOpen(Player p_18954_) {
        Container.super.stopOpen(p_18954_);
    }

    @Override
    public boolean canPlaceItem(int p_18952_, ItemStack p_18953_) {
        return  false;
    }

    @Override
    public int countItem(Item p_18948_) {
        return Container.super.countItem(p_18948_);
    }

    @Override
    public boolean hasAnyOf(Set<Item> p_18950_) {
        return Container.super.hasAnyOf(p_18950_);
    }

    @Override
    public boolean hasAnyMatching(Predicate<ItemStack> p_216875_) {
        return Container.super.hasAnyMatching(p_216875_);
    }

}
