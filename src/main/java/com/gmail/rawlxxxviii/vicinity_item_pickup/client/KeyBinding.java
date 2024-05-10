package com.gmail.rawlxxxviii.vicinity_item_pickup.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String MOD_KEY_CATEGORY = "Vicinity pickup";

    public static final String OPEN_VICINITY_PICKUP = "Open Vicinity Pickup";


    public static final KeyMapping OPEN_VICINITY_PICKUP_KEY = new KeyMapping(
            OPEN_VICINITY_PICKUP,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_E,
            MOD_KEY_CATEGORY);


    public static void register(RegisterKeyMappingsEvent event){
        event.register(KeyBinding.OPEN_VICINITY_PICKUP_KEY);
    }
}