 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateAdvancedPickupSettings_S2CPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateNameFilter_S2C_Packet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class UpdateNameFilter_C2SPacket {

    private String name;
    private ItemFilterType itemFilterType;

    public UpdateNameFilter_C2SPacket(String name, ItemFilterType itemFilterType) {
        this.name = name;
        this.itemFilterType = itemFilterType;
    }

    public UpdateNameFilter_C2SPacket(FriendlyByteBuf buffer) {
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
            var level = player.getLevel();
            if(level.isClientSide){
                return;
            }

            player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                c.updateNameFilter(name,itemFilterType);

                player.sendSystemMessage(Component.translatable("autopickup: " + name + " - " + itemFilterType ));

                PacketHandler.sendToPlayer(
                        new UpdateNameFilter_S2C_Packet(name,itemFilterType)
                        ,player
                );

            });
        });
    }


}
