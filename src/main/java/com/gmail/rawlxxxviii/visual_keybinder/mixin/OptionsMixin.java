package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.AlternativeKeybindScreen;
import com.gmail.rawlxxxviii.visual_keybinder.FileUtil;
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

@Mixin(Options.class)
public abstract class OptionsMixin {



    @Inject(method = "load()V", at = @At("RETURN"))
    protected void initInject(CallbackInfo ci){


        FileUtil.loadInitialPreset( (Options) (Object) this );
    }

    
}
