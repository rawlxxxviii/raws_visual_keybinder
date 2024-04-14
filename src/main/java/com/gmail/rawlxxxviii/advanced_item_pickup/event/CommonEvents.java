package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(PacketHandler::register);
    }}
