package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Predicate;

public class VicinityContainer implements Container {

    public int containerSize = 0;
    
    public VicinityContainer() {
    }

    @Override
    public int getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(int size) {
        containerSize = size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return new ItemStack( ForgeRegistries.ITEMS.getValue( new ResourceLocation("minecraft:dirt") ),10);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return new ItemStack( ForgeRegistries.ITEMS.getValue( new ResourceLocation("minecraft:dirt") ),10);
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
        return Container.super.canPlaceItem(p_18952_, p_18953_);
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

    @Override
    public void clearContent() {

    }
}
