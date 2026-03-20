package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.neoforge.mods.toml file
@Mod(VisualKeybinderMod.MODID)
public class VisualKeybinderMod
{
    public static final String MODID = "visual_keybinder";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VisualKeybinderMod(ModContainer modContainer)
    {

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.GENERAL_SPEC, MODID+".toml");

    }
    public void commonSetup(final FMLCommonSetupEvent event) {

    }

}
