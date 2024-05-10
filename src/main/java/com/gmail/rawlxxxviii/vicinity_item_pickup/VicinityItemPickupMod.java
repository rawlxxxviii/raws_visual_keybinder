package com.gmail.rawlxxxviii.vicinity_item_pickup;

import com.gmail.rawlxxxviii.vicinity_item_pickup.config.VicinityPickupConfig;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.vicinity_item_pickup.menu.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VicinityItemPickupMod.MODID)
public class VicinityItemPickupMod
{
    public static final String MODID = "vicinity_item_pickup";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VicinityItemPickupMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModMenuTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, VicinityPickupConfig.GENERAL_SPEC, MODID+".toml");

    }


    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }

}
