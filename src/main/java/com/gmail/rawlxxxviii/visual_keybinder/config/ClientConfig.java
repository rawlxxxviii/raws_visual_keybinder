package com.gmail.rawlxxxviii.visual_keybinder.config;

import com.gmail.rawlxxxviii.visual_keybinder.KeyBoardLayout;
import com.gmail.rawlxxxviii.visual_keybinder.KeyboardLayoutKey;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.*;

public class ClientConfig {
    public static final ModConfigSpec GENERAL_SPEC;

    public static ModConfigSpec.IntValue menuButtonOffsetX;
    public static ModConfigSpec.IntValue menuButtonOffsetY;
    public static ModConfigSpec.BooleanValue displayConflictContext;
    public static ModConfigSpec.BooleanValue displayLayoutButtonTooltips;
    public static ModConfigSpec.BooleanValue displayChangeAndResetButtonTooltips;


    static {
        ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {


        builder.push("Layouts");
        builder.comment("The layout files are found in the minecraft folder /visual keybinder/layouts");
        builder.comment("The name of the file should be [number].[name].layout.txt");
        builder.comment("If all the layouts are deleted, the default will generate on startup");
        builder.comment("Layout file line format:");
        builder.comment("for a small button: [keyName];[xPosition];[yPosition];");
        builder.comment("for a wide button:  [keyName];[xPosition];[yPosition];wide");

        builder.pop();

        builder.push("Preferences");
        displayConflictContext = builder
                .comment("Display whether a binding is for GUI, in game or both.")
                .define( "display_conflict_context", false)
        ;
        displayLayoutButtonTooltips = builder
                .comment("Should a tooltip be displayed when the mouse is over a button.")
                .define( "display_layout_button_tooltip", true)
        ;
        displayChangeAndResetButtonTooltips = builder
                .comment("Should a tooltip be displayed for the change and reset button.")
                .define( "display_button_tooltip", true)
        ;

        menuButtonOffsetX = builder
                .comment("Reposition the menu button horizontally.")
                .defineInRange( "menu_button_offset_x", 0,-10000,10000)
        ;
        menuButtonOffsetY = builder
                .comment("Reposition the menu button vertically.")
                .defineInRange( "menu_button_offset_y", 0,-10000,10000)
        ;

        builder.pop();

    }
}