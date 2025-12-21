package com.gmail.rawlxxxviii.visual_keybinder.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public class GuiUtil {
    public static void enableScissor(int x1, int y1, int x2, int y2){
        double guiScale = Minecraft.getInstance().getWindow().getGuiScale();
        double scaledX = x1 * guiScale;
        double scaledY = Minecraft.getInstance().getWindow().getHeight() - y2 * guiScale;
        double scaledWidth = (x2 - x1) * guiScale;
        double scaledHeight = (y2 - y1) * guiScale;
        RenderSystem.enableScissor((int) scaledX, (int)scaledY, Math.max(0, (int)scaledWidth), Math.max(0, (int)scaledHeight));
    }
}
