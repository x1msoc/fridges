package me.cosm1x.fridges.client.gui.block;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.Foods;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class FridgeMenu extends AbstractContainerMenu {
    private final Inventory inventory;
    private final Container container;

    public FridgeMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new SimpleContainer(27));
    }

    public FridgeMenu(int syncId, Inventory inventory, Container container) {
        super(MenuType.GENERIC_9x3, syncId);
        checkContainerSize(inventory, 27);
        this.inventory = inventory;
        this.container = container;

        inventory.startOpen(inventory.player);

        int m;
        int l;

        // FRIDGE INVENTORY
        for(l = 0; l < 3; ++l) {
            for(m = 0; m < 9; ++m) {
                this.addSlot(new Slot(container, m + l * 9, 8 + m * 18, 18 + l * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.getItem().components().has(DataComponents.FOOD);
                    }
                });
            }
        }

        // PLAYER INVENTORY
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        // HOTBAR
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotId) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotId);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (slotId < 27) {
                if (!this.moveItemStackTo(originalStack, 27, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

            return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

//    @Override
//    protected boolean moveItemStackTo(ItemStack itemStack, int i, int j, boolean bl) {
//
//        boolean bl2 = false;
//        int k = i;
//        if (bl) {
//            k = j - 1;
//        }
//
//        Slot slot;
//        ItemStack itemStack2;
//        int l;
//        if (itemStack.isStackable()) {
//            while(!itemStack.isEmpty()) {
//                if (bl) {
//                    if (k < i) {
//                        break;
//                    }
//                } else if (k >= j) {
//                    break;
//                }
//
//                slot = this.slots.get(k);
//                itemStack2 = slot.getItem();
//                if (!slot.mayPlace(itemStack)) continue;
//                if (!itemStack2.isEmpty() && ItemStack.isSameItemSameComponents(itemStack, itemStack2)) {
//                    l = itemStack2.getCount() + itemStack.getCount();
//                    int m = slot.getMaxStackSize(itemStack2);
//                    if (l <= m) {
//                        itemStack.setCount(0);
//                        itemStack2.setCount(l);
//                        slot.setChanged();
//                        bl2 = true;
//                    } else if (itemStack2.getCount() < m) {
//                        itemStack.shrink(m - itemStack2.getCount());
//                        itemStack2.setCount(m);
//                        slot.setChanged();
//                        bl2 = true;
//                    }
//                }
//
//                if (bl) {
//                    --k;
//                } else {
//                    ++k;
//                }
//            }
//        }
//
//        if (!itemStack.isEmpty()) {
//            if (bl) {
//                k = j - 1;
//            } else {
//                k = i;
//            }
//
//            while(true) {
//                if (bl) {
//                    if (k < i) {
//                        break;
//                    }
//                } else if (k >= j) {
//                    break;
//                }
//
//                slot = this.slots.get(k);
//                itemStack2 = slot.getItem();
//                if (!slot.mayPlace(itemStack)) continue;
//                if (itemStack2.isEmpty() && slot.mayPlace(itemStack)) {
//                    l = slot.getMaxStackSize(itemStack);
//                    slot.setByPlayer(itemStack.split(Math.min(itemStack.getCount(), l)));
//                    slot.setChanged();
//                    bl2 = true;
//                    break;
//                }
//
//                if (bl) {
//                    --k;
//                } else {
//                    ++k;
//                }
//            }
//        }
//
//        return bl2;
//    }
}
