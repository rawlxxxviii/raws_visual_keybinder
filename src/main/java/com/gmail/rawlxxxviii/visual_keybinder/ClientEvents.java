package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof ControlsScreen controlsScreen) {

            WidgetSprites buttonSprites = new WidgetSprites(
                    ResourceLocation.fromNamespaceAndPath("visual_keybinder", "icon"),
                    ResourceLocation.fromNamespaceAndPath("visual_keybinder", "icon_highlighted")
            );

            ImageButton visualKeybinderButton = new ImageButton(
                    0, 0,
                    27, 20,
                    buttonSprites,
                    btn -> {
                        Minecraft.getInstance().setScreen(new AlternativeKeybindScreen(controlsScreen, Minecraft.getInstance().options));
                    }
            ) {
                // THE CACHE: Stores the target so we don't have to search for it every frame!
                private AbstractWidget cachedTarget = null;
                private boolean hasSearched = false;

                @Override
                public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

                    // 1. Only run the heavy folder-digging search ONCE.
                    if (!hasSearched) {
                        List<AbstractWidget> allWidgets = new ArrayList<>();
                        harvestAllWidgets(controlsScreen.children(), allWidgets);

                        for (AbstractWidget widget : allWidgets) {
                            if (widget.getX() >= controlsScreen.width / 2 && widget.getWidth() >= 100) {
                                if (cachedTarget == null || widget.getY() < cachedTarget.getY()) {
                                    cachedTarget = widget;
                                }
                            }
                        }
                        hasSearched = true; // Lock the door!
                    }

                    // 2. Read the coordinates directly from our saved cache memory
                    if (cachedTarget != null) {
                        this.setX(cachedTarget.getX() + cachedTarget.getWidth() + 16);
                        this.setY(cachedTarget.getY());
                    } else {
                        // Fallback corner
                        this.setX(10);
                        this.setY(10);
                    }

                    super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
                }
            };

            event.addListener(visualKeybinderButton);
        }
    }

    // The Harvester: Rips open every layout folder to find the widgets
    private static void harvestAllWidgets(Iterable<? extends GuiEventListener> elements, List<AbstractWidget> bucket) {
        for (GuiEventListener element : elements) {
            if (element instanceof AbstractWidget widget) {
                bucket.add(widget);
            }
            if (element instanceof ContainerEventHandler container) {
                harvestAllWidgets(container.children(), bucket);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof LocalPlayer) {

            if (KeyBinding.OPEN_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new AlternativeKeybindScreen(null, Minecraft.getInstance().options));
            }
            if (KeyBinding.SWITCH_TO_PRESET_1.consumeClick()) {
                loadPreset(0);
            }
            if (KeyBinding.SWITCH_TO_PRESET_2.consumeClick()) {
                loadPreset(1);
            }
            if (KeyBinding.SWITCH_TO_PRESET_3.consumeClick()) {
                loadPreset(2);
            }
            if (KeyBinding.SWITCH_TO_PRESET_4.consumeClick()) {
                loadPreset(3);
            }
            if (KeyBinding.SWITCH_TO_PRESET_5.consumeClick()) {
                loadPreset(4);
            }


        }
    }
    private static void loadPreset(int index){
        if(index < 0){
            return;
        }
        var presets = FileUtil.getPresets();
        if(presets == null || presets.size() <= index ){
            displayClientMessage(Component.translatable("raws_visual_keybinder.action_result_message.failed_to_get_presets", index + 1));
            return;
        }

        var preset = presets.get(index);
        try {
            displayClientMessage(
                    Component.translatable("raws_visual_keybinder.action_result_message.preset_loaded", preset.getName())
                            .withStyle(ChatFormatting.GREEN)
            );
            FileUtil.loadPreset(Minecraft.getInstance().options, preset.getName());
        } catch (IOException e) {
            displayClientMessage(
                    Component.translatable("raws_visual_keybinder.action_result_message.loading_preset_failed", preset.getName())
                            .withStyle(ChatFormatting.DARK_RED)
            );
            throw new RuntimeException(e);
        }


    }

    private static void displayClientMessage(Component messageComponent){

        var localPlayer = Minecraft.getInstance().player;
        if(localPlayer == null){
            return;
        }
        localPlayer.displayClientMessage(messageComponent,true);

    }

}
