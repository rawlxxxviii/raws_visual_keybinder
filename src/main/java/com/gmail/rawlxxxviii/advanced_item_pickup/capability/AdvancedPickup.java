package com.gmail.rawlxxxviii.advanced_item_pickup.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class AdvancedPickup {

    public static final Capability<IAdvancedPickup> INSTANCE = CapabilityManager.get(new CapabilityToken<IAdvancedPickup>() {});

    public static void register(RegisterCapabilitiesEvent event){
        event.register((IAdvancedPickup.class));
    }

    public AdvancedPickup() {
    }

}
