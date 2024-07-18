package com.gmail.rawlxxxviii.visual_keybinder;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Options;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    private static final String FOLDER_LOCATION = "keybinding presets";


    @Nullable
    public static List<KeybindingPreset> getPresets()  {

        try {
            List<KeybindingPreset> presetList = new ArrayList<>();
            for (var name : getFileList()){

                var allLines = getAllLines(name);
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

            var allLines = getAllLines(name);
            var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
            if(isReadOnly){
                return;
            }

        }

        FileWriter writer = new FileWriter(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION+"\\"+name+".txt").toString());
        for (var keymapping : options.keyMappings){
            String mappingName = keymapping.getName();
            String key = keymapping.saveString() + (keymapping.getKeyModifier() != net.minecraftforge.client.settings.KeyModifier.NONE ? ":" + keymapping.getKeyModifier() : "");
            writer.append(mappingName).append(":").append(key);
            writer.append("\n");
        }
        writer.close();


    }
    public static void loadPreset(Options options, String name) throws IOException {

        var allNames = getFileList().stream().filter(x->x.equals(name));
        if(allNames.noneMatch(x -> x.equals(name))){
            return;
        }
        var allLines = getAllLines(name).stream().filter(x->!x.equals("readonly") && !x.isBlank()).toList();


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


    }

    public static void deletePreset(String name) throws IOException {

        var allLines = getAllLines(name);
        var isReadOnly = !allLines.isEmpty() && allLines.get(0).equals("readonly");
        if(isReadOnly){
            return;
        }

        Files.delete(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION+"\\"+name+".txt"));
    }

    public static List<String> getFileList() throws IOException {

        enforceDirectory();

        try (Stream<Path> stream = Files.list( FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION)) ){


            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(x-> x.getFileName().toString())
                    .filter(file -> file.endsWith(".txt"))
                    .map(file-> file.substring(0,file.length()-4)).toList();

        }
    }

    public static List<String> getAllLines(String name) throws IOException {
        return
            Files.readAllLines(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION+"\\"+name+".txt"));

    }

    private static void enforceDirectory() throws IOException {
        Files.createDirectories(FMLPaths.GAMEDIR.get().resolve(FOLDER_LOCATION));
    }



}
