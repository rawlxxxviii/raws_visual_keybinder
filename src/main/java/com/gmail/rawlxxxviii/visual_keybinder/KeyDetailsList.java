package com.gmail.rawlxxxviii.visual_keybinder;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KeyDetailsList extends ContainerObjectSelectionList<KeyDetailsList.Entry> {

    private Button resetButton;
    private List<KeyMapping> keyMappings;

    private final AlternativeKeybindScreen parentScreen;
    private final InputConstants.Key selectedKey;

    public KeyDetailsList(AlternativeKeybindScreen parentScreen, Minecraft minecraft, List<KeyMapping> keyMappings, Options options, InputConstants.Key selectedKey, int left, int top, int width, int height) {

        super(minecraft, width, height, top, height + top, 20);

        this.setRenderTopAndBottom(false);
        this.setRenderBackground(false); // own implementation in this renderBackground class
        this.height = height;

        this.x0 = left;
        this.x1 = width + this.x0;

        this.parentScreen = parentScreen;
        this.selectedKey = selectedKey;

        keyMappings.forEach(item->{
            addEntry( new KeyEntry( item ) );
        });
    }


    public InputConstants.Key getSelectedKey() {
        return selectedKey;
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
    public class KeyEntry extends KeyDetailsList.Entry {
        private final KeyMapping key;
        private final Button changeButton;
        private final Button resetButton;

        KeyEntry(final KeyMapping key) {
            this.key = key;
            this.changeButton = new Button(0, 0, 75 + 20 /* Forge: Add space */, 20, Component.literal("Edit"), (p_193939_) -> {
//                KeyDetailsList.this.parentScreen.selectedKey = key;

            });

            this.resetButton = new Button(
                0, 0, 20, 20,
                Component.literal("rst " + key.getDefaultKey().getDisplayName().getString()),
                (p_193935_) -> {
                    this.key.setToDefault();
                    KeyDetailsList.this.minecraft.options.setKey(key, key.getDefaultKey());
                    KeyMapping.resetMapping();
                });
        }


        public List<? extends GuiEventListener> children() {
            return ImmutableList.of(this.changeButton, this.resetButton);
        }

        public void render(PoseStack p_193923_, int p_193924_, int p_193925_, int p_193926_, int p_193927_, int p_193928_, int p_193929_, int p_193930_, boolean p_193931_, float p_193932_) {
//            boolean flag = KeyBindsList.this.keyBindsScreen.selectedKey == this.key;

            KeyDetailsList.this.minecraft.font.draw(p_193923_, Component.translatable(key.getCategory()), getLeft(), (float)(p_193925_ + p_193928_ / 2 - 9 / 2), 16777215);
            KeyDetailsList.this.minecraft.font.draw(p_193923_, Component.translatable(key.getName()), getLeft()+60, (float)(p_193925_ + p_193928_ / 2 - 9 / 2), 16777215);
            KeyDetailsList.this.minecraft.font.draw(p_193923_, Component.translatable(key.getKeyModifier().toString()), getLeft()+90, (float)(p_193925_ + p_193928_ / 2 - 9 / 2), 16777215);
            KeyDetailsList.this.minecraft.font.draw(p_193923_, Component.translatable(key.getKeyConflictContext().toString()), getLeft()+120, (float)(p_193925_ + p_193928_ / 2 - 9 / 2), 16777215);

            this.resetButton.x = getLeft() + 160;
            this.resetButton.y = p_193925_;
            this.resetButton.active = !this.key.isDefault();
            this.resetButton.render(p_193923_, p_193929_, p_193930_, p_193932_);

            this.changeButton.x = getLeft() + 200;
            this.changeButton.y = p_193925_;
            this.changeButton.setMessage(this.key.getTranslatedKeyMessage());

//            boolean flag1 = false;
//            boolean keyCodeModifierConflict = true; // gracefully handle conflicts like SHIFT vs SHIFT+G
//            if (!this.key.isUnbound()) {
//                for(KeyMapping keymapping : KeyBindsList.this.minecraft.options.keyMappings) {
//                    if (keymapping != this.key && this.key.same(keymapping)) {
//                        flag1 = true;
//                        keyCodeModifierConflict &= keymapping.hasKeyModifierConflict(this.key);
//                    }
//                }
//            }
//
//            if (flag) {
//                this.changeButton.setMessage(Component.literal("> ").append(this.changeButton.getMessage().copy().withStyle(ChatFormatting.YELLOW)).append(" <").withStyle(ChatFormatting.YELLOW));
//            } else if (flag1) {
//                this.changeButton.setMessage(this.changeButton.getMessage().copy().withStyle(keyCodeModifierConflict ? ChatFormatting.GOLD : ChatFormatting.RED));
//            }
//
//            this.changeButton.render(p_193923_, p_193929_, p_193930_, p_193932_);
//        }

        }
//        public List<? extends NarratableEntry> narratables() {
//            return ImmutableList.of(this.changeButton, this.resetButton);
//        }
//
//        public boolean mouseClicked(double p_193919_, double p_193920_, int p_193921_) {
//            if (this.changeButton.mouseClicked(p_193919_, p_193920_, p_193921_)) {
//                return true;
//            } else {
//                return this.resetButton.mouseClicked(p_193919_, p_193920_, p_193921_);
//            }
//        }
//
//        public boolean mouseReleased(double p_193941_, double p_193942_, int p_193943_) {
//            return this.changeButton.mouseReleased(p_193941_, p_193942_, p_193943_) || this.resetButton.mouseReleased(p_193941_, p_193942_, p_193943_);
//        }


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
