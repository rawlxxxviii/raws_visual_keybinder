package com.gmail.rawlxxxviii.vicinity_item_pickup.util;

import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.concurrent.atomic.AtomicReference;

public class DistanceUtils {

    public static double getDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static AABB getPlayerAABB(Player player){

        AtomicReference<AABB> aabb = new AtomicReference<>(new AABB(0, 0, 0, 0, 0, 0));
        player.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{
            aabb.set(new AABB(
                    player.getX() - c.getReach(),
                    player.getY() - c.getReach() + c.getVerticalOffset(),
                    player.getZ() - c.getReach(),
                    player.getX() + c.getReach(),
                    player.getY() + c.getReach() + c.getVerticalOffset() + c.getVerticalExtraReachTop(),
                    player.getZ() + c.getReach()
            ));
        });

        return aabb.get();
    }
}
