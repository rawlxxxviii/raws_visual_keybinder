package com.gmail.rawlxxxviii.advanced_item_pickup.capability.vicinity_pickup;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class VicinityPickup {

    public static final Capability<IVicinityPickup> INSTANCE = CapabilityManager.get(new CapabilityToken<IVicinityPickup>() {});

    public static void register(RegisterCapabilitiesEvent event){
        event.register((IVicinityPickup.class));
    }

    public VicinityPickup() {
    }

}
