package com.mikitellurium.telluriumsrandomstuff.common.blockentity;

import com.mikitellurium.telluriumsrandomstuff.client.gui.menu.SoulCompactorMenu;
import com.mikitellurium.telluriumsrandomstuff.common.block.SoulInfuserBlock;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.CompactingRecipe;
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

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public class SoulCompactorBlockEntity extends AbstractSoulSmeltingBlockEntity<CompactingRecipe> implements MenuProvider {

    private static final int BUCKET_SLOT = 0;
    private static final int[] INPUT_SLOTS = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
    private static final int OUTPUT_SLOT = 9;

    private int progress = 0;
    private int maxProgress = 120;
    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> SoulCompactorBlockEntity.this.progress;
                case 1 -> SoulCompactorBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> SoulCompactorBlockEntity.this.progress = value;
                case 1 -> SoulCompactorBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public SoulCompactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SOUL_COMPACTOR.get(), pos, state, 4000, CompactingRecipe.Type.INSTANCE, 10,
                (i) -> i >= INPUT_SLOTS[0] && i <= INPUT_SLOTS[INPUT_SLOTS.length - 1], (i) -> i == OUTPUT_SLOT, BUCKET_SLOT);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }
        super.tick(level, blockPos, blockState);
        level.setBlock(blockPos, blockState.setValue(SoulInfuserBlock.LIT, this.isLit()), 2);
        setChanged(level, blockPos, blockState);
    }

    @Override
    protected boolean canProcessRecipe(CompactingRecipe recipe) {
        ItemStack outputStack = this.getStackInSlot(OUTPUT_SLOT);
        if (outputStack.getCount() >= outputStack.getMaxStackSize()) return false;
        if (!this.hasEnoughFuel(recipe.getRecipeCost())) return false;
        ItemStack result = recipe.getResultItem(this.level.registryAccess());
        if(result.getItem() != outputStack.getItem()) return false;
        int itemSum = outputStack.getCount() + result.getCount();
        return itemSum <= outputStack.getMaxStackSize();
    }

    @Override
    protected void onProcessRecipe(CompactingRecipe recipe) {
        this.progress++;
        if (this.progress >= this.maxProgress) {
            this.produceOutput(recipe);
            this.resetProgress();
        }
    }

    @Override
    protected void produceOutput(CompactingRecipe recipe) {
        ItemStack result = recipe.assemble(this.getInventory(), level.registryAccess());
        ItemStack outputStack = this.getItemHandler().getStackInSlot(OUTPUT_SLOT);
        this.forEachInputStack((itemStack -> itemStack.shrink(1)));
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
    protected Optional<CompactingRecipe> getRecipe() {
        SimpleContainer container = new SimpleContainer(8);
        this.forEachInputStack((container::addItem));
        return this.quickCheck().getRecipeFor(container, this.level);
    }

    private boolean isLit() {
        return this.progress > 0;
    }

    public void forEachInputStack(Consumer<ItemStack> consumer) {
        Arrays.stream(INPUT_SLOTS).mapToObj(this::getStackInSlot).forEach(consumer);
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.telluriumsrandomstuff.soul_compactor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SoulCompactorMenu(id, inventory, this, this.containerData);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putInt("soul_compactor.progress", this.progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt("soul_compactor.progress");
    }

}
