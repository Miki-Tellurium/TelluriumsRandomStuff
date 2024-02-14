package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulFurnaceMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulFurnaceBlock;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SoulFurnaceBlockEntity extends AbstractSoulSmeltingBlockEntity<SmeltingRecipe> implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private static final int litFurnaceCost = 50; // How much soul lava is consumed to lit the furnace
    private static final int itemSmelted = 8; // How many item get smelted with a full "lit"
    private int progress = 0;
    private int maxProgress = 100;
    private int litTime = 0;
    private int maxLitTime = maxProgress * itemSmelted;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress;
                case 2 -> SoulFurnaceBlockEntity.this.litTime;
                case 3 -> SoulFurnaceBlockEntity.this.maxLitTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulFurnaceBlockEntity.this.progress = value;
                case 1 -> SoulFurnaceBlockEntity.this.maxProgress = value;
                case 2 -> SoulFurnaceBlockEntity.this.litTime = value;
                case 3 -> SoulFurnaceBlockEntity.this.maxLitTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public SoulFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_FURNACE.get(), pos, state, 4000, RecipeType.SMELTING,
                3, (i) -> i == INPUT_SLOT, (i) -> i == OUTPUT_SLOT, BUCKET_SLOT);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }
        super.tick(level, blockPos, blockState);
        level.setBlock(blockPos, blockState.setValue(SoulFurnaceBlock.LIT, this.isLit()), 2);
        if (this.isLit()) {
            this.litTime--;
        }
        setChanged(level, blockPos, blockState);
    }

    @Override
    protected boolean canProcessRecipe(SmeltingRecipe recipe) {
        if (this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getCount() >=
                this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;
        return this.getItemHandler().getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                recipe.getResultItem(level.registryAccess()).getItem() == this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getItem();
    }

    @Override
    protected void onProcessRecipe(SmeltingRecipe recipe) {
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
                this.produceOutput(recipe);
                this.resetProgress();
            }
        } else {
            this.resetProgress();
        }
    }

    @Override
    protected void produceOutput(SmeltingRecipe recipe) {
        ItemStack result = recipe.assemble(this.getInventory(), level.registryAccess());
        ItemStack outputStack = this.getItemHandler().getStackInSlot(OUTPUT_SLOT);
        this.getItemHandler().getStackInSlot(INPUT_SLOT).shrink(1);
        if (outputStack.isEmpty()) {
            this.getItemHandler().setStackInSlot(OUTPUT_SLOT, result);
        } else {
            outputStack.grow(result.getCount());
        }
    }

    @Override
    protected void resetProgress() {
        this.progress = 0;
    }

    @Override
    protected Optional<SmeltingRecipe> getRecipe() {
        return this.quickCheck().getRecipeFor(
                new SimpleContainer(this.getItemHandler().getStackInSlot(INPUT_SLOT)), this.level);
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    private boolean hasEnoughFuel() {
        return this.getFluidTank().getFluidAmount() >= litFurnaceCost;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulFurnaceMenu(id, inventory, this, this.getContainerData());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("soul_furnace.progress", this.progress);
        tag.putInt("soul_furnace.litTime", this.litTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt("soul_furnace.progress");
        this.litTime = tag.getInt("soul_furnace.litTime");
    }

}
