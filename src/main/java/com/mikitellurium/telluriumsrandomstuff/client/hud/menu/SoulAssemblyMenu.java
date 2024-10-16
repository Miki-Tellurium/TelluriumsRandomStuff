package com.mikitellurium.telluriumsrandomstuff.client.hud.menu;

import com.mikitellurium.telluriumsrandomstuff.test.bin.SoulStorage;
import com.mikitellurium.telluriumsrandomstuff.common.item.SoulStorageItem;
import com.mikitellurium.telluriumsrandomstuff.lib.TickingMenu;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModMenuTypes;
import com.mikitellurium.telluriumsrandomstuff.util.ContainerUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

public class SoulAssemblyMenu extends QuickMoveContainerMenu implements TickingMenu {

    private static final int[] ASSEMBLE_SLOTS = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
    private static final int DISASSEMBLE_SLOT = 8;
    private final ContainerLevelAccess access;
    private Mode mode = Mode.ASSEMBLE;
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return SoulStorageItem.isSoulStorageItem(stack);
        }
    };

    public SoulAssemblyMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, ContainerLevelAccess.NULL, DataSlot.standalone());
    }

    public SoulAssemblyMenu(int id, Inventory inventory, ContainerLevelAccess access, DataSlot data) {
        super(ModMenuTypes.SOUL_ASSEMBLY.get(), id, 9);
        this.access = access;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 31, 17));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 49, 17));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 67, 17));
        this.addSlot(new SlotItemHandler(itemHandler, 3, 31, 35));
        this.addSlot(new SlotItemHandler(itemHandler, 4, 67, 35));
        this.addSlot(new SlotItemHandler(itemHandler, 5, 31, 53));
        this.addSlot(new SlotItemHandler(itemHandler, 6, 49, 53));
        this.addSlot(new SlotItemHandler(itemHandler, 7, 67, 53));
        this.addSlot(new SlotItemHandler(itemHandler, 8, 125, 35));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    public Mode getMode() {
        return this.mode;
    }

    public Mode getOppositeMode() {
        return this.mode == Mode.ASSEMBLE ? Mode.DISASSEMBLE : Mode.ASSEMBLE;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    private static void handleRecipe(ItemStackHandler itemHandler, Mode mode, RandomSource random) {
        if (mode == Mode.ASSEMBLE) {

        } else {

        }
    }

    private static OptionalInt findAvailableSlot(ItemStackHandler itemHandler, EntityType<?> entityType) {
        for (int i = 0; i < itemHandler.getSlots() - 1; i++) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                return OptionalInt.of(i);
            } else if (itemStack.is(ModItems.SMALL_SOUL_FRAGMENT.get()) && itemStack.getCount() < itemStack.getMaxStackSize() &&
                    SoulStorage.evaluate(itemStack, (storage) -> storage.contains(entityType))) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    @Override
    public void tickMenu(ServerPlayer player) {
        this.access.execute((level, pos) -> handleRecipe(this.itemHandler, this.mode, level.random));
    }

    @Override
    public void removed(Player player) {
        this.access.execute((level, pos) -> this.clearContainer(player, ContainerUtil.fromItemHandler(this.itemHandler)));
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return this.itemHandler.isItemValid(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.SOUL_ASSEMBLY_TABLE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public enum Mode {
        ASSEMBLE,
        DISASSEMBLE
    }

}
