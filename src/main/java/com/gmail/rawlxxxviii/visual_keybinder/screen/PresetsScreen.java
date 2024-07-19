package com.gmail.rawlxxxviii.visual_keybinder.screen;

import com.gmail.rawlxxxviii.visual_keybinder.ui_list.KeyPresetOptionsList;
import com.gmail.rawlxxxviii.visual_keybinder.KeybindingPreset;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.settings.KeyModifier;

import javax.annotation.Nullable;
import java.awt.*;

public class PresetsScreen extends OptionsSubScreen {


    private EditBox newPresetNameEditBox;
    private Button saveNewPresetButton;

    private com.gmail.rawlxxxviii.visual_keybinder.ui_list.KeyPresetOptionsList KeyPresetOptionsList;

    public PresetsScreen(Screen screen, Options options) {
        super(screen, options, Component.literal("Keybinding presets"));
    }

    @Override
    protected void init() {

        int uiWidth = 400;
        int uiLeft = width / 2 - uiWidth / 2;
        int uiRight = uiLeft + width;
        int uiTop = 40;
        int bottomPadding = 50;
        int uiHeight = height - uiTop - bottomPadding;
        int uiBottom = uiTop + uiHeight;

        addRenderableWidget(new Button(
                width / 2 - 75,
                this.height - 25,
                150, 20, CommonComponents.GUI_DONE,
                (button) -> onClose()
        ));


        newPresetNameEditBox = addWidget(
                new EditBox(
                        font,
                        uiLeft + 1,
                        uiTop,
                        uiWidth - 100 - 10,
                        20,
                        Component.literal("new preset name")
                )
        );

        saveNewPresetButton = addRenderableWidget(new Button(
                newPresetNameEditBox.x + newPresetNameEditBox.getWidth() + 10,
                uiTop,
                100, 20,
                Component.literal("Save preset"),
                (button) -> savePreset(newPresetNameEditBox.getValue()))
        );



        KeyPresetOptionsList = addWidget(new KeyPresetOptionsList(
                this,
                minecraft,
                options,
                uiLeft,
                uiTop + 30,
                uiWidth,
                uiHeight - 30
        ));

    }

    public void savePreset(String name){

        name = FileUtil.sanitizeString(name);

        if(name.isBlank()){
            return;
        }

        try {
            FileUtil.savePreset(options, name);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        rebuildWidgets();
    }

    public void deletePreset(String name){

        try {
            FileUtil.deletePreset(name);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        rebuildWidgets();
    }

    public void loadPreset(String name){

        try {
            FileUtil.loadPreset(options, name);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        rebuildWidgets();
    }

    public void unbindAll(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            keymapping.setKeyModifierAndCode(KeyModifier.NONE,InputConstants.UNKNOWN);
            this.options.setKey(keymapping, InputConstants.UNKNOWN);
        }
        options.save();
        KeyMapping.resetMapping();
        rebuildWidgets();
    }

    public void resetAll(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            keymapping.setToDefault();
            options.setKey(keymapping, keymapping.getDefaultKey());
        }
        options.save();
        KeyMapping.resetMapping();
        rebuildWidgets();
    }

    @Override
    public void tick(){
        newPresetNameEditBox.tick();
    }

    @Override
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        var currentFocused = getFocused();
        if(currentFocused instanceof EditBox editBox && !currentFocused.equals(p_94677_)){
            editBox.setFocus(false);
        }
        super.setFocused(p_94677_);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float p_193994_) {
        this.renderDirtBackground(0);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 16, 16777215);

        saveNewPresetButton.render(poseStack, mouseX, mouseY, p_193994_);
        newPresetNameEditBox.render(poseStack, mouseX, mouseY, p_193994_);

        if(this.newPresetNameEditBox.getValue().isEmpty()){
            drawString(
                    poseStack,
                    font,
                    Component.literal("Preset name"),
                    newPresetNameEditBox.x + 4,
                    newPresetNameEditBox.y + 6,
                    Color.darkGray.getRGB()
            );
        }

        KeyPresetOptionsList.render(poseStack, mouseX, mouseY, p_193994_);

        super.render(poseStack, mouseX, mouseY, p_193994_);
    }

    public boolean isPresetActive(KeybindingPreset keybindingPreset){

        if(options.keyMappings.length != keybindingPreset.getLines().size()){
            return false;
        }

        for(var keymapping : options.keyMappings){

            KeyModifier keyModifier = null;
            InputConstants.Key key = null;

            String mappingName = keymapping.getName();

            var list = keybindingPreset.getLines().stream().filter(x -> x.startsWith(mappingName + ":")).toList();
            if(list.isEmpty()){
                return false;
            }

            var presetValue = list.get(0).substring(mappingName.length() + 1 );

            if (presetValue.indexOf(':') != -1) {
                String[] pts = presetValue.split(":");

                keyModifier = KeyModifier.valueFromString(pts[1]);
                key = InputConstants.getKey(pts[0]);


            } else {

                keyModifier = KeyModifier.NONE;
                key = InputConstants.getKey(presetValue);
            }

            if(
                keyModifier != keymapping.getKeyModifier()
                ||
                !key.equals(keymapping.getKey())
            ){
                return  false;
            }


        }
        return true;


    }

    public boolean hasNonDefaultBindings(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            if (!keymapping.isDefault()) {
                return  true;
            }
        }
        return false;
    }

    public boolean hasBoundBindings(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            if (!keymapping.isUnbound()) {
                return  true;
            }
        }
        return false;
    }

}
