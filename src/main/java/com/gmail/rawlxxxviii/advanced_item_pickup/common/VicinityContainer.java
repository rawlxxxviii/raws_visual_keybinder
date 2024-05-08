package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import com.gmail.rawlxxxviii.advanced_item_pickup.menu.VicinityPickupMenu;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.MathUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class VicinityContainer implements Container {

    private List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
    private VicinityPickupMenu parentMenu;
    private Player player;

    private static final double REACH = 2.3f;

    public VicinityContainer(VicinityPickupMenu parentMenu,Player player) {
        this.parentMenu = parentMenu;
        this.player = player;

        this.setItemEntities();
    }


    public void setItemEntities(){

        var verticalOffset = .5;
        var verticalAddedReach = .5;

        var aabb = new AABB(
                player.getX() - REACH,
                player.getY() - REACH + verticalOffset,
                player.getZ() - REACH,
                player.getX() + REACH,
                player.getY() + REACH + verticalOffset + verticalAddedReach,
                player.getZ() + REACH
        );

        this.itemEntities = player.level.getEntitiesOfClass(
                ItemEntity.class,
                aabb
        );

        this.filterItemsByDistance();
        this.sortEntities();

    }

    public void filterItemsByDistance(){

        this.itemEntities.removeIf(x->
                        MathUtils.getDistance(player.getX(),x.getX(),player.getZ(),x.getZ())
                                > REACH
            );
    }
    
    public void sortEntities(){
        this.itemEntities
            .sort(Comparator.comparing(a -> a.getUUID().toString()));
    }


    public void onUpdate(){
        this.setItemEntities();
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

    private int parentIndexCorrection(){
        return parentMenu.getScrollRowPos() * VicinityPickupMenu.COLUMN_COUNT - 36;
    }

    @Override
    public ItemStack getItem(int index) {

        index += parentIndexCorrection();

        if(index >= itemEntities.size()){
            return ItemStack.EMPTY;
        }

        var itemEntity = itemEntities.get(index);
        return itemEntity == null ? ItemStack.EMPTY : itemEntity.getItem();

    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        index += parentIndexCorrection();

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
        return ItemStack.EMPTY;
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
