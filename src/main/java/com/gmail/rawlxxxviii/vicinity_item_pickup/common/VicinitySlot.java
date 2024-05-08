package com.gmail.rawlxxxviii.vicinity_item_pickup.common;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class VicinitySlot extends Slot {
    public VicinitySlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

}
