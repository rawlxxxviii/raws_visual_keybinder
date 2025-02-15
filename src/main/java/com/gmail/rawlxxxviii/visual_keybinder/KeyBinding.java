package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyBinding {


    public static KeyMapping OPEN_SCREEN;


    static {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(KeyBinding::registerKeyBindings);
    }


    private static KeyMapping createKeyMapping (RegisterKeyMappingsEvent event, KeyMapping keyMapping){
        event.register(keyMapping);
        return keyMapping;
    }

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
