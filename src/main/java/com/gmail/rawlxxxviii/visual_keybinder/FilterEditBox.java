package com.gmail.rawlxxxviii.visual_keybinder;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class FilterEditBox extends EditBox {
    public FilterEditBox(Font p_94114_, int p_94115_, int p_94116_, int p_94117_, int p_94118_, Component p_94119_) {
        super(p_94114_, p_94115_, p_94116_, p_94117_, p_94118_, p_94119_);
    }


    @Override
    public boolean mouseClicked(double p_94125_, double p_94126_, int p_94127_) {

        if(p_94127_ == 0){
            return super.mouseClicked(p_94125_, p_94126_, p_94127_);
        } else if (p_94127_ == 1) {
            if(p_94125_ >= (double)x && p_94125_ < (double)(x + width) && p_94126_ >= (double)y && p_94126_ < (double)(y + height)){
                setValue("");
                return true;
            }
        }

        return false;
    }

}
