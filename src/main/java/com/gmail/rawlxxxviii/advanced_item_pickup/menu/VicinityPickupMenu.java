package com.gmail.rawlxxxviii.advanced_item_pickup.menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.VicinityContainer;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class VicinityPickupMenu extends AbstractContainerMenu {

    private final VicinityContainer vicinityContainer = new VicinityContainer();


    public VicinityPickupMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory);
    }

    public VicinityPickupMenu(int id, Inventory inventory) {
        super(ModMenuTypes.VICINITY_PICKUP_MENU.get(), id);


        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        vicinityContainer.setContainerSize(3);
        var containerSize = vicinityContainer.getContainerSize();
        for (int i = 0; i < containerSize; i++) {

            addSlot(new Slot(vicinityContainer,i,10 + 10*i,20));
        }

        getItemEntities(inventory.player);
    }

    private void getItemEntities(Player player){


        System.out.println(
                player.level.getEntitiesOfClass(
                        ItemEntity.class,
                        new AABB(player.blockPosition())
                )
        );
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
                this.addSlot(
                        new Slot(
                                playerInventory,
                                i + row * 9 + 9,
                                8 + i * 18,
                                166 +25 + row * 18
                        )
                );
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 224 +25));
        }
    }
}
