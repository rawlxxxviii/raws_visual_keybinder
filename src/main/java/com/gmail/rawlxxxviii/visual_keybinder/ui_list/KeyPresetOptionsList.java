package com.gmail.rawlxxxviii.visual_keybinder.ui_list;


import com.gmail.rawlxxxviii.visual_keybinder.KeybindingPreset;
import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.screen.PresetsScreen;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class KeyPresetOptionsList extends ContainerObjectSelectionList<KeyPresetOptionsList.Entry> {


    private final PresetsScreen parentScreen;
    private final int left;
    protected final Options options;

    public KeyPresetOptionsList(PresetsScreen parentScreen, Minecraft minecraft, Options options, int left, int top, int width, int height) {

        super(minecraft, width, height, top, 26);
        this.options = options;

        this.height = height;

        this.left = left;
        this.setX(left);

        this.parentScreen = parentScreen;

        buildEntries();

    }


    private void buildEntries(){
        clearEntries();

        var presets = FileUtil.getPresets();
        if(presets == null){
            return;
        }

        for (var a : presets){
            addEntry(new PresetEntry(a, parentScreen.isPresetActive(a)));
        }

        addEntry( new EmptyEntry() );
        addEntry( new ResetAllEntry() );
        addEntry( new UnbindAllEntry() );
    }

    public void onBindingsUpdated(){
        buildEntries();
        setScrollAmount(getScrollAmount());
    }

    @Override public int getX() { return this.left; }

    @Override
    public int getRowWidth() {
        return width;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width + this.getX() - 6;
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
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }

    @Override
    public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
        return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int p_94710_, int p_94711_, int p_94712_) {
        return super.keyPressed(p_94710_, p_94711_, p_94712_);
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
    public void setFocused(@Nullable GuiEventListener p_94726_) {
        super.setFocused(true);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class Entry extends ContainerObjectSelectionList.Entry<KeyPresetOptionsList.Entry> {
    }

    @OnlyIn(Dist.CLIENT)
    public class PresetEntry extends KeyPresetOptionsList.Entry {
        private final KeybindingPreset keybindingPreset;
        private final Button saveButton;
        private final Button loadButton;
        private final Button deleteButton;
        private final boolean isPresetActive;

        PresetEntry(KeybindingPreset keybindingPreset, boolean isPresetActive) {
            this.keybindingPreset = keybindingPreset;
            this.isPresetActive = isPresetActive;

            int buttonWidth = 50;

            this.saveButton = new Button.Builder(Component.literal("Save"),
                    (p_193939_) -> parentScreen.savePreset(keybindingPreset.getName()))
                    .pos(getRight() - (buttonWidth + 5) * 3 , 0)
                    .size(buttonWidth , 20)
                    .build();
            this.saveButton.active = !keybindingPreset.isReadOnly();

            this.loadButton = new Button.Builder(Component.literal("Load"),
                    (p_193939_) -> parentScreen.loadPreset(keybindingPreset.getName()))
                    .pos(getRight() - (buttonWidth + 5) * 2, 0)
                    .size(buttonWidth , 20)
                    .build();
            this.loadButton.active = !isPresetActive;


            this.deleteButton = new Button.Builder(
                    Component.literal("Delete"),
                    (p_193935_) -> parentScreen.deletePreset(keybindingPreset.getName())
                )
                .pos(getRight() - buttonWidth - 5, 0)
                .size(buttonWidth, 20)
                .build()
            ;
            this.deleteButton.active = !keybindingPreset.isReadOnly();
        }

        public Button getLoadButton() {
            return loadButton;
        }

        public Button getSaveButton() {
            return saveButton;
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.saveButton, this.loadButton, this.deleteButton);
        }

        public void render(GuiGraphics guiGraphics, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            guiGraphics.drawString(
                    parentScreen.getMinecraft().font,
                    Component.literal(this.keybindingPreset.getName()),
                    getRowLeft() + 5,
                    getRowTop(p_193924_) + 6,
                    isPresetActive ? AlternativeKeybindScreen.ACTIVE_COLOR : Color.white.getRGB());

            this.saveButton.setY(p_193925_);
            this.saveButton.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            this.loadButton.setY(p_193925_ );
            this.loadButton.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            this.deleteButton.setY(p_193925_ );
            this.deleteButton.setFGColor( this.deleteButton.active ? AlternativeKeybindScreen.DANGER_COLOR : Color.GRAY.getRGB());
            this.deleteButton.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            guiGraphics.disableScissor();
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
        public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
            return super.mouseClicked(p_94695_, p_94696_, p_94697_);
        }

        @Override
        public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
            return super.mouseReleased(p_94722_, p_94723_, p_94724_);
        }

        @Override
        public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
            return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        @Override
        public boolean keyPressed(int p_94710_, int p_94711_, int p_94712_) {
            return super.keyPressed(p_94710_, p_94711_, p_94712_);
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
        public void setFocused(@Nullable GuiEventListener p_94726_) {
            super.setFocused(true);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public class UnbindAllEntry extends KeyPresetOptionsList.Entry {
        private final Button button;

        UnbindAllEntry() {

            int buttonWidth = 50;
            this.button = new Button.Builder(
                    Component.literal("Unbind"),
                    (p_193939_) -> parentScreen.unbindAll()
            )
            .pos(getRight() - (buttonWidth + 5) * 3 , 0)
            .size(100,20)
            .build();
            this.button.active = parentScreen.hasBoundBindings();

        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.button);
        }

        public void render(GuiGraphics guiGraphics, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            guiGraphics.drawString(
                    parentScreen.getMinecraft().font,
                    Component.literal("Unbind all keybindings"),
                    getRowLeft() + 5,
                    getRowTop(p_193924_) + 6,
                    parentScreen.hasBoundBindings() ? Color.LIGHT_GRAY.getRGB() : AlternativeKeybindScreen.ACTIVE_COLOR
            );

            this.button.setY(p_193925_);
            this.button.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            guiGraphics.disableScissor();
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
        public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
            return super.mouseClicked(p_94695_, p_94696_, p_94697_);
        }

        @Override
        public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
            return super.mouseReleased(p_94722_, p_94723_, p_94724_);
        }

        @Override
        public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
            return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        @Override
        public boolean keyPressed(int p_94710_, int p_94711_, int p_94712_) {
            return super.keyPressed(p_94710_, p_94711_, p_94712_);
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
        public void setFocused(@Nullable GuiEventListener p_94726_) {
            super.setFocused(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class EmptyEntry extends KeyPresetOptionsList.Entry {

        EmptyEntry() {

        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of();
        }

        public void render(GuiGraphics poseStack, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {

        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
        public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
            return super.mouseClicked(p_94695_, p_94696_, p_94697_);
        }

        @Override
        public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
            return super.mouseReleased(p_94722_, p_94723_, p_94724_);
        }

        @Override
        public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
            return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        @Override
        public boolean keyPressed(int p_94710_, int p_94711_, int p_94712_) {
            return super.keyPressed(p_94710_, p_94711_, p_94712_);
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
        public void setFocused(@Nullable GuiEventListener p_94726_) {
            super.setFocused(true);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public class ResetAllEntry extends KeyPresetOptionsList.Entry {
        private final Button button;

        ResetAllEntry() {

            int buttonWidth = 50;
            this.button = new Button.Builder(
                    Component.literal("Reset"),
                    (p_193939_) ->parentScreen.resetAll()
            )
            .pos(getRight() - (buttonWidth + 5) * 3 , 0)
            .size(100 , 20)
            .build()
            ;

            this.button.active = parentScreen.hasNonDefaultBindings();

        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.button);
        }

        public void render(GuiGraphics guiGraphics, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            guiGraphics.drawString(
                    parentScreen.getMinecraft().font,
                    Component.literal("Reset to defaults"),
                    getRowLeft() + 5,
                    getRowTop(p_193924_) + 6,
                     parentScreen.hasNonDefaultBindings() ? Color.LIGHT_GRAY.getRGB() : AlternativeKeybindScreen.ACTIVE_COLOR
            );

            this.button.setY(p_193925_);
            this.button.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            guiGraphics.disableScissor();
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
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
        public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
            return super.mouseClicked(p_94695_, p_94696_, p_94697_);
        }

        @Override
        public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
            return super.mouseReleased(p_94722_, p_94723_, p_94724_);
        }

        @Override
        public boolean mouseDragged(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
            return super.mouseDragged(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
            return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
        }

        @Override
        public boolean keyPressed(int p_94710_, int p_94711_, int p_94712_) {
            return super.keyPressed(p_94710_, p_94711_, p_94712_);
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
        public void setFocused(@Nullable GuiEventListener p_94726_) {
            super.setFocused(true);
        }
    }



}
