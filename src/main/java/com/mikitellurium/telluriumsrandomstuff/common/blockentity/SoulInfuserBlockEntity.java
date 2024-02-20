package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulInfuserMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulInfuserBlock;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulInfusionRecipe;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SoulInfuserBlockEntity extends AbstractSoulSmeltingBlockEntity<SoulInfusionRecipe> implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int INPUT_SLOT1 = 1;
    private static final int INPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT = 3;

    private int progress = 0;
    private int maxProgress = 120;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulInfuserBlockEntity.this.progress;
                case 1 -> SoulInfuserBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulInfuserBlockEntity.this.progress = value;
                case 1 -> SoulInfuserBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    @SuppressWarnings("all")
    public SoulInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_INFUSER.get(), pos, state, 4000, SoulInfusionRecipe.Type.INSTANCE,
                4, (i) -> i == INPUT_SLOT1 || i == INPUT_SLOT2, (i) -> i == OUTPUT_SLOT, BUCKET_SLOT);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }
        int cachedProgress = this.progress;
        super.tick(level, blockPos, blockState);
        level.setBlock(blockPos, blockState.setValue(SoulInfuserBlock.LIT,
                        cachedProgress >= this.maxProgress - 1 || this.isLit()), 2);
        setChanged(level, blockPos, blockState);
    }

    @Override
    protected boolean canProcessRecipe(SoulInfusionRecipe recipe) {
        if (this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getCount() >=
                this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getMaxStackSize()) return false;
        if (!this.hasEnoughFuel(recipe.getRecipeCost())) return false;
        return this.getItemHandler().getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                recipe.getResultItem(level.registryAccess()).getItem() == this.getItemHandler().getStackInSlot(OUTPUT_SLOT).getItem();
    }

    @Override
    protected void onProcessRecipe(SoulInfusionRecipe recipe) {
        this.progress++;
        if (this.progress >= this.maxProgress) {
            this.produceOutput(recipe);
            this.resetProgress();
        }
    }

    @Override
    protected void produceOutput(SoulInfusionRecipe recipe) {
        ItemStack result = recipe.assemble(this.getInventory(), level.registryAccess());
        ItemStack outputStack = this.getItemHandler().getStackInSlot(OUTPUT_SLOT);
        this.getItemHandler().getStackInSlot(INPUT_SLOT1).shrink(1);
        this.getItemHandler().getStackInSlot(INPUT_SLOT2).shrink(1);
        this.drainTank(recipe.getRecipeCost());
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
    protected Optional<SoulInfusionRecipe> getRecipe() {
        return this.quickCheck().getRecipeFor(new SimpleContainer(
                this.getItemHandler().getStackInSlot(INPUT_SLOT1),
                this.getItemHandler().getStackInSlot(INPUT_SLOT2)), this.level);
    }

    private boolean isLit() {
        return this.progress > 0;
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_infuser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulInfuserMenu(id, inventory, this, this.containerData);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("soul_infuser.progress", this.progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt("soul_infuser.progress");
    }

}
