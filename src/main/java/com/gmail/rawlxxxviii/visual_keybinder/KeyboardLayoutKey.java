package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;

public class KeyboardLayoutKey {


    private final InputConstants.Key key;
    private final int x;
    private final int y;

    KeyboardLayoutKey(String keyName, int x, int y){

        key = InputConstants.getKey(keyName);
        this.x = x;
        this.y = y;
    }

    public InputConstants.Key getKey() {
        return key;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
