package com.gmail.rawlxxxviii.visual_keybinder.ui_list;


import com.gmail.rawlxxxviii.visual_keybinder.KeyboardLayoutKey;
import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.util.KeyUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
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
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class KeyDetailsList extends ContainerObjectSelectionList<KeyDetailsList.Entry> {


    private final AlternativeKeybindScreen parentScreen;
    private final KeyboardLayoutKey selectedKey;
    protected final Options options;
    private final int left;

    public KeyDetailsList(AlternativeKeybindScreen parentScreen, Minecraft minecraft, Options options, KeyboardLayoutKey selectedKey, int left, int top, int width, int height) {

        super(minecraft, width, height, top, 20);
        this.options = options;

        this.height = height;
        this.left = left;

        this.setX(left);

        this.parentScreen = parentScreen;
        this.selectedKey = selectedKey;

        buildEndtries();

    }


    private void buildEndtries(){
        clearEntries();

        KeyMapping[] allKeyMappings = ArrayUtils.clone(options.keyMappings);

        var keyMappings = parentScreen.getKeyMappings(selectedKey);
        var hasConflicts = KeyUtil.hasConflict(keyMappings);

        this.addEntry(new DetailsListTitleEntry(hasConflicts));

        if(keyMappings.length == 0){
            this.addEntry(new TitleEntry( Component.literal("No bindings"), Color.GRAY.getRGB()));
        }

        String c = null;
        for (KeyMapping keyMapping : keyMappings) {
            String category = keyMapping.getCategory();
            if (!category.equals(c)) {
                c = category;
                this.addEntry(new TitleEntry(Component.translatable(category), AlternativeKeybindScreen.CATEGORY_COLOR));
            }

            var isConflicting = KeyUtil.hasConflict(keyMappings, keyMapping);
            var isDefaultConflicting = KeyUtil.hasDefaultConflict(allKeyMappings, keyMapping);

            addEntry(new KeyEntry(keyMapping, isConflicting));
            addEntry(new KeyInfoEntry(keyMapping, isConflicting, isDefaultConflicting));
        }
        addEntry(new EmptyEntry());
    }

    public void onBindingsUpdated(){
        buildEndtries();
        setScrollAmount(getScrollAmount());
    }

    @Override
    public int getX() {
        return this.left;
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
        public void render(GuiGraphics guiGraphics, int p_193889_, int y, int p_193891_, int p_193892_, int height, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());
            guiGraphics.drawString(minecraft.font, this.name, getX() + 5 , y + height - 4, color);
            guiGraphics.disableScissor();
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

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class DetailsListTitleEntry extends KeyDetailsList.Entry {

        private final boolean hasConflicts;

        public DetailsListTitleEntry(boolean hasConflicts) {
            this.hasConflicts = hasConflicts;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            guiGraphics.fill(
                    getX(),
                    getRowTop(0) - 4,
                    getRight(),
                    getRowTop(0) + itemHeight,
                    AlternativeKeybindScreen.LIST_TITLE_BACKGROUND_COLOR
            );

            guiGraphics.drawString(minecraft.font,
                    KeyDetailsList.this.getSelectedKey().getKey().getDisplayName(),
                    getX() + 5 , p_193890_ + 5,
                    hasConflicts ? AlternativeKeybindScreen.CONFLICT_COLOR :Color.white.getRGB()
            );

            guiGraphics.disableScissor();
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
        public void render(GuiGraphics p_193888_, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {

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

        public void render(GuiGraphics guiGraphics, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            guiGraphics.drawString(minecraft.font, Component.translatable(key.getName()), getX() + 5, (p_193925_ + p_193928_ / 2), 16777215);
            if(ClientConfig.displayConflictContext.get() && getWidth() > 170 ){
                if(key.getKeyConflictContext() == KeyConflictContext.GUI){
                    guiGraphics.drawString(minecraft.font, Component.literal("In GUI"), getRight()-75, p_193925_ + p_193928_ / 2 , Color.DARK_GRAY.getRGB());
                } else if (key.getKeyConflictContext() == KeyConflictContext.IN_GAME) {
                    guiGraphics.drawString(minecraft.font, Component.literal("In game"), getRight()-75, p_193925_ + p_193928_ / 2 , Color.DARK_GRAY.getRGB());
                }else{
                    guiGraphics.drawString(minecraft.font, Component.literal("In game and GUI"), getRight()-105, p_193925_ + p_193928_ / 2 , Color.DARK_GRAY.getRGB());
                }
            }

            guiGraphics.disableScissor();
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
    public class KeyInfoEntry extends KeyDetailsList.Entry {
        private final KeyMapping key;
        private final Button changeButton;
        private final Button resetButton;
        private final boolean isConflicting;
        private final boolean isDefaultConflicting;


        KeyInfoEntry(final KeyMapping key, boolean isConflicting, boolean isDefaultConflicting) {
            this.key = key;
            this.isConflicting = isConflicting;
            this.isDefaultConflicting = isDefaultConflicting;

            this.changeButton =
                new Button.Builder(
                        Component.literal("Edit"),
                        (p_193939_) -> KeyDetailsList.this.parentScreen.setKeyMappingToChange(key)
                )
                .pos(0,0)
                .size(110,20)
                .build()
            ;
            this.resetButton =
                    new Button.Builder(
                        Component.literal("(").append(key.getDefaultKey().getDisplayName().getString()).append(")"),
                        (p_193935_) -> {
                            this.key.setToDefault();
                            options.setKey(key, key.getDefaultKey());
                            KeyMapping.resetMapping();
                            parentScreen.onBindingsChanged();
                        }
                    )
                    .pos(0,0)
                    .size(80,20)
                    .build()
            ;
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

        public void render(GuiGraphics guiGraphics, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            guiGraphics.enableScissor(getX(),getY(),getRight(), getBottom());

            this.changeButton.setX(getX() + 5);
            this.changeButton.setWidth( Math.max(40, Math.min(120, (int) ((float)getWidth() *.4F) )) );
            this.changeButton.setY(p_193925_);
            this.changeButton.setFGColor(isConflicting ? AlternativeKeybindScreen.CONFLICT_COLOR:Color.white.getRGB());
            this.changeButton.setMessage(parentScreen.getKeyMappingToChange() == key ? Component.literal("> ... <") : this.key.getTranslatedKeyMessage());
            this.changeButton.render(guiGraphics, p_193929_, p_193930_, p_193932_);

            this.resetButton.setX(this.changeButton.getX() + this.changeButton.getWidth() + 3);
            this.resetButton.setWidth( Math.max(40, Math.min(100, (int) ((float)getWidth() *.35F) )) );
            this.resetButton.setY( p_193925_ );
            this.resetButton.active = !this.key.isDefault();
            this.resetButton.setFGColor( key.isDefault() ? Color.gray.getRGB() : isDefaultConflicting ? AlternativeKeybindScreen.CONFLICT_COLOR:AlternativeKeybindScreen.RESET_COLOR);
            this.resetButton.render(guiGraphics, p_193929_, p_193930_, p_193932_);



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
