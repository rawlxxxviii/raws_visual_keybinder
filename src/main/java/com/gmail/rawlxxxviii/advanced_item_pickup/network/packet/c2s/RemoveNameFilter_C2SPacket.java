 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.RemoveNameFilter_S2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class RemoveNameFilter_C2SPacket {

     private String name;

     public RemoveNameFilter_C2SPacket(String name) {
         this.name = name;
     }

     public RemoveNameFilter_C2SPacket(FriendlyByteBuf buffer) {
         this.name = buffer.readUtf();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeUtf(this.name);
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
                 c.removeNameFilter(name);
                 PacketHandler.sendToPlayer(
                         new RemoveNameFilter_S2CPacket(name)
                         ,player
                 );
             });
         });
     }
 }
