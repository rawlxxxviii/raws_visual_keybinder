 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.RemoveNameFilter_S2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class RemoveNameFilter_C2SPacket {

     private ResourceLocation resourceLocation;

     public RemoveNameFilter_C2SPacket(ResourceLocation resourceLocation) {
         this.resourceLocation = resourceLocation;
     }

     public RemoveNameFilter_C2SPacket(FriendlyByteBuf buffer) {
         this.resourceLocation = buffer.readResourceLocation();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeResourceLocation(this.resourceLocation);
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
                 c.removeNameFilter(resourceLocation);
                 PacketHandler.sendToPlayer(
                         new RemoveNameFilter_S2CPacket(resourceLocation)
                         ,player
                 );
             });
         });
     }
 }
