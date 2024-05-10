package com.gmail.rawlxxxviii.vicinity_item_pickup.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.DoubleValue reach;
    public static ForgeConfigSpec.DoubleValue verticalOffset;
    public static ForgeConfigSpec.DoubleValue verticalExtraReachTop;

    public static ForgeConfigSpec.BooleanValue disableVanillaPickup;


    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Editing the config requires the server to restart. Clients will receive config on entering the world. All settings are server sided.");
        reach = builder
                .comment("The reach of the player. Only effects vicinity pickup.")
                .defineInRange("reach", 2.3, 0, 50);
        verticalOffset = builder
                .comment("Shifts the vertical pickup min and max in order to compensate for the fact that items are on the floor and the player head and hands are higher from the ground. ")
                .defineInRange("vertical_offset", 0.5, 0, 50);
        verticalExtraReachTop = builder
                .comment("Adjusts the top reach.")
                .defineInRange("vertical_extra_reach_top", 0.5, -50, 50);

        disableVanillaPickup = builder
                .comment("Disable vanilla's auto pickup on item touch. This mod is designed as an addition to Raws' advanced pickup mod, which gives players control over which items to pickup. Therefore this value is defaulted to false.")
                .define("disable_vanilla_pickup", false);

    }
}
