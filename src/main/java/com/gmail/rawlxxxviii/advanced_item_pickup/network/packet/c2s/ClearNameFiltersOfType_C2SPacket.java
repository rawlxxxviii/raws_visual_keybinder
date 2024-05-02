 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.ClearNameFiltersOfType_S2CPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.RemoveNameFilter_S2CPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class ClearNameFiltersOfType_C2SPacket {

     private ItemFilterType itemFilterType;

     public ClearNameFiltersOfType_C2SPacket(ItemFilterType itemFilterType) {
         this.itemFilterType = itemFilterType;
     }

     public ClearNameFiltersOfType_C2SPacket(FriendlyByteBuf buffer) {
         this.itemFilterType = buffer.readEnum(ItemFilterType.class);
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
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
                 c.clearNameFiltersOfType(itemFilterType);
                 PacketHandler.sendToPlayer(
                         new ClearNameFiltersOfType_S2CPacket(itemFilterType)
                         ,player
                 );
             });
         });
     }
 }
