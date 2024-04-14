package com.gmail.rawlxxxviii.advanced_item_pickup.server;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class ItemPickupControl {

    private static boolean PickupAllKeyDown = false;
    public static boolean isPickupAllKeyDown() {
        return PickupAllKeyDown;
    }
    public static void setPickupAllKeyDown(boolean pickupAllKeyDown) {
        if(pickupAllKeyDown != PickupAllKeyDown){
            System.out.println("Pickup: " + pickupAllKeyDown);
        }
        PickupAllKeyDown = pickupAllKeyDown;
    }


    private static boolean AutoPickupEnabled = true;
    public static boolean isAutoPickupEnabled() {
        return AutoPickupEnabled;
    }
    public static void setAutoPickupEnabled(boolean autoPickupEnabled) {
        AutoPickupEnabled = autoPickupEnabled;
        System.out.println("auto pickup: " + (AutoPickupEnabled ? "ON" : "OFF"));

    }


    public static boolean preventItemPickup(ItemEntity item, Player player) {

        return
                (!isAutoPickupEnabled() && !isPickupAllKeyDown());

    }


}
