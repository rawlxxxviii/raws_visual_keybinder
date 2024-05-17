package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;

public class KeyButton extends ImageButton {

    private final KeyboardLayoutKey keyboardLayoutKey;
    private final AlternativeKeybindScreen parentScreen;

    public KeyButton(AlternativeKeybindScreen parentScreen, KeyboardLayoutKey keyboardLayoutKey, int x, int y, OnPress onPress) {

        super (
            x, y,
            keyboardLayoutKey.isWide() ? AlternativeKeybindScreen.WIDE_KEY_BUTTON_WIDTH : AlternativeKeybindScreen.KEY_BUTTON_WIDTH, AlternativeKeybindScreen.KEY_BUTTON_HEIGHT,
            keyboardLayoutKey.isWide() ? 120 : 0, 0,
            AlternativeKeybindScreen.KEY_BUTTON_HEIGHT,
            new ResourceLocation(VisualKeybinderMod.MODID,"textures/gui/gui.png"),
            512, 512,
            onPress,
            keyboardLayoutKey.getKey().getDisplayName()
        );


        this.parentScreen = parentScreen;
        this.keyboardLayoutKey = keyboardLayoutKey;

    }

    @Override
    public void renderButton(PoseStack p_94282_, int p_94283_, int p_94284_, float p_94285_) {

        super.renderButton(p_94282_,p_94283_,p_94284_,p_94285_);

        renderOverlay(p_94282_,p_94283_,p_94284_,p_94285_);
    }

    public List<KeyMapping> getKeymappings(){
        return parentScreen.getKeyMappings(keyboardLayoutKey);
    }

    public boolean isEmpty(){
        return getKeymappings().isEmpty();
    }

    public boolean hasConflict(){
        return KeyUtil.hasConflict(getKeymappings());
    }

    private void renderOverlay(PoseStack poseStack, int p_94283_, int p_94284_, float p_94285_){

        var isEmpty = isEmpty();
        var hasConflict = hasConflict();

        var canvasX = keyboardLayoutKey.isWide()?120:0;

//        //usage
        blit(poseStack,
                this.x, this.y,

                isEmpty ? canvasX + 3*width : hasConflict ? canvasX + width : canvasX + 2*width,
                (this.isHoveredOrFocused()? AlternativeKeybindScreen.KEY_BUTTON_HEIGHT : 0),

                this.width, this.height,
                512,512
            );

        // selected
        if(this.parentScreen.getDetailsList()!=null && this.parentScreen.getDetailsList().getSelectedKey().getKey().getValue() == keyboardLayoutKey.getKey().getValue()){
            blit(poseStack,
                    this.x, this.y,
                    canvasX + 4*width,0,
                    this.width, this.height,
                    512,512
                );
        }

        // key
        drawCenteredString(poseStack, parentScreen.getMinecraft().font,
                keyboardLayoutKey.getKey().getDisplayName(),
                this.x + this.width / 2,
                this.y + (this.height - 8) / 2,
                isEmpty ? Color.gray.getRGB() : hasConflict? AlternativeKeybindScreen.CONFLICT_COLOR : Color.WHITE.getRGB()
        );
    }


}
