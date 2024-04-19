package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.IAdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
    public static void  onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(AdvancedPickup.INSTANCE).ifPresent(oldStore ->{
//                        event.getOriginal().getCapability(AdvancedPickupCapability.INSTANCE)
//                            ).ifPresent(newStore->{
//                            newStore.copyFrom(oldStore);
//                        });
                    });
        }
    }

    @SubscribeEvent
    public static void  onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(AdvancedPickup.INSTANCE).ifPresent(IAdvancedPickup::subtractPickupTick);
        }
    }



}
