package com.gmail.rawlxxxviii.vicinity_item_pickup.event;

import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickup;
import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickupAttacher;
import com.gmail.rawlxxxviii.vicinity_item_pickup.config.VicinityPickupConfig;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.s2c.UpdateVicinityPickupSettings_S2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ForgeEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {

        if((event.getObject() instanceof Player)){
            if(!event.getObject().getCapability(VicinityPickup.INSTANCE).isPresent()) {
                VicinityPickupAttacher.VicinityPickupProvider.attach(event);
            }
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


        player.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{

            c.setReach(VicinityPickupConfig.reach.get());
            c.setVerticalOffset(VicinityPickupConfig.verticalOffset.get());
            c.setVerticalExtraReachTop(VicinityPickupConfig.verticalExtraReachTop.get());
            c.setDisableVanillaPickup(VicinityPickupConfig.disableVanillaPickup.get());

            PacketHandler.sendToPlayer(
                    new UpdateVicinityPickupSettings_S2CPacket(
                            c.serializeNBT()
                    ),
                    player
            );

        });

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


            event.getOriginal().getCapability(VicinityPickup.INSTANCE).ifPresent(oldStore ->{
                event.getEntity().getCapability(VicinityPickup.INSTANCE).ifPresent(newStore->{
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }



}
