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

     public PickupItemEntity_C2SPacket(int id) {
         this.id = id;
     }

     public PickupItemEntity_C2SPacket(FriendlyByteBuf buffer) {
         this.id = buffer.readInt();
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
         buffer.writeInt(this.id);
     }

     public void handle(Supplier<NetworkEvent.Context> supplier) {

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


                 ItemStack itemstack = itemEntity.getItem();
                 Item item = itemstack.getItem();
                 int initialCount = itemstack.getCount();

                 int hook = net.minecraftforge.event.ForgeEventFactory.onItemPickup(itemEntity, serverPlayer);
                 if (hook < 0) return;

                 ItemStack copy = itemstack.copy();
                 if (
                         (hook == 1 || initialCount <= 0 || serverPlayer.getInventory().add(itemstack))
                 ) {
                     copy.setCount(copy.getCount() - itemEntity.getItem().getCount());
                     net.minecraftforge.event.ForgeEventFactory.firePlayerItemPickupEvent(serverPlayer, itemEntity, copy);
                     serverPlayer.take(itemEntity, initialCount);
                     if (itemstack.isEmpty()) {
                         itemEntity.discard();
                         itemstack.setCount(initialCount);
                     }

                     serverPlayer.awardStat(Stats.ITEM_PICKED_UP.get(item), initialCount);
                     serverPlayer.onItemPickup(itemEntity);
                 }
             });


         });
     }


 }
