package com.gmail.rawlxxxviii.vicinity_item_pickup.client;

import com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod;
import com.gmail.rawlxxxviii.vicinity_item_pickup.capability.vicinity_pickup.VicinityPickup;
import com.gmail.rawlxxxviii.vicinity_item_pickup.menu.VicinityPickupMenu;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.c2s.PickupItemEntity_C2SPacket;
import com.gmail.rawlxxxviii.vicinity_item_pickup.util.DistanceUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VicinityPickupContainerScreen extends AbstractContainerScreen<VicinityPickupMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
                    VicinityItemPickupMod.MODID,
                    "textures/gui/vicinity_pickup_gui.png");


    private List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
    private LocalPlayer player;

    public static final int VICINITY_SLOT_ROW_COUNT = 6;
    public static final int VICINITY_SLOT_COLUMN_COUNT = 9;

    private static final int VICINITY_TOP = 20;
    private static final int VICINITY_LEFT = 8;
    private int scrollRowPos = 0;

    public VicinityPickupContainerScreen(VicinityPickupMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);

        this.player = (LocalPlayer) inventory.player;

    }

    @Override
    protected void init(){

        this.imageHeight = 228;

        super.init();


        //todo move to onupdate
        setItemEntities();

    }


    private void renderItem(ItemStack itemStack, int x, int y){

        itemRenderer.renderAndDecorateItem(itemStack, x, y);
        itemRenderer.renderGuiItemDecorations(font, itemStack, x, y);

    }

    @Override
    public boolean mouseClicked(double x, double y, int buttonNumber) {

        var vicinitySlotIndex = getHoverVicinitySlotIndex(x, y);
        if(vicinitySlotIndex > -1){
            var itemEntityIndex = vicinitySlotIndex + scrollRowPos*VICINITY_SLOT_COLUMN_COUNT;
            if(this.itemEntities.size() > itemEntityIndex){
                PacketHandler.sendToServer(new PickupItemEntity_C2SPacket( itemEntities.get(itemEntityIndex).getId() ));
                return true;
            }
        }

        return  super.mouseClicked(x, y, buttonNumber);

    }

    private boolean isInsideVicinitySlots(double x, double y){

        return
                x >= getGuiLeft() + VICINITY_LEFT && x < getGuiLeft() + VICINITY_LEFT + VICINITY_SLOT_COLUMN_COUNT * 18
                &&
                y >= getGuiTop() + VICINITY_TOP && y < getGuiTop() + VICINITY_TOP +VICINITY_SLOT_ROW_COUNT*18;

    }


    private int getHoverVicinitySlotIndex(double x, double y){
        if(!isInsideVicinitySlots(x,y)){
            return -1;
        }

        var row = (int)((y - getGuiTop() - VICINITY_TOP ) / 18);
        var column = (int)(( x - getGuiLeft() - VICINITY_LEFT ) / 18);

        return row * VICINITY_SLOT_COLUMN_COUNT + column;

    }


    @Override
    protected void containerTick() {
        setItemEntities();
    }

    @Override
    protected boolean hasClickedOutside(double x, double y, int p_97759_, int p_97760_, int p_97761_) {
        if(isInsideVicinitySlots(x, y)){
            return true;
        }

        return x < (double)p_97759_ || y < (double)p_97760_ || x >= (double)(p_97759_ + this.imageWidth) || y >= (double)(p_97760_ + this.imageHeight);
    }

    public void setItemEntities(){


        this.itemEntities = player.level.getEntitiesOfClass(
                ItemEntity.class,
                DistanceUtils.getPlayerAABB(player)
        );

        this.filterItemsByDistance();
        this.sortEntities();


        this.setScrollRowPos(this.getScrollRowPosition());
    }

    public void sortEntities(){

        this.itemEntities
//            .sort(Comparator.comparing(a -> a.getUUID().toString()));
                .sort(Comparator.comparing(ItemEntity::getAge, Collections.reverseOrder()));
    }

    public int getMaxScroll() {
        var itemCount = this.vicinityItemCount();
        if(itemCount <= VICINITY_SLOT_ROW_COUNT * VICINITY_SLOT_COLUMN_COUNT){
            return 0;
        }
        return itemCount / VICINITY_SLOT_COLUMN_COUNT + 1 - VICINITY_SLOT_ROW_COUNT;
    }

    public int vicinityItemCount(){
        return this.itemEntities.size();
    }

    public boolean canScroll() {
        return this.getMaxScroll()>0;
    }


    public void filterItemsByDistance(){

        player.getCapability(VicinityPickup.INSTANCE).ifPresent(c->{
            this.itemEntities.removeIf(x->
                    DistanceUtils.getDistance(player.getX(),x.getX(),player.getZ(),x.getZ())
                            > c.getReach()
            );
        });
    }


    @Override
    protected void renderLabels(PoseStack poseStack, int x, int y) {

        drawCenteredString(poseStack,font,
                Component.literal("Vicinity"),
                this.imageWidth / 2,
                7,
                new Color(250, 250, 250).getRGB()
        );

    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(KeyBinding.OPEN_VICINITY_PICKUP_KEY_OLD.getKey().getValue() == pKeyCode){
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight, 512, 512);


        //scrollbar
        int scrollbarHeight = 108;
        int scrollbarButtonHeight = 10;
        blit(pPoseStack,
                this.getGuiLeft() + imageWidth + 4,  this.getGuiTop() + 18,
                220 + (this.canScroll() ? 0 : 10), 0,
                10, scrollbarHeight,
                512, 512
        );

        //scrollbar button
        int buttonY = 0;
        if(getMaxScroll() > 0){
            double a = (double)getScrollRowPosition() / (double) getMaxScroll();
            double b = scrollbarHeight - scrollbarButtonHeight;
            buttonY = (int)(a * b);
        }
        this.blit(pPoseStack,
                this.getGuiLeft() + imageWidth + 4, this.getGuiTop() + 18 + buttonY,
                240 + (this.canScroll() ? 0 : 10),0,
                10,10,
                512, 512
        );
    }



    @Override
    public void render(@NotNull PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {

        this.renderBackground(poseStack);

        super.render(poseStack, mouseX, mouseY, partialTicks);


        var itemEntitiesToRender = this.itemEntities.stream()
                .skip((long) VICINITY_SLOT_COLUMN_COUNT * this.getScrollRowPosition())
                .limit(VICINITY_SLOT_ROW_COUNT*VICINITY_SLOT_COLUMN_COUNT)
                .toList();


        rowLoop:
        for (int row = 0; row < VICINITY_SLOT_ROW_COUNT; row++) {
            for (int column = 0; column < VICINITY_SLOT_COLUMN_COUNT; column++) {

                var index = row * VICINITY_SLOT_COLUMN_COUNT + column;
                if(index >= itemEntitiesToRender.size() ){
                    break rowLoop;
                }

                var itemEntity = itemEntitiesToRender.get(index);
                renderItem(
                        itemEntity.getItem(),
                        getGuiLeft() + VICINITY_LEFT + column * 18,
                        getGuiTop() + VICINITY_TOP + row * 18
                        );
            }
        }


        var item = this.hoveredSlot;
        if(item != null && !Registry.ITEM.getKey(item.getItem().getItem()).equals(new ResourceLocation("minecraft:air"))){
            renderTooltip(poseStack, new ItemStack(item.getItem().getItem()), mouseX,mouseY);
        }else{

            var vicinitySlotIndex = getHoverVicinitySlotIndex(mouseX, mouseY);
            if(vicinitySlotIndex > -1){
                var itemEntityIndex = vicinitySlotIndex + scrollRowPos*VICINITY_SLOT_COLUMN_COUNT;
                if(
                    this.itemEntities.size() > itemEntityIndex &&
                    !Registry.ITEM.getKey(itemEntities.get(itemEntityIndex).getItem().getItem()).equals(new ResourceLocation("minecraft:air"))
                ){
                    renderTooltip(poseStack, itemEntities.get(itemEntityIndex).getItem(), mouseX,mouseY);
                }

            }
        }

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
    public boolean mouseScrolled(double p_94686_, double p_94687_, double value) {

        if(super.mouseScrolled(p_94686_, p_94687_, value)){
            return true;
        }

        if(true){
            this.setScrollRowPos(this.getScrollRowPosition() - (int)value);
            return true;
        }

        return false;
    }

    public int getScrollRowPosition() {
        return scrollRowPos;
    }

    public void setScrollRowPos(int value) {

        this.scrollRowPos =
                Math.max(0, Math.min(getMaxScroll(), value));

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
