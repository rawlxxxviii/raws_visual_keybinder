package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.util.KeyUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;


public class KeyButton extends ImageButton {

    private static final WidgetSprites KEY_BUTTON_WIDGET_SPRITES = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID, "key_button"), ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID, "key_button_highlighted"));
    private static final WidgetSprites KEY_BUTTON_WIDGET_SPRITES_WIDE = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID, "key_button_wide"), ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID, "key_button_wide_highlighted"));
    private final KeyboardLayoutKey keyboardLayoutKey;
    private final AlternativeKeybindScreen parentScreen;
    private final KeyBoardLayout keyBoardLayout;

    public KeyButton(AlternativeKeybindScreen parentScreen, KeyBoardLayout keyBoardLayout , KeyboardLayoutKey keyboardLayoutKey, int x, int y, net.minecraft.client.gui.components.Button.OnPress onPress) {

        super(
                x, y,
                keyboardLayoutKey.isWide() ? 60 : 16,
                16,
                keyboardLayoutKey.isWide() ? KEY_BUTTON_WIDGET_SPRITES_WIDE : KEY_BUTTON_WIDGET_SPRITES,
                onPress,
                keyboardLayoutKey.getKey().getDisplayName()
        );


        this.keyBoardLayout = keyBoardLayout;
        this.parentScreen = parentScreen;
        this.keyboardLayoutKey = keyboardLayoutKey;

    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_93660_) {
        float scale = getScale();

        this.isHovered = checkIsMouseOver(mouseX, mouseY); // Fixes hover hitboxes!

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(getDiffX(), getDiffY(), 0.0F); // Fixes tooltip overlap!
        guiGraphics.pose().scale(scale, scale, 0.0F);

        super.renderWidget(guiGraphics, mouseX, mouseY, p_93660_);
        renderOverlay(guiGraphics,mouseX,mouseY,p_93660_);

        guiGraphics.pose().popPose();
    }

    private float getScale(){

        float minScale = .3f;
        float maxScale = 1.2f;

        float scaleX =
                Math.max(
                        minScale,
                        Math.min(
                                maxScale,
                                (float) parentScreen.getLayoutWidth() / (float) keyBoardLayout.getWidth()
                        )
                );

        float scaleY =
                Math.max(
                        minScale,
                        Math.min(
                                maxScale,
                                (float) parentScreen.getLayoutHeight() / (float) keyBoardLayout.getHeight()
                        )
                );
        return   Math.min(scaleX,scaleY);
    }

    private float getDiffX(){
        float scale = getScale();

        var centerXOrigin = parentScreen.getLayoutLeft() + parentScreen.getLayoutWidth() / 2;
        var centerXAfterScale = centerXOrigin * scale;
        return centerXOrigin - centerXAfterScale;
    }

    private float getDiffY(){

        float scale = getScale();


        var centerYOrigin = parentScreen.getLayoutTop() + parentScreen.getLayoutHeight() / 2;
        var centerYAfterScale = centerYOrigin * scale;
        return centerYOrigin - centerYAfterScale;
    }

    private boolean checkIsMouseOver(double mouseX, double mouseY){

        float scale = getScale();
        var diffX = getDiffX();
        var diffY = getDiffY();

        return
                mouseX >= this.getX() * scale + diffX
                        && mouseY >= this.getY() * scale + diffY
                        && mouseX < (this.getX() + this.width) * scale + diffX
                        && mouseY < (this.getY() + this.height) * scale + diffY
                ;
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return this.active && this.visible && checkIsMouseOver(mouseX,mouseY);
    }

    public KeyMapping[] getKeymappings(){
        return parentScreen.getKeyMappings(keyboardLayoutKey);
    }

    public boolean isEmpty(){
        return getKeymappings().length < 1;
    }

    public boolean hasConflict(){
        return KeyUtil.hasConflict(getKeymappings());
    }

    private void renderOverlay(GuiGraphics guiGraphics, int p_94283_, int p_94284_, float p_94285_){

        var isEmpty = isEmpty();
        var hasConflict = hasConflict();

        var canvasX = keyboardLayoutKey.isWide()?120:0;

//        //usage
        guiGraphics.blit(
                ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID,"textures/gui/gui.png"),
                getX(), getY(),

                isEmpty ? canvasX + 3*width : hasConflict ? canvasX + width : canvasX + 2*width,
                (this.isHoveredOrFocused()? AlternativeKeybindScreen.KEY_BUTTON_HEIGHT : 0),

                this.width, this.height,
                512,512
            );

        // selected
        if(this.parentScreen.getDetailsList()!=null && this.parentScreen.getDetailsList().getSelectedKey().getKey().getValue() == keyboardLayoutKey.getKey().getValue()){
            guiGraphics.blit(
                    ResourceLocation.fromNamespaceAndPath(VisualKeybinderMod.MODID,"textures/gui/gui.png"),
                    getX(), getY(),
                    canvasX + 4*width,0,
                    this.width, this.height,
                    512,512
                );
        }

        // key
        guiGraphics.drawCenteredString(
                parentScreen.getMinecraft().font,
                keyboardLayoutKey.getKey().getDisplayName(),
                getX() + this.width / 2,
                getY() + (this.height - 8) / 2,
                isEmpty ? Color.gray.getRGB() : hasConflict? AlternativeKeybindScreen.CONFLICT_COLOR : Color.WHITE.getRGB()
        );
    }


}
