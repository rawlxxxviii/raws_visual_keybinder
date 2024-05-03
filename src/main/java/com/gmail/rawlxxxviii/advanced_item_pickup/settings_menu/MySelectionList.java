package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.ListUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class MySelectionList extends AbstractSelectionList<MySelectionList.Entry> {

    AdvancedPickupSettingsScreen parentList;

    public MySelectionList(
            Minecraft minecraft,
            int width,
            int height,
            int columnCount,
            int top,
            int left,
            int itemHeight,
            List<ItemNameFilter> itemNameFilters,
            AdvancedPickupSettingsScreen parentListScreen
    ) {

        super(minecraft, width, height, top, height + top, itemHeight);

        this.setRenderTopAndBottom(false);
        this.setRenderBackground(false); // own implementation in this renderBackground class

        this.height = height;

        this.x0 = left;
        this.x1 = width + this.x0;

        this.parentList = parentListScreen;

        this.clearEntries();
        List<List<ItemNameFilter>> rows = ListUtils.splitList(itemNameFilters, columnCount);

        for (int i = 0; i < rows.size(); i++) {
            addEntry(
                    new ItemEntry(
                            this.parentList,
                            this,
                            rows.get(i),
                            i,
                            itemHeight)
            );
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width + this.x0;
    }



    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {
    }

    @Override
    public void renderBackground(PoseStack p_93447_) {
        super.renderBackground(p_93447_);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);


        RenderSystem.setShaderTexture(0, new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/list_background.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex((double)this.x0, (double)this.y1, 0.0D).uv((float)this.x0 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
        bufferbuilder.vertex((double)this.x1, (double)this.y1, 0.0D).uv((float)this.x1 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
        bufferbuilder.vertex((double)this.x1, (double)this.y0, 0.0D).uv((float)this.x1 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
        bufferbuilder.vertex((double)this.x0, (double)this.y0, 0.0D).uv((float)this.x0 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
        tesselator.end();


    }

    public void renderTopAndBottom(PoseStack p_93447_) {


        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);


        RenderSystem.setShaderTexture(0, new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/list_cover_top.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);


        int coverHeight = 22;
        var width = x1 - x0;

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder // bot left
                .vertex( this.x0, this.y0 , 250.0D)
                .uv((float)0, (float)coverHeight / 256.0F)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // bot right
                .vertex((double)this.x1, (double)this.y0, 250.0D)
                .uv((float)width / 256.0F, (float)coverHeight / 256.0F)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // top right
                .vertex((double)this.x1, (double)this.y0 - coverHeight, 250.0D)
                .uv((float)width / 256.0F, (float)0)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // top left
                .vertex((double)this.x0, (double)this.y0 - coverHeight, 250.0D)
                .uv(0, 0)
                .color(255, 255, 255, 255)
                .endVertex();
        tesselator.end();


        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder // bot left
                .vertex( this.x0, this.y1 + coverHeight , 250.0D)
                .uv((float)0, (float)coverHeight / 256.0F)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // bot right
                .vertex((double)this.x1, (double)this.y1 + coverHeight, 250.0D)
                .uv((float)width / 256.0F, (float)coverHeight / 256.0F)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // top right
                .vertex((double)this.x1, (double)this.y1, 250.0D)
                .uv((float)width / 256.0F, (float)0)
                .color(255, 255, 255, 255)
                .endVertex();
        bufferbuilder // top left
                .vertex((double)this.x0, (double)this.y1 , 250.0D)
                .uv(0, 0)
                .color(255, 255, 255, 255)
                .endVertex();
        tesselator.end();

    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int p_93449_, float p_93450_) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, p_93449_, p_93450_);

        renderTopAndBottom(poseStack);

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
        AdvancedPickupSettingsScreen parentScreen;
        private final int rowIndex;
        private final int itemHeight;
        private final int itemWidth;
        private final MySelectionList selectionList;

        ItemEntry(AdvancedPickupSettingsScreen parentScreen, MySelectionList selectionList,List<ItemNameFilter> columns, int rowIndex, int itemHeight){
            this.parentScreen = parentScreen;
            this.columns = columns;
            this.rowIndex = rowIndex;
            this.itemHeight = itemHeight;
            this.itemWidth = itemHeight;
            this.selectionList = selectionList;
        }

        @Override
        public void mouseMoved(double p_94758_, double p_94759_) {
            super.mouseMoved(p_94758_, p_94759_);
        }

        @Override
        public void render(
                PoseStack poseStack,
                int rowIndex,
                int p_93525_,
                int p_93526_,
                int p_93527_,
                int p_93528_,
                int mouseX,
                int mouseY,
                boolean p_93531_,
                float p_93532_
        ) {

            for (int i = 0; i < columns.size(); i++) {
                var item2 = ForgeRegistries.ITEMS.getValue(columns.get(i).getResourceLocation());


                renderItem_TEST(
                        new ItemStack(item2),
                        getLeft() + i * itemWidth,
                        getRowTop(rowIndex)
                );

                //check mouse over
                if(
                        mouseX >= getLeft() &&
                        mouseX <= getRight() &&
                        mouseY >= getTop() &&
                        mouseY <= getBottom() &&

                        mouseX >= getLeft() + i * itemWidth &&
                        mouseX <= getLeft() + i * itemWidth + itemWidth &&
                        mouseY >= getRowTop(rowIndex) &&
                        mouseY <= getRowTop(rowIndex) + itemHeight
                ){
                    this.parentScreen.setHoveredListItem(item2);
                }
            }

        }

        private void renderItem_TEST(ItemStack itemStack, int x, int y){
            var itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderAndDecorateItem(itemStack, x, y);
        }


        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int buttonNumber) {
            return super.mouseClicked(mouseX, mouseY, buttonNumber);
        }

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


    }
}
