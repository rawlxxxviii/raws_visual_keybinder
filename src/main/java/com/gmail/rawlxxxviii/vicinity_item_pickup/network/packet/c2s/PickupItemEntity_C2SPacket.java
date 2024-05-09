 package com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickup;
import com.gmail.rawlxxxviii.vicinity_item_pickup.util.DistanceUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

 public class PickupItemEntity_C2SPacket {

     private int id;
     private int count;

     public PickupItemEntity_C2SPacket(int id, int count) {
         this.id = id;
         this.count = count;
     }

     public PickupItemEntity_C2SPacket(FriendlyByteBuf buffer) {
         this.id = buffer.readInt();
         this.count = buffer.readInt();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeInt(this.id);
         buffer.writeInt(this.count);
     }

     public void handle(Supplier<NetworkEvent.Context> supplier) {

         if(count < 1){
             return;
         }

         var context = supplier.get();

         context.enqueueWork(()->{
             var serverPlayer = context.getSender();
             if(serverPlayer == null){
                 return;
             }
             var level = serverPlayer.getLevel();
             if(level.isClientSide){
                 return;
             }

             if(!(level.getEntity(id) instanceof ItemEntity itemEntity)){
                 return;
             }

             serverPlayer.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{

                 if(
                        !DistanceUtils.getPlayerAABB(serverPlayer)
                            .contains(itemEntity.getX(),itemEntity.getY(),itemEntity.getZ())
                        ||
                        DistanceUtils.getDistance(serverPlayer.getX(),itemEntity.getX(),serverPlayer.getZ(),itemEntity.getZ())
                            > c.getReach()
                 ){
                     return;
                 }


                 ItemStack itemStack = itemEntity.getItem();
                 Item item = itemStack.getItem();

                 var itemsToAddCount = Math.min(itemStack.getCount(), count);
                 if(
                         itemsToAddCount < 1
                                 ||
                         net.minecraftforge.event.ForgeEventFactory.onItemPickup(itemEntity, serverPlayer) < 0
                 ){
                     return;
                 }


                 ItemStack copyToAdd = itemStack.copy();
                 copyToAdd.setCount(count);


                 if (serverPlayer.getInventory().add(copyToAdd)) {

                     var amountTaken = itemsToAddCount - copyToAdd.getCount();
                     itemStack.setCount(itemStack.getCount() - amountTaken);

                     ItemStack copy = itemStack.copy(); //
                     copy.setCount(amountTaken);
                     net.minecraftforge.event.ForgeEventFactory.firePlayerItemPickupEvent(serverPlayer, itemEntity, copy);

                     serverPlayer.take(itemEntity, amountTaken);
                     if (itemStack.isEmpty()) {
                         itemEntity.discard();
                     }

                     serverPlayer.awardStat(Stats.ITEM_PICKED_UP.get(item), amountTaken);
                     serverPlayer.onItemPickup(itemEntity);

                 }
             });


         });
     }


 }
