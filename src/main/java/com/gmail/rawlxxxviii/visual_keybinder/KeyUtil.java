package com.gmail.rawlxxxviii.visual_keybinder;

import java.util.List;
import net.minecraft.client.KeyMapping;

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



}
