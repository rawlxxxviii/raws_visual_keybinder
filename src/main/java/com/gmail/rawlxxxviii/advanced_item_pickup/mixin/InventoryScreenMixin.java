package com.gmail.rawlxxxviii.advanced_item_pickup.mixin;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.AdvancedPickupSettingsScreen;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.DisableAutoPickupKeyPressed_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.EnableAutoPickupKeyPressed_C2SPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {


    @Shadow @Final private RecipeBookComponent recipeBookComponent;
    private ImageButton enableAutoPickupButton;
    private ImageButton disableAutoPickupButton;
    private ImageButton openAutoPickupFilterScreenButton;
    private static final int OFFSET_HORIZONTAl = 0;
    private static final int OFFSET_VERTICAl = 0;


    public InventoryScreenMixin(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render(PoseStack p_98875_, int p_98876_, int p_98877_, float p_98878_, CallbackInfo ci)
    {
        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent( c-> {

            enableAutoPickupButton.visible = !c.isAutoPickupEnabled();
            enableAutoPickupButton.active = !c.isAutoPickupEnabled();
            disableAutoPickupButton.visible = c.isAutoPickupEnabled();
            disableAutoPickupButton.active = c.isAutoPickupEnabled();

        });
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void init(CallbackInfo ci) {


        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent( c->{

            System.out.println("is auto pickup enabled: " + c.isAutoPickupEnabled());



            int leftOffset = 0;
            int heightOffset = 0;
            enableAutoPickupButton = new ImageButton(
                    this.leftPos + 104 + 25, this.height / 2 - 22,
                     20, 18,
                     20, 0,
                    18,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {

                        PacketHandler.sendToServer(new EnableAutoPickupKeyPressed_C2SPacket());

                    },
                    Component.literal("Enable auto pickup")
            );

            disableAutoPickupButton = new ImageButton(
                    this.leftPos + 104 + 25, this.height / 2 - 22,
                     20, 18,
                     0, 0,
                    18,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {


                        PacketHandler.sendToServer(new DisableAutoPickupKeyPressed_C2SPacket());

                    },
                    Component.literal("Enable auto pickup")
            );

            openAutoPickupFilterScreenButton = new ImageButton(
                    this.leftPos + 104 + 25 + 20 , this.height / 2 - 22,
                     20, 18,
                     40, 0,
                    18,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {
//                        this.minecraft.setScreen(new AdvancedPickupSettingsScreen(Component.literal("")));
                    },
                    Component.literal("Advanced Item pickup settings")
            );

            addRenderableWidget(enableAutoPickupButton);
            addRenderableWidget(disableAutoPickupButton);
            addRenderableWidget(openAutoPickupFilterScreenButton);

        });

    }

    @Inject(method = "lambda$init$0(Lnet/minecraft/client/gui/components/Button;)V", at = @At("RETURN"))
    protected void updateGuiSize(CallbackInfo ci) {
        if(enableAutoPickupButton == null){
            return;
        }

        System.out.println(this.width);

        enableAutoPickupButton.setPosition(this.leftPos + 104 + 25, this.height / 2 - 22);
        disableAutoPickupButton.setPosition(this.leftPos + 104 + 25, this.height / 2 - 22);
        openAutoPickupFilterScreenButton.setPosition(this.leftPos + 104 + 25 + 20 , this.height / 2 - 22);
    }

}
