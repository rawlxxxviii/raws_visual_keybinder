package com.gmail.rawlxxxviii.advanced_item_pickup.capability;


import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdvancedPickupAttacher  {

    public static class AdvancedPickupProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

//        public static final ResourceLocation IDENTIFIER = new ResourceLocation(AdvancedItemPickupMod.MODID, "advanced_pickup");
        public static final ResourceLocation IDENTIFIER = new ResourceLocation(AdvancedItemPickupMod.MODID, "advanced_pickup");


        private IAdvancedPickup backend = new AdvancedPickupImplementation();
        private final LazyOptional<IAdvancedPickup> optional = LazyOptional.of(()->this.backend);



        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
            return AdvancedPickup.INSTANCE.orEmpty(capability, this.optional);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
            return ICapabilityProvider.super.getCapability(cap);
        }

        void invalidate() {
            this.optional.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }

        public static void attach(final AttachCapabilitiesEvent<Entity> event){

            final AdvancedPickupProvider provider = new AdvancedPickupProvider();
            event.addCapability(AdvancedPickupProvider.IDENTIFIER, provider);
        }


    }

}
