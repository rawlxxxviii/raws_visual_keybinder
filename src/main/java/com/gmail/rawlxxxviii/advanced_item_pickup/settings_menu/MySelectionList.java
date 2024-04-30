package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySelectionList extends AbstractSelectionList<MySelectionList.Entry> {


    AdvancedPickupSettingsScreen parentList;

    public MySelectionList(
            Minecraft minecraft,
            int width,
            int height,
            int top,
            int left,
            int itemHeight,
            List<ItemNameFilter> itemNameFilters,
            AdvancedPickupSettingsScreen parentListScreen
    ) {

        super(minecraft, width, height, top, height + top, itemHeight);

        this.height = height;

        this.x0 = left;
        this.x1 = width + this.x0;

        this.parentList = parentListScreen;

        int columns = 5;

        List<List<ItemNameFilter>> rows = new ArrayList<>();

        for (int i = 0; i < itemNameFilters.size(); i++) {
            rows.add(itemNameFilters.stream().skip((long) i * columns).limit(columns).toList());
        }

        rows.forEach(item -> {
            addEntry(new ItemEntry(this.parentList,item));
        });
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width + this.x0;
    }



    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

    @Override
    public void render(PoseStack p_93447_, int p_93448_, int p_93449_, float p_93450_) {
        super.render(p_93447_, p_93448_, p_93449_, p_93450_);
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
    public boolean isActive() {
        return super.isActive();
    }

    @OnlyIn(Dist.CLIENT)
    public abstract static class Entry extends AbstractSelectionList.Entry<MySelectionList.Entry> {
    }

    @OnlyIn(Dist.CLIENT)
    public class ItemEntry extends MySelectionList.Entry {

        private final List<ItemNameFilter> columns;
        AdvancedPickupSettingsScreen parentList;

        ItemEntry(AdvancedPickupSettingsScreen parentList,List<ItemNameFilter> columns){
            this.parentList = parentList;
            this.columns = columns;
        }

        @Override
        public void mouseMoved(double p_94758_, double p_94759_) {
            super.mouseMoved(p_94758_, p_94759_);
        }

//        @Override
//        public boolean mouseClicked(double p_94737_, double p_94738_, int buttonNumber) {
////            if(buttonNumber == 0)//left
////            {
////                this.parentList.updateNameFilter(name, ItemFilterType.ALWAYS);
////                return true;
////            }
////            else if(buttonNumber == 1)//left
////            {
////                this.parentList.updateNameFilter(name, ItemFilterType.NEVER);
////                return true;
////            }
////            else if(buttonNumber == 2)//left
////            {
////                this.parentList.removeNameFilter(name);
////                return true;
////            }
////
////            return super.mouseClicked(p_94737_, p_94738_, buttonNumber);
//        }

        @Override
        public boolean mouseReleased(double p_94753_, double p_94754_, int p_94755_) {
            return super.mouseReleased(p_94753_, p_94754_, p_94755_);
        }

        @Override
        public boolean mouseDragged(double p_94740_, double p_94741_, int p_94742_, double p_94743_, double p_94744_) {
            return super.mouseDragged(p_94740_, p_94741_, p_94742_, p_94743_, p_94744_);
        }

        @Override
        public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_) {
            return super.mouseScrolled(p_94734_, p_94735_, p_94736_);
        }

        @Override
        public boolean keyPressed(int p_94745_, int p_94746_, int p_94747_) {
            return super.keyPressed(p_94745_, p_94746_, p_94747_);
        }

        @Override
        public boolean keyReleased(int p_94750_, int p_94751_, int p_94752_) {
            return super.keyReleased(p_94750_, p_94751_, p_94752_);
        }

        @Override
        public boolean charTyped(char p_94732_, int p_94733_) {
            return super.charTyped(p_94732_, p_94733_);
        }

        @Override
        public boolean changeFocus(boolean p_94756_) {
            return super.changeFocus(p_94756_);
        }

        @Override
        public void render(PoseStack poseStack, int p_93524_, int p_93525_, int p_93526_, int p_93527_, int p_93528_, int p_93529_, int p_93530_, boolean p_93531_, float p_93532_) {

            this.columns.forEach(item->{
                Minecraft.getInstance().font.draw(poseStack, item.getResourceLocation().toString(), p_93526_, (float)(p_93525_ + p_93528_ / 2 - 9 / 2), 16777215);
            });

        }


//        private void renderItem_TEST(ItemStack itemStack, int x, int y, String text){
//
//            PoseStack posestack = RenderSystem.getModelViewStack();
//            posestack.translate(0.0D, 0.0D, 32.0D);
//            RenderSystem.applyModelViewMatrix();
//            this.setBlitOffset(200);
//            this.itemRenderer.blitOffset = 200.0F;
//            var font = net.minecraftforge.client.extensions.common.IClientItemExtensions.of(itemStack).getFont(itemStack, net.minecraftforge.client.extensions.common.IClientItemExtensions.FontContext.ITEM_COUNT);
//            if (font == null) font = this.font;
//            this.itemRenderer.renderAndDecorateItem(itemStack, x, y);
////        this.itemRenderer.renderGuiItemDecorations(font, itemStack, x, y - (this.draggingItem.isEmpty() ? 0 : 8), text);
//            this.setBlitOffset(0);
//            this.itemRenderer.blitOffset = 0.0F;
//        }

    }
}
