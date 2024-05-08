package com.gmail.rawlxxxviii.vicinity_item_pickup.settings_menu;

import com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod;
import com.gmail.rawlxxxviii.vicinity_item_pickup.menu.VicinityPickupMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, VicinityItemPickupMod.MODID);

    public static final RegistryObject<MenuType<VicinityPickupMenu>> VICINITY_PICKUP_MENU =
            MENUS.register("vicinity_pickup_menu", ()-> IForgeMenuType.create(VicinityPickupMenu::new));

    public static void register (IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
