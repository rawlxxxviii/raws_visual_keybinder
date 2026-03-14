package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.io.IOException;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

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
