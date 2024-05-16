package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.ArrayUtils;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeKeybindScreen extends OptionsSubScreen {

    public static final int CATEGORY_COLOR =
            new Color(143, 178, 236).getRGB();
    public static final int CONFLICT_COLOR = new Color(243, 164, 39).getRGB();

    public static final int KEY_BUTTON_WIDTH = 16;
    public static final int WIDE_KEY_BUTTON_WIDTH = 60;
    public static final int KEY_BUTTON_HEIGHT = 16;

    //default
    private DefaultKeyBindsList keyBindsList;

    // custom
    private Button resetButton;
    private List<KeyboardLayoutKey> keyboardLayout;
    private List<KeyButton> keyButtons;


    private static final int PAGE_PADDING_TOP = 30;
    private static final int PAGE_PADDING_BOTTOM = 30;
    private static final int PAGE_PADDING_RIGHT = 10;
    private static final int PAGE_PADDING_LEFT = 10;
    private static final int PAGE_MID_GAP = 20;


    private static int defaultListWidth = 30;
    private static int defaultListHeight = 30;
    private static int defaultListTop = 30;
    private static int defaultListLeft = 30;

    private static int layoutTop = 30;
    private static int layoutLeft = 30;
    private static int layoutHeight = 30;

    private static int detailsListTop = 30;
    private static int detailsListLeft = 30;
    private static int detailsListWidth = 30;
    private static int detailsListHeight = 30;

    private KeyDetailsList detailsList;


    private KeyMapping keyMappingToChange;


    public AlternativeKeybindScreen(Screen screen, Options options) {
        super(screen, options, Component.literal("Visual keybinder"));

    }


    @Override
    protected void init() {




        layoutHeight = (int)((height - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM) * 0.5);
        detailsListHeight = (height - layoutHeight - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM);
        defaultListHeight = detailsListHeight;

        layoutTop = PAGE_PADDING_TOP;
        defaultListTop = PAGE_PADDING_TOP + layoutHeight;
        detailsListTop = PAGE_PADDING_TOP + layoutHeight;

        detailsListWidth = (int)(width * 0.45  - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT);
        defaultListWidth = width - detailsListWidth - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT - PAGE_MID_GAP;

        layoutLeft = PAGE_PADDING_LEFT;
        detailsListLeft = PAGE_PADDING_LEFT;
        defaultListLeft = detailsListLeft + detailsListWidth + PAGE_MID_GAP;


        this.keyBindsList = addWidget(new DefaultKeyBindsList(
                this, this.minecraft,
                defaultListLeft,
                defaultListTop,
                defaultListWidth,
                defaultListHeight
        ));

        createLayout();
        createLayoutButtons();

        this.addRenderableWidget(new Button(
                this.width / 2 + 50,
                this.height - PAGE_PADDING_BOTTOM + 5,
                150, 20, CommonComponents.GUI_DONE,
                (button) -> this.minecraft.setScreen(this.lastScreen)
        ));

        this.resetButton = this.addRenderableWidget(new Button(
                this.width / 2 - 155,
                this.height - PAGE_PADDING_BOTTOM + 5,
                150, 20,
                Component.translatable("controls.resetAll"),
                (button) -> {
                    for(KeyMapping keymapping : this.options.keyMappings) {
                        keymapping.setToDefault();
                    }

                    KeyMapping.resetMapping();
                    onBindingsChanged();
                })
        );

    }

    @Override
    public boolean keyPressed(int p_193987_, int p_193988_, int p_193989_) {
        if (this.keyMappingToChange != null &&
                !net.minecraftforge.client.settings.KeyModifier.isKeyCodeModifier(
                        InputConstants.getKey(p_193987_,p_193988_)
                )
        ) {
            if (p_193987_ == 256) {
                this.keyMappingToChange.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.getActiveModifier(), InputConstants.UNKNOWN);
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
    public boolean mouseClicked(double p_193983_, double p_193984_, int p_19394_) {
        if (this.keyMappingToChange != null) {
            this.options.setKey(this.keyMappingToChange, InputConstants.Type.MOUSE.getOrCreate(p_19394_));
            this.keyMappingToChange = null;
            KeyMapping.resetMapping();
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

    private void createLayout(){
        keyboardLayout = new ArrayList<>();

        int buttonWidth = 16;
        int buttonHeight = 16;
        int buttonGap = 0;

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f1", 0 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f2", 1 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f3", 2 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f4", 3 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f5", 5 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f6", 6 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f7", 7 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f8", 8 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f9", 10 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f10", 11 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f11", 12 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f12", 13 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) - 5 ));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.1", 0 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.2", 1 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.3", 2 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.4", 3 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.5", 4 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.6", 5 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.7", 6 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.8", 7 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.9", 8 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.0", 9 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.minus", 10 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.equal", 11 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.backspace", 12 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap), true));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.q",5 + 0 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.w",5 + 1 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.e",5 + 2 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.r",5 + 3 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.t",5 + 4 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.y",5 + 5 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.u",5 + 6 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.i",5 + 7 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.o",5 + 8 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.p",5 + 9 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.left.bracket",5 + 10 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.right.bracket",5 + 11 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.a",10 + 0 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.s",10 + 1 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.d",10 + 2 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.f",10 + 3 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.g",10 + 4 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.h",10 + 5 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.j",10 + 6 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.k",10 + 7 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.l",10 + 8 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.semicolon",10 + 9 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.apostrophe",10 + 10 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.backslash",10 + 11 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.enter",10 + 12 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap),true));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.z",15 + 0 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.x",15 + 1 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.c",15 + 2 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.v",15 + 3 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.b",15 + 4 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.n",15 + 5 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.m",15 + 6 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.comma",15 + 7 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.period",15 + 8 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.slash",15 + 9 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.left",15 + 14 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.down",15 + 16 * (buttonWidth + buttonGap), 5 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.right",15 + 18 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.up",15 + 16 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap),true));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.home",15 + 15 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.end",15 + 19 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.insert",15 + 15 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.page.up",15 + 19 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.delete",15 + 15 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap),true));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.page.down",15 + 19 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap),true));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.space",15 + 4 * (buttonWidth + buttonGap), 5 * (buttonHeight+buttonGap),true));

        // keypad
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.divide",15 + 24 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.multiply",15 + 25 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.subtract",15 + 26 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.7",15 + 23 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.8",15 + 24 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.9",15 + 25 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.add",15 + 26 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.4",15 + 23 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.5",15 + 24 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.6",15 + 25 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.1",15 + 23 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.2",15 + 24 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.3",15 + 25 * (buttonWidth + buttonGap), 4 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.0",15 + 23 * (buttonWidth + buttonGap), 5 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.decimal",15 + 24 * (buttonWidth + buttonGap), 5 * (buttonHeight+buttonGap)));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.keypad.enter",15 + 26 * (buttonWidth + buttonGap), 5 * (buttonHeight+buttonGap),true));

        // mouse
        keyboardLayout.add(new KeyboardLayoutKey("key.mouse.left",15 + 28 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap) ,true));
        keyboardLayout.add(new KeyboardLayoutKey("key.mouse.middle",15 + 30 * (buttonWidth + buttonGap), 0 * (buttonHeight+buttonGap) ,true));
        keyboardLayout.add(new KeyboardLayoutKey("key.mouse.right",15 + 32 * (buttonWidth + buttonGap), 1 * (buttonHeight+buttonGap) ,true));
        keyboardLayout.add(new KeyboardLayoutKey("key.mouse.4",15 + 29 * (buttonWidth + buttonGap), 2 * (buttonHeight+buttonGap) +5,true));
        keyboardLayout.add(new KeyboardLayoutKey("key.mouse.5",15 + 30 * (buttonWidth + buttonGap), 3 * (buttonHeight+buttonGap) +5,true));

    }

    public void onBindingsChanged(){
        if(this.detailsList != null){
            detailsList.onBindingsUpdated();
        }
    }



    public List<KeyMapping> getKeyMappings(KeyboardLayoutKey key){
        return Arrays.stream(ArrayUtils.clone(options.keyMappings)).filter(x->
                x.getKey().getValue() == key.getKey().getValue()
        ).toList();
    }

    private void createLayoutButtons(){
        this.keyButtons = new ArrayList<>();

        keyboardLayout.forEach(item->{

            var btn = new KeyButton(
                    this,
                    item,
                    layoutLeft + item.getX(),
                    layoutTop + item.getY(),
                    (x)->{
                        if(detailsList != null){
                            removeWidget(detailsList);
                        }
                        detailsList = addRenderableWidget(new KeyDetailsList(
                                this,
                                minecraft,
                                options,
                                item,
                                detailsListLeft,
                                detailsListTop,
                                detailsListWidth,
                                detailsListHeight
                        ));

                    },
                    (button, poseStack, mouseX, mouseY)->{}
            );

            keyButtons.add(addRenderableWidget(btn));
        });
    }



    @Override
    public void render(PoseStack poseStack, int p_193992_, int p_193993_, float p_193994_) {
        this.renderBackground(poseStack);

        if(this.detailsList != null){
            detailsList.render(poseStack, p_193992_, p_193993_, p_193994_);
        }
        this.keyBindsList.render(poseStack, p_193992_, p_193993_, p_193994_);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);

        resetButton.active = hasNonDefaultBindings();

        super.render(poseStack, p_193992_, p_193993_, p_193994_);
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
