package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.List;

public class KeyButton extends ImageButton {

    private final InputConstants.Key key;
    private final boolean hasConflict;
    private List<KeyMapping> keyMappings;
    private boolean selected;
    private final AlternativeKeybindScreen parentScreen;

    public KeyButton(AlternativeKeybindScreen parentScreen, InputConstants.Key key, List<KeyMapping> keyMappings, int x, int y, OnPress onPress, OnTooltip onTooltip) {

        super (
            x, y,
            16, 16,
            0, 0,
            16,
            new ResourceLocation(VisualKeybinderMod.MODID,"textures/gui/gui.png"),
            512, 512,
            onPress,onTooltip,
            Component.empty()
        );

//        super(x, y, 16, 18, key.getDisplayName(), onPress, onTooltip);

        this.parentScreen = parentScreen;

        this.key = key;
        this.keyMappings = keyMappings;

        hasConflict = KeyUtil.hasConflict(keyMappings);

    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void renderButton(PoseStack p_94282_, int p_94283_, int p_94284_, float p_94285_) {

        super.renderButton(p_94282_,p_94283_,p_94284_,p_94285_);

        renderOverlay(p_94282_,p_94283_,p_94284_,p_94285_);
    }

    private void renderOverlay(PoseStack poseStack, int p_94283_, int p_94284_, float p_94285_){

//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//
//        RenderSystem.enableDepthTest();
        blit(poseStack,
                this.x, this.y,

                keyMappings.isEmpty() ? 48 : hasConflict ? 16 : 32,
                (this.isHoveredOrFocused()? 16 : 0),

                this.width, this.height,
                512,512
            );

        if(this.parentScreen.getDetailsList()!=null && this.parentScreen.getDetailsList().getSelectedKey().getValue() == key.getValue()){

            blit(poseStack,
                    this.x, this.y,
                    64,0,
                    this.width, this.height,
                    512,512
                );
        }

        drawCenteredString(poseStack, parentScreen.getMinecraft().font,
                key.getDisplayName(),
                this.x + this.width / 2,
                this.y + (this.height - 8) / 2,
                Color.WHITE.getRGB()
        );

    }


//    public interface OnKeyButtonPress {
//        void OnKeyButtonPress(KeyButton p_93751_);
//    }

}
