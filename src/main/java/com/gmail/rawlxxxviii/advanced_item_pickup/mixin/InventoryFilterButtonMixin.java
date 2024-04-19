package com.gmail.rawlxxxviii.advanced_item_pickup.mixin;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.ToggleAutoPickupKeyPressed_P2SPacket;
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

import java.lang.reflect.Method;

@Mixin(InventoryScreen.class)
public abstract class InventoryFilterButtonMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {


    public InventoryFilterButtonMixin(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {


        int leftOffset = 0;
        int heightOffset = 0;

        var imageButton = new ImageButton(
                this.leftPos + leftOffset, this.height / 2 - heightOffset,
                20, 18,
                0, 220,
                18,
                new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/biomepedia.png"),
                256, 256,
                (button) -> {
                    PacketHandler.sendToServer(new ToggleAutoPickupKeyPressed_P2SPacket());
                },
//                (button) -> Minecraft.getInstance().setScreen(new BiomepediaHomeScreen(Component.literal(""))),
                Component.literal("Lorem Ipsum")
        );
        imageButton.visible = true;
        imageButton.active = true;

        addRenderableWidget(imageButton);

    }

}
