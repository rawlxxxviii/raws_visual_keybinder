package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupCapability;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleAutoPickupKeyPressed_P2SPacket {

    public ToggleAutoPickupKeyPressed_P2SPacket() {
    }

    public ToggleAutoPickupKeyPressed_P2SPacket(FriendlyByteBuf buffer) {
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{

            var player = context.getSender();
            if(player == null)
                return;

            player.getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(c->{
                c.setAutoPickupEnabled(!c.isAutoPickupEnabled());
                player.sendSystemMessage(Component.translatable("Toggled " + String.valueOf(c.isAutoPickupEnabled())).withStyle(ChatFormatting.DARK_AQUA));

            });

        });
    }
}
