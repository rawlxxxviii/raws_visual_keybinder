package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.IAdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import net.minecraft.network.FriendlyByteBuf;
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

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(IAdvancedPickup::addPickupTick);

        });
    }
}
