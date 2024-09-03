package me.cosm1x.fridges.registry;

import me.cosm1x.fridges.Fridges;
import me.cosm1x.fridges.client.gui.block.FridgeMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MenuTypes {

    public static final MenuType<FridgeMenu> FRIDGE_MENU = register("fridge", new MenuType<FridgeMenu>(FridgeMenu::new, FeatureFlagSet.of()));

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType<T> menuType) {
        return Registry.register(BuiltInRegistries.MENU, Fridges.id(name), menuType);
    }

    public static void init() {}
}
