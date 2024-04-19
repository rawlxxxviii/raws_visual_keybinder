 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateNameFilter_P2SPacket {

    private String name;
    private ItemFilterType itemFilterType;

    public UpdateNameFilter_P2SPacket(String name, ItemFilterType itemFilterType) {
        this.name = name;
        this.itemFilterType = itemFilterType;
    }

    public UpdateNameFilter_P2SPacket(FriendlyByteBuf buffer) {
        this.name = buffer.readUtf();
        this.itemFilterType = buffer.readEnum(ItemFilterType.class);
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.name);
        buffer.writeEnum(this.itemFilterType);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        var context = supplier.get();

        context.enqueueWork(()->{
            var player = context.getSender();
            if(player == null){
                return;
            }

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.updateNameFilter(name,itemFilterType);
            });
        });
    }
}
