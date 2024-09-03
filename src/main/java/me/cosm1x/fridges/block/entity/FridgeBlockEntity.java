package me.cosm1x.fridges.block.entity;

import me.cosm1x.fridges.client.gui.block.FridgeMenu;
import me.cosm1x.fridges.registry.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FridgeBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    private int timer = 0;
    private static final int SECOND = 20;
    private final NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    public FridgeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.FRIDGE_BLOCk, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("Fridge");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items.clear();
        for (int i = 0; i < nonNullList.size(); i++) {
            this.items.set(i, nonNullList.get(i));
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new FridgeMenu(i, inventory, this);
    }


    public static void tick(Level level, BlockPos blockPos, BlockState state, FridgeBlockEntity fridgeBlockEntity) {
        if (level.isClientSide) return;
        fridgeBlockEntity.timer++;
        if (fridgeBlockEntity.timer >= 60 * SECOND) {
            fridgeBlockEntity.timer = 0;
            for (int i = 0; i < fridgeBlockEntity.items.size(); i++) {
                ItemStack itemStack = fridgeBlockEntity.items.get(i);
                if (!itemStack.isEmpty() && itemStack.getItem().equals(Items.ROTTEN_FLESH)) {
                    itemStack.shrink(1);
                    fridgeBlockEntity.addItem(new ItemStack(Items.LEATHER));
                    break;
                }
            }
        }
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);
        ContainerHelper.loadAllItems(compoundTag, this.items, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        ContainerHelper.saveAllItems(compoundTag, this.items, provider);
    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack itemStack) {
        for(int i = 0; i < this.items.size(); ++i) {
            ItemStack itemStack2 = this.getItem(i);
            if (ItemStack.isSameItemSameComponents(itemStack2, itemStack)) {
                this.moveItemsBetweenStacks(itemStack, itemStack2);
                if (itemStack.isEmpty()) {
                    return;
                }
            }
        }
    }

    private void moveItemsBetweenStacks(ItemStack itemStack, ItemStack itemStack2) {
        int i = this.getMaxStackSize(itemStack2);
        int j = Math.min(itemStack.getCount(), i - itemStack2.getCount());
        if (j > 0) {
            itemStack2.grow(j);
            itemStack.shrink(j);
            this.setChanged();
        }
    }

    public ItemStack addItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack2 = itemStack.copy();
            this.moveItemToOccupiedSlotsWithSameType(itemStack2);
            if (itemStack2.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                this.moveItemToEmptySlots(itemStack2);
                return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
            }
        }
    }

    private void moveItemToEmptySlots(ItemStack itemStack) {
        for(int i = 0; i < this.items.size(); ++i) {
            ItemStack itemStack2 = this.getItem(i);
            if (itemStack2.isEmpty()) {
                this.setItem(i, itemStack.copyAndClear());
                return;
            }
        }
    }
}
