 package com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.s2c;

import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickup;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class UpdateVicinityPickupSettings_S2CPacket {

     private CompoundTag tag;

     public UpdateVicinityPickupSettings_S2CPacket(CompoundTag tag) {
         this.tag = tag;
     }

     public UpdateVicinityPickupSettings_S2CPacket(FriendlyByteBuf buffer) {
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

             player.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{
                 c.deserializeNBT(this.tag);
             });
         });

         return  true;
     }

 }
