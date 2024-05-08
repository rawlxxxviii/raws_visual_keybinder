package com.gmail.rawlxxxviii.advanced_item_pickup.util;

import com.mojang.math.Vector3f;

public class MathUtils {

    public static double getDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
