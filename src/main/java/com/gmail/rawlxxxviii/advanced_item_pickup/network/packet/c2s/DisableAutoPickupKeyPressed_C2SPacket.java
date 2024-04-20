package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.DisableAutoPickup_S2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisableAutoPickupKeyPressed_C2SPacket {

    public DisableAutoPickupKeyPressed_C2SPacket() {
    }

    public DisableAutoPickupKeyPressed_C2SPacket(FriendlyByteBuf buffer) {
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{
            var player = context.getSender();
            if(player == null){
                return;
            }
            var level = player.getLevel();
            if(level.isClientSide){
                return;
            }

            player.sendSystemMessage(Component.translatable("Auto pickup: Off" ).withStyle(ChatFormatting.DARK_AQUA));

            PacketHandler.sendToPlayer(
                    new DisableAutoPickup_S2CPacket(),
                    player
            );

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.setAutoPickupEnabled(false);
            });
        });
    }
}
