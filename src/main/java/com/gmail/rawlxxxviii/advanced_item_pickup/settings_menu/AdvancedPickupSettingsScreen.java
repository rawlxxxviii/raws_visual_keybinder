package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AdvancedPickupSettingsScreen extends AbstractContainerScreen<AdvancedPickupSettingsMenu> {


    public AdvancedPickupSettingsScreen(AdvancedPickupSettingsMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

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
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
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
