package com.gmail.rawlxxxviii.visual_keybinder.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;

import java.util.EnumSet;

public class KeyUtil {

    public static net.neoforged.neoforge.client.settings.KeyModifier getActiveKeyModifier(){

        for(var a : EnumSet.allOf(KeyModifier.class)){

            if(a.isActive(null)){
                return a;
            }

        }

        return KeyModifier.NONE;

    }

    public static boolean hasConflict(KeyMapping[] keyMappings){

        for (int i = 0; i < keyMappings.length; i++) {

            for (int j = 0; j < keyMappings.length; j++) {
                if(i == j){
                    continue;
                }
                if(keyMappings[i].same(keyMappings[j])){
                    return true;
                }
            }
        }

        return false;
    }


    public static boolean hasDefaultConflict(KeyMapping[] keyMappings, KeyMapping item){

        for (KeyMapping keyMapping : keyMappings) {
            if (keyMapping.getName().equals(item.getName())) {
                continue;
            }
            if (isDefaultConflicting(keyMapping, item.getKeyConflictContext(), item.getDefaultKey(), item.getDefaultKeyModifier())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isDefaultConflicting(KeyMapping keyMappingA, IKeyConflictContext keyConflictContextB, InputConstants.Key keyB, KeyModifier keyModifierB){

        if(keyMappingA.isUnbound() || keyB.equals(InputConstants.UNKNOWN)) {
            return false;
        }

        if (keyMappingA.getKeyConflictContext().conflicts(keyConflictContextB) || keyConflictContextB.conflicts(keyMappingA.getKeyConflictContext())) {
            net.neoforged.neoforge.client.settings.KeyModifier keyModifier = keyMappingA.getKeyModifier();
            if (keyModifier.matches(keyB) || keyModifierB.matches(keyMappingA.getKey())) {
                return true;
            } else if (keyMappingA.getKey().equals(keyB)) {
                // IN_GAME key contexts have a conflict when at least one modifier is NONE.
                // For example: If you hold shift to crouch, you can still press E to open your inventory. This means that a Shift+E hotkey is in conflict with E.
                // GUI and other key contexts do not have this limitation.
                return keyModifier == keyModifierB ||
                        (keyMappingA.getKeyConflictContext().conflicts(net.neoforged.neoforge.client.settings.KeyConflictContext.IN_GAME) &&
                                (keyModifier == net.neoforged.neoforge.client.settings.KeyModifier.NONE || keyModifierB == net.neoforged.neoforge.client.settings.KeyModifier.NONE));
            }
        }
        return keyMappingA.getKey().equals(keyB);
    }

    public static boolean hasConflict(KeyMapping[] keyMappings, KeyMapping item){

        for (KeyMapping keyMapping : keyMappings) {
            if (keyMapping.getName().equals(item.getName())) {
                continue;
            }
            if(keyMapping.isUnbound() || item.isUnbound()) {
                continue;
            }
            if (keyMapping.same(item)) {
                return true;
            }
        }

        return false;
    }



}
