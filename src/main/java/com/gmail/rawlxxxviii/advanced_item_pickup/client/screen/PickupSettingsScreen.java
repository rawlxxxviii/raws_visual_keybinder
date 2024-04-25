package com.gmail.rawlxxxviii.advanced_item_pickup.client.screen;

import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickup;
import com.gmail.rawlxxxviii.advanced_item_pickup.capability.AdvancedPickupAttacher;
import com.gmail.rawlxxxviii.advanced_item_pickup.client.KeyBinding;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemFilterType;
import com.gmail.rawlxxxviii.advanced_item_pickup.common.ItemNameFilter;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.PacketHandler;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.c2s.UpdateNameFilter_C2SPacket;
import com.gmail.rawlxxxviii.advanced_item_pickup.network.packet.s2c.UpdateNameFilter_S2C_Packet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

public class PickupSettingsScreen extends Screen {

    private final int imageWidth = 167, imageHeight = 166;
    private int leftPos = 0, topPos = 0;

    public PickupSettingsScreen() {
        super(Component.literal("Pickup settings"));
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.minecraft.player.getCapability(AdvancedPickup.INSTANCE).ifPresent(c->{
            for (int i = 0; i < c.getAutoPickupFilters().size(); i++) {
                addNameFilterItem(i,c.getAutoPickupFilters().get(i));
            }
        });

        // Add widgets and precomputed values
//        this.addRenderableWidget(new EditBox(this.font,1,1,1,1,Component.literal("asd")));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
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
                    PacketHandler.sendToServer(new UpdateNameFilter_C2SPacket(item.getItemName(), ItemFilterType.ALWAYS));
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
                    PacketHandler.sendToServer(new UpdateNameFilter_C2SPacket(item.getItemName(), ItemFilterType.NEVER));
                }
        );
        nevereBtn.active = item.getType() != ItemFilterType.NEVER;

        var disabledBtn = new Button(
                200 + btnWidth*2,
                y,
                btnWidth,
                btnHeight,
                Component.literal("Disabled"),
                (x)->{
                    PacketHandler.sendToServer(new UpdateNameFilter_C2SPacket(item.getItemName(), ItemFilterType.DISABLED));
                }
        );
        disabledBtn.active = item.getType() != ItemFilterType.DISABLED;

        this.addRenderableWidget(nameBtn);
        this.addRenderableWidget(alwaysBtn);
        this.addRenderableWidget(nevereBtn);
        this.addRenderableWidget(disabledBtn);

    }

}
