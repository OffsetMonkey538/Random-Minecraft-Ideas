package io.github.offsetmonkey538.withermeal.registry;

import io.github.offsetmonkey538.withermeal.item.WitheredBoneMealItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.offsetmonkey538.withermeal.WithermealMain.getId;

@SuppressWarnings("unused")
public class ModItems {
    private ModItems() {

    }


    public static final WitheredBoneMealItem WITHERED_BONE_MEAL = register(new WitheredBoneMealItem(new FabricItemSettings().group(ItemGroup.MATERIALS)), getId("withered_bone_meal"));
    public static final Item                 WITHERED_BONE      = register(new Item(                new FabricItemSettings().group(ItemGroup.MISC)),      getId("withered_bone"));

    public static final BlockItem WITHERED_BONE_BLOCK = register(new BlockItem(ModBlocks.WITHERED_BONE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)), getId("withered_bone_block"));

    protected static <T extends Item> T register(T item, Identifier id) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static void register() {
        // Loads all items because the class gets loaded
    }
}
