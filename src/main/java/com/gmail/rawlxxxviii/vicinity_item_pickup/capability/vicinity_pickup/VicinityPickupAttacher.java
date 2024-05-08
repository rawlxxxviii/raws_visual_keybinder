package com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup;


import com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VicinityPickupAttacher {

    public static class VicinityPickupProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(VicinityItemPickupMod.MODID, "vicinity_pickup");


        private IVicinityPickup backend = new VicinityPickupImplementation();
        private final LazyOptional<IVicinityPickup> optional = LazyOptional.of(()->this.backend);



        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
            return VicinityPickup.INSTANCE.orEmpty(capability, this.optional);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
            return ICapabilityProvider.super.getCapability(cap);
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

            final VicinityPickupProvider provider = new VicinityPickupProvider();
            event.addCapability(VicinityPickupProvider.IDENTIFIER, provider);
        }


    }

}
