package com.gmail.rawlxxxviii.advanced_item_pickup.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String MOD_KEY_CATEGORY = "Advanced item pickup";

    public static final String ENABLE_AUTO_PICKUP = "Enable auto pickup";
    public static final String DISABLE_AUTO_PICKUP = "Disable auto pickup";
    public static final String TOGGLE_AUTO_PICKUP = "Toggle auto pickup";
    public static final String PICKUP_ALL = "Pickup items";
    public static final String OPEN_VICINITY_PICKUP = "Open Vicinity Pickup";
    public static final String OPEN_SETTINGS_MENU = "Open filter settings menu";

    public static final KeyMapping ENABLE_AUTO_PICKUP_KEY = new KeyMapping(
            ENABLE_AUTO_PICKUP,
            KeyConflictContext.IN_GAME,
            KeyModifier.SHIFT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            MOD_KEY_CATEGORY);

    public static final KeyMapping DISABLE_AUTO_PICKUP_KEY = new KeyMapping(
            DISABLE_AUTO_PICKUP,
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            MOD_KEY_CATEGORY);

    public static final KeyMapping TOGGLE_AUTO_PICKUP_KEY = new KeyMapping(
            TOGGLE_AUTO_PICKUP,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_T,
            MOD_KEY_CATEGORY);

    public static final KeyMapping PICKUP_ALL_KEY = new KeyMapping(
            PICKUP_ALL,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            MOD_KEY_CATEGORY);

    public static final KeyMapping OPEN_VICINITY_PICKUP_KEY = new KeyMapping(
            OPEN_VICINITY_PICKUP,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            MOD_KEY_CATEGORY);

    public static final KeyMapping OPEN_SETTINGS_MENU_KEY = new KeyMapping(
            OPEN_SETTINGS_MENU,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            MOD_KEY_CATEGORY);

}