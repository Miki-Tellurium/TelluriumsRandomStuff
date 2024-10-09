package com.mikitellurium.telluriumsrandomstuff.client.hud.menu;

import com.mikitellurium.telluriumsrandomstuff.common.blockentity.AbstractSoulFueledBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractSoulFuelMenu extends QuickMoveContainerMenu {

    private final AbstractSoulFueledBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;
    private final int invSize;
    private final int invYOffset;

    public AbstractSoulFuelMenu(int id, MenuType menuType, Inventory inventory, int invSize,
                                BlockEntity entity, ContainerData data) {
        this(id, menuType, inventory, invSize, 0, entity, data);
    }

    public AbstractSoulFuelMenu(int id, MenuType menuType, Inventory inventory, int invSize, int invYOffset,
                                BlockEntity entity, ContainerData data) {
        super(menuType, id, invSize);
        checkContainerSize(inventory, invSize);
        blockEntity = (AbstractSoulFueledBlockEntity) entity;
        this.level = inventory.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();
        this.invSize = invSize;
        this.invYOffset = invYOffset;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addDataSlots(data);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, blockEntity.getBlockState().getBlock());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18 + invYOffset));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142 + invYOffset));
        }
    }

    public boolean isCrafting() {
        return this.getData().get(0) > 0;
    }

    public int getScaledProgress(int scale) {
        int progress = this.getData().get(0);
        int maxProgress = this.getData().get(1);

        return maxProgress != 0 && progress != 0 ? progress * scale / maxProgress : 0;
    }

    public AbstractSoulFueledBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public ContainerData getData() {
        return this.data;
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

}
