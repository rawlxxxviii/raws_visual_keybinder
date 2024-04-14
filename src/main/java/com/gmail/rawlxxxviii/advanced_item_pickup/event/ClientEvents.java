package com.gmail.rawlxxxviii.advanced_item_pickup.event;

import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.DisableAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.EnableAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.ToggleAutoPickupKeyPressed_P2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.server.ItemPickupControl;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


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
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.PICKUP_ALL_KEY);
        event.register(KeyBinding.ENABLE_AUTO_PICKUP_KEY);
        event.register(KeyBinding.DISABLE_AUTO_PICKUP_KEY);
        event.register(KeyBinding.TOGGLE_AUTO_PICKUP_KEY);
    }

}
