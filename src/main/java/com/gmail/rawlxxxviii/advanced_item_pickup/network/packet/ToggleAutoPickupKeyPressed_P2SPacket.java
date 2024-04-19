package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.setAutoPickupEnabled(!c.isAutoPickupEnabled());

                if(c.isAutoPickupEnabled()){
                    player.sendSystemMessage(Component.translatable("Auto pickup: On"));

                }else{
                    player.sendSystemMessage(Component.translatable("Auto pickup: Off"));
                }

            });

        });
    }
}
