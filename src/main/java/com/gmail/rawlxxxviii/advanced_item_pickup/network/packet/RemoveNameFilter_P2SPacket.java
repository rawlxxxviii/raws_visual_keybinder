 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class RemoveNameFilter_P2SPacket {

     private String name;

     public RemoveNameFilter_P2SPacket(String name) {
         this.name = name;
     }

     public RemoveNameFilter_P2SPacket(FriendlyByteBuf buffer) {
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

             player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
                 c.removeNameFilter(name);
             });
         });
     }
 }
