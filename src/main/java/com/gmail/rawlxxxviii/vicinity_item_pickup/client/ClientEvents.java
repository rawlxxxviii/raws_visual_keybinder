package com.gmail.rawlxxxviii.vicinity_item_pickup.client;

import com.gmail.rawlxxxviii.vicinity_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.c2s.*;
import com.gmail.rawlxxxviii.vicinity_item_pickup.menu.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import static com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod.MODID;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {


        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {

            if(KeyBinding.OPEN_VICINITY_PICKUP_KEY_OLD.consumeClick()) {
                PacketHandler.sendToServer(new OpenVicinityPickupMenu_C2SPacket());
            }
            if(KeyBinding.OPEN_VICINITY_PICKUP_KEY.consumeClick()) {

                if(Minecraft.getInstance().player != null){
                    Minecraft.getInstance().setScreen(new VicinityPickupScreen(Minecraft.getInstance().player));
                }
            }

        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.@NotNull ClientTickEvent event) {

            if(!event.phase.equals(TickEvent.Phase.END)){
                return;
            }

        }

    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            KeyBinding.register(event);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            MenuScreens.register(ModMenuTypes.VICINITY_PICKUP_MENU.get(), VicinityPickupContainerScreen::new);

        }

    }



}
