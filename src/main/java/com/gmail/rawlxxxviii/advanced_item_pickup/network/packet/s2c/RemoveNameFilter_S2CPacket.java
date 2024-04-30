 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class RemoveNameFilter_S2CPacket {

     private ResourceLocation resourceLocation;

     public RemoveNameFilter_S2CPacket(ResourceLocation resourceLocation) {
         this.resourceLocation = resourceLocation;
     }

     public RemoveNameFilter_S2CPacket(FriendlyByteBuf buffer) {
         this.resourceLocation = buffer.readResourceLocation();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeResourceLocation(this.resourceLocation);
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
                 c.removeNameFilter(resourceLocation);
                 c.setSettingsScreenUpToDate(false);
             });
         });
     }
 }
