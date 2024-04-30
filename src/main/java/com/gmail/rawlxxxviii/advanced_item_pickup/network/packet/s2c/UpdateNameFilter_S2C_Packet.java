 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class UpdateNameFilter_S2C_Packet {

     private ResourceLocation resourceLocation;
     private ItemFilterType itemFilterType;

     public UpdateNameFilter_S2C_Packet(ResourceLocation resourceLocation, ItemFilterType itemFilterType) {
         this.resourceLocation = resourceLocation;
         this.itemFilterType = itemFilterType;
     }

     public UpdateNameFilter_S2C_Packet(FriendlyByteBuf buffer) {
         this.resourceLocation = buffer.readResourceLocation();
         this.itemFilterType = buffer.readEnum(ItemFilterType.class);
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeResourceLocation(this.resourceLocation);
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
                 c.updateNameFilter(resourceLocation,itemFilterType);
                 c.setSettingsScreenUpToDate(false);
             });

         });
     }

 }
