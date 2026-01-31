package com.gmail.rawlxxxviii.visual_keybinder.mixin;

import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public abstract class OptionsMixin {



    @Inject(method = "load()V", at = @At("RETURN"))
    protected void initInject(CallbackInfo ci){


        if(FileUtil.getLayoutFileList().isEmpty()){
            FileUtil.createDefaultLayouts();
        }
        FileUtil.loadInitialPreset( (Options) (Object) this );

        if(!FileUtil.isInitializedFileCreated()){
            FileUtil.createInitializedFile();

        }
    }

    
}
