package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdvancedPickupProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<AdvancedPickupCapability> ADVANCED_PICKUP_CAPABILITY = CapabilityManager.get(new CapabilityToken<AdvancedPickupCapability>() { });

    private AdvancedPickupCapability advancedPickupCapability = null;
    private final LazyOptional<AdvancedPickupCapability> optional = LazyOptional.of(this::createAdvancedPickupCapability);

    private AdvancedPickupCapability createAdvancedPickupCapability() {
        if(this.advancedPickupCapability == null){
            this.advancedPickupCapability = new AdvancedPickupCapability();
        }

        return this.advancedPickupCapability;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ADVANCED_PICKUP_CAPABILITY ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        var nbt = new CompoundTag();
        createAdvancedPickupCapability().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
