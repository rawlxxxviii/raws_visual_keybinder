package com.gmail.rawlxxxviii.visual_keybinder.util;

import com.gmail.rawlxxxviii.visual_keybinder.KeyBoardLayout;
import com.gmail.rawlxxxviii.visual_keybinder.KeybindingPreset;
import com.gmail.rawlxxxviii.visual_keybinder.KeyboardLayoutKey;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.settings.KeyModifier;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileUtil {

    private static final String BASE_FOLDER = "visual_keybinder";
    private static final String PRESET_FOLDER = "presets";
    private static final String LAYOUT_FOLDER = "layouts";
    private static final String LAYOUT_EXTENSION = ".layout.txt";
    private static final String PRESET_EXTENSION = ".preset.txt";
    private static final String IS_INITIALIZED_FILE_NAME = "initialized.dat";
    private static final String LOAD_ON_STARTUP_FILE_NAME = "preset to load on first startup.txt";
    private static final String LOAD_ON_STARTUP_FILE_CONTENT = "// Fill in the name of the preset on the next line, without '.preset.txt'. It will only be loaded if initialized is not present.";

    private  static final int BUTTON_WIDTH = 16;
    private  static final int BUTTON_HEIGHT = 16;

    private static List<KeyboardLayoutKey> getLayout1(){

        var list = new ArrayList<KeyboardLayoutKey>();
        list.add(new KeyboardLayoutKey("key.keyboard.f1",0 , 0));

        list.add(new KeyboardLayoutKey("key.keyboard.f1", 0 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f2", 1 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f3", 2 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f4", 3 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f5", 5 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f6", 6 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f7", 7 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f8", 8 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f9", 10 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f10", 11 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f11", 12 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f12", 13 * BUTTON_WIDTH, 0));

        list.add(new KeyboardLayoutKey("key.keyboard.grave.accent", -1 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.1", 0 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.2", 1 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.3", 2 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.4", 3 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.5", 4 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.6", 5 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.7", 6 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.8", 7 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.9", 8 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.0", 9 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.minus", 10 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.equal", 11 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.backspace", 12 * BUTTON_WIDTH, BUTTON_HEIGHT, true));

        list.add(new KeyboardLayoutKey("key.keyboard.tab",1 + -4 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.q",1 + 0 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.w",1 + 1 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.e",1 + 2 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.r",1 + 3 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.t",1 + 4 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.y",1 + 5 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.u",1 + 6 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.i",1 + 7 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.o",1 + 8 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.p",1 + 9 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.left.bracket",1 + 10 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.right.bracket",1 + 11 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.backslash",1 + 12 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.caps.lock",2 + -4 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.a",2 + 0 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.s",2 + 1 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.d",2 + 2 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.f",2 + 3 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.g",2 + 4 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.h",2 + 5 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.j",2 + 6 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.k",2 + 7 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.l",2 + 8 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.semicolon",2 + 9 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.apostrophe",2 + 10 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.enter",2 + 11 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left.shift",3 + -4 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.z",3 + 0 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.x",3 + 1 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.c",3 + 2 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.v",3 + 3 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.b",3 + 4 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.n",3 + 5 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.m",3 + 6 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.comma",3 + 7 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.period",3 + 8 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.slash",3 + 9 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.right.shift",3 + 10 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left",13 + 3 + 15 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.down",13 + 3 + 17 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right",13 + 3 + 19 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.up",13 + 3 + 17 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.home",13 + 3 + 15 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.end",13 + 3 + 19 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.insert",13 + 3 + 15 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.page.up",13 + 3 + 19 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.delete",13 + 3 + 15 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.page.down",13 + 3 + 19 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left.control",3 + -3 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.left.alt",3 + 1 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right.alt",3 + 5 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right.control",3 + 9* BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.space",3 + 3 * BUTTON_WIDTH, 6 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.keypad.divide", 15 + 27 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.multiply", 15 + 31 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.subtract", 15 + 35 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.7", 15 + 23 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.8", 15 + 27 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.9", 15 + 31 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.add", 15 + 35 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.4",15 + 23 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.5",15 + 27 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.6", 15 + 31 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.1", 15 + 23 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.2", 15 + 27 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.3", 15 + 31 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.0", 15 + 27 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.decimal", 15 + 31 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.enter", 15 + 35 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.mouse.left",3 + 39 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.middle",3 + 41 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.right",3 + 43 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.5",3 + 40 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT +5,true));
        list.add(new KeyboardLayoutKey("key.mouse.4",3 + 41 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT +5,true));

        return list;
    }

    private static List<KeyboardLayoutKey> getLayout2(){

        var list = new ArrayList<KeyboardLayoutKey>();

        list.add(new KeyboardLayoutKey("key.mouse.left",3 + 39 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.middle",3 + 41 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.right",3 + 43 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT ,true));
        list.add(new KeyboardLayoutKey("key.mouse.5",3 + 40 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT +5,true));
        list.add(new KeyboardLayoutKey("key.mouse.4",3 + 41 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT +5,true));

        return list;
    }

    private static List<KeyboardLayoutKey> getLayout3(){

        var list = new ArrayList<KeyboardLayoutKey>();
        list.add(new KeyboardLayoutKey("key.keyboard.f1",0 , 0));

        list.add(new KeyboardLayoutKey("key.keyboard.f1", 0 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f2", 1 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f3", 2 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f4", 3 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f5", 5 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f6", 6 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f7", 7 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f8", 8 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f9", 10 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f10", 11 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f11", 12 * BUTTON_WIDTH, 0));
        list.add(new KeyboardLayoutKey("key.keyboard.f12", 13 * BUTTON_WIDTH, 0));

        list.add(new KeyboardLayoutKey("key.keyboard.grave.accent", -1 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.1", 0 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.2", 1 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.3", 2 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.4", 3 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.5", 4 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.6", 5 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.7", 6 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.8", 7 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.9", 8 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.0", 9 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.minus", 10 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.equal", 11 * BUTTON_WIDTH, BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.backspace", 12 * BUTTON_WIDTH, BUTTON_HEIGHT, true));

        list.add(new KeyboardLayoutKey("key.keyboard.tab",1 + -4 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.q",1 + 0 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.w",1 + 1 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.e",1 + 2 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.r",1 + 3 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.t",1 + 4 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.y",1 + 5 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.u",1 + 6 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.i",1 + 7 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.o",1 + 8 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.p",1 + 9 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.left.bracket",1 + 10 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.right.bracket",1 + 11 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.backslash",1 + 12 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.caps.lock",2 + -4 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.a",2 + 0 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.s",2 + 1 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.d",2 + 2 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.f",2 + 3 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.g",2 + 4 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.h",2 + 5 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.j",2 + 6 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.k",2 + 7 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.l",2 + 8 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.semicolon",2 + 9 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.apostrophe",2 + 10 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.enter",2 + 11 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left.shift",3 + -4 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.z",3 + 0 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.x",3 + 1 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.c",3 + 2 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.v",3 + 3 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.b",3 + 4 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.n",3 + 5 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.m",3 + 6 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.comma",3 + 7 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.period",3 + 8 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.slash",3 + 9 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT));
        list.add(new KeyboardLayoutKey("key.keyboard.right.shift",3 + 10 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left",13 + 3 + 15 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.down",13 + 3 + 17 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right",13 + 3 + 19 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.up",13 + 3 + 17 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.home",13 + 3 + 15 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.end",13 + 3 + 19 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.insert",13 + 3 + 15 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.page.up",13 + 3 + 19 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.delete",13 + 3 + 15 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.page.down",13 + 3 + 19 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.left.control",3 + -3 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.left.alt",3 + 1 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right.alt",3 + 5 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.right.control",3 + 9* BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));

        list.add(new KeyboardLayoutKey("key.keyboard.space",3 + 3 * BUTTON_WIDTH, 6 * BUTTON_HEIGHT,true));

        return list;
    }

    private static List<KeyboardLayoutKey> getLayout4(){

        var list = new ArrayList<KeyboardLayoutKey>();

        list.add(new KeyboardLayoutKey("key.keyboard.keypad.divide", 15 + 27 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.multiply", 15 + 31 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.subtract", 15 + 35 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.7", 15 + 23 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.8", 15 + 27 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.9", 15 + 31 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.add", 15 + 35 * BUTTON_WIDTH, 2 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.4",15 + 23 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.5",15 + 27 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.6", 15 + 31 * BUTTON_WIDTH, 3 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.1", 15 + 23 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.2", 15 + 27 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.3", 15 + 31 * BUTTON_WIDTH, 4 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.0", 15 + 27 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.decimal", 15 + 31 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));
        list.add(new KeyboardLayoutKey("key.keyboard.keypad.enter", 15 + 35 * BUTTON_WIDTH, 5 * BUTTON_HEIGHT,true));

        return list;
    }

    private static List<KeyboardLayoutKey> getLayout5(){

        var list = new ArrayList<KeyboardLayoutKey>();

        list.add(new KeyboardLayoutKey("key.keyboard.f1", 0 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f2", 1 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f3", 2 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f4", 3 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f5", 5 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f6", 6 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f7", 7 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f8", 8 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f9", 10 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f10", 11 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f11", 12 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f12", 13 * BUTTON_WIDTH, 0 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f13", 0 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f14", 1 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f15", 2 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f16", 3 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f17", 5 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f18", 6 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f19", 7 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f20", 8 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f21", 10 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f22", 11 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f23", 12 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));
        list.add(new KeyboardLayoutKey("key.keyboard.f24", 13 * BUTTON_WIDTH, 1 * BUTTON_HEIGHT - 5));

        return list;
    }

    private static String createFileContents(List<KeyboardLayoutKey> items){
        StringBuilder a = new StringBuilder();
        items.forEach(x->
                a.append(x.toString()).append("\n"));
        return a.toString();
    }

    public static void createInitializedFile(){
        try {
            new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + IS_INITIALIZED_FILE_NAME))).createNewFile();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static boolean isInitializedFileCreated(){
        return new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + IS_INITIALIZED_FILE_NAME ))).isFile();
    }

    public static void createDefaultLayouts(){

        try {
            enforceLayoutDirectory();

            saveLayoutFile("1.Full Keyboard and mouse" + LAYOUT_EXTENSION, createFileContents(getLayout1()));
            saveLayoutFile("2.Mouse" + LAYOUT_EXTENSION, createFileContents(getLayout2()));
            saveLayoutFile("3.Keyboard" + LAYOUT_EXTENSION, createFileContents(getLayout3()));
            saveLayoutFile("4.Keypad" + LAYOUT_EXTENSION, createFileContents(getLayout4()));
            saveLayoutFile("5.Function keys" + LAYOUT_EXTENSION, createFileContents(getLayout5()));

        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    public static void loadInitialPreset(Options options){

        try {
            enforceBaseDirectory();

            var file = new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LOAD_ON_STARTUP_FILE_NAME)));
            if(!file.exists() || !file.isFile()){
                FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LOAD_ON_STARTUP_FILE_NAME).toString());
                writer.append(LOAD_ON_STARTUP_FILE_CONTENT);
                writer.append("\n");
                writer.close();
            }else{

                if(!isInitializedFileCreated()){

                    var allLines = getAllFileLines(LOAD_ON_STARTUP_FILE_NAME);

                    if(allLines.size() > 1){
                        var name = allLines.get(1);
                        if(!name.isBlank()){
                            loadPreset(options, name);
                        }
                    }

                }
            }



        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }





    }

    @Nullable
    public static List<KeybindingPreset> getPresets()  {

        try {
            List<KeybindingPreset> presetList = new ArrayList<>();
            for (var name : getPresetFileList()){

                var allLines = getAllPresetFileLines(name + PRESET_EXTENSION);
                var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");

                List<String> lines = new ArrayList<>();

                allLines.forEach(x->
                {
                    if(x.equals("readonly")){
                        return;
                    }
                    lines.add(x);
                });

                presetList.add(new KeybindingPreset(name,isReadOnly,lines));

            }
            presetList.sort(Comparator.comparing(KeybindingPreset::getName));
            return presetList;

        }catch (Exception ex){
            return null;
        }
    }

    public static void saveLayoutFile(String fileName, String contents) throws IOException {

        enforceLayoutDirectory();

        FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LAYOUT_FOLDER + File.separator + fileName).toString());
        writer.append(contents);
        writer.close();


    }

    public static void savePreset(Options options, String name) throws IOException {

        enforcePresetDirectory();

        var allNames = getPresetFileList().stream().filter(x->x.equals(name));
        if(allNames.anyMatch(x -> x.equals(name))){

            var allLines = getAllPresetFileLines(name + PRESET_EXTENSION);
            var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
            if(isReadOnly){
                return;
            }

        }

        FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + PRESET_FOLDER + File.separator + name + PRESET_EXTENSION).toString());
        for (var keymapping : options.keyMappings){
            String mappingName = keymapping.getName();
            String key = keymapping.saveString() + (keymapping.getKeyModifier() != net.neoforged.neoforge.client.settings.KeyModifier.NONE ? ":" + keymapping.getKeyModifier() : "");
            writer.append(mappingName).append(":").append(key);
            writer.append("\n");
        }
        writer.close();



    }

    public static String sanitizeString(String input){
        return input.replaceAll("[^a-zA-Z0-9._\\s]+", "_");
    }

    public static List<KeyBoardLayout> getKeyboardLayoutsFromFile() {

        // 1. ADD THIS SAFETY CHECK:
        if (getLayoutFileList().isEmpty()) {
            createDefaultLayouts();
        }

        var result = new ArrayList<KeyBoardLayout>();
        var layoutFileList = new ArrayList<>(getLayoutFileList());

        // ... the rest of the code stays the same

        layoutFileList.sort(
                Comparator.comparingInt(a ->
                        Integer.parseInt(a.split("\\.")[0])
                ));

        for (String a : layoutFileList) {

            var split = a.split("\\.");
            var name = split[1];
            var layoutFileLines = getAllLayoutFileLines(a + LAYOUT_EXTENSION);
            if (layoutFileLines == null) {
                continue;
            }
            var keyboardLayoutKeys = layoutFileLines.stream()
                    .map(KeyboardLayoutKey::fromString)
                    .filter(Objects::nonNull)
                    .toList();

            result.add(
                    new KeyBoardLayout(
                            Component.literal(name),
                            keyboardLayoutKeys
                    )
            );


        }

        return result;
    }

    public static void loadPreset(Options options, String name) throws IOException {

        var allNames = getPresetFileList().stream().filter(x->x.equals(name));
        if(allNames.noneMatch(x -> x.equals(name))){
            return;
        }
        var allLines = getAllPresetFileLines(name + PRESET_EXTENSION).stream().filter(x->!x.equals("readonly") && !x.isBlank()).toList();


        for (var keymapping : options.keyMappings){

            String mappingName = keymapping.getName();

            var presetValue = allLines.stream().filter(x -> x.startsWith(mappingName + ":")).findFirst();
            presetValue.ifPresentOrElse(
                    x->{
                        var value = x.substring(mappingName.length() + 1 );

                        if (value.indexOf(':') != -1) {
                            String[] pts = value.split(":");
                            keymapping.setKeyModifierAndCode(net.neoforged.neoforge.client.settings.KeyModifier.valueFromString(pts[1]), InputConstants.getKey(pts[0]));
                        } else {
                            keymapping.setKeyModifierAndCode(net.neoforged.neoforge.client.settings.KeyModifier.NONE, InputConstants.getKey(value));
                        }
                    },
                    () -> {
                        keymapping.setKeyModifierAndCode(KeyModifier.NONE,InputConstants.UNKNOWN);
                    }
                );

        }

        options.save();
        KeyMapping.resetMapping();


    }

    public static void deletePreset(String name) throws IOException {

        var allLines = getAllPresetFileLines(name + PRESET_EXTENSION);
        var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
        if(isReadOnly){
            return;
        }

        Files.delete(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + PRESET_FOLDER + File.separator + name + PRESET_EXTENSION));
    }

    public static List<String> getPresetFileList() throws IOException {

        enforcePresetDirectory();

        try (Stream<Path> stream = Files.list( FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + PRESET_FOLDER)) ){


            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(x-> x.getFileName().toString())
                    .filter(file -> file.endsWith(PRESET_EXTENSION))
                    .map(file-> file.substring(0,file.length()-PRESET_EXTENSION.length())).toList();

        }
    }

    public static List<String> getLayoutFileList()  {

        enforceLayoutDirectory();

        try (Stream<Path> stream = Files.list( FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LAYOUT_FOLDER)) ){

            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(x-> x.getFileName().toString())
                    .filter(file -> {
                        var split = file.split("\\.");
                        try {
                            Integer.parseInt(split[0]);
                        }catch (Exception ex){
                            return false;
                        }
                        return split.length == 4;
                    })
                    .filter(file -> file.endsWith(LAYOUT_EXTENSION))
                    .map(file-> file.substring(0,file.length()-PRESET_EXTENSION.length())).toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllFileLines(String fileName) throws IOException {
        return
                Files.readAllLines(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + fileName));

    }

    public static List<String> getAllPresetFileLines(String fileName) throws IOException {
        return
                Files.readAllLines(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + PRESET_FOLDER + File.separator + fileName));

    }

    @Nullable
    public static List<String> getAllLayoutFileLines(String fileName)  {
        try {
            return
                    Files.readAllLines(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LAYOUT_FOLDER + File.separator + fileName));
        } catch (IOException e) {
            return null;
        }

    }

    private static void enforceBaseDirectory(){
        try {
            Files.createDirectories(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void enforcePresetDirectory()  {
        try {
            Files.createDirectories(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + PRESET_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void enforceLayoutDirectory()  {
        try {
            Files.createDirectories(FMLPaths.GAMEDIR.get().resolve(BASE_FOLDER + File.separator + LAYOUT_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
