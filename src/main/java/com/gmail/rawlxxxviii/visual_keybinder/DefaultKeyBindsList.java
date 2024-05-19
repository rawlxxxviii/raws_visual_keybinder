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
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultKeyBindsList extends ContainerObjectSelectionList<DefaultKeyBindsList.Entry> {
    private final AlternativeKeybindScreen parentScreen;
    protected final Options options;


    public DefaultKeyBindsList(AlternativeKeybindScreen parentScreen, Minecraft minecraft, Options options, int left, int top, int width, int height) {
        super(minecraft, width, height, top, height + top, 20);

        this.parentScreen = parentScreen;
        this.options = options;

        this.setRenderTopAndBottom(false);
        this.setRenderBackground(false);
        this.x0 = left;
        this.x1 = width + this.x0;


        buildEntries();

    }

    private void buildEntries(){
        clearEntries();

        KeyMapping[] keyMappings = ArrayUtils.clone(options.keyMappings);
        Arrays.sort(keyMappings);

        String c = null;
        for (int i = 0; i < keyMappings.length; i++) {
            var keyMapping = keyMappings[i];
            String category = keyMapping.getCategory();
            if (!category.equals(c)) {
                if(c != null){
                    this.addEntry(new EmptyEntry());
                }
                c = category;
                this.addEntry(new CategoryEntry(Component.translatable(category)));
            }
            Component component = Component.translatable(keyMapping.getName());
            this.addEntry(new KeyEntry(keyMapping, component, KeyUtil.hasConflict(keyMappings, i),KeyUtil.hasDefaultConflict(keyMappings, keyMapping)));
        }
        this.addEntry(new EmptyEntry());

    }


    @Override
    protected void renderBackground(PoseStack poseStack) {

        fillGradient(poseStack,
                getLeft(),
                getTop(),
                getRight(),
                getBottom(),
                AlternativeKeybindScreen.LIST_BACKGROUND_COLOR,
                AlternativeKeybindScreen.LIST_BACKGROUND_COLOR_2
                );
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    public void onBindingsUpdated(){
        buildEntries();
        setScrollAmount(getScrollAmount());
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width + this.x0 - 6;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class Entry extends ContainerObjectSelectionList.Entry<DefaultKeyBindsList.Entry> {
    }


    @OnlyIn(Dist.CLIENT)
    public class CategoryEntry extends DefaultKeyBindsList.Entry {
        final Component name;

        public CategoryEntry(Component p_193886_) {
            this.name = p_193886_;
        }

        @Override
        public void render(@NotNull PoseStack poseStack, int p_193889_, int p_193890_, int p_193891_, int p_193892_, int p_193893_, int p_193894_, int p_193895_, boolean p_193896_, float p_193897_) {
            enableScissor(getLeft(),getTop(),getRight(), getBottom());
            minecraft.font.draw(poseStack, this.name, getLeft() + 5 , p_193890_ + p_193893_ - 10, AlternativeKeybindScreen.CATEGORY_COLOR);
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
    public class EmptyEntry extends DefaultKeyBindsList.Entry {

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
    public class KeyEntry extends DefaultKeyBindsList.Entry {
        private final KeyMapping key;
        private final Component name;
        private final Button changeButton;
        private final Button resetButton;
        private final boolean isConflicting;
        private final boolean isDefaultConflicting;

        KeyEntry(final KeyMapping p_193916_, final Component p_193917_, boolean isConflicting, boolean isDefaultConflicting) {
            this.key = p_193916_;
            this.name = p_193917_;
            this.isConflicting = isConflicting;
            this.isDefaultConflicting = isDefaultConflicting;
            this.changeButton = new Button(0, 0, 100 , 20, p_193917_,
                (p_193939_) -> {
                    DefaultKeyBindsList.this.parentScreen.setKeyMappingToChange(p_193916_);
                }
            );
            this.resetButton = new Button(0, 0, 90, 20,
                    Component.literal("(").append(key.getDefaultKey().getDisplayName().getString()).append(")"),
                    (p_193935_) -> {
                        this.key.setToDefault();
                        options.setKey(p_193916_, p_193916_.getDefaultKey());
                        KeyMapping.resetMapping();
                        DefaultKeyBindsList.this.parentScreen.onBindingsChanged();
                    }
            );
        }

        public boolean isConflicting() {
            return isConflicting;
        }

        public Button getChangeButton() {
            return changeButton;
        }

        public Button getResetButton() {
            return resetButton;
        }


        @Override
        public void render(PoseStack poseStack, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
            enableScissor(getLeft(),getTop(),getRight(), getBottom());


            minecraft.font.draw(poseStack, this.name, getLeft() + 5, p_193925_ + p_193928_ - 10, 16777215);

            this.changeButton.x = p_193926_ + (int)((float)getWidth() * .43F);
            this.changeButton.y = p_193925_;
            this.changeButton.setWidth( Math.max(40, Math.min(120, (int) ((float)getWidth() *.27F) )) );
            this.changeButton.setMessage(parentScreen.getKeyMappingToChange() == key ? Component.literal("> ... <") : this.key.getTranslatedKeyMessage());
            this.changeButton.setFGColor(key.isUnbound() ? AlternativeKeybindScreen.UNBOUND_COLOR : isConflicting ? AlternativeKeybindScreen.CONFLICT_COLOR:16777215);
            this.changeButton.render(poseStack, p_193929_, p_193930_, p_193932_);

            this.resetButton.x = this.changeButton.x + this.changeButton.getWidth() + 2;
            this.resetButton.setWidth( Math.max(40, Math.min(120, (int) ((float)getWidth() *.23F) )) );
            this.resetButton.y = p_193925_;
            this.resetButton.active = !this.key.isDefault();
            this.resetButton.setFGColor( key.isDefault() ? Color.gray.getRGB() : isDefaultConflicting ? AlternativeKeybindScreen.CONFLICT_COLOR:AlternativeKeybindScreen.RESET_COLOR);
            this.resetButton.render(poseStack, p_193929_, p_193930_, p_193932_);

            disableScissor();

        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        @Override
        public boolean mouseClicked(double p_193919_, double p_193920_, int p_193921_) {
            if (this.changeButton.mouseClicked(p_193919_, p_193920_, p_193921_)) {
                return true;
            } else {
                return this.resetButton.mouseClicked(p_193919_, p_193920_, p_193921_);
            }
        }

        @Override
        public boolean mouseReleased(double p_193941_, double p_193942_, int p_193943_) {
            return this.changeButton.mouseReleased(p_193941_, p_193942_, p_193943_) || this.resetButton.mouseReleased(p_193941_, p_193942_, p_193943_);
        }
    }
}
