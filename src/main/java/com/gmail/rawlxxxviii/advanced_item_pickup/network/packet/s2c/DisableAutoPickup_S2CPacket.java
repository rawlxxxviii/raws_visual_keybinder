package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.AdvancedPickup;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisableAutoPickup_S2CPacket {

    public DisableAutoPickup_S2CPacket() {
    }

    public DisableAutoPickup_S2CPacket(FriendlyByteBuf buffer) {
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{

            var player = Minecraft.getInstance().player;
            if(player == null){
                return;
            }
            var level = player.getLevel();
            if(!level.isClientSide){
                return;
            }
            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.setAutoPickupEnabled(false);
            });
        });
    }
}
