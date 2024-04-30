package com.gmail.rawlxxxviii.advanced_item_pickup.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemUtils {

    public static ResourceLocation getResourceLocation(Item item){
        return item.builtInRegistryHolder().key().location();
    }

//    public static String getResourceLocationString(Item item){
//        return  getResourceLocation(item).toString();
//    }
}
