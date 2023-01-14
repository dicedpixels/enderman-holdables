package com.dicedpixels.endermanholdables;

import com.dicedpixels.endermanholdables.config.ConfigHandler;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndermanHoldables implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("enderman-holdables");

    public static ImmutableList<Block> getHoldableFlowerBlocks() {
        return ImmutableList.of(
                Blocks.DANDELION,          // minecraft:dandelion
                Blocks.POPPY,              // minecraft:poppy
                Blocks.BLUE_ORCHID,        // minecraft:blue_orchid
                Blocks.ALLIUM,             // minecraft:allium
                Blocks.AZURE_BLUET,        // minecraft:azure_bluet
                Blocks.RED_TULIP,          // minecraft:red_tulip
                Blocks.ORANGE_TULIP,       // minecraft:orange_tulip
                Blocks.WHITE_TULIP,        // minecraft:white_tulip
                Blocks.PINK_TULIP,         // minecraft:pink_tulip
                Blocks.OXEYE_DAISY,        // minecraft:oxeye_daisy
                Blocks.CORNFLOWER,         // minecraft:cornflower
                Blocks.LILY_OF_THE_VALLEY, // minecraft:lily_of_the_valley
                Blocks.WITHER_ROSE         // minecraft:wither_rose);
        );
    }

    public static ImmutableList<Block> getHoldableDirtBlocks() {
        return ImmutableList.of(
                Blocks.DIRT,                // minecraft:dirt,
                Blocks.GRASS_BLOCK,         // minecraft:grass_block,
                Blocks.PODZOL,              // minecraft:podzol,
                Blocks.COARSE_DIRT,         // minecraft:coarse_dirt,
                Blocks.MYCELIUM,            // minecraft:mycelium,
                Blocks.ROOTED_DIRT,         // minecraft:rooted_dirt,
                Blocks.MOSS_BLOCK,          // minecraft:moss_block,
                Blocks.MUD,                 // minecraft:mud,
                Blocks.MUDDY_MANGROVE_ROOTS // minecraft:muddy_mangrove_roots
        );
    }

    public static ImmutableList<Block> getHoldablelocks() {
        return ImmutableList.of(
                Blocks.GRAVEL,         // minecraft:gravel
                Blocks.BROWN_MUSHROOM, // minecraft:brown_mushroom
                Blocks.RED_MUSHROOM,   // minecraft:red_mushroom
                Blocks.TNT,            // minecraft:tnt
                Blocks.CACTUS,         // minecraft:cactus
                Blocks.CLAY,           // minecraft:clay
                Blocks.PUMPKIN,        // minecraft:pumpkin
                Blocks.CARVED_PUMPKIN, // minecraft:carved_pumpkin
                Blocks.MELON,          // minecraft:melon
                Blocks.CRIMSON_FUNGUS, // minecraft:crimson_fungus
                Blocks.CRIMSON_NYLIUM, // minecraft:crimson_nylium
                Blocks.CRIMSON_ROOTS,  // minecraft:crimson_roots
                Blocks.WARPED_FUNGUS,  // minecraft:warped_fungus
                Blocks.WARPED_NYLIUM,  // minecraft:warped_nylium
                Blocks.WARPED_ROOTS,   // minecraft:warped_roots
                Blocks.SAND,           // minecraft:sand
                Blocks.RED_SAND        // minecraft:red_sand
        );
    }

    public static ImmutableList<Block> getAllHoldablelocks() {
        return ImmutableList.<Block>builder()
                .addAll(getHoldableFlowerBlocks())
                .addAll(getHoldableDirtBlocks())
                .addAll(getHoldablelocks())
                .build();
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Enderman Holdables initialized.");
        ConfigHandler.load();
    }
}
