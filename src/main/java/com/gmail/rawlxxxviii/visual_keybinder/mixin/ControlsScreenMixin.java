package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.AlternativeKeybindScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
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
            (new Button(
                    this.width / 2 - 155 + 160 + 150,
                    this.height / 6 - 12,
                    150, 20,
                    Component.literal("Alternative"),
                    (p_97538_) -> this.minecraft.setScreen(new AlternativeKeybindScreen(this, this.options)))
        );

    }

    
}
