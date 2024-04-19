package com.gmail.rawlxxxviii.advanced_item_pickup.common;

public enum ItemFilterType {
    ALWAYS, NEVER, DISABLED;

    public static ItemFilterType fromInteger(int x) {
        switch(x) {
            case 0:
                return ALWAYS;
            case 1:
                return NEVER;
        }
        return DISABLED;
    }
}
