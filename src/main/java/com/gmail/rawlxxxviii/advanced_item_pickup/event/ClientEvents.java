package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.DisableAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.EnableAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.PickUpAllKeyDown_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.ToggleAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.server.ItemPickupControl;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {


            if(KeyBinding.ENABLE_AUTO_PICKUP_KEY.consumeClick()) {
                ItemPickupControl.setAutoPickupEnabled(true);

                PacketHandler.sendToServer(new EnableAutoPickupKeyPressed_P2SPacket());
            }
            if(KeyBinding.DISABLE_AUTO_PICKUP_KEY.consumeClick()) {
                ItemPickupControl.setAutoPickupEnabled(false);
                PacketHandler.sendToServer(new DisableAutoPickupKeyPressed_P2SPacket());
            }
            if(KeyBinding.TOGGLE_AUTO_PICKUP_KEY.consumeClick()) {
                ItemPickupControl.setAutoPickupEnabled(!ItemPickupControl.isAutoPickupEnabled());
                PacketHandler.sendToServer(new ToggleAutoPickupKeyPressed_P2SPacket());
            }
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.@NotNull ClientTickEvent event) {

            if(
                event.phase.equals(TickEvent.Phase.END)
                &&
                KeyBinding.PICKUP_ALL_KEY.isDown()
            ){
                PacketHandler.sendToServer(new PickUpAllKeyDown_P2SPacket());

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
        }
    }



}
