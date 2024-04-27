package com.gmail.rawlxxxviii.advanced_item_pickup.common;

import net.minecraft.client.gui.components.Button;

public class FilterListItem {

    private String key;
    private Button nameButton;
    private Button allwaysButton;
    private Button neverButton;
    private Button disabledButton;
    private Button deleteButton;

    public FilterListItem(String key, Button nameButton, Button allwaysButton, Button neverButton, Button disabledButton, Button deleteButton) {
        this.key = key;
        this.nameButton = nameButton;
        this.allwaysButton = allwaysButton;
        this.neverButton = neverButton;
        this.disabledButton = disabledButton;
        this.deleteButton = deleteButton;
    }

    public String getKey() {
        return key;
    }

}
