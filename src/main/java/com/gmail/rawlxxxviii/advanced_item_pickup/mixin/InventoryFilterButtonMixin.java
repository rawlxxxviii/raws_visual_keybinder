package com.gmail.rawlxxxviii.advanced_item_pickup.mixin;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.ToggleAutoPickupKeyPressed_C2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryFilterButtonMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {


    private ImageButton filterButton;
    private static final int OFFSET_HORIZONTAl = 0;
    private static final int OFFSET_VERTICAl = 0;


    public InventoryFilterButtonMixin(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {


        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent( c->{

            System.out.println("is auto pickup enabled: " + c.isAutoPickupEnabled());

        });

        int leftOffset = 0;
        int heightOffset = 0;

        filterButton = new ImageButton(
                this.leftPos + leftOffset, this.height / 2 - heightOffset,
                20, 18,
                0, 220,
                18,
                new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/inventory_filter_menu_button.png"),
                256, 256,
                (button) -> {
                    PacketHandler.sendToServer(new ToggleAutoPickupKeyPressed_C2SPacket());
                },
//                (button) -> Minecraft.getInstance().setScreen(new BiomepediaHomeScreen(Component.literal(""))),
                Component.literal("Lorem Ipsum")
        );
        filterButton.visible = true;
        filterButton.active = true;

        addRenderableWidget(filterButton);

    }

    @Inject(method = "lambda$init$0(Lnet/minecraft/client/gui/components/Button;)V", at = @At("RETURN"))
    protected void updateGuiSize(CallbackInfo ci) {
        if(filterButton == null){
            return;
        }

        System.out.println(this.width);

        filterButton.setPosition(this.leftPos + OFFSET_HORIZONTAl, this.height / 2 - OFFSET_VERTICAl);
        filterButton.setPosition( OFFSET_HORIZONTAl, OFFSET_VERTICAl);
    }

}
