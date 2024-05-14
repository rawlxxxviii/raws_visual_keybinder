package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VisualKeybinderMod.MODID)
public class VisualKeybinderMod
{
    public static final String MODID = "visual_keybinder";
    private static final Logger LOGGER = LogUtils.getLogger();

    public VisualKeybinderMod()
    {

        MinecraftForge.EVENT_BUS.register(this);

    }

}
