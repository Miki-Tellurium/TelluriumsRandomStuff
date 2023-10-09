package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.util.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class AbstractSoulLavaFurnace extends AbstractSoulFueledBlockEntity {

    private static final int litFurnaceCost = 50; // How much soul lava is consumed to lit the furnace
    private static final int itemSmelted = 8; // How many item get smelted with a full "lit"
    private int progress = 0;
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    private final RecipeManager.CachedCheck<Container, Recipe<Container>> quickCheck;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AbstractSoulLavaFurnace.this.progress;
                case 1 -> AbstractSoulLavaFurnace.this.maxProgress;
                case 2 -> AbstractSoulLavaFurnace.this.litTime;
                case 3 -> AbstractSoulLavaFurnace.this.maxLitTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AbstractSoulLavaFurnace.this.progress = value;
                case 1 -> AbstractSoulLavaFurnace.this.maxProgress = value;
                case 2 -> AbstractSoulLavaFurnace.this.litTime = value;
                case 3 -> AbstractSoulLavaFurnace.this.maxLitTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    @SuppressWarnings("all")
    public AbstractSoulLavaFurnace(BlockEntityType<? extends AbstractSoulLavaFurnace> entityType, RecipeType<?> recipeType,
                                   BlockPos pos, BlockState state) {
        super(entityType, pos, state, 4000);
        this.quickCheck = RecipeManager.createCheck((RecipeType) recipeType);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

        this.validateCurrentRecipe();
        this.handleTankRefill();

        // Recipe handling
        if (this.hasValidRecipe()) {
            // Lit if furnace is not lit
            if (!this.isLit() && hasEnoughFuel()) {
                this.drainTank(litFurnaceCost);
                this.litTime = this.maxLitTime;
            }
            // If furnace is lit start smelting item
            if (this.isLit()) {
                this.progress++;
                // If progress is completed output smelted item
                if (this.progress >= this.maxProgress) {
                    this.smeltItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }

        } else {
            this.resetProgress();
        }

        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, this.isLit()), 2);
        if (this.isLit()) {
            this.litTime--;
        }
        setChanged(level, blockPos, blockState);
    }

    abstract void handleTankRefill();

    abstract void validateCurrentRecipe();

    abstract boolean hasValidRecipe();

    abstract void smeltItem();

    abstract Optional<Recipe<Container>> getRecipe();

    protected void resetProgress() {
        this.progress = 0;
    }

    protected boolean hasEnoughFuel() {
        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    public RecipeManager.CachedCheck<Container, Recipe<Container>> quickCheck() {
        return this.quickCheck;
    }

    // NBT
    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("soul_furnace.progress", this.progress);
        tag.putInt("soul_furnace.litTime", this.litTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        int x = tag.getInt("soul_furnace.progress");
        this.progress = x;
        this.litTime = tag.getInt("soul_furnace.litTime");
    }

}
