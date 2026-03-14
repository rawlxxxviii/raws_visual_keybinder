package com.gmail.rawlxxxviii.visual_keybinder.screen;

import com.gmail.rawlxxxviii.visual_keybinder.*;
import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import com.gmail.rawlxxxviii.visual_keybinder.ui_list.DefaultKeyBindsList;
import com.gmail.rawlxxxviii.visual_keybinder.ui_list.KeyDetailsList;
import com.gmail.rawlxxxviii.visual_keybinder.util.FileUtil;
import com.gmail.rawlxxxviii.visual_keybinder.util.KeyUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeKeybindScreen extends OptionsSubScreen {

    public static final int CATEGORY_COLOR =
            new Color(143, 178, 236).getRGB();
    public static final int ACTIVE_COLOR = new Color(31, 176, 255).getRGB();
    public static final int DANGER_COLOR = new Color(230, 96, 96).getRGB();
    public static final int CONFLICT_COLOR = new Color(243, 164, 39).getRGB();
    public static final int RESET_COLOR = new Color(161, 200, 123).getRGB();
    public static final int UNBOUND_COLOR = new Color(142, 149, 154).getRGB();
    public static final int LIST_TITLE_BACKGROUND_COLOR = new Color(0, 0, 0, 158).getRGB();

    public static final int KEY_BUTTON_WIDTH = 16;
    public static final int WIDE_KEY_BUTTON_WIDTH = 60;
    public static final int KEY_BUTTON_HEIGHT = 16;

    private DefaultKeyBindsList defaultKeyBindsList;


    private Button rotateLayoutButton;

    private EditBox keyMappingNameFilterEditBox;
    private EditBox keyMappingCategoryFilterEditBox;

    private List<KeyBoardLayout> keyboardLayouts = new ArrayList<>();
    private KeyBoardLayout activeKeyboardLayout;
    private int activeKeyboardLayoutIndex = 0;
    private List<KeyButton> keyButtons = new ArrayList<>();

    private static final int PAGE_PADDING_TOP = 30;
    private static final int FILTERS_HEIGHT = 25;
    private static final int PAGE_PADDING_BOTTOM = 35;
    private static final int PAGE_PADDING_RIGHT = 5;
    private static final int PAGE_PADDING_LEFT = 5;
    private static final int PAGE_MID_GAP_HORIZONTAL = 5;
    private static final int PAGE_MID_GAP_VERTICAL = 10;


    private int defaultListWidth = 30;
    private int defaultListHeight = 30;
    private int defaultListTop = 30;
    private int defaultListLeft = 30;

    private int layoutTop = 30;
    private int layoutLeft = 30;
    private int layoutWidth = 30;
    private int layoutHeight = 30;

    private int detailsListTop = 30;
    private int detailsListLeft = 30;
    private int detailsListWidth = 30;
    private int detailsListHeight = 30;

    private KeyDetailsList detailsList;

    private KeyMapping keyMappingToChange;


    public AlternativeKeybindScreen(Screen screen, Options options) {
        super(screen, options, Component.literal("Visual keybinder"));

        getKeyboardLayouts();
    }

    @Override
    protected void renderBlurredBackground(float partialTick) {
        // Leave this completely empty to stop the 1.21.1 background blur!
    }

    @Override
    protected void repositionElements() {
        this.rebuildWidgets();
    }

    @Override
    protected void init() {

        layoutHeight = (int)((height - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM - PAGE_MID_GAP_VERTICAL) * 0.45);
        detailsListHeight = (height - layoutHeight - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM - PAGE_MID_GAP_VERTICAL);
        defaultListHeight = detailsListHeight - FILTERS_HEIGHT;

        layoutTop = PAGE_PADDING_TOP + detailsListHeight + PAGE_MID_GAP_VERTICAL;
        defaultListTop = PAGE_PADDING_TOP + FILTERS_HEIGHT;
        detailsListTop = PAGE_PADDING_TOP;


        detailsListWidth = (int)(width * 0.45  - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT);
        defaultListWidth = width - detailsListWidth - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT - PAGE_MID_GAP_HORIZONTAL;

        layoutLeft = PAGE_PADDING_LEFT;
        layoutWidth = width - PAGE_PADDING_RIGHT - layoutLeft;
        detailsListLeft = PAGE_PADDING_LEFT;
        defaultListLeft = detailsListLeft + detailsListWidth + PAGE_MID_GAP_HORIZONTAL;


        this.defaultKeyBindsList = addWidget(new DefaultKeyBindsList(
                this, this.minecraft,
                options,
                defaultListLeft,
                defaultListTop,
                defaultListWidth,
                defaultListHeight
        ));


        this.setActiveKeyboardLayout(activeKeyboardLayoutIndex);



        addRenderableWidget(
                new Button.Builder(
                    CommonComponents.GUI_DONE,
                    (button) -> this.minecraft.setScreen(this.lastScreen)
                )
                .pos(width / 2 - 50, height - 25)
                .size(100, 20)
                .build()
        );

        addRenderableWidget(
                new Button.Builder(
                    Component.literal("Presets").withStyle(ChatFormatting.WHITE),
                    (button) -> Minecraft.getInstance().setScreen(new PresetsScreen(this, options))
                )
                .pos(5, height - 25)
                .size(70, 20)
                .build()
        );



        rotateLayoutButton = addRenderableWidget(
                new Button.Builder(
                        Component.empty(),
                        (button) -> setActiveKeyboardLayout(getActiveKeyboardLayoutIndex()+1)
                )
                        .pos(width - 155, height - 25)
                        .size(150, 20)
                        .build()
        );

        if(detailsList != null){
            setDetailsList(detailsList.getSelectedKey());
        }

        keyMappingNameFilterEditBox = addWidget(
                new FilterEditBox(
                        font,
                        defaultListLeft,
                        defaultListTop - FILTERS_HEIGHT,
                        (defaultListWidth / 2) - 5,
                        20,
                        Component.literal("Name filter")
                )
        );

        keyMappingCategoryFilterEditBox = addWidget(
                new FilterEditBox(
                        font,
                        defaultListLeft + (defaultListWidth / 2),
                        defaultListTop - FILTERS_HEIGHT,
                        defaultListWidth / 2,
                        20,
                        Component.literal("Category filter")
                )
        );
        keyMappingCategoryFilterEditBox.setTextColor(CATEGORY_COLOR);


        keyMappingCategoryFilterEditBox.setResponder(defaultKeyBindsList::setCategoryFilter);
        keyMappingNameFilterEditBox.setResponder(defaultKeyBindsList::setKeyMappingFilter);

    }

    @Override
    protected void addOptions() {

    }


    public int getLayoutLeft() {
        return layoutLeft;
    }

    public int getLayoutTop() {
        return layoutTop;
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public int getLayoutHeight() {
        return layoutHeight;
    }

    public int getActiveKeyboardLayoutIndex() {
        return activeKeyboardLayoutIndex;
    }

    private void getKeyboardLayouts(){

        this.keyboardLayouts.clear();
        this.keyboardLayouts.addAll(
                FileUtil.getKeyboardLayoutsFromFile()
        );
    }

    public KeyBoardLayout getActiveKeyboardLayout() {
        return activeKeyboardLayout;
    }

    public void setActiveKeyboardLayout(int index) {
        if(keyboardLayouts.isEmpty()){
            this.activeKeyboardLayout = null;
            activeKeyboardLayoutIndex = index;
            return;
        }
        if(index >= keyboardLayouts.size()){
            index = 0;
        } else if (index < 0) {
            index = keyboardLayouts.size() - 1;
        }
        activeKeyboardLayout = keyboardLayouts.get(index);
        activeKeyboardLayoutIndex = index;
        createLayoutButtons(this.activeKeyboardLayout);

    }


    @Override
    public boolean keyPressed(int p_193987_, int p_193988_, int p_193989_) {
        if (this.keyMappingToChange != null &&
                !net.neoforged.neoforge.client.settings.KeyModifier.isKeyCodeModifier(
                        InputConstants.getKey(p_193987_,p_193988_)
                )
        ) {
            if (p_193987_ == 256) {
                this.keyMappingToChange.setKeyModifierAndCode(KeyModifier.NONE, InputConstants.UNKNOWN);
                this.options.setKey(this.keyMappingToChange, InputConstants.UNKNOWN);
            } else {
                this.keyMappingToChange.setKeyModifierAndCode(KeyUtil.getActiveKeyModifier(), InputConstants.getKey(p_193987_, p_193988_));
                this.options.setKey(this.keyMappingToChange, InputConstants.getKey(p_193987_, p_193988_));
            }

            this.keyMappingToChange = null;

            KeyMapping.resetMapping();
            onBindingsChanged();
            return true;
        } else {
            try {
                // seems to cause NullPointerException on tabbing when last item
                return super.keyPressed(p_193987_, p_193988_, p_193989_);
            }catch (Exception ex){
                return false;
            }
        }
    }

    @Override
    public boolean keyReleased(int p_193987_, int p_193988_, int p_193989_) {
        var pressedKey = InputConstants.getKey(p_193987_,p_193988_);
        if (this.keyMappingToChange != null &&
                net.neoforged.neoforge.client.settings.KeyModifier.isKeyCodeModifier(pressedKey)
        ) {
            this.keyMappingToChange.setKeyModifierAndCode(KeyModifier.NONE, InputConstants.getKey(p_193987_, p_193988_));
            options.setKey(this.keyMappingToChange, pressedKey);
            this.keyMappingToChange = null;
            KeyMapping.resetMapping();

            onBindingsChanged();
            return true;
        } else {
            return super.keyReleased(p_193987_, p_193988_, p_193989_);
        }
    }

    @Override
    public boolean mouseClicked(double p_193983_, double p_193984_, int p_19394_) {
        if (this.keyMappingToChange != null) {
            this.keyMappingToChange.setKeyModifierAndCode(KeyUtil.getActiveKeyModifier(), InputConstants.Type.MOUSE.getOrCreate(p_19394_));
            this.options.setKey(this.keyMappingToChange, InputConstants.Type.MOUSE.getOrCreate(p_19394_));
            this.keyMappingToChange = null;
            KeyMapping.resetMapping();
            onBindingsChanged();
            return true;
        } else {
            return super.mouseClicked(p_193983_, p_193984_, p_19394_);
        }
    }

    @Override
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        var currentFocused = getFocused();
        if(currentFocused instanceof EditBox editBox && !currentFocused.equals(p_94677_)){
            editBox.setFocused(false);
        }
        super.setFocused(p_94677_);
    }

    public KeyMapping getKeyMappingToChange() {
        return keyMappingToChange;
    }

    public void setKeyMappingToChange(KeyMapping keyMappingToChange) {
        this.keyMappingToChange = keyMappingToChange;
    }

    public KeyDetailsList getDetailsList() {
        return detailsList;
    }

    public void onBindingsChanged(){
        if(this.detailsList != null){
            detailsList.onBindingsUpdated();
        }
        defaultKeyBindsList.onBindingsUpdated();
    }


    public KeyMapping[] getKeyMappings(KeyboardLayoutKey key){
        return Arrays.stream(ArrayUtils.clone(options.keyMappings)).filter(x->
                x.getKey().getValue() == key.getKey().getValue()
                ||
                x.getKeyModifier().matches(key.getKey())
        ).toArray(KeyMapping[]::new);
    }

    private void createLayoutButtons(KeyBoardLayout keyboardLayout){


        keyButtons.forEach(item->{
            removeWidget(item);
        });
        keyButtons.clear();


        keyboardLayout.getKeyboardLayoutKeys().forEach(item->{

            var btn = new KeyButton(
                    this,
                    keyboardLayout,
                    item,
                    layoutLeft + item.getX()
                            + (layoutWidth / 2 -  keyboardLayout.getWidth() / 2) // center to center
                            - keyboardLayout.getMinX() // set min to 0
                    ,
                    layoutTop + item.getY()
                            + (layoutHeight / 2 - keyboardLayout.getHeight() / 2) // center to center
                            - keyboardLayout.getMinY()
                    ,
                    (x)->{

                        if(detailsList != null){
                            if (detailsList.getSelectedKey().getKey().getValue() == item.getKey().getValue()) {
                                removeWidget(detailsList);
                                detailsList = null;
                                return;
                            }
                        }

                        setDetailsList(item);

                    }
            );

            keyButtons.add(addRenderableWidget(btn));
        });
    }

    public void setDetailsList(KeyboardLayoutKey keyboardLayoutKey) {
        if(detailsList != null){
            removeWidget(detailsList);
        }
        detailsList = addWidget(new KeyDetailsList(
                this,
                minecraft,
                options,
                keyboardLayoutKey,
                detailsListLeft,
                detailsListTop,
                detailsListWidth,
                detailsListHeight
        ));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_193994_) {
        super.render(guiGraphics, mouseX, mouseY, p_193994_);

        guiGraphics.fillGradient(
                0, height - 30,
                width,height,
                new Color(0,0,0,120).getRGB(),
                new Color(0,0,0,120).getRGB()
        );

        if(this.detailsList != null){
           detailsList.render(guiGraphics, mouseX, mouseY, p_193994_);
        }

        this.defaultKeyBindsList.render(guiGraphics, mouseX, mouseY, p_193994_);

        guiGraphics.drawCenteredString( this.font, this.title, this.width / 2, 8, 16777215);

        if(activeKeyboardLayout == null){
            rotateLayoutButton.setMessage(Component.literal(keyboardLayouts.isEmpty() ? "No layouts.." : "Select layout"));
        }else{
            rotateLayoutButton.setMessage(Component.literal(activeKeyboardLayout.getName().getString())
                    .append(" " + (getActiveKeyboardLayoutIndex() + 1) + "/" + keyboardLayouts.size() ));
        }
        rotateLayoutButton.render(guiGraphics, mouseX, mouseY, p_193994_);

        if(this.getDetailsList() == null){
            renderNoSelectionInfo(guiGraphics, mouseX, mouseY, p_193994_);
        }

        if(ClientConfig.displayLayoutButtonTooltips.get()){
            keyButtons.forEach(keyButton -> {
                if(keyButton.isHovered()){
                    List<Component> componentList = new ArrayList<>();
                    componentList.add(keyButton.getMessage());

                    var keyMappings = keyButton.getKeymappings();

                    if(keyMappings.length == 0){
                        componentList.add(Component.literal( "No bindings").withStyle(ChatFormatting.DARK_GRAY));
                    }else{
                        componentList.add(Component.literal(""));
                        for (var keymapping : keyMappings){
                            componentList.add(Component.literal(" - ").append(Component.translatable(keymapping.getName()))
                                    .withStyle(KeyUtil.hasConflict(keyMappings, keymapping) ? ChatFormatting.GOLD: ChatFormatting.GRAY));
                        }
                    }
                    guiGraphics.renderComponentTooltip(font,componentList,mouseX,mouseY);
                }
            });
        }

        if(ClientConfig.displayChangeAndResetButtonTooltips.get()){
            if(detailsList != null){
                detailsList.getChildAt(mouseX,mouseY).ifPresent(x->{
                    if(x instanceof KeyDetailsList.KeyInfoEntry keyInfoEntry){
                        if(keyInfoEntry.getResetButton().isHoveredOrFocused()){
                            guiGraphics. renderTooltip(font,Component.translatable("controls.reset"),mouseX,mouseY);
                        } else if (keyInfoEntry.getChangeButton().isHoveredOrFocused()) {
                            guiGraphics.renderTooltip(font,Component.literal("Change binding"),mouseX,mouseY);
                        }
                    }
                });
            }
            defaultKeyBindsList.getChildAt(mouseX,mouseY).ifPresent(x->{
                if(x instanceof DefaultKeyBindsList.KeyEntry keyInfoEntry){
                    if(keyInfoEntry.getResetButton().isHoveredOrFocused()){
                        guiGraphics.renderTooltip(font,Component.translatable("controls.reset"),mouseX,mouseY);
                    } else if (keyInfoEntry.getChangeButton().isHoveredOrFocused()) {
                        guiGraphics.renderTooltip(font,Component.literal("Change binding"),mouseX,mouseY);
                    }
                }
            });
        }

        this.keyMappingNameFilterEditBox.render(guiGraphics, mouseX, mouseY, p_193994_);
        if(this.keyMappingNameFilterEditBox.getValue().isEmpty()){
            guiGraphics.drawString(
                    font,
                    Component.literal("Filter name"),
                    defaultListLeft + 4,
                    PAGE_PADDING_TOP + 6,
                    Color.darkGray.getRGB()
            );
        }
        this.keyMappingCategoryFilterEditBox.render(guiGraphics, mouseX, mouseY, p_193994_);
        if(this.keyMappingCategoryFilterEditBox.getValue().isEmpty()){
            guiGraphics.drawString(
                    font,
                    Component.literal("Filter category "),
                    defaultListLeft + (defaultListWidth / 2) + 4,
                    PAGE_PADDING_TOP + 6,
                    Color.darkGray.getRGB()
            );
        }

        super.render(guiGraphics, mouseX, mouseY, p_193994_);
    }


    public void renderNoSelectionInfo(GuiGraphics guiGraphics, int p_193992_, int p_193993_, float p_193994_) {
        guiGraphics.drawString(
            font,
            Component.literal("Select a key to view/edit bindings"),
            detailsListLeft + 10,
            defaultListTop + 30 ,
            Color.GRAY.getRGB()
        );
    }

    private boolean hasNonDefaultBindings(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            if (!keymapping.isDefault()) {
                return  true;
            }
        }
        return false;
    }
    private boolean hasBoundBindings(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            if (!keymapping.isUnbound()) {
                return  true;
            }
        }
        return false;
    }

}
