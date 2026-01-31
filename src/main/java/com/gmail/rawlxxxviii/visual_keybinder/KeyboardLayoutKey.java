package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class KeyboardLayoutKey {


    private final InputConstants.Key key;
    private final int x;
    private final int y;
    private final boolean wide;

    public KeyboardLayoutKey(String keyName, int x, int y){
        this(keyName,x,y, false);
    }

    public KeyboardLayoutKey(String keyName, int x, int y, boolean wide){
        key = InputConstants.getKey(keyName);
        this.x = x;
        this.y = y;
        this.wide = wide;
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

    public boolean isWide() {
        return wide;
    }

    @Nullable
    public static KeyboardLayoutKey fromString(String content){
        var splitted = content.split(";");
        if(splitted.length < 3){
            return null;
        }

        InputConstants.Key key;
        int x;
        int y;
        try {
            key = InputConstants.getKey(splitted[0]);
            x = Integer.parseInt(splitted[1]);
            y = Integer.parseInt(splitted[2]);
        }catch (Exception exception){
            return null;
        }
        return new KeyboardLayoutKey(
                splitted[0],
                x,
                y,
                splitted.length > 3 && Objects.equals(splitted[3], "wide")
        );
    }

    @Override
    public String toString() {
        return
                key.toString() + ";" +
                        x + ";" +
                        y + ";" +
                        (wide ? "wide" : "")
                ;
    }
}
