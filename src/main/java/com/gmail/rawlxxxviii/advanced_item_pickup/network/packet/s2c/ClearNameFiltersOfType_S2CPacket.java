 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class ClearNameFiltersOfType_S2CPacket {

     private ItemFilterType itemFilterType;

     public ClearNameFiltersOfType_S2CPacket(ItemFilterType itemFilterType) {
         this.itemFilterType = itemFilterType;
     }

     public ClearNameFiltersOfType_S2CPacket(FriendlyByteBuf buffer) {
         this.itemFilterType = buffer.readEnum(ItemFilterType.class);
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
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
                 c.clearNameFiltersOfType(itemFilterType);
                 c.setSettingsScreenUpToDate(false);
             });
         });
     }
 }
