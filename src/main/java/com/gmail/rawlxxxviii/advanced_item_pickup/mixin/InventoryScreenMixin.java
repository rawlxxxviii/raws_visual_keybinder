package com.gmail.rawlxxxviii.advanced_item_pickup.mixin;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.OpenAdvancedPickupSettingsMenu_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.DisableAutoPickupKeyPressed_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.EnableAutoPickupKeyPressed_C2SPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    @Unique
    private ImageButton raws_advanced_item_pickup$enableAutoPickupButton;
    @Unique
    private ImageButton raws_advanced_item_pickup$disableAutoPickupButton;
    @Unique
    private ImageButton raws_advanced_item_pickup$openAutoPickupFilterScreenButton;


    public InventoryScreenMixin(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render(PoseStack p_98875_, int p_98876_, int p_98877_, float p_98878_, CallbackInfo ci)
    {
        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent( c-> {

            raws_advanced_item_pickup$enableAutoPickupButton.visible = !c.isAutoPickupEnabled();
            raws_advanced_item_pickup$enableAutoPickupButton.active = !c.isAutoPickupEnabled();
            raws_advanced_item_pickup$disableAutoPickupButton.visible = c.isAutoPickupEnabled();
            raws_advanced_item_pickup$disableAutoPickupButton.active = c.isAutoPickupEnabled();

        });
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {


        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent( c->{


            raws_advanced_item_pickup$disableAutoPickupButton = new ImageButton(
                    this.leftPos + this.imageWidth - 28,
                    this.topPos + 6,
                    10, 10,
                    0, 0,
                    10,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {


                        PacketHandler.sendToServer(new DisableAutoPickupKeyPressed_C2SPacket());

                    },
                    Component.literal("Disable auto pickup")
            );

            raws_advanced_item_pickup$enableAutoPickupButton = new ImageButton(
                    this.leftPos + this.imageWidth - 28,
                    this.topPos + 6,
                     10, 10,
                     10, 0,
                    10,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {

                        PacketHandler.sendToServer(new EnableAutoPickupKeyPressed_C2SPacket());

                    },
                    Component.literal("Enable auto pickup")
            );

            raws_advanced_item_pickup$openAutoPickupFilterScreenButton = new ImageButton(
                    this.leftPos + this.imageWidth - 16,
                    this.topPos + 6,
                     10, 10,
                     20, 0,
                    10,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {
                        PacketHandler.sendToServer(new OpenAdvancedPickupSettingsMenu_C2SPacket());
                    },
                    Component.literal("Advanced Item pickup settings")
            );

            addRenderableWidget(raws_advanced_item_pickup$enableAutoPickupButton);
            addRenderableWidget(raws_advanced_item_pickup$disableAutoPickupButton);
            addRenderableWidget(raws_advanced_item_pickup$openAutoPickupFilterScreenButton);

        });

    }

    @Inject(method = "lambda$init$0(Lnet/minecraft/client/gui/components/Button;)V", at = @At("RETURN"))
    protected void updateGuiSize(CallbackInfo ci) {

        raws_advanced_item_pickup$enableAutoPickupButton.setPosition(
                this.leftPos + this.imageWidth - 28,
                this.topPos + 6
        );
        raws_advanced_item_pickup$disableAutoPickupButton.setPosition(
                this.leftPos + this.imageWidth - 28,
                this.topPos + 6
        );
        raws_advanced_item_pickup$openAutoPickupFilterScreenButton.setPosition(
                this.leftPos + this.imageWidth - 16,
                this.topPos + 6
        );
    }

}
