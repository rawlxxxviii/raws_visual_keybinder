package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.screen.AlternativeKeybindScreen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class KeyBoardLayout {

    private final List<KeyboardLayoutKey> keyboardLayoutKeys;
    private final Component name;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public KeyBoardLayout(Component name, List<KeyboardLayoutKey> keyboardLayoutKeys) {
        this.keyboardLayoutKeys = keyboardLayoutKeys;
        this.name = name;
        updateMinMax();
    }

    private void updateMinMax(){
        minX = keyboardLayoutKeys.stream().mapToInt(KeyboardLayoutKey::getX).min().orElse(0);
        maxX = keyboardLayoutKeys.stream().mapToInt(x->x.getX() + (x.isWide() ? AlternativeKeybindScreen.WIDE_KEY_BUTTON_WIDTH : AlternativeKeybindScreen.KEY_BUTTON_WIDTH )).max().orElse(0);
        minY = keyboardLayoutKeys.stream().mapToInt(KeyboardLayoutKey::getY).min().orElse(0);
        maxY = keyboardLayoutKeys.stream().mapToInt(x->x.getY() + AlternativeKeybindScreen.KEY_BUTTON_HEIGHT).max().orElse(0);
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
