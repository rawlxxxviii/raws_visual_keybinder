package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.client.KeyBinding;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.RemoveNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.UpdateNameFilter_C2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class AdvancedPickupSettingsScreen extends AbstractContainerScreen<AdvancedPickupSettingsMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(AdvancedItemPickupMod.MODID,"textures/gui/pickup_settings_menu_gui.png");

    private MySelectionList itemSelectionList;
    private MySelectionList itemSelectionList2;

    public AdvancedPickupSettingsScreen(AdvancedPickupSettingsMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init(){

        super.init();

        this.addStaticButtons();
//        this.addVariableWidgets();
        this.addList();



        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            c.setSettingsScreenUpToDate(true);
        });


    }


    private void addList(){
        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{

            itemSelectionList =
                    new MySelectionList(
                            Minecraft.getInstance(),
                            200,
                            80,
                            20,
                            20,
                            10,
                            c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.ALWAYS).toList(),
                            this
                    );

            itemSelectionList2 =
                    new MySelectionList(
                            Minecraft.getInstance(),
                            200,
                            80,
                            120,
                            20,
                            10,
                            c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.NEVER).toList(),
                            this
                    );


            addWidget(itemSelectionList);
            addWidget(itemSelectionList2);

        });
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int buttonNumber) {

        var hoveredSlot = this.hoveredSlot;
        if(hoveredSlot == null){
            return super.mouseClicked(p_97748_, p_97749_, buttonNumber);
        }
        if(buttonNumber == 0)//left
        {
            this.updateNameFilter(hoveredSlot.getItem().getDescriptionId(), ItemFilterType.ALWAYS);
            return true;
        }
        else if(buttonNumber == 1)//left
        {
            this.updateNameFilter(hoveredSlot.getItem().getDescriptionId(), ItemFilterType.NEVER);
            return true;
        }
        else if(buttonNumber == 2)//left
        {
            this.removeNameFilter(hoveredSlot.getItem().getDescriptionId());
            return true;
        }

        return  false;
    }

    private void addStaticButtons(){



        var btn = new Button(
                leftPos + 10,
                topPos + 10,
                100,
                10,
                Component.literal("+ always (" + KeyBinding.MENU_ADD_TO_ALWAYS_KEY.getKey().getDisplayName().getString() + ")"),
                (x)->{
                    updateNameFilter(menu.getCarried().getDescriptionId(),ItemFilterType.ALWAYS);
                }
        );
        var btn2 = new Button(
                leftPos + 10,
                topPos + 20,
                100,
                10,
                Component.literal("+ never (" + KeyBinding.MENU_ADD_TO_NEVER_KEY.getKey().getDisplayName().getString() + ")"),
                (x)->{
                    updateNameFilter(menu.getCarried().getDescriptionId(),ItemFilterType.NEVER);
                }
        );
        var btn4 = new Button(
                leftPos + 10,
                topPos + 40,
                100,
                10,
                Component.literal("Remove (" + KeyBinding.MENU_REMOVE_FROM_FILTERS_KEY.getKey().getDisplayName().getString() + ")"),
                (x)->{
                    removeNameFilter(menu.getCarried().getDescriptionId());
                }
        );

        this.addRenderableWidget(btn);
        this.addRenderableWidget(btn2);
        this.addRenderableWidget(btn4);

    }

    @Override
    protected void containerTick() {

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            if(!c.isSettingsScreenUpToDate()){
                this.rebuildWidgets();
            }
        });

    }

    private void addVariableWidgets(){

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{


            // list
            for (int i = 0; i < c.getAutoPickupFilters().size(); i++) {
                addNameFilterItem(i,c.getAutoPickupFilters().get(i));
            }

            var allwaysItems = c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.ALWAYS).toList();
            for (int i = 0; i < allwaysItems.size(); i++) {
                var item = allwaysItems.get(i);

                addNameFilterItem(i,c.getAutoPickupFilters().get(i));
                var btn = new Button(
                        50,
                        200 + i*18,
                        100,
                        10,
                        Component.literal(item.getItemName()),
                        (x)->{
                        }
                );

                this.addRenderableWidget(btn);
            }

            var neverItems = c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.NEVER).toList();
            for (int i = 0; i < neverItems.size(); i++) {
                var item = neverItems.get(i);

                addNameFilterItem(i,c.getAutoPickupFilters().get(i));
                var btn = new Button(
                        250,
                        200 + i*18,
                        100,
                        10,
                        Component.literal(item.getItemName()),
                        (x)->{
                        }
                );

                this.addRenderableWidget(btn);
            }

        });

    }

    private void addNameFilterItem(int rowN, ItemNameFilter item){

        int btnBottomMargin = 2;
        int btnHeight = 12;
        int btnWidth = 50;

        int y = btnHeight * rowN + btnBottomMargin;

        var nameBtn = new Button(
                0,
                y,
                200,
                btnHeight,
                Component.literal(item.getItemName() ),
                (x)->{
                }
        );
        nameBtn.active = false;

        var alwaysBtn = new Button(
                200,
                y,
                btnWidth,
                btnHeight,
                Component.literal("Allways"),
                (x)->{
                    updateNameFilter(item.getItemName(), ItemFilterType.ALWAYS);
                }
        );
        alwaysBtn.active = item.getType() != ItemFilterType.ALWAYS;

        var nevereBtn = new Button(
                200 + btnWidth,
                y,
                btnWidth,
                btnHeight,
                Component.literal("Never"),
                (x)->{
                    updateNameFilter(item.getItemName(), ItemFilterType.NEVER);
                }
        );
        nevereBtn.active = item.getType() != ItemFilterType.NEVER;

        var removeBtn = new Button(
                200 + btnWidth*3,
                y,
                50,
                btnHeight,
                Component.literal("X"),
                (x)->{
                    removeNameFilter(item.getItemName());
                }
        );

        this.addRenderableWidget(nameBtn);
        this.addRenderableWidget(alwaysBtn);
        this.addRenderableWidget(nevereBtn);
        this.addRenderableWidget(removeBtn);

    }


    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        }else if(KeyBinding.OPEN_SETTINGS_MENU_KEY.getKey().getValue() == pKeyCode){
            this.onClose();
            return true;
        } else if (KeyBinding.MENU_ADD_TO_ALWAYS_KEY.getKey().getValue() == pKeyCode) {
            var hoveredSlot = this.hoveredSlot;
            if(hoveredSlot != null){
                updateNameFilter(hoveredSlot.getItem().getDescriptionId(),ItemFilterType.ALWAYS);
                return true;
            }
        }else if (KeyBinding.MENU_ADD_TO_NEVER_KEY.getKey().getValue() == pKeyCode) {
            var hoveredSlot = this.hoveredSlot;
            if(hoveredSlot != null){
                updateNameFilter(hoveredSlot.getItem().getDescriptionId(),ItemFilterType.NEVER);
                return true;
            }
        }else if (KeyBinding.MENU_REMOVE_FROM_FILTERS_KEY.getKey().getValue() == pKeyCode) {
            var hoveredSlot = this.hoveredSlot;
            if(hoveredSlot != null){
                removeNameFilter(hoveredSlot.getItem().getDescriptionId());
                return true;
            }
        }

        return false;
    }

    public void updateNameFilter(String key, ItemFilterType type){
        PacketHandler.sendToServer(new UpdateNameFilter_C2SPacket(key, type));
    }

    public void removeNameFilter(String key){
        PacketHandler.sendToServer(new RemoveNameFilter_C2SPacket(key));
    }

    private void renderItemStateIcons(PoseStack pPoseStack){

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            var filters = c.getAutoPickupFilters();

            menu.slots.forEach(slot -> {
                if(!slot.hasItem()) {
                    return;
                }

                var itemNameFilter = filters.stream().filter(x-> x.isMatch(slot.getItem())).findFirst().orElse(null);
                if(itemNameFilter == null){
                    return;
                }

                var color = new Color(76, 76, 76,175).getRGB();
                if(itemNameFilter.getType() == ItemFilterType.ALWAYS){
                    color = new Color(120, 202, 60,175).getRGB();
                } else if (itemNameFilter.getType() == ItemFilterType.NEVER) {
                    color = new Color(150,50,50,175).getRGB();
                }

                renderBlock( pPoseStack,
                        getGuiLeft() + slot.x + 1,
                        getGuiTop() + slot.y +1,
                        color
                );

            });

        });
    }

    private void renderBlock(PoseStack poseStack, int x, int y, int color){
        var size = 3;
        fill(poseStack, x, y, x + size,  y + size, color);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
    }


    @Override
    public void render(@NotNull PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(poseStack);


        super.render(poseStack, mouseX, mouseY, partialTicks);

        itemSelectionList.render(poseStack,mouseX, mouseY, partialTicks);
        itemSelectionList2.render(poseStack,mouseX, mouseY, partialTicks);
        renderItemStateIcons(poseStack);
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
