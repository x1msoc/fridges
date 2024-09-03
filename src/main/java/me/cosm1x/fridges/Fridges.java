package me.cosm1x.fridges;

import me.cosm1x.fridges.registry.BlockEntityTypes;
import me.cosm1x.fridges.registry.Blocks;
import me.cosm1x.fridges.registry.MenuTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fridges implements ModInitializer {

    public static final String MOD_ID = "fridges";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello from Fridges!");
        Blocks.init();
        BlockEntityTypes.init();
        MenuTypes.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
