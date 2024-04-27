 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class RemoveNameFilter_S2CPacket {

     private String name;

     public RemoveNameFilter_S2CPacket(String name) {
         this.name = name;
     }

     public RemoveNameFilter_S2CPacket(FriendlyByteBuf buffer) {
         this.name = buffer.readUtf();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeUtf(this.name);
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
                 c.removeNameFilter(name);
                 c.setSettingsScreenUpToDate(false);
             });
         });
     }
 }
