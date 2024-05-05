package com.gmail.rawlxxxviii.advanced_item_pickup.settings_menu;

import com.gmail.rawlxxxviii.advanced_item_pickup.AdvancedItemPickupMod;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.client.KeyBinding;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.ClearNameFiltersOfType_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.DisableAutoPickupKeyPressed_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.RemoveNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.UpdateNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.util.ItemUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class AdvancedPickupSettingsScreen extends AbstractContainerScreen<AdvancedPickupSettingsMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
                    AdvancedItemPickupMod.MODID,
                    "textures/gui/pickup_settings_menu_gui.png");

    private final int columnCount = 4;
    private final int rowCount = 7;
    private final int columnSize = 18;
    private final int listWidth = columnCount * columnSize;
    private final int listHeight = columnSize * rowCount;
    private final int itemHeight = columnSize;

    private MySelectionList itemSelectionList;
    private MySelectionList itemSelectionList2;
    private Item hoveredListItem = null;
    private ImageButton clearAlwaysButton;
    private ImageButton clearNeverButton;


    public AdvancedPickupSettingsScreen(AdvancedPickupSettingsMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init(){

        this.imageHeight = 246;

        super.init();

        this.addLists();

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            c.setSettingsScreenUpToDate(true);
        });
    }


    public void setHoveredListItem(Item item){
        this.hoveredListItem = item;
    }

    private void addLists(){
        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{

            this.clearAlwaysButton = addWidget(new ImageButton(
                    this.getGuiLeft() + 8 + listWidth - 10,
                    this.getGuiTop() + 10,
                    10, 10,
                    30, 0,
                    10,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {
                        PacketHandler.sendToServer(new ClearNameFiltersOfType_C2SPacket(ItemFilterType.ALWAYS));
                    },
                    Component.literal("Clear allways list")
            ));
            this.clearNeverButton = addWidget(new ImageButton(
                    this.getGuiLeft()+8 + listWidth*2 + columnSize - 9 - 10,
                    this.getGuiTop() + 10,
                    10, 10,
                    30, 0,
                    10,
                    new ResourceLocation(AdvancedItemPickupMod.MODID, "textures/gui/gui_buttons.png"),
                    64, 64,
                    (button) -> {
                        PacketHandler.sendToServer(new ClearNameFiltersOfType_C2SPacket(ItemFilterType.NEVER));
                    },
                    Component.literal("Clear allways list")
            ));


            itemSelectionList = addWidget(new MySelectionList(
                    Minecraft.getInstance(),
                    listWidth,
                    listHeight,
                    columnCount,
                    this.getGuiTop()+38,
                    this.getGuiLeft()+8,
                    itemHeight,
                    c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.ALWAYS).toList(),
                    this,
                    itemSelectionList == null ? 0 : itemSelectionList.getScrollAmount(),
                    Component.literal("Allways"),
                    new Color(50, 50, 50)
            ));
            itemSelectionList2 = addWidget(new MySelectionList(
                    Minecraft.getInstance(),
                    listWidth,
                    listHeight,
                    columnCount,
                    this.getGuiTop()+38,
                    this.getGuiLeft()+8 + listWidth + columnSize - 9,
                    itemHeight,
                    c.getAutoPickupFilters().stream().filter(x->x.getType() == ItemFilterType.NEVER).toList(),
                    this,
                    itemSelectionList2 == null ? 0 : itemSelectionList2.getScrollAmount(),
                    Component.literal("Never"),
                    new Color(50, 50, 50)
            ));

        });
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int buttonNumber) {

        var hoveredItem = this.getHoveredItem();
        if(hoveredItem != null){

            if(buttonNumber == 0)//left
            {
                this.updateNameFilter(ItemUtils.getResourceLocation(hoveredItem), ItemFilterType.ALWAYS);
                return true;
            }
            else if(buttonNumber == 1)//left
            {
                this.updateNameFilter(ItemUtils.getResourceLocation(hoveredItem), ItemFilterType.NEVER);
                return true;
            }
            else if(buttonNumber == 2)//left
            {
                this.removeNameFilter(ItemUtils.getResourceLocation(hoveredItem));
                return true;
            }
        }
        return super.mouseClicked(p_97748_, p_97749_, buttonNumber);
    }


    @Override
    protected void containerTick() {

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            if(!c.isSettingsScreenUpToDate()){
                this.rebuildWidgets();
            }
        });

    }



    @Override
    protected void renderLabels(PoseStack poseStack, int p_97809_, int p_97810_) {

        drawCenteredString(poseStack,font,Component.literal("Auto pickup settings"), getGuiLeft() + this.imageWidth / 2, 15, new Color(250, 250, 250).getRGB());

    }

    public Item getHoveredItem() {
        if(this.hoveredSlot != null){
            return this.hoveredSlot.getItem().getItem();
        }else if(this.hoveredListItem != null){
            return this.hoveredListItem;
        }
        return null;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

        var hoveredItem = this.getHoveredItem();
        if(hoveredItem != null){
            if(KeyBinding.OPEN_SETTINGS_MENU_KEY.getKey().getValue() == pKeyCode){
                this.onClose();
                return true;
            } else if (KeyBinding.MENU_ADD_TO_ALWAYS_KEY.getKey().getValue() == pKeyCode) {
                updateNameFilter(ItemUtils.getResourceLocation(hoveredItem),ItemFilterType.ALWAYS);
                return true;
            }else if (KeyBinding.MENU_ADD_TO_NEVER_KEY.getKey().getValue() == pKeyCode) {
                    updateNameFilter(ItemUtils.getResourceLocation(hoveredItem),ItemFilterType.NEVER);
                    return true;
            }else if (KeyBinding.MENU_REMOVE_FROM_FILTERS_KEY.getKey().getValue() == pKeyCode) {
                removeNameFilter(ItemUtils.getResourceLocation(hoveredItem));
                return true;
            }
        }

        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public void updateNameFilter(ResourceLocation resourceLocation, ItemFilterType type){
        PacketHandler.sendToServer(new UpdateNameFilter_C2SPacket(resourceLocation, type));
    }

    public void removeNameFilter(ResourceLocation resourceLocation){
        PacketHandler.sendToServer(new RemoveNameFilter_C2SPacket(resourceLocation));
    }

    private void renderItemStateIcons(PoseStack pPoseStack){

        Minecraft.getInstance().player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            var filters = c.getAutoPickupFilters();

            menu.slots.forEach(slot -> {
                if(!slot.hasItem()) {
                    return;
                }
                var itemNameFilter = filters.stream().filter(x-> x.isMatch(slot.getItem().getItem())).findFirst().orElse(null);
                if(itemNameFilter == null){
                    return;
                }

                var color = new Color(76, 76, 76,175).getRGB();
                if(itemNameFilter.getType() == ItemFilterType.ALWAYS){
                    color = new Color(120, 202, 60,175).getRGB();
                } else if (itemNameFilter.getType() == ItemFilterType.NEVER) {
                    color = new Color(150,50,50,175).getRGB();
                }

                renderIconOverlay( pPoseStack,
                        getGuiLeft() + slot.x,
                        getGuiTop() + slot.y,
                        color
                );

            });

        });
    }

    private void renderIconOverlay(PoseStack poseStack, int x, int y, int color){
        var size = 3;
        int topPadding = 1;
        int leftPadding = 1;

        fill(
                poseStack,
                x + leftPadding,
                y + topPadding,
                x + leftPadding + size ,
                y + topPadding + size,
                color
        );
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
        this.setHoveredListItem(null);

        this.renderBackground(poseStack);

        super.render(poseStack, mouseX, mouseY, partialTicks);

        itemSelectionList.render(poseStack,mouseX, mouseY, partialTicks);
        itemSelectionList2.render(poseStack,mouseX, mouseY, partialTicks);

        clearAlwaysButton.render(poseStack,mouseX, mouseY, partialTicks);
        clearNeverButton.render(poseStack,mouseX, mouseY, partialTicks);

        renderItemStateIcons(poseStack);

        var item = this.getHoveredItem();
        if(item != null && !Registry.ITEM.getKey(item).equals(new ResourceLocation("minecraft:air"))){
            renderTooltip(poseStack, new ItemStack(item), mouseX,mouseY);
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
