package com.gmail.rawlxxxviii.visual_keybinder.config;

import com.gmail.rawlxxxviii.visual_keybinder.KeyBoardLayout;
import com.gmail.rawlxxxviii.visual_keybinder.KeyboardLayoutKey;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.*;

public class ClientConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.IntValue menuButtonOffsetX;
    public static ForgeConfigSpec.IntValue menuButtonOffsetY;
    public static ForgeConfigSpec.BooleanValue displayConflictContext;
    public static ForgeConfigSpec.BooleanValue displayLayoutButtonTooltips;
    public static ForgeConfigSpec.BooleanValue displayChangeAndResetButtonTooltips;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> keyboardLayouts;


    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {

        builder.comment("Use this to create your own layouts. Multiple layouts can be defined. A standard button is 16px and a wide button is 60px. The order is defined by the occurrence of the name of the layout. This file can be deleted in order to restore to default.");
        builder.push("Layouts");
        keyboardLayouts = builder
                .comment("format:")
                .comment("layout_name;key_name;position_x;position_y;wide/standard")
                .comment("example:")
                .comment("Full keyboard and mouse;key.keyboard.5;64;16;standard;")
                .defineList( "button_layouts",
                        createList(),
                         entry -> true
                )
        ;
        builder.pop();

        builder.push("Preferences");
        displayConflictContext = builder
                .comment("Display whether a binding is for GUI, in game or both.")
                .define( "display_conflict_context", false)
        ;
        displayLayoutButtonTooltips = builder
                .comment("Should a tooltip be displayed when the mouse is over a button.")
                .define( "display_layout_button_tooltip", true)
        ;
        displayChangeAndResetButtonTooltips = builder
                .comment("Should a tooltip be displayed for the change and reset button.")
                .define( "display_button_tooltip", true)
        ;

        menuButtonOffsetX = builder
                .comment("Reposition the menu button horizontally.")
                .defineInRange( "menu_button_offset_x", 0,-10000,10000)
        ;
        menuButtonOffsetY = builder
                .comment("Reposition the menu button vertically.")
                .defineInRange( "menu_button_offset_y", 0,-10000,10000)
        ;

        builder.pop();

    }

    private static List<String> createList(){

        List<String> list = new ArrayList<>();
        list.addAll(layout1());
        list.addAll(layout2());
        list.addAll(layout3());
        list.addAll(layout4());

        return list;
    }
    
    private static void addItemToStringList(List<String> list, String layoutName, String keyName, int x, int y, boolean isWide){
        list.add(
                layoutName + ";" +
                keyName + ";" +
                x + ";" +
                y + ";" +
                (isWide ? "wide" : "standard")
        );
    }

    public static List<KeyBoardLayout> getKeyboardLayoutsFromConfig(){

        Map<String,List<KeyboardLayoutKey>> map = new HashMap<>();

        keyboardLayouts.get().forEach(item-> {
            var splittedString = item.split(";");
            if(splittedString.length != 5){
                return;
            }
            var itemLayoutName = splittedString[0];
            if(!map.containsKey(itemLayoutName)){
                map.put(itemLayoutName, new ArrayList<KeyboardLayoutKey>());
            }
        });

        keyboardLayouts.get().forEach(item-> {

            var splittedString = item.split(";");
            if(splittedString.length != 5){
                return;
            }

            var itemLayoutName = splittedString[0];
            var itemKeyName = splittedString[1];
            var itemX = Integer.parseInt(splittedString[2]);
            var itemY = Integer.parseInt(splittedString[3]);
            boolean isWide = Objects.equals(splittedString[4], "wide");

            map.get(itemLayoutName).add(
                new KeyboardLayoutKey(
                    itemKeyName,
                    itemX,
                    itemY,
                    isWide
            ));
        });

        List<KeyBoardLayout> resultList = new ArrayList<KeyBoardLayout>();

        map.forEach(
                (a,b)->{
                    resultList.add( new KeyBoardLayout(Component.literal(a),b));
                }
        );

        return resultList;
    }

    private static List<String> layout1(){

        int buttonWidth = 16;
        int buttonHeight = 16;

        List<String> list = new ArrayList<>();
        var layoutName = "Full keyboard and mouse";
        
        addItemToStringList(list, layoutName, "key.keyboard.f1", 0 * (buttonWidth ), 0 * (buttonHeight) - 5, false );
        addItemToStringList(list, layoutName, "key.keyboard.f2", 1 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f3", 2 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f4", 3 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f5", 5 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f6", 6 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f7", 7 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f8", 8 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f9", 10 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f10", 11 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f11", 12 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f12", 13 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);

//        addItemToStringList(list, layoutName, "key.keyboard.tilde", -1 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.1", 0 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.2", 1 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.3", 2 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.4", 3 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.5", 4 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.6", 5 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.7", 6 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.8", 7 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.9", 8 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.0", 9 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.minus", 10 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.equal", 11 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.backspace", 12 * (buttonWidth ), 1 * (buttonHeight), true);

        addItemToStringList(list, layoutName, "key.keyboard.tab",5 + -4 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.q",5 + 0 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.w",5 + 1 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.e",5 + 2 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.r",5 + 3 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.t",5 + 4 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.y",5 + 5 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.u",5 + 6 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.i",5 + 7 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.o",5 + 8 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.p",5 + 9 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.left.bracket",5 + 10 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.right.bracket",5 + 11 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.backslash",5 + 12 * (buttonWidth ), 2 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.caps.lock",10 + -4 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.a",10 + 0 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.s",10 + 1 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.d",10 + 2 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.f",10 + 3 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.g",10 + 4 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.h",10 + 5 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.j",10 + 6 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.k",10 + 7 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.l",10 + 8 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.semicolon",10 + 9 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.apostrophe",10 + 10 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.enter",10 + 11 * (buttonWidth ), 3 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left.shift",15 + -4 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.z",15 + 0 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.x",15 + 1 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.c",15 + 2 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.v",15 + 3 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.b",15 + 4 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.n",15 + 5 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.m",15 + 6 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.comma",15 + 7 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.period",15 + 8 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.slash",15 + 9 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.right.shift",15 + 10 * (buttonWidth ), 4 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left",15 + 15 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.down",15 + 17 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right",15 + 19 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.up",15 + 17 * (buttonWidth ), 3 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.home",15 + 15 * (buttonWidth ), 0 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.end",15 + 19 * (buttonWidth ), 0 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.insert",15 + 15 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.page.up",15 + 19 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.delete",15 + 15 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.page.down",15 + 19 * (buttonWidth ), 2 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left.control",15 + -3 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.left.alt",15 + 1 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right.alt",15 + 5 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right.control",15 + 9* (buttonWidth ), 5 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.space",15 + 3 * (buttonWidth ), 6 * (buttonHeight),true);

        // keypad
        addItemToStringList(list, layoutName, "key.keyboard.keypad.divide",+ 15 + 27 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.multiply",+ 15 + 31 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.subtract",+ 15 + 35 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.7",+ 15 + 23 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.8",+ 15 + 27 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.9",+ 15 + 31 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.add",+ 15 + 35 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.4",+ 15 + 23 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.5",+ 15 + 27 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.6",+ 15 + 31 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.1",15 + 23 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.2",+ 15 + 27 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.3",+ 15 + 31 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.0",+ 15 + 27 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.decimal",+ 15 + 31 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.enter",+ 15 + 35 * (buttonWidth ), 5 * (buttonHeight),true);

        // mouse
        addItemToStringList(list, layoutName, "key.mouse.left",15 + 39 * (buttonWidth ), 1 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.middle",15 + 41 * (buttonWidth ), 0 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.right",15 + 43 * (buttonWidth ), 1 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.5",15 + 40 * (buttonWidth ), 2 * (buttonHeight) +5,true);
        addItemToStringList(list, layoutName, "key.mouse.4",15 + 41 * (buttonWidth ), 3 * (buttonHeight) +5,true);

        return list;
    }

    private static List<String>  layout2(){

        int buttonWidth = 16;
        int buttonHeight = 16;

        List<String> list = new ArrayList<>();
        var layoutName = "Mouse";

        // mouse
        addItemToStringList(list, layoutName, "key.mouse.left",15 + 28 * (buttonWidth ), 1 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.middle",15 + 30 * (buttonWidth ), 0 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.right",15 + 32 * (buttonWidth ), 1 * (buttonHeight) ,true);
        addItemToStringList(list, layoutName, "key.mouse.5",15 + 29 * (buttonWidth ), 2 * (buttonHeight) +5,true);
        addItemToStringList(list, layoutName, "key.mouse.4",15 + 30 * (buttonWidth ), 3 * (buttonHeight) +5,true);

        return list;
    }

    private static List<String> layout3(){
        int buttonWidth = 16;
        int buttonHeight = 16;

        List<String> list = new ArrayList<>();
        var layoutName = "Full keyboard";

        addItemToStringList(list, layoutName, "key.keyboard.f1", 0 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f2", 1 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f3", 2 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f4", 3 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f5", 5 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f6", 6 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f7", 7 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f8", 8 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f9", 10 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f10", 11 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f11", 12 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);
        addItemToStringList(list, layoutName, "key.keyboard.f12", 13 * (buttonWidth ), 0 * (buttonHeight) - 5 , false);

//        addItemToStringList(list, layoutName, "key.keyboard.tilde", -1 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.1", 0 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.2", 1 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.3", 2 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.4", 3 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.5", 4 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.6", 5 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.7", 6 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.8", 7 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.9", 8 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.0", 9 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.minus", 10 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.equal", 11 * (buttonWidth ), 1 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.backspace", 12 * (buttonWidth ), 1 * (buttonHeight), true);

        addItemToStringList(list, layoutName, "key.keyboard.tab",5 + -4 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.q",5 + 0 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.w",5 + 1 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.e",5 + 2 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.r",5 + 3 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.t",5 + 4 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.y",5 + 5 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.u",5 + 6 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.i",5 + 7 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.o",5 + 8 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.p",5 + 9 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.left.bracket",5 + 10 * (buttonWidth ), 2 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.right.bracket",5 + 11 * (buttonWidth ), 2 * (buttonHeight),false);

        addItemToStringList(list, layoutName, "key.keyboard.caps.lock",10 + -4 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.a",10 + 0 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.s",10 + 1 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.d",10 + 2 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.f",10 + 3 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.g",10 + 4 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.h",10 + 5 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.j",10 + 6 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.k",10 + 7 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.l",10 + 8 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.semicolon",10 + 9 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.apostrophe",10 + 10 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.backslash",10 + 11 * (buttonWidth ), 3 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.enter",10 + 12 * (buttonWidth ), 3 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left.shift",15 + -4 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.z",15 + 0 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.x",15 + 1 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.c",15 + 2 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.v",15 + 3 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.b",15 + 4 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.n",15 + 5 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.m",15 + 6 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.comma",15 + 7 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.period",15 + 8 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.slash",15 + 9 * (buttonWidth ), 4 * (buttonHeight),false);
        addItemToStringList(list, layoutName, "key.keyboard.right.shift",15 + 10 * (buttonWidth ), 4 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left",15 + 15 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.down",15 + 17 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right",15 + 19 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.up",15 + 17 * (buttonWidth ), 3 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.home",15 + 15 * (buttonWidth ), 0 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.end",15 + 19 * (buttonWidth ), 0 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.insert",15 + 15 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.page.up",15 + 19 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.delete",15 + 15 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.page.down",15 + 19 * (buttonWidth ), 2 * (buttonHeight),true);

        addItemToStringList(list, layoutName, "key.keyboard.left.control",15 + -3 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.left.alt",15 + 1 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.space",15 + 4 * (buttonWidth ), 6 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right.alt",15 + 7 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.right.control",15 + 11* (buttonWidth ), 5 * (buttonHeight),true);

        return list;
    }

    private static List<String> layout4(){
        int buttonWidth = 16;
        int buttonHeight = 16;

        List<String> list = new ArrayList<>();
        var layoutName = "Keypad";

        // keypad
        addItemToStringList(list, layoutName, "key.keyboard.keypad.divide",15 + 24 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.multiply",15 + 28 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.subtract",15 + 32 * (buttonWidth ), 1 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.7",15 + 20 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.8",15 + 24 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.9",15 + 28 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.add",15 + 32 * (buttonWidth ), 2 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.4",15 + 20 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.5",15 + 24 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.6",15 + 28 * (buttonWidth ), 3 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.1",15 + 20 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.2",15 + 24 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.3",15 + 28 * (buttonWidth ), 4 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.0",15 + 24 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.decimal",15 + 28 * (buttonWidth ), 5 * (buttonHeight),true);
        addItemToStringList(list, layoutName, "key.keyboard.keypad.enter",15 + 32 * (buttonWidth ), 5 * (buttonHeight),true);

        return list;
    }

}
