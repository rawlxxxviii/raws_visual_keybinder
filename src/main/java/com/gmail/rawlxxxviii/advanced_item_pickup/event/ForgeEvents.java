package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupCapability;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupProvider;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.PickUpAllKeyDown_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.KeyBinding;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if(KeyBinding.PICKUP_ALL_KEY.isDown()){
            PacketHandler.sendToServer(new PickUpAllKeyDown_P2SPacket());
        }

    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(AdvancedItemPickupMod.MODID, "properties"), new AdvancedPickupProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(AdvancedPickupCapability.class);
    }

    @SubscribeEvent
    public static void  onPlayerCloned(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(oldStore ->{
                event.getOriginal().getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(newStore->{
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void  onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side == LogicalSide.SERVER) {
            event.player.getCapability(AdvancedPickupProvider.ADVANCED_PICKUP_CAPABILITY).ifPresent(AdvancedPickupCapability::subtractPickupTick);
        }
    }
}
