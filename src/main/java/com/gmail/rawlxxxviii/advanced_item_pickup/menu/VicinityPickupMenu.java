package com.gmail.rawlxxxviii.advanced_item_pickup.menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.VicinityContainer;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.VicinitySlot;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VicinityPickupMenu extends AbstractContainerMenu implements ContainerListener {

    private static final int VICINITY_SLOT_ROW_COUNT = 6;
    private static final int VICINITY_TOP = 20;
    private static final int INVENTORY_TOP = 146;

    public static final int COLUMN_COUNT = 9;

    private final VicinityContainer vicinityContainer;
    private final Inventory inventory;

    private final List<Slot> vicinitySlots = new ArrayList<>();

    private int scrollRowPos = 0;


    public VicinityPickupMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory);
    }

    public VicinityPickupMenu(int id, Inventory inventory) {
        super(ModMenuTypes.VICINITY_PICKUP_MENU.get(), id);


        this.inventory = inventory;

        vicinityContainer = new VicinityContainer(this, inventory.player);

        addSlots();


    }



    public int getScrollRowPos() {
        return scrollRowPos;
    }

    public void setScrollRowPos(int scrollRowPos) {
        this.scrollRowPos = scrollRowPos;
//        this.broadcastFullState();
    }

    private void addSlots() {

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot( inventory, i, 8 + i * 18, INVENTORY_TOP + 54 + 4));
        }

        for (int row = 0; row < 3; ++row) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(
                        new Slot(
                                inventory,
                                i + row * 9 + 9,
                                8 + i * 18,
                                INVENTORY_TOP + row * 18
                        )
                );
            }
        }

        for (int row = 0; row < VICINITY_SLOT_ROW_COUNT; ++row) {
            for (int i = 0; i < COLUMN_COUNT; ++i) {
                var slot =
                        new VicinitySlot(
                                vicinityContainer,
                                i + row * COLUMN_COUNT+36,
                                8 + i * 18,
                                VICINITY_TOP + row * 18
                        );
                this.vicinitySlots.add(slot);
                this.addSlot(slot);
            }
        }
    }



    @Override
    public void containerChanged(Container p_18983_) {
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {

        if(slotIndex >= slots.size() ){
            return ItemStack.EMPTY;
        }
        Slot slot = this.slots.get(slotIndex);
        if(!slot.hasItem()){
            return ItemStack.EMPTY;
        }
        ItemStack itemstack = slot.getItem();
        ItemStack itemstackCopy = itemstack.copy();

        if(slot.container instanceof VicinityContainer){
            if (!this.moveItemStackTo(itemstack, 0, 4*9, false)) {
                return ItemStack.EMPTY;
            }
        }else{
            player.drop(slot.getItem().copy(),true);
            slot.getItem().setCount(0);
            slot.setChanged();
            return ItemStack.EMPTY;
        }




        if (itemstack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }


        return itemstackCopy;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    public void onEntitiesUpdated(){
        this.vicinityContainer.onUpdate();
    }

}
