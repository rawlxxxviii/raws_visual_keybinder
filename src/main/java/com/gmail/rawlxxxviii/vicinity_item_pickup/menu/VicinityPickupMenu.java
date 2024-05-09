package com.gmail.rawlxxxviii.vicinity_item_pickup.menu;

import com.gmail.rawlxxxviii.vicinity_item_pickup.settings_menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VicinityPickupMenu extends AbstractContainerMenu {

    private static final int INVENTORY_TOP = 146;

    private final Inventory inventory;

    public VicinityPickupMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory);
    }

    public VicinityPickupMenu(int id, Inventory inventory) {
        super(ModMenuTypes.VICINITY_PICKUP_MENU.get(), id);

        this.inventory = inventory;

        addSlots();

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

        player.drop(slot.getItem().copy(),true);
        slot.getItem().setCount(0);
        slot.setChanged();
        return ItemStack.EMPTY;

    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }


}
