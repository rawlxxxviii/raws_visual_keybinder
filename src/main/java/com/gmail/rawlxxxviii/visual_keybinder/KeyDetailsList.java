package com.gmail.rawlxxxviii.visual_keybinder;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class KeyDetailsList extends ContainerObjectSelectionList<KeyDetailsList.Entry> {


    private final AlternativeKeybindScreen parentScreen;
    private final KeyboardLayoutKey selectedKey;

    public KeyDetailsList(AlternativeKeybindScreen parentScreen, Minecraft minecraft, KeyboardLayoutKey selectedKey, int left, int top, int width, int height) {

        super(minecraft, width, height, top, height + top, 20);

        this.setRenderTopAndBottom(false);
        this.setRenderBackground(false);
        this.height = height;

        this.x0 = left;
        this.x1 = width + this.x0;

        this.parentScreen = parentScreen;
        this.selectedKey = selectedKey;

        buildEndtries();

    }


    private void buildEndtries(){
        clearEntries();

        var keyMappings = parentScreen.getKeyMappings(selectedKey);


        if(keyMappings.isEmpty()){
            this.addEntry(new EmptyEntry());
            this.addEntry(new TitleEntry(Component.literal("- Unused -"), Color.DARK_GRAY.getRGB()));
        }

        String c = null;
        for (int i = 0; i < keyMappings.size(); i++) {
            var keyMapping = keyMappings.get(i);


            String category = keyMapping.getCategory();
            if (!category.equals(c)) {
                c = category;
                this.addEntry(new TitleEntry(Component.translatable(category),AlternativeKeybindScreen.CATEGORY_COLOR));
            }

            addEntry( new KeyEntry( keyMapping, KeyUtil.hasConflict(keyMappings, keyMapping, i)) );
            addEntry( new KeyInfoEntry( keyMapping, KeyUtil.hasConflict(keyMappings, keyMapping, i)) );
        }
    }

    public void onBindingsUpdated(){
        buildEndtries();
        setScrollAmount(getScrollAmount());
    }


    @Override
    public int getRowWidth() {
        return width;
    }

    public KeyboardLayoutKey getSelectedKey() {
        return selectedKey;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width + this.x0;
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
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
        return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
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


    @OnlyIn(Dist.CLIENT)
    public abstract static class Entry extends ContainerObjectSelectionList.Entry<KeyDetailsList.Entry> {
    }

    @OnlyIn(Dist.CLIENT)
    public class TitleEntry extends KeyDetailsList.Entry {
        final Component name;
        private final int color;

        public TitleEntry(Component p_193886_, int color) {
            this.name = p_193886_;
            this.color = color;
        }

        @Override
        public void render(PoseStack p_193888_, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {
            enableScissor(getLeft(),getTop(),getRight(), getBottom());
            minecraft.font.draw(p_193888_, this.name, getLeft()  , p_193890_ + p_193893_ - 4, color);
            disableScissor();
        }

        @Override
        public List<? extends GuiEventListener> children() {
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
        public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
            return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
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

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class EmptyEntry extends KeyDetailsList.Entry {

        public EmptyEntry() {
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }

        @Override
        public void render(PoseStack p_193888_, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {

        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class KeyEntry extends KeyDetailsList.Entry {
        private final KeyMapping key;
        private final boolean isConflicting;

        KeyEntry(final KeyMapping key, boolean isConflicting) {
            this.key = key;
            this.isConflicting = isConflicting;
        }

        public void render(PoseStack poseStack, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            enableScissor(getLeft(),getTop(),getRight(), getBottom());

            minecraft.font.draw(poseStack, Component.translatable(key.getName()), getLeft(), (float)(p_193925_ + p_193928_ / 2), 16777215);
            if(key.getKeyConflictContext() == KeyConflictContext.GUI){
                minecraft.font.draw(poseStack, Component.literal("In GUI"), getRight()-75, (float)(p_193925_ + p_193928_ / 2 ), Color.GRAY.getRGB());
            } else if (key.getKeyConflictContext() == KeyConflictContext.IN_GAME) {
                minecraft.font.draw(poseStack, Component.literal("In game"), getRight()-75, (float)(p_193925_ + p_193928_ / 2 ), Color.GRAY.getRGB());
            }else{
                minecraft.font.draw(poseStack, Component.literal("In game and GUI"), getRight()-105, (float)(p_193925_ + p_193928_ / 2 ), Color.GRAY.getRGB());
            }

            disableScissor();
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }


        @Override
        public List<? extends GuiEventListener> children() {
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
        public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
            return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
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

    @OnlyIn(Dist.CLIENT)
    public class KeyInfoEntry extends KeyDetailsList.Entry {
        private final KeyMapping key;
        private final Button changeButton;
        private final Button resetButton;
        private final boolean isConflicting;


        KeyInfoEntry(final KeyMapping key, boolean isConflicting) {
            this.key = key;
            this.isConflicting = isConflicting;
            this.changeButton = new Button(
                    0, 0,
                    110 , 20,
                Component.literal("Edit"), (p_193939_) -> {
                    KeyDetailsList.this.parentScreen.setKeyMappingToChange(key);
                });

            this.resetButton = new Button(
                0, 0,
                80, 20,
                Component.literal("(").append(key.getDefaultKey().getDisplayName().getString()).append(")"),
                (p_193935_) -> {
                    this.key.setToDefault();
                    minecraft.options.setKey(key, key.getDefaultKey());
                    KeyMapping.resetMapping();
                    parentScreen.onBindingsChanged();
                });
        }

        public Button getResetButton() {
            return resetButton;
        }

        public Button getChangeButton() {
            return changeButton;
        }

        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        public void render(PoseStack poseStack, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            enableScissor(getLeft(),getTop(),getRight(), getBottom());


            this.resetButton.x = getLeft() + 10 + 110 + 5;
            this.resetButton.y = p_193925_;
            this.resetButton.active = !this.key.isDefault();
            this.resetButton.setFGColor( key.isDefault() ? Color.gray.getRGB() : KeyUtil.hasDefaultConflict(parentScreen.getAllKeyMappings(), key) ? AlternativeKeybindScreen.CONFLICT_COLOR:AlternativeKeybindScreen.RESET_COLOR);
            this.resetButton.render(poseStack, p_193929_, p_193930_, p_193932_);


            this.changeButton.x = getLeft() + 10;
            this.changeButton.y = p_193925_;
            this.changeButton.setFGColor(isConflicting ? AlternativeKeybindScreen.CONFLICT_COLOR:Color.white.getRGB());
            this.changeButton.setMessage(parentScreen.getKeyMappingToChange() == key ? Component.literal("> ... <") : this.key.getTranslatedKeyMessage());
            this.changeButton.render(poseStack, p_193929_, p_193930_, p_193932_);

            disableScissor();
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
        public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_) {
            return super.mouseScrolled(p_94686_, p_94687_, p_94688_);
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



}
