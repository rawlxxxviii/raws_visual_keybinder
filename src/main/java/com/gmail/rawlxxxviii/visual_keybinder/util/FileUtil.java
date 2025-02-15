package com.gmail.rawlxxxviii.visual_keybinder.util;

import com.gmail.rawlxxxviii.visual_keybinder.KeybindingPreset;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    private static final String FOLDER_LOCATION = "keybinding presets";
    private static final String PRESET_EXTENSION = ".preset.txt";
    private static final String IS_INITIALIZED_FILE_NAME = "initialized.dat";
    private static final String LOAD_ON_STARTUP_FILE_NAME = "preset_to_load_on_first_startup.txt";
    private static final String LOAD_ON_STARTUP_FILE_CONTENT = "// Fill in the name of the preset on the next line, without '.preset.txt'. It will only be loaded if initialized is not present.";


    public static boolean createInitializedFile(){
        try {
            return new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + IS_INITIALIZED_FILE_NAME ))).createNewFile();
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static boolean isInitializedFileCreated(){
        return new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + IS_INITIALIZED_FILE_NAME ))).isFile();
    }

    public static void loadInitialPreset(Options options){

        try {
            enforceDirectory();

            var file = new File(String.valueOf(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + LOAD_ON_STARTUP_FILE_NAME)));
            if(// create file if not exist
                !file.exists() || !file.isFile()
            ){
                FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + LOAD_ON_STARTUP_FILE_NAME).toString());
                writer.append(LOAD_ON_STARTUP_FILE_CONTENT);
                writer.append("\n");
                writer.close();
            }else{

                if(!isInitializedFileCreated()){

                    var allLines = getAllLines(LOAD_ON_STARTUP_FILE_NAME);

                    if(allLines.size() > 1){
                        var name = allLines.get(1);
                        if(!name.isBlank()){
                            loadPreset(options, name);
                        }
                    }

                }
            }


            createInitializedFile();

        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }





    }

    @Nullable
    public static List<KeybindingPreset> getPresets()  {

        try {
            List<KeybindingPreset> presetList = new ArrayList<>();
            for (var name : getFileList()){

                var allLines = getAllLines(name + PRESET_EXTENSION);
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
            return presetList;

        }catch (Exception ex){
            return null;
        }
    }

    public static void savePreset(Options options, String name) throws IOException {

        var allNames = getFileList().stream().filter(x->x.equals(name));
        if(allNames.anyMatch(x -> x.equals(name))){

            var allLines = getAllLines(name + PRESET_EXTENSION);
            var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
            if(isReadOnly){
                return;
            }

        }

        FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + name + PRESET_EXTENSION).toString());
        for (var keymapping : options.keyMappings){
            String mappingName = keymapping.getName();
            String key = keymapping.saveString() + (keymapping.getKeyModifier() != net.minecraftforge.client.settings.KeyModifier.NONE ? ":" + keymapping.getKeyModifier() : "");
            writer.append(mappingName).append(":").append(key);
            writer.append("\n");
        }
        writer.close();


    }

    public static String sanitizeString(String input){
        return input.replaceAll("[^a-zA-Z0-9._]+", "_");
    }

    public static void loadPreset(Options options, String name) throws IOException {

        var allNames = getFileList().stream().filter(x->x.equals(name));
        if(allNames.noneMatch(x -> x.equals(name))){
            return;
        }
        var allLines = getAllLines(name + PRESET_EXTENSION).stream().filter(x->!x.equals("readonly") && !x.isBlank()).toList();


        for (var keymapping : options.keyMappings){

            String mappingName = keymapping.getName();

            var presetValue = allLines.stream().filter(x -> x.startsWith(mappingName + ":")).findFirst();
            presetValue.ifPresentOrElse(
                    x->{
                        var value = x.substring(mappingName.length() + 1 );

                        if (value.indexOf(':') != -1) {
                            String[] pts = value.split(":");
                            keymapping.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.valueFromString(pts[1]), InputConstants.getKey(pts[0]));
                        } else {
                            keymapping.setKeyModifierAndCode(net.minecraftforge.client.settings.KeyModifier.NONE, InputConstants.getKey(value));
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

        var allLines = getAllLines(name + PRESET_EXTENSION);
        var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
        if(isReadOnly){
            return;
        }

        Files.delete(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + name + PRESET_EXTENSION));
    }

    public static List<String> getFileList() throws IOException {

        enforceDirectory();

        try (Stream<Path> stream = Files.list( FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION)) ){


            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(x-> x.getFileName().toString())
                    .filter(file -> file.endsWith(PRESET_EXTENSION))
                    .map(file-> file.substring(0,file.length()-PRESET_EXTENSION.length())).toList();

        }
    }

    public static List<String> getAllLines(String fileName) throws IOException {
        return
            Files.readAllLines(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION + File.separator + fileName));

    }

    private static void enforceDirectory() throws IOException {
        Files.createDirectories(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION));
    }



}
