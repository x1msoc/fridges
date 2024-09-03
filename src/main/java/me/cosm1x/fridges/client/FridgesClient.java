package me.cosm1x.fridges.client;

import me.cosm1x.fridges.client.gui.block.FridgeScreen;
import me.cosm1x.fridges.registry.MenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class FridgesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(MenuTypes.FRIDGE_MENU, FridgeScreen::new);
    }
}
