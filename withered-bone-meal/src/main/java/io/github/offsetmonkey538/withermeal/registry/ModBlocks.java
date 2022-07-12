package io.github.offsetmonkey538.withermeal.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.offsetmonkey538.withermeal.WithermealMain.getId;

public class ModBlocks {
    private ModBlocks() {

    }


    public static final Block WITHERED_BONE_BLOCK = register(new PillarBlock(FabricBlockSettings.copyOf(Blocks.BONE_BLOCK).mapColor(MapColor.GRAY)), getId("withered_bone_block"));


    protected static <T extends Block> T register(T block, Identifier id) {
        return Registry.register(Registry.BLOCK, id, block);
    }

    public static void register() {
        // Loads all blocks because the class gets loaded
    }
}
