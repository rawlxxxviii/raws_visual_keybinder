package com.gmail.rawlxxxviii.visual_keybinder;

import net.minecraft.network.chat.Component;

import java.util.List;

public class KeyBoardLayout {

    private final List<KeyboardLayoutKey> keyboardLayout;
    private final Component name;

    public KeyBoardLayout(List<KeyboardLayoutKey> keyboardLayout, Component name) {
        this.keyboardLayout = keyboardLayout;
        this.name = name;
    }

    public List<KeyboardLayoutKey> getKeyboardLayout() {
        return keyboardLayout;
    }

    public Component getName() {
        return name;
    }
}
