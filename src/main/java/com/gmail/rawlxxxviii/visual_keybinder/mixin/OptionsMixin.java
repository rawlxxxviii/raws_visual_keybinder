package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public abstract class OptionsMixin {


    @Inject(method = "<init>", at = @At("RETURN"))
    protected void initInject(CallbackInfo ci) {

        // 1. THIS is the line that actually creates the text files!
        if (FileUtil.getLayoutFileList().isEmpty()) {
            FileUtil.createDefaultLayouts();
        }

        // 2. Your original line
        FileUtil.loadInitialPreset((Options) (Object) this);

        // 3. Creates the initialization file so it doesn't overwrite your custom stuff later
        if (!FileUtil.isInitializedFileCreated()) {
            FileUtil.createInitializedFile();
        }
    }
}