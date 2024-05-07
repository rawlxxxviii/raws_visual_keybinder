package com.gmail.rawlxxxviii.advanced_item_pickup.screen;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.advanced_pickup.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.vicinity_pickup.VicinityPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.client.KeyBinding;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.VicinityContainer;
import com.gmail.rawlxxxviii.advanced_item_pickup.menu.VicinityPickupMenu;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.ClearNameFiltersOfType_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.PickUpAllKeyDown_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.RemoveNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.UpdateNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.AdvancedPickupSettingsMenu;
import com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu.MySelectionList;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.ItemUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class VicinityPickupScreen extends AbstractContainerScreen<VicinityPickupMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
                    AdvancedItemPickupMod.MODID,
                    "textures/gui/vicinity_pickup_gui.png");



    public VicinityPickupScreen(VicinityPickupMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init(){

        this.imageHeight = 222;

        super.init();


    }


    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int buttonNumber) {
        return super.mouseClicked(p_97748_, p_97749_, buttonNumber);
    }

    @Override
    protected void containerTick() {
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int p_97809_, int p_97810_) {

        drawCenteredString(poseStack,font,
                Component.literal("Vicinity"),
                this.imageWidth / 2,
                8,
                new Color(250, 250, 250).getRGB()
        );

    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        System.out.println("keyPressed");
        if(KeyBinding.OPEN_VICINITY_PICKUP_KEY.getKey().getValue() == pKeyCode){
            this.onClose();
            return true;
        }
        if (KeyBinding.PICKUP_ALL_KEY.getKey().getValue() == pKeyCode) {
            PacketHandler.sendToServer(new PickUpAllKeyDown_C2SPacket());
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight, 512, 512);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {

        this.renderBackground(poseStack);

        super.render(poseStack, mouseX, mouseY, partialTicks);



        var item = this.hoveredSlot;
        if(item != null && !Registry.ITEM.getKey(item.getItem().getItem()).equals(new ResourceLocation("minecraft:air"))){
            renderTooltip(poseStack, new ItemStack(item.getItem().getItem()), mouseX,mouseY);
        }

    }

    @Override
    public Optional<GuiEventListener> getChildAt(double p_94730_, double p_94731_) {
        return super.getChildAt(p_94730_, p_94731_);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double value) {

        var min = 0;
        var max = 10;
        this.menu.setScrollRowPos(
                Math.max(min, Math.min(max, this.menu.getScrollRowPos() - (int)value))
        );

        return super.mouseScrolled(p_94686_, p_94687_, value);
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        return super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    @Override
    public boolean charTyped(char p_94683_, int p_94684_) {
        return super.charTyped(p_94683_, p_94684_);
    }

    @Override
    public void setInitialFocus(@Nullable GuiEventListener p_94719_) {
        super.setInitialFocus(p_94719_);
    }

    @Override
    public void magicalSpecialHackyFocus(@Nullable GuiEventListener p_94726_) {
        super.magicalSpecialHackyFocus(p_94726_);
    }

    @Override
    public boolean changeFocus(boolean p_94728_) {
        return super.changeFocus(p_94728_);
    }
}
