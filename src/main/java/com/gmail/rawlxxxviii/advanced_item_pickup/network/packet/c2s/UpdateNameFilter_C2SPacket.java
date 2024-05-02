 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateNameFilter_S2C_Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateNameFilter_C2SPacket {

    private ResourceLocation resourceLocation;
    private ItemFilterType itemFilterType;

    public UpdateNameFilter_C2SPacket(ResourceLocation resourceLocation, ItemFilterType itemFilterType) {
        this.resourceLocation = resourceLocation;
        this.itemFilterType = itemFilterType;
    }

    public UpdateNameFilter_C2SPacket(FriendlyByteBuf buffer) {
        this.resourceLocation = buffer.readResourceLocation();
        this.itemFilterType = buffer.readEnum(ItemFilterType.class);
    }

    public void encodeToBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.resourceLocation);
        buffer.writeEnum(this.itemFilterType);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {

        if(resourceLocation.equals(new ResourceLocation("minecraft:air"))){
            return;
        }

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

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.updateNameFilter(resourceLocation,itemFilterType);

                PacketHandler.sendToPlayer(
                        new UpdateNameFilter_S2C_Packet(resourceLocation,itemFilterType)
                        ,player
                );

            });
        });
    }


}
