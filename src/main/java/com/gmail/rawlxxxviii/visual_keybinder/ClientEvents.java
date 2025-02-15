package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent event) {
        if ((event.phase == Phase.END) && (event.player instanceof LocalPlayer)) {

            if (KeyBinding.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new AlternativeKeybindScreen(null, Minecraft.getInstance().options));
            }

        }
    }


}
