package com.mikitellurium.telluriumsrandomstuff.common.content.block;

import com.mikitellurium.telluriumsrandomstuff.common.content.block.interaction.ModCauldronInteractions;
import com.mikitellurium.telluriumsrandomstuff.common.content.fluid.SoulLavaFluid;
import com.mikitellurium.telluriumsrandomstuff.common.recipe.SoulLavaTransmutationRecipe;
import com.mikitellurium.telluriumsrandomstuff.registry.ModBlocks;
import com.mikitellurium.telluriumsrandomstuff.registry.ModItems;
import com.mikitellurium.telluriumsrandomstuff.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SoulLavaCauldronBlock extends AbstractCauldronBlock {

    public SoulLavaCauldronBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.LAVA_CAULDRON), ModCauldronInteractions.SOUL_LAVA);
    }

    protected double getContentHeight(BlockState blockState) {
        return 0.9375D;
    }

    @Override
    public boolean isFull(BlockState blockState) {
        return true;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        if (this.isEntityInsideContent(blockState, pos, entity) &&
                !entity.getType().is(ModTags.EntityTypes.SOUL_LAVA_IMMUNE)) {
            if (entity instanceof LivingEntity livingEntity) {
                SoulLavaFluid.soulLavaHurt(livingEntity);
            } else if (entity instanceof ItemEntity item) {
                Optional<SoulLavaTransmutationRecipe> recipe = level.getRecipeManager().getRecipeFor(
                        SoulLavaTransmutationRecipe.Type.INSTANCE, new SimpleContainer(item.getItem()), level);
                if (recipe.isPresent()) {
                    level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                    level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockState));
                    level.playSound(null, pos, SoundEvents.SOUL_ESCAPE, SoundSource.BLOCKS, 1.0f, 1.2f);
                    this.convertItem(level, item, recipe.get().assemble(new SimpleContainer(item.getItem()), level.registryAccess()));
                } else {
                    entity.lavaHurt();
                }
            }
        }
    }

    private void convertItem(Level level, ItemEntity entity, ItemStack output) {
        output.setCount(entity.getItem().getCount());
        ItemEntity result = new ItemEntity(level, entity.getX(), entity.getY() - 0.4D, entity.getZ(), output);
        entity.remove(Entity.RemovalReason.DISCARDED);
        level.addFreshEntity(result);
        result.getDeltaMovement().add(0.0D, 0.02D, 0.0D);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return 5;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult target, BlockGetter level, BlockPos pos,
                                       Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }

}
