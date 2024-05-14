package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlternativeKeybindScreen extends OptionsSubScreen {

    private Button resetButton;
    private List<KeyboardLayoutKey> keyboardLayout;
    private List<KeyButton> keyButtons;


    private static final int PAGE_PADDING_TOP = 30;
    private static final int PAGE_PADDING_BOTTOM = 30;
    private static final int PAGE_PADDING_RIGHT = 30;
    private static final int PAGE_PADDING_LEFT = 30;

    private static int layoutHeight = 30;

    private static int defaultListWidth = 30;
    private static int defaultListHeight = 30;

    private static int layoutTop = 30;
    private static int defaultListTop = 30;
    private static int detailsListTop = 30;
    private static int layoutLeft = 30;
    private static int detailsListLeft = 30;
    private static int defaultListLeft = 30;
    private static int detailsListWidth = 30;
    private static int detailsListHeight = 30;

    private KeyDetailsList detailsList;

    public AlternativeKeybindScreen(Screen screen, Options options) {
        super(screen, options, Component.literal("Visual keybinder"));

    }


    @Override
    protected void init() {




        layoutHeight = (int)(height * 0.6 - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM);
        detailsListHeight = (height - layoutHeight - PAGE_PADDING_TOP - PAGE_PADDING_BOTTOM);
        defaultListHeight = detailsListHeight;

        layoutTop = PAGE_PADDING_TOP;
        defaultListTop = PAGE_PADDING_TOP + layoutHeight;
        detailsListTop = PAGE_PADDING_TOP + layoutHeight;

        detailsListWidth = width / 2 - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT;
        defaultListWidth = width / 2 - PAGE_PADDING_LEFT - PAGE_PADDING_RIGHT;

        layoutLeft = PAGE_PADDING_LEFT;
        detailsListLeft = PAGE_PADDING_LEFT;
        defaultListLeft = width / 2 ;


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
                    this.rebuildWidgets();
                })
        );

    }


    public KeyDetailsList getDetailsList() {
        return detailsList;
    }

    private void createLayout(){
        keyboardLayout = new ArrayList<>();

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.1",10, 10));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.2",30, 10));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.3",50, 10));

        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.q",15, 30));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.w",35, 30));
        keyboardLayout.add(new KeyboardLayoutKey("key.keyboard.e",55, 30));
    }

    private void createLayoutButtons(){
        this.keyButtons = new ArrayList<>();

        keyboardLayout.forEach(item->{

            var keyMappings = Arrays.stream(ArrayUtils.clone(options.keyMappings)).filter(x->
                    x.getKey().getValue() == item.getKey().getValue()
            ).toList();

            var btn = new KeyButton(
                    this,
                    item.getKey(),
                    keyMappings,
                    layoutLeft + item.getX(),
                    layoutTop + item.getY(),
                    (x)->{
                        if(detailsList != null){
                            removeWidget(detailsList);
                        }
                        detailsList = addRenderableWidget(new KeyDetailsList(
                                this,
                                minecraft,
                                keyMappings,
                                options,
                                item.getKey(),
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
