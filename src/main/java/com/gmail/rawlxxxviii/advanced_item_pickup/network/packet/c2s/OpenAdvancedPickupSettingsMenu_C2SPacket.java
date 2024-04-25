 package com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateNameFilter_S2C_Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

 public class OpenAdvancedPickupSettingsMenu_C2SPacket {


     public OpenAdvancedPickupSettingsMenu_C2SPacket() {
     }

     public OpenAdvancedPickupSettingsMenu_C2SPacket(FriendlyByteBuf buffer) {
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

             serverPlayer.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{

                 NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                                 (containterId, playerInventory,player)-> c.createMenu(containterId,playerInventory,player),
                                 Component.literal("advanced pickup menu")
                         )
                     );


             });
         });
     }


 }
