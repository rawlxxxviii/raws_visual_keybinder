package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.IAdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateAdvancedPickupSettings_S2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ForgeEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {

        if(!(event.getObject() instanceof Player))
            return;

        if(!event.getObject().getCapability(AdvancedPickup.INSTANCE).isPresent()) {
            AdvancedPickupAttacher.AdvancedPickupProvider.attach(event);
        }
    }


    @SubscribeEvent
    public static void  onPlayerClone(PlayerEvent.Clone event){
        if(event.isWasDeath()){

            if(!(event.getEntity() instanceof ServerPlayer serverPlayer)){
                return;
            }
            var player = event.getEntity();
            var level = player.level;
            if(level.isClientSide){
                return;
            }

            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(AdvancedPickup.INSTANCE).ifPresent(oldStore ->{



                        event.getEntity().getCapability(AdvancedPickup.INSTANCE).ifPresent(newStore->{
                            newStore.copyFrom(oldStore);

                            PacketHandler.sendToPlayer(
                                    new UpdateAdvancedPickupSettings_S2CPacket(newStore.serializeNBT())
                                    ,serverPlayer
                            );
                        });
                    });

            event.getOriginal().invalidateCaps();
        }
    }


    @SubscribeEvent
    public static void  onEntityJoinWorldEvent(EntityJoinLevelEvent event){

        if(!(event.getEntity() instanceof ServerPlayer player)){
            return;
        }
        if(player.level.isClientSide){
            return;
        }


        player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{

            PacketHandler.sendToPlayer(
                    new UpdateAdvancedPickupSettings_S2CPacket(
                            c.serializeNBT()
                    ),
                    player
            );

        });

    }

    @SubscribeEvent
    public static void  onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(AdvancedPickup.INSTANCE).ifPresent(IAdvancedPickup::subtractPickupTick);
        }
    }



}
