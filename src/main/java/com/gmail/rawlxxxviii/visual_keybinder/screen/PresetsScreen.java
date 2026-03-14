package com.gmail.rawlxxxviii.visual_keybinder.screen;

import com.gmail.rawlxxxviii.visual_keybinder.ui_list.KeyPresetOptionsList;
import com.gmail.rawlxxxviii.visual_keybinder.KeybindingPreset;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.settings.KeyModifier;

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
    protected void renderBlurredBackground(float partialTick) {
    }

    @Override
    protected void repositionElements() {
        this.rebuildWidgets();
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

        addRenderableWidget(
                new Button.Builder(CommonComponents.GUI_DONE,
                        (button) -> onClose()
                )
                .pos(width / 2 - 75,this.height - 25)
                .size(150, 20)
                .build()
        );


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

        saveNewPresetButton = addRenderableWidget(
                new Button.Builder(
                        Component.literal("Save preset"),
                        (button) -> savePreset(newPresetNameEditBox.getValue())
                )
                .pos(newPresetNameEditBox.getX() + newPresetNameEditBox.getWidth() + 10, uiTop)
                .size(100, 20)
                .build()
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

    @Override
    protected void addOptions() {

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
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        var currentFocused = getFocused();
        if(currentFocused instanceof EditBox editBox && !currentFocused.equals(p_94677_)){
            editBox.setFocused(false);
        }
        super.setFocused(p_94677_);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_193994_) {
        this.renderBackground(guiGraphics, mouseX, mouseY, p_193994_);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 16, 16777215);

        saveNewPresetButton.render(guiGraphics, mouseX, mouseY, p_193994_);
        newPresetNameEditBox.render(guiGraphics, mouseX, mouseY, p_193994_);

        if(this.newPresetNameEditBox.getValue().isEmpty()){
            guiGraphics.drawString(
                    font,
                    Component.literal("Preset name"),
                    newPresetNameEditBox.getX() + 4,
                    newPresetNameEditBox.getY() + 6,
                    Color.darkGray.getRGB()
            );
        }

        KeyPresetOptionsList.render(guiGraphics, mouseX, mouseY, p_193994_);

        super.render(guiGraphics, mouseX, mouseY, p_193994_);
    }

    public boolean isPresetActive(KeybindingPreset keybindingPreset){

        if(options.keyMappings.length != keybindingPreset.getLines().size()){
            return false;
        }

        for(var keymapping : options.keyMappings){

            KeyModifier presetKeyModifier = KeyModifier.NONE;

            String mappingName = keymapping.getName();

            var presetItem = keybindingPreset.getLines().stream().filter(x -> x.startsWith(mappingName + ":")).toList().stream().findFirst().orElse(null);
            if(presetItem == null){
                return false;
            }
            var split = presetItem.split(":");
            if(split.length < 2){
                return false;
            }

            InputConstants.Key presetKey = InputConstants.getKey(split[1]);

            if(split.length == 3){
                presetKeyModifier = KeyModifier.valueFromString(split[2]);
            }

            if(
                    !presetKey.equals(keymapping.getKey())
                            ||
                            presetKeyModifier != keymapping.getKeyModifier()
            ){
                return false;
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
