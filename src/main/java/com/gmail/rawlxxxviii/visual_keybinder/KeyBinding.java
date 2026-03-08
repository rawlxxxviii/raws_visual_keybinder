package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = VisualKeybinderMod.MODID, value = Dist.CLIENT)
public class KeyBinding {


    public static KeyMapping OPEN_SCREEN;


       private static KeyMapping createKeyMapping (RegisterKeyMappingsEvent event, KeyMapping keyMapping){
        event.register(keyMapping);
        return keyMapping;
    }

    @SubscribeEvent

    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        OPEN_SCREEN = createKeyMapping(event,
                new KeyMapping(
                        "key.raws_visual_keybinder.open_screen",
                        KeyConflictContext.IN_GAME,
                        KeyModifier.NONE,
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_F10,
                        "key.raws_visual_keybinder.category"
                )
        );

    }


}
