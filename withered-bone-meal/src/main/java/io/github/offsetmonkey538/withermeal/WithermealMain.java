package io.github.offsetmonkey538.withermeal;

import io.github.offsetmonkey538.withermeal.registry.ModBlocks;
import io.github.offsetmonkey538.withermeal.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class WithermealMain implements ModInitializer {
    public static final String MOD_ID = "withermeal";

    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
    }

    public static Identifier getId(String id) {
        return new Identifier(MOD_ID, id);
    }
}
