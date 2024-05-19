package com.gmail.rawlxxxviii.visual_keybinder;

import com.gmail.rawlxxxviii.visual_keybinder.config.ClientConfig;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.settings.KeyModifier;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeKeybindScreen extends OptionsSubScreen {

    public static final int CATEGORY_COLOR =
            new Color(143, 178, 236).getRGB();
    public static final int CONFLICT_COLOR = new Color(243, 164, 39).getRGB();
    public static final int RESET_COLOR = new Color(161, 200, 123).getRGB();
    public static final int UNBOUND_COLOR = new Color(142, 149, 154).getRGB();
    public static final int LIST_BACKGROUND_COLOR = new Color(0, 0, 0, 30).getRGB();
    public static final int LIST_BACKGROUND_COLOR_2 = new Color(0, 0, 0, 50).getRGB();
    public static final int LIST_TITLE_BACKGROUND_COLOR = new Color(0, 0, 0).getRGB();

    public static final int KEY_BUTTON_WIDTH = 16;
    public static final int WIDE_KEY_BUTTON_WIDTH = 60;
    public static final int KEY_BUTTON_HEIGHT = 16;

    private DefaultKeyBindsList defaultKeyBindsList;


    private Button rotateLayoutButton;
    private Button resetButton;

    private List<KeyBoardLayout> keyBoardLayouts = new ArrayList<>();
    private KeyBoardLayout activeKeyboardLayout;
    private int activeKeyboardLayoutIndex = 0;
    private List<KeyButton> keyButtons = new ArrayList<>();

    private static final int PAGE_PADDING_TOP = 30;
    private static final int PAGE_PADDING_BOTTOM = 30;
    private static final int PAGE_PADDING_RIGHT = 5;
    private static final int PAGE_PADDING_LEFT = 5;
    private static final int PAGE_MID_GAP_HORIZONTAL = 5;
    private static final int PAGE_MID_GAP_VERTICAL = 5;


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
    protected void init() {


        layoutHeight = (int)((height - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM - PAGE_MID_GAP_VERTICAL) * 0.45);
        detailsListHeight = (height - layoutHeight - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM - PAGE_MID_GAP_VERTICAL);
        defaultListHeight = detailsListHeight;

        layoutTop = PAGE_PADDING_TOP;
        defaultListTop = PAGE_PADDING_TOP + layoutHeight + PAGE_MID_GAP_VERTICAL;
        detailsListTop = PAGE_PADDING_TOP + layoutHeight + PAGE_MID_GAP_VERTICAL;

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


        rotateLayoutButton = addRenderableWidget(new Button(
                this.width - 155,
                + 5,
                150, 20,
                Component.empty(),
                (button) -> setActiveKeyboardLayout(getActiveKeyboardLayoutIndex()+1)
            )
        );

        addRenderableWidget(new Button(
                this.width / 2 - 75,
                this.height - PAGE_PADDING_BOTTOM + 5,
                150, 20, CommonComponents.GUI_DONE,
                (button) -> this.minecraft.setScreen(this.lastScreen)
        ));

        resetButton = this.addRenderableWidget(new Button(
                5,
                this.height - PAGE_PADDING_BOTTOM + 5,
                75, 20,
                Component.translatable("controls.resetAll"),
                (button) -> {
                    for(KeyMapping keymapping : this.options.keyMappings) {
                        keymapping.setToDefault();
                    }

                    KeyMapping.resetMapping();
                    onBindingsChanged();
                })
        );

        if(detailsList != null){

            var a = detailsList.getSelectedKey();

            setDetailsList(a);

        }

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

        this.keyBoardLayouts.clear();
        this.keyBoardLayouts.addAll(
            ClientConfig.getKeyboardLayoutsFromConfig()
        );
    }

    public KeyBoardLayout getActiveKeyboardLayout() {
        return activeKeyboardLayout;
    }

    public void setActiveKeyboardLayout(int index) {
        if(keyBoardLayouts.isEmpty()){
            this.activeKeyboardLayout = null;
            activeKeyboardLayoutIndex = index;
            return;
        }
        if(index >= keyBoardLayouts.size()){
            index = 0;
        } else if (index < 0) {
            index = keyBoardLayouts.size() - 1;
        }
        activeKeyboardLayout = keyBoardLayouts.get(index);
        activeKeyboardLayoutIndex = index;
        createLayoutButtons(this.activeKeyboardLayout);

    }


    @Override
    public boolean keyPressed(int p_193987_, int p_193988_, int p_193989_) {
        if (this.keyMappingToChange != null &&
                !net.minecraftforge.client.settings.KeyModifier.isKeyCodeModifier(
                        InputConstants.getKey(p_193987_,p_193988_)
                )
        ) {
            if (p_193987_ == 256) {
                this.keyMappingToChange.setKeyModifierAndCode(KeyModifier.NONE, InputConstants.UNKNOWN);
                this.options.setKey(this.keyMappingToChange, InputConstants.UNKNOWN);
            } else {
                this.keyMappingToChange.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), InputConstants.getKey(p_193987_, p_193988_));
                this.options.setKey(this.keyMappingToChange, InputConstants.getKey(p_193987_, p_193988_));
            }

            this.keyMappingToChange = null;

            KeyMapping.resetMapping();
            onBindingsChanged();
            return true;
        } else {
            return super.keyPressed(p_193987_, p_193988_, p_193989_);
        }
    }

    @Override
    public boolean keyReleased(int p_193987_, int p_193988_, int p_193989_) {
        var pressedKey = InputConstants.getKey(p_193987_,p_193988_);
        if (this.keyMappingToChange != null &&
                net.minecraftforge.client.settings.KeyModifier.isKeyCodeModifier(pressedKey)
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
            this.keyMappingToChange.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), InputConstants.Type.MOUSE.getOrCreate(p_19394_));
            this.options.setKey(this.keyMappingToChange, InputConstants.Type.MOUSE.getOrCreate(p_19394_));
            this.keyMappingToChange = null;
            KeyMapping.resetMapping();
            onBindingsChanged();
            return true;
        } else {
            return super.mouseClicked(p_193983_, p_193984_, p_19394_);
        }
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


    public KeyMapping[] getAllKeyMappings(){
        return ArrayUtils.clone(options.keyMappings);
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
                            removeWidget(detailsList);

                            if (detailsList.getSelectedKey().getKey().getValue() == item.getKey().getValue()) {
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
        detailsList = addRenderableWidget(new KeyDetailsList(
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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float p_193994_) {
        this.renderBackground(poseStack);

        if(this.detailsList != null){
            detailsList.render(poseStack, mouseX, mouseY, p_193994_);
        }
        this.defaultKeyBindsList.render(poseStack, mouseX, mouseY, p_193994_);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);

        resetButton.active = hasNonDefaultBindings();



        if(activeKeyboardLayout == null){
            rotateLayoutButton.setMessage(Component.literal(keyBoardLayouts.isEmpty() ? "No layouts.." : "Select layout"));
        }else{
            rotateLayoutButton.setMessage(Component.literal(activeKeyboardLayout.getName().getString())
                    .append(" " + (getActiveKeyboardLayoutIndex() + 1) + "/" + keyBoardLayouts.size() ));

        }

        if(this.getDetailsList() == null){
            renderNoSelectionInfo(poseStack, mouseX, mouseY, p_193994_);
        }

        if(ClientConfig.displayLayoutButtonTooltips.get()){
            keyButtons.forEach(keyButton -> {
                if(keyButton.isHoveredOrFocused()){
                    List<Component> componentList = new ArrayList<>();
                    componentList.add(keyButton.getMessage());

                    var count = keyButton.getKeymappings().length;
                    if(count == 0){
                        componentList.add(Component.literal( "No bindings").withStyle(ChatFormatting.DARK_GRAY));
                    }else{
                        componentList.add(Component.literal(String.valueOf(count)).append(" binding" + (count == 1 ? "" : "s")).withStyle(ChatFormatting.DARK_GRAY));
                    }
                    if(keyButton.hasConflict()){
                        componentList.add(Component.literal( "Has conflict").withStyle(ChatFormatting.GOLD));
                    }
                    renderComponentTooltip(poseStack,componentList,mouseX,mouseY);
                }
            });
        }

        if(ClientConfig.displayChangeAndResetButtonTooltips.get()){
            if(detailsList != null){
                detailsList.getChildAt(mouseX,mouseY).ifPresent(x->{
                    if(x instanceof KeyDetailsList.KeyInfoEntry keyInfoEntry){
                        if(keyInfoEntry.getResetButton().isHoveredOrFocused()){
                            renderTooltip(poseStack,Component.translatable("controls.reset"),mouseX,mouseY);
                        } else if (keyInfoEntry.getChangeButton().isHoveredOrFocused()) {
                            renderTooltip(poseStack,Component.literal("Change binding"),mouseX,mouseY);
                        }
                    }
                });
            }
            defaultKeyBindsList.getChildAt(mouseX,mouseY).ifPresent(x->{
                if(x instanceof DefaultKeyBindsList.KeyEntry keyInfoEntry){
                    if(keyInfoEntry.getResetButton().isHoveredOrFocused()){
                        renderTooltip(poseStack,Component.translatable("controls.reset"),mouseX,mouseY);
                    } else if (keyInfoEntry.getChangeButton().isHoveredOrFocused()) {
                        renderTooltip(poseStack,Component.literal("Change binding"),mouseX,mouseY);
                    }
                }
            });
        }

        super.render(poseStack, mouseX, mouseY, p_193994_);
    }


    public void renderNoSelectionInfo(PoseStack poseStack, int p_193992_, int p_193993_, float p_193994_) {
        minecraft.font.draw(poseStack, Component.literal("Select a key to view/edit bindings"),
                detailsListLeft + 10, defaultListTop + 30 , Color.GRAY.getRGB());
    }

    private boolean hasNonDefaultBindings(){
        for(KeyMapping keymapping : this.options.keyMappings) {
            if (!keymapping.isDefault()) {
                return  true;
            }
        }
        return false;
    }

}
