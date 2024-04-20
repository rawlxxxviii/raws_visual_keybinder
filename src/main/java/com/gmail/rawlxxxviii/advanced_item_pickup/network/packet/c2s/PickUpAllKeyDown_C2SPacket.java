package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.IAdvancedPickup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PickUpAllKeyDown_C2SPacket {

    public PickUpAllKeyDown_C2SPacket() {
    }

    public PickUpAllKeyDown_C2SPacket(FriendlyByteBuf buffer) {
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{

            var player = context.getSender();
            if(player == null)
                return;
            var level = player.getLevel();
            if(level.isClientSide){
                return;
            }

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(IAdvancedPickup::addPickupTick);

        });
    }
}
