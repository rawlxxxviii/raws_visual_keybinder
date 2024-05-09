package com.gmail.rawlxxxviii.vicinity_item_pickup.client;

import com.gmail.rawlxxxviii.vicinity_item_pickup.VicinityItemPickupMod;
import com.gmail.rawlxxxviii.vicinity_item_pickup.common.VicinitySlot;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.vicinity_item_pickup.network.packet.c2s.PickupItemEntity_C2SPacket;
import com.gmail.rawlxxxviii.vicinity_item_pickup.util.DistanceUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VicinityPickupScreen extends Screen {

    protected int leftPos;
    protected int topPos;

    private List<ItemEntity> itemEntities = new ArrayList<ItemEntity>();
    private LocalPlayer player;

    private List<VicinitySlot> vicinitySlots = new ArrayList<VicinitySlot>();
    private List<Slot> inventorySlots = new ArrayList<Slot>();

    private static final double REACH = 12.3f; // 2.3

    private static final int imageHeight = 228;
    private static final int imageWidth = 176;

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(
                    VicinityItemPickupMod.MODID,
                    "textures/gui/vicinity_pickup_gui.png");

    public static final int VICINITY_SLOT_ROW_COUNT = 6;
    public static final int VICINITY_SLOT_COLUMN_COUNT = 9;

    private static final int VICINITY_TOP = 20;
    private static final int INVENTORY_TOP = 146;

    private int scrollRowPos = 0;

    public VicinityPickupScreen(LocalPlayer player) {
        super(Component.literal("Vicinity"));
        this.player = player;
    }

    @Override
    protected void init(){

        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        setItemEntities();
    }

    @Override
    public void tick(){
        this.setScrollRowPos(this.getScrollRowPosition());
    }

    public int getGuiLeft() { return leftPos; }
    public int getGuiTop() { return topPos; }

    public void setItemEntities(){

        var verticalOffset = .5;
        var verticalAddedReach = .5;

        var aabb = new AABB(
                player.getX() - REACH,
                player.getY() - REACH + verticalOffset,
                player.getZ() - REACH,
                player.getX() + REACH,
                player.getY() + REACH + verticalOffset + verticalAddedReach,
                player.getZ() + REACH
        );

        this.itemEntities = player.level.getEntitiesOfClass(
                ItemEntity.class,
                aabb
        );

        this.filterItemsByDistance();
        this.sortEntities();

    }

    public void filterItemsByDistance(){

        this.itemEntities.removeIf(x->
                DistanceUtils.getDistance(player.getX(),x.getX(),player.getZ(),x.getZ())
                        > REACH
        );
    }

    private void addSlots() {

        this.vicinitySlots.clear();
        this.inventorySlots.clear();

        var inventory = player.getInventory();

        for (int i = 0; i < 9; ++i) {
            this.inventorySlots.add(new Slot( inventory, i, 8 + i * 18, INVENTORY_TOP + 54 + 4));
        }

        for (int row = 0; row < 3; ++row) {
            for (int i = 0; i < 9; ++i) {
                this.inventorySlots.add(
                        new Slot(
                                inventory,
                                i + row * 9 + 9,
                                8 + i * 18,
                                INVENTORY_TOP + row * 18
                        )
                );
            }
        }

//        for (int row = 0; row < VICINITY_SLOT_ROW_COUNT; ++row) {
//            for (int i = 0; i < VICINITY_SLOT_COLUMN_COUNT; ++i) {
//                var slot =
//                        new VicinitySlot(
//                                vicinityContainer,
//                                i + row * VICINITY_SLOT_COLUMN_COUNT +36,
//                                8 + i * 18,
//                                VICINITY_TOP + row * 18
//                        );
//                this.vicinitySlots.add(slot);
//            }
//        }
    }

    public void sortEntities(){

        this.itemEntities
//            .sort(Comparator.comparing(a -> a.getUUID().toString()));
                .sort(Comparator.comparing(ItemEntity::getAge, Collections.reverseOrder()));
    }

    public int getMaxScroll() {
        var itemCount = this.vicinityItemCount();
        if(itemCount <= VICINITY_SLOT_ROW_COUNT*VICINITY_SLOT_COLUMN_COUNT){
            return 0;
        }
        return itemCount/VICINITY_SLOT_COLUMN_COUNT + 1 - VICINITY_SLOT_ROW_COUNT;
    }

    public int vicinityItemCount(){
        return this.itemEntities.size();
    }

    public boolean canScroll() {
        return this.getMaxScroll()>0;
    }



    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int buttonNumber) {

        this.itemEntities.stream().findFirst().ifPresent(itemEntity ->
                PacketHandler.sendToServer(new PickupItemEntity_C2SPacket(itemEntity.getId()))
        );

        return super.mouseClicked(p_97748_, p_97749_, buttonNumber);
    }


    protected void renderLabels(PoseStack poseStack, int p_97809_, int p_97810_) {

        drawCenteredString(poseStack,font,
                Component.literal("Vicinity"),
                 this.getGuiLeft() + this.imageWidth / 2,
                this.getGuiTop() + 7,
                new Color(250, 250, 250).getRGB()
        );
//        this.font.draw(p_98616_, creativemodetab.getDisplayName(), 8.0F, 6.0F, creativemodetab.getLabelColor());


    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(KeyBinding.OPEN_VICINITY_PICKUP_KEY.getKey().getValue() == pKeyCode){
            this.onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void renderBackground(PoseStack pPoseStack) {
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

//
//        var item = this.hoveredSlot;
//        if(item != null && !Registry.ITEM.getKey(item.getItem().getItem()).equals(new ResourceLocation("minecraft:air"))){
//            renderTooltip(poseStack, new ItemStack(item.getItem().getItem()), mouseX,mouseY);
//        }

        renderLabels(poseStack,mouseX,mouseY);
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

        var isMouseOverScrollBar = true;
        if(isMouseOverScrollBar){

            this.setScrollRowPos(this.getScrollRowPosition() - (int)value);

            return true;
        }

        return super.mouseScrolled(p_94686_, p_94687_, value);
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
