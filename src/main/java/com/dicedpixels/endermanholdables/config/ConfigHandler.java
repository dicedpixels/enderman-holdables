package com.dicedpixels.endermanholdables.config;

import com.dicedpixels.endermanholdables.EndermanHoldables;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashSet;

public class ConfigHandler {
    private static final HashSet<String> holdableBlocks = new HashSet<>();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("enderman-holdables.json");
    private static final HashSet<String> defaultHoldableBlocks = new HashSet<>(EndermanHoldables.getAllHoldablelocks().stream().map(b -> Registries.BLOCK.getId(b).toString()).toList());

    private static String getBlockId(Block block) {
        return Registries.BLOCK.getId(block).toString();
    }

    public static boolean getValue(Block block) {
        return holdableBlocks.contains(getBlockId(block));
    }

    public static void clearValues() {
        holdableBlocks.clear();
    }

    public static void setValue(Block block) {
        holdableBlocks.add(getBlockId(block));
    }

    public static void toggleValue(Block block) {
        if (holdableBlocks.contains(getBlockId(block))) {
            holdableBlocks.remove(getBlockId(block));
        } else {
            holdableBlocks.add(getBlockId(block));
        }
    }

    public static void load() {
        if (!configFile.toFile().exists()) {
            EndermanHoldables.LOGGER.warn("Enderman Holdables could not load the config file.");
            holdableBlocks.clear();
            holdableBlocks.addAll(defaultHoldableBlocks);
        } else {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(configFile.toFile())) {
                Type setType = new TypeToken<HashSet<String>>() {}.getType();
                HashSet<String> blocksFromConfig = gson.fromJson(reader, setType);
                blocksFromConfig.removeIf(b -> !defaultHoldableBlocks.contains(b));
                holdableBlocks.clear();
                holdableBlocks.addAll(blocksFromConfig);
            } catch (FileNotFoundException e) {
                //
            } catch (IOException e) {
                EndermanHoldables.LOGGER.error("Enderman Holdables encountered an error while reading the config file.");
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(holdableBlocks, writer);
        } catch (IOException e) {
            EndermanHoldables.LOGGER.error("Enderman Holdables encountered an error while saving the config file.");
        }
    }
}
