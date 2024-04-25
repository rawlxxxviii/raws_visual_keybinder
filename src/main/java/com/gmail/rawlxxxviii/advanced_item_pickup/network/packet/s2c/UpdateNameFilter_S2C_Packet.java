 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class UpdateNameFilter_S2C_Packet {

     private String name;
     private ItemFilterType itemFilterType;

     public UpdateNameFilter_S2C_Packet(String name, ItemFilterType itemFilterType) {
         this.name = name;
         this.itemFilterType = itemFilterType;
     }

     public UpdateNameFilter_S2C_Packet(FriendlyByteBuf buffer) {
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

             var player = Minecraft.getInstance().player;
             if(player == null){
                 return;
             }
             var level = player.getLevel();
             if(!level.isClientSide){
                 return;
             }

             player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                 c.updateNameFilter(name,itemFilterType);
             });

         });
     }

 }
