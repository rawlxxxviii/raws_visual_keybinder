package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AdvancedPickupSettingsMenu extends AbstractContainerMenu {


    public AdvancedPickupSettingsMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory);
    }

    public AdvancedPickupSettingsMenu(int id, Inventory inventory) {
        super(ModMenuTypes.ADVANCED_PICKUP_SETTINGS_MENU.get(), id);

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + row * 9 + 9, 8 + i * 18, 155 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 213));
        }
    }
}
