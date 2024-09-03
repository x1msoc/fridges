package me.cosm1x.fridges.registry;

import me.cosm1x.fridges.Fridges;
import me.cosm1x.fridges.block.FridgeBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.List;

public class Blocks {

    public static final Block FRIDGE = register(new FridgeBlock(BlockBehaviour.Properties.of()), "fridge", true,2);

    private static Block register(Block block, String name, boolean shouldRegisterItem, int tooltipLines) {
        ResourceLocation id = Fridges.id(name);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
                    super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
                    for (int i = 1; i <= tooltipLines; i++) {
                        list.add(Component.translatable( "tooltip." + Fridges.MOD_ID + "." + name + "." + i));
                    }
                }
            };
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void init() {}
}
