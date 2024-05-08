package com.gmail.rawlxxxviii.advanced_item_pickup.screen;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.client.KeyBinding;
import com.gmail.rawlxxxviii.advanced_item_pickup.menu.VicinityPickupMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class VicinityPickupScreen extends AbstractContainerScreen<VicinityPickupMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
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

    public boolean canScroll() {
        return this.menu.getMaxScroll()>0;
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
                7,
                new Color(250, 250, 250).getRGB()
        );
//        this.font.draw(p_98616_, creativemodetab.getDisplayName(), 8.0F, 6.0F, creativemodetab.getLabelColor());

        drawCenteredString(poseStack,font,
                Component.literal(String.valueOf(this.menu.getScrollRowPosition())+"/"+String.valueOf(menu.getMaxScroll())),
                this.imageWidth / 2 + 50,
                7,
                new Color(250, 250, 250).getRGB()
        );

    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(KeyBinding.OPEN_VICINITY_PICKUP_KEY.getKey().getValue() == pKeyCode){
            this.onClose();
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
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight, 512, 512);


        //scrollbar
        int scrollbarHeight = 108;
        int scrollbarButtonHeight = 10;

        blit(pPoseStack,
                this.getGuiLeft() + imageWidth + 15,  this.getGuiTop() + 18,
                220, 0,
                10, scrollbarHeight,
                512, 512
        );


        int buttonY = 0;

        if(menu.getMaxScroll() > 0){
            double a = (double)menu.getScrollRowPosition() / (double) menu.getMaxScroll();
            double b = scrollbarHeight - scrollbarButtonHeight;
            buttonY = (int)(a * b);
        }

        this.blit(pPoseStack,
                this.getGuiLeft() + imageWidth + 15, this.getGuiTop() + 18 + buttonY,
                240 + (this.canScroll() ? 0 : 20),0,
                10,10,
                512, 512
        );
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

        var isMouseOverScrollBar = true;
        if(isMouseOverScrollBar){

            this.menu.setScrollRowPos(this.menu.getScrollRowPosition() - (int)value);

            return true;
        }

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
