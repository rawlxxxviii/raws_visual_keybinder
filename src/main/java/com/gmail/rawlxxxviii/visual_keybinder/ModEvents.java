package com.gmail.rawlxxxviii.visual_keybinder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import static com.gmail.rawlxxxviii.visual_keybinder.VisualKeybinderMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {


    @SubscribeEvent
    public static void onLoaded(InterModEnqueueEvent event) {

//        FileUtil.loadInitialPreset( Minecraft.getInstance().options );

    }


}
