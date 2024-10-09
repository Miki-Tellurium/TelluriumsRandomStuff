package com.mikitellurium.telluriumsrandomstuff.client.hud.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class QuickMoveContainerMenu extends AbstractContainerMenu {

    private final int invSize;

    protected QuickMoveContainerMenu(@Nullable MenuType<?> menuType, int id, int invSize) {
        super(menuType, id);
        this.invSize = invSize;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + this.invSize, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + this.invSize) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int start, int end, boolean reverseDirection) {
        boolean flag = false;
        int i = start;
        if (reverseDirection) {
            i = end - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < start) {
                        break;
                    }
                } else if (i >= end) {
                    break;
                }

                if (i < TE_INVENTORY_FIRST_SLOT_INDEX || this.isItemValid(i - VANILLA_SLOT_COUNT, stack)) {

                    Slot slot = this.slots.get(i);
                    ItemStack itemstack = slot.getItem();
                    if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack)) {
                        int j = itemstack.getCount() + stack.getCount();
                        int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());
                        if (j <= maxSize) {
                            stack.setCount(0);
                            itemstack.setCount(j);
                            slot.setChanged();
                            flag = true;
                        } else if (itemstack.getCount() < maxSize) {
                            stack.shrink(maxSize - itemstack.getCount());
                            itemstack.setCount(maxSize);
                            slot.setChanged();
                            flag = true;
                        }
                    }

                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = end - 1;
            } else {
                i = start;
            }

            while(true) {
                if (reverseDirection) {
                    if (i < start) {
                        break;
                    }
                } else if (i >= end) {
                    break;
                }

                if (i < TE_INVENTORY_FIRST_SLOT_INDEX || this.isItemValid(i - VANILLA_SLOT_COUNT, stack)) {

                    Slot slot1 = this.slots.get(i);
                    ItemStack itemstack1 = slot1.getItem();
                    if (itemstack1.isEmpty() && slot1.mayPlace(stack)) {
                        if (stack.getCount() > slot1.getMaxStackSize()) {
                            slot1.setByPlayer(stack.split(slot1.getMaxStackSize()));
                        } else {
                            slot1.setByPlayer(stack.split(stack.getCount()));
                        }

                        slot1.setChanged();
                        flag = true;
                        break;
                    }

                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }

    public abstract boolean isItemValid(int slot, ItemStack itemStack);

    @Override
    public abstract boolean stillValid(Player pPlayer);

}
