package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupCapability;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PickUpAllKeyDown_P2SPacket {

    public PickUpAllKeyDown_P2SPacket() {
    }

    public PickUpAllKeyDown_P2SPacket(FriendlyByteBuf buffer) {
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{

            var player = context.getSender();
            if(player == null)
                return;

            player.sendSystemMessage(Component.translatable("Picking up all").withStyle(ChatFormatting.DARK_AQUA));

            player.getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(AdvancedPickupCapability::addPickupTick);
            player.getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(c->{
                player.sendSystemMessage(Component.translatable(String.valueOf(c.getPickupTicks())).withStyle(ChatFormatting.DARK_AQUA));

            });

        });
    }
}
