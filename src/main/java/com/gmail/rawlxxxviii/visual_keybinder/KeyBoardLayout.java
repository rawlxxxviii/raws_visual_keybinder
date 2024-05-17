package com.gmail.rawlxxxviii.visual_keybinder;

import net.minecraft.network.chat.Component;

import java.util.List;

public class KeyBoardLayout {

    private final List<KeyboardLayoutKey> keyboardLayoutKeys;
    private final Component name;

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    public KeyBoardLayout(List<KeyboardLayoutKey> keyboardLayout, Component name) {
        this.keyboardLayoutKeys = keyboardLayout;
        this.name = name;

        minX = keyboardLayout.stream().mapToInt(KeyboardLayoutKey::getX).min().orElse(0);
        maxX = keyboardLayout.stream().mapToInt(x->x.getX() + (x.isWide() ? AlternativeKeybindScreen.WIDE_KEY_BUTTON_WIDTH : AlternativeKeybindScreen.KEY_BUTTON_WIDTH )).max().orElse(0);
        minY = keyboardLayout.stream().mapToInt(KeyboardLayoutKey::getY).min().orElse(0);
        maxY = keyboardLayout.stream().mapToInt(x->x.getY() + AlternativeKeybindScreen.KEY_BUTTON_HEIGHT).max().orElse(0);
    }

    public int getMinX(){
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getWidth() {
        return maxX - minX;
    }
    public int getHeight() {
        return maxY - minY;
    }

    public List<KeyboardLayoutKey> getKeyboardLayoutKeys() {
        return keyboardLayoutKeys;
    }

    public Component getName() {
        return name;
    }
}
