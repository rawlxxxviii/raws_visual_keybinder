package com.gmail.rawlxxxviii.advanced_item_pickup;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AdvancedItemPickupMod.MODID)
public class AdvancedItemPickupMod
{
    public static final String MODID = "advanced_item_pickup";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AdvancedItemPickupMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModMenuTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(PacketHandler::register);
    }

}
