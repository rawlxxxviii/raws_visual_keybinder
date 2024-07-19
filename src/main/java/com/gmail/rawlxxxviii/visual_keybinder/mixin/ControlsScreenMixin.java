package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.VisualKeybinderMod;
import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
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

        this.addRenderableWidget
            (new ImageButton(
                    this.width / 2 + 150 + 10 + ClientConfig.menuButtonOffsetX.get(),
                    this.height / 6 - 12 + ClientConfig.menuButtonOffsetY.get(),

                    27, 20,
                    0, 56,
                    20,
                    new ResourceLocation(VisualKeybinderMod.MODID,"textures/gui/gui.png"),
                    512,512,
                    (p_97538_) -> this.minecraft.setScreen(new AlternativeKeybindScreen(this, this.options)),
                    (button, posestack,x,y)->{
                        renderTooltip(posestack, Component.literal("Visual Keybinder") ,x,y);
                    },
                    Component.literal("Visual keybinder")
            )
        );

    }

    
}
