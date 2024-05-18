package com.gmail.rawlxxxviii.visual_keybinder;

import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyUtil {

    public static boolean hasConflict(List<KeyMapping> keyMappings){

        for (int i = 0; i < keyMappings.size(); i++) {

            for (int j = 0; j < keyMappings.size(); j++) {
                if(i == j){
                    continue;
                }
                if(keyMappings.get(i).same(keyMappings.get(j))){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasDefaultConflict(List<KeyMapping> keyMappings, KeyMapping item){

        var copy = new KeyMapping(item.getName(),item.getKeyConflictContext(),item.getDefaultKeyModifier(), item.getDefaultKey(),item.getCategory());
        for (int i = 0; i < keyMappings.size(); i++) {
            if(keyMappings.get(i).same(copy)){
                return true;
            }
        }

        return false;
    }

    public static boolean hasConflict(List<KeyMapping> keyMappings, int itemIndex){

        if( itemIndex >= keyMappings.size()){
            return false;
        }
        
        var item = keyMappings.get(itemIndex);
        
        for (int i = 0; i < keyMappings.size(); i++) {
            if(i == itemIndex){
                continue;
            }
            if(keyMappings.get(i).same(item)){
                return true;
            }
        }

        return false;
    }



}
