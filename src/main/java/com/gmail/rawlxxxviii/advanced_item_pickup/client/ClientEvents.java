package com.gmail.rawlxxxviii.advanced_item_pickup.client;

import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.*;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.AdvancedPickupSettingsScreen;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {


        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {


            if(KeyBinding.ENABLE_AUTO_PICKUP_KEY.consumeClick()) {

                PacketHandler.sendToServer(new EnableAutoPickupKeyPressed_C2SPacket());
            }
            if(KeyBinding.DISABLE_AUTO_PICKUP_KEY.consumeClick()) {
                PacketHandler.sendToServer(new DisableAutoPickupKeyPressed_C2SPacket());
            }
            if(KeyBinding.TOGGLE_AUTO_PICKUP_KEY.consumeClick()) {
                PacketHandler.sendToServer(new ToggleAutoPickupKeyPressed_C2SPacket());
            }
            if(KeyBinding.OPEN_VICINITY_PICKUP_KEY.consumeClick()) {

//                Minecraft.getInstance().setScreen(new AdvancedPickupSettingsScreen(Component.literal("")));
            }

            if(KeyBinding.OPEN_SETTINGS_MENU_KEY.consumeClick()) {
                PacketHandler.sendToServer(new OpenAdvancedPickupSettingsMenu_C2SPacket());
            }
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.@NotNull ClientTickEvent event) {

            if(
                event.phase.equals(TickEvent.Phase.END)
                &&
                KeyBinding.PICKUP_ALL_KEY.isDown()
            ){
                PacketHandler.sendToServer(new PickUpAllKeyDown_C2SPacket());

            }

        }

    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.PICKUP_ALL_KEY);
            event.register(KeyBinding.ENABLE_AUTO_PICKUP_KEY);
            event.register(KeyBinding.DISABLE_AUTO_PICKUP_KEY);
            event.register(KeyBinding.TOGGLE_AUTO_PICKUP_KEY);
            event.register(KeyBinding.OPEN_VICINITY_PICKUP_KEY);
            event.register(KeyBinding.OPEN_SETTINGS_MENU_KEY);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.ADVANCED_PICKUP_SETTINGS_MENU.get(), AdvancedPickupSettingsScreen::new);

        }

    }



}
