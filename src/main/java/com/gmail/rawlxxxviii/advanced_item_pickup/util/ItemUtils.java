package com.gmail.rawlxxxviii.advanced_item_pickup.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemUtils {

    public static ResourceLocation getResourceLocation(Item item){
        return item.builtInRegistryHolder().key().location();
    }

    public static Item getItem(ResourceLocation resourceLocation){
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    public static ItemStack getItemStack(ResourceLocation resourceLocation){
        return new ItemStack(ItemUtils.getItem(resourceLocation));
    }

}
