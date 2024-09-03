package me.cosm1x.fridges.registry;

import me.cosm1x.fridges.Fridges;
import me.cosm1x.fridges.block.entity.FridgeBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Fridges.id(path), blockEntityType);
    }

    public static final BlockEntityType<FridgeBlockEntity> FRIDGE_BLOCk = register(
            "demo_block",
            BlockEntityType.Builder.of(FridgeBlockEntity::new, Blocks.FRIDGE).build()
    );

    public static void init() {}
}
