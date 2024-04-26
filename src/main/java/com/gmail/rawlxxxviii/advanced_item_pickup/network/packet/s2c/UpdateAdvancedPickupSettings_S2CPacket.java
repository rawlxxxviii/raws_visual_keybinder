 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

 public class UpdateAdvancedPickupSettings_S2CPacket {

     private CompoundTag tag;

     public UpdateAdvancedPickupSettings_S2CPacket(CompoundTag tag) {
         this.tag = tag;
     }

     public UpdateAdvancedPickupSettings_S2CPacket(FriendlyByteBuf buffer) {
         this.tag = buffer.readNbt();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeNbt(this.tag);
     }



     public boolean handle(Supplier<NetworkEvent.Context> supplier) {

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
                 c.deserializeNBT(this.tag);
//                 c.setSettingsScreenUpToDate(false);
             });
         });
         return  true;
     }

 }
