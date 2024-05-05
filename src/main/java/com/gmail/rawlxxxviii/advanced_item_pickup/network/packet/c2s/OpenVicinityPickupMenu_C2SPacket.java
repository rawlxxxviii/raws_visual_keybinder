 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.vicinity_pickup.VicinityPickup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

 public class OpenVicinityPickupMenu_C2SPacket {


     public OpenVicinityPickupMenu_C2SPacket() {
     }

     public OpenVicinityPickupMenu_C2SPacket(FriendlyByteBuf buffer) {
     }

     public void encodeToBytes(FriendlyByteBuf buffer) {
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

             serverPlayer.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{

                 NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                                 (containterId, playerInventory,player)-> c.createMenu(containterId,playerInventory,player),
                                 Component.literal("Vicinity Pickup menu")
                         )
                     );


             });
         });
     }


 }
