package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.VisualKeybinderMod;
import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin extends OptionsSubScreen {

    public ControlsScreenMixin(Screen p_96284_, Options p_96285_, Component p_96286_) {
        super(p_96284_, p_96285_, p_96286_);
    }

    @Inject(method = "init", at = @At("RETURN"))
    protected void initInject(CallbackInfo ci){
        var button = Button.builder(
                        Component.literal("VK"),
                        (btn) -> this.minecraft.setScreen(new AlternativeKeybindScreen(this, this.options))
                )
                .bounds(
                        this.width / 2 + 150 + 10 + ClientConfig.menuButtonOffsetX.get(),
                        this.height / 6 - 12 + ClientConfig.menuButtonOffsetY.get(),
                        27, 20
                )
                .tooltip(Tooltip.create(Component.literal("Visual keybinder")))
                .build();

        this.addRenderableWidget(button);
        button.setTooltip(Tooltip.create(button.getMessage()));

        this.addRenderableWidget
            (button);

    }

    
}
